package com.webgis.map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapEntityTest {

    @Test
    void MapGetterAndSetterTest(){
        //Arrange
        Map map= new Map("title",
                "risk map",
                "dataZip".getBytes(),
                "dataGeoJson".getBytes());
        //Act
        map.setTitle("newTitle");
        map.setDescription("newDescription");
        map.setZipFile("newDataZip".getBytes());
        map.setFileGeoJson("newGeoJson".getBytes());

        //Assert
        assertThat(map.getTitle()).isEqualTo("newTitle");
        assertThat(map.getDescription()).isEqualTo("newDescription");
        assertThat(map.getZipFile()).isEqualTo("newDataZip".getBytes());
        assertThat(map.getFileGeoJson()).isEqualTo("newGeoJson".getBytes());
    }
}
