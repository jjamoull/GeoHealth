package com.webgis.map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapEntityTest {

    @Test
    void MapGetterAndSetterTest(){
        //Arrange
        byte[] dataZip ={66};
        byte[] dataGeoJson ={12};

        byte[] newDataZip ="newDataZip".getBytes();
        byte[] newDataGeoJson ="newDataGeoJson".getBytes();


        Map map= new Map("title",
                "risk map",
                dataZip,
                dataGeoJson);


        //Act
        map.setTitle("newTitle");
        map.setDescription("newDescription");
        map.setZipFile(newDataZip);
        map.setFileGeoJson(newDataGeoJson);

        //Assert
        assertThat(map.getTitle()).isEqualTo("newTitle");
        assertThat(map.getDescription()).isEqualTo("newDescription");
        assertThat(map.getZipFile()).isEqualTo(newDataZip);
        assertThat(map.getFileGeoJson()).isEqualTo(newDataGeoJson);
    }
}
