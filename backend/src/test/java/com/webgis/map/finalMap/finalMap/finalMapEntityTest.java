package com.webgis.map.finalMap.finalMap;

import com.webgis.map.finalmap.FinalMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class finalMapEntityTest {

    @Test
    void MapGetterAndSetterTest(){
        //Arrange
        byte[] dataZip ={66};
        byte[] newDataZip ="newDataZip".getBytes();


        FinalMap finalMap = new FinalMap("title",
                "risk map",
                dataZip,
                "file");


        //Act
        finalMap.setTitle("newTitle");
        finalMap.setDescription("newDescription");
        finalMap.setZipFile(newDataZip);
        finalMap.setFileGeoJson("newFile");

        //Assert
        assertThat(finalMap.getTitle()).isEqualTo("newTitle");
        assertThat(finalMap.getDescription()).isEqualTo("newDescription");
        assertThat(finalMap.getZipFile()).isEqualTo(newDataZip);
        assertThat(finalMap.getFileGeoJson()).isEqualTo("newFile");
    }
}
