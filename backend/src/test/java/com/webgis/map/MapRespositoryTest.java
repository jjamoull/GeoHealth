package com.webgis.map;

import com.webgis.user.User;
import com.webgis.user.UserRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MapRespositoryTest {

    @Autowired
    private MapRepository mapRepository;

    private void assertMapEquals(Map actual, Map expected) {
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getZipFile()).containsExactly(expected.getZipFile());;
        assertThat(actual.getFileGeoJson()).containsExactly(expected.getFileGeoJson());;
    }

    @Test
    void SaveAndfindByTitleMapExist(){
        //Arrange
        Map map= new Map("title",
                "risk map",
                "dataZip".getBytes(),
                "dataGeoJson".getBytes());

        //Act
        mapRepository.save(map);
        Optional<Map> found = mapRepository.findByTitle(map.getTitle());

        //Assert
        assertThat(found).isPresent();
        assertMapEquals(found.get(),map);
    }

    @Test
    void SaveAndfindByTitleMapNotExist(){
        //Arrange
        Map map= new Map("title",
                "risk map",
                "dataZip".getBytes(),
                "dataGeoJson".getBytes());

        //Act
        mapRepository.save(map);
        Optional<Map> found = mapRepository.findByTitle("otherTitle");

        //Assert
        assertThat(found).isEmpty();
    }

    @Test
    void SaveAndfindByIdMapExist(){
        //Arrange
        Map map= new Map("title",
                "risk map",
                "dataZip".getBytes(),
                "dataGeoJson".getBytes());

        //Act
        mapRepository.save(map);
        Optional<Map> found = mapRepository.findById(map.getId());

        //Assert
        assertThat(found).isPresent();
        assertMapEquals(found.get(),map);
    }

    @Test
    void SaveAndfindByIdMapNotExist(){
        //Arrange
        Map map= new Map("title",
                "risk map",
                "dataZip".getBytes(),
                "dataGeoJson".getBytes());

        //Act
        mapRepository.save(map);
        Optional<Map> found = mapRepository.findById(88);

        //Assert
        assertThat(found).isEmpty();
    }



}
