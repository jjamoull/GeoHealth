package com.webgis.map.raster;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RasterMapRepositoryTest {

    @Autowired
    private RasterMapRepository riskFactorMapRepository;

    private void assertMapEquals(RasterMap actual, RasterMap expected) {
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    private RasterMap riskFactorMap1 = new RasterMap("Title1", "Description1");
    private RasterMap riskFactorMap2 = new RasterMap("Title2", "Description2");

    @Test
    void findByIdIsOKTest(){

        // Arrange && Act
        riskFactorMapRepository.save(riskFactorMap1);
        riskFactorMapRepository.save(riskFactorMap2);

        Optional<RasterMap> result1 = riskFactorMapRepository.findById(riskFactorMap1.getId());
        Optional<RasterMap> result2 = riskFactorMapRepository.findById(riskFactorMap2.getId());

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());

        assertMapEquals(riskFactorMap1, result1.get());
        assertMapEquals(riskFactorMap2, result2.get());
    }


    @Test
    void notFindByIdIsOKTest(){

        // Arrange && Act
        riskFactorMapRepository.save(riskFactorMap1);

        Optional<RasterMap> result1 = riskFactorMapRepository.findById(riskFactorMap1.getId());
        Optional<RasterMap> result2 = riskFactorMapRepository.findById(2);

        // Assert
        assertNotNull(result1);

        assertThrows(NoSuchElementException.class,
                result2::get);

        assertTrue(result1.isPresent());
        assertTrue(result2.isEmpty());

        assertMapEquals(riskFactorMap1, result1.get());
    }
}
