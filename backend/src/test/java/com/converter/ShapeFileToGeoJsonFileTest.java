package com.converter;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeFileToGeoJsonFileTest {


    @Test
    void shouldConvertValidShapefile() throws Exception {
        File file = new File("src/test/resources/fileTest/Risk_categories_by_division_comb_dry.shp");

        String result = ShapeFileToGeoJsonFile.transformShapeFileToGeoJsonFile(file);

        assertNotNull(result);
        assertTrue(result.contains("FeatureCollection"));
    }

    @Test
    void shouldThrowExceptionWhenCRSMissing() {
        File file = new File("src/test/resources/fileTest/no_valid.shp");

        assertThrows(Exception.class, () -> {
            ShapeFileToGeoJsonFile.transformShapeFileToGeoJsonFile(file);
        });
    }
}
