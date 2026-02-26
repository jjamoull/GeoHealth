package com.Converter;


import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;

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
     * */
    public static String transformShapeFileToGeoJsonFile(File shpFile) throws IOException {
        System.out.println("6");

        ShapefileDataStore dataStore = new ShapefileDataStore(shpFile.toURI().toURL());
        dataStore.setCharset(StandardCharsets.UTF_8);

        String typeName = dataStore.getTypeNames()[0];

        SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
        System.out.println("7");

        SimpleFeatureCollection collection = featureSource.getFeatures();

        FeatureJSON featureJSON = new FeatureJSON();
        StringWriter writer = new StringWriter();
        System.out.println("8");

        featureJSON.writeFeatureCollection(collection, writer);

        dataStore.dispose();
        System.out.println("9");
        System.out.println(writer.toString());
        return writer.toString();
    }
}
