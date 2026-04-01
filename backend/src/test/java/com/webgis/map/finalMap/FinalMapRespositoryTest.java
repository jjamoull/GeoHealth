package com.webgis.map.finalMap;


import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FinalMapRespositoryTest {

    @Autowired
    private FinalMapRepository finalMapRepository;

    private FinalMap finalMap;

    @BeforeEach
    void init(){
        //Arrange
        byte[] dataZip = new byte[]{66};

        finalMap = new FinalMap("title",
                "risk map",
                dataZip,
                "file");
    }

    private void assertMapEquals(FinalMap actual, FinalMap expected) {
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getZipFile()).containsExactly(expected.getZipFile());
        assertThat(actual.getFileGeoJson()).isEqualTo(expected.getFileGeoJson());
    }

    @Test
    void SaveAndfindByTitleMapExist(){
        //Act
        finalMapRepository.save(finalMap);
        Optional<FinalMap> found = finalMapRepository.findByTitle(finalMap.getTitle());

        //Assert
        assertThat(found).isPresent();
        assertMapEquals(found.get(), finalMap);
    }

    @Test
    void SaveAndfindByTitleMapNotExist(){
        //Act
        finalMapRepository.save(finalMap);
        Optional<FinalMap> found = finalMapRepository.findByTitle("otherTitle");

        //Assert
        assertThat(found).isEmpty();
    }

    @Test
    void SaveAndfindByIdMapExist(){
        //Act
        finalMapRepository.save(finalMap);
        Optional<FinalMap> found = finalMapRepository.findById(finalMap.getId());

        //Assert
        assertThat(found).isPresent();
        assertMapEquals(found.get(), finalMap);
    }

    @Test
    void SaveAndfindByIdMapNotExist(){
        //Act
        finalMapRepository.save(finalMap);
        Optional<FinalMap> found = finalMapRepository.findById(88);

        //Assert
        assertThat(found).isEmpty();
    }



}
