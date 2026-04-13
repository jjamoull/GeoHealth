package com.converter;


import org.geotools.api.data.SimpleFeatureSource;

import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.store.ReprojectingFeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.referencing.CRS;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @overview This class allow thanks to the method transformShapeFileToGeoJsonFile(…) to transform
 * a shapefile consisting of files ".cpg, .dbf, .prj, .shp, .shx" to be transformed into a GeoJson file.
 * The GeoJson file will be used to display data on the map
 * */
public class ShapeFileToGeoJsonFile {



    /**
     * Converts given shapefiles into geoJSON format (String)
     *
     * @param shpFile : represents shp file used to detect others files into
     *                shape file to converted it into geojson file
     * @return a string geoJSON format
     * @throws IOException if there is a problem with at least one of these methods :
     *  - toURL()
     *  - getTypeNames()
     *  - getFeatureSource()
     *  - writeFeatureCollection()
     *
     *  This method used a lot of predefined methods from library : GeoTools
     * */
    public static String transformShapeFileToGeoJsonFile(File shpFile) throws IOException, FactoryException {
        final ShapefileDataStore dataStore = new ShapefileDataStore(shpFile.toURI().toURL());
        dataStore.setCharset(StandardCharsets.UTF_8);

        final String typeName = dataStore.getTypeNames()[0];

        final SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);

        final SimpleFeatureCollection collection = featureSource.getFeatures();

        final CoordinateReferenceSystem sourceCRS = featureSource.getSchema().getCoordinateReferenceSystem();

        if (sourceCRS == null) {
            throw new IllegalArgumentException("Missing CRS in shapefile (.prj absent)");
        }

        final CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326", true);

        final SimpleFeatureCollection reprojected = new ReprojectingFeatureCollection(collection, targetCRS);

        final FeatureJSON featureJSON = new FeatureJSON();
        final StringWriter writer = new StringWriter();

        featureJSON.writeFeatureCollection(reprojected, writer);

        dataStore.dispose();
        return writer.toString();
    }
}
