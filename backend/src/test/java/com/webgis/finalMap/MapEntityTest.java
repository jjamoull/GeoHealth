package com.webgis.finalMap;

import com.webgis.map.finalmap.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MapEntityTest {

    @Test
    void MapGetterAndSetterTest(){
        //Arrange
        byte[] dataZip ={66};
        byte[] newDataZip ="newDataZip".getBytes();


        Map map= new Map("title",
                "risk map",
                dataZip,
                "file");


        //Act
        map.setTitle("newTitle");
        map.setDescription("newDescription");
        map.setZipFile(newDataZip);
        map.setFileGeoJson("newFile");

        //Assert
        assertThat(map.getTitle()).isEqualTo("newTitle");
        assertThat(map.getDescription()).isEqualTo("newDescription");
        assertThat(map.getZipFile()).isEqualTo(newDataZip);
        assertThat(map.getFileGeoJson()).isEqualTo("newFile");
    }
}
