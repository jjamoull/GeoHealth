package com.webgis.map.riskmap.tile;


import com.webgis.map.riskmap.riskfactormap.RiskFactorMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TileRepositoryTest {
    @Autowired
    private TileRepository tileRepository;

    private void assertMapEquals(Tile actual, Tile expected) {
        assertThat(actual.getTileId()).isEqualTo(expected.getTileId());
        assertThat(actual.getTileData()).containsExactly(expected.getTileData());    }

    private TileId tileId2;
    private TileId tileId3;
    private Tile tile1;
    private Tile tile2;


    @BeforeEach
    void init(){
        // Arrange
        RiskFactorMap riskFactorMap = new RiskFactorMap("Title1", "Description1");
        RiskFactorMap riskFactorMap2 = new RiskFactorMap("Title2", "Description2");
        TileId tileId1 = new TileId(1L,1,1,1);
        tileId2 = new TileId(2L,2,2,2);
        tileId3 = new TileId(3L,3,3,3);
        byte[] byteArray1 = {62};
        byte[] byteArray2 = {32};

        tile1 = new Tile(tileId1, byteArray1, riskFactorMap);
        tile2 = new Tile(tileId2, byteArray2, riskFactorMap2);
    }


    @Test
    void findByIdIsOKTest(){


        // Act
        tile1 = tileRepository.save(tile1);
        tile2 = tileRepository.save(tile2);

        Optional<Tile> result1 = tileRepository.findByTileId(tile1.getTileId());
        Optional<Tile> result2 = tileRepository.findByTileId(tile2.getTileId());

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());

        assertMapEquals(tile1, result1.get());
        assertMapEquals(tile2, result2.get());
    }


    @Test
    void notFindByIdIsOKTest(){

        // Act
        tile1 = tileRepository.save(tile1);

        Optional<Tile> result1 = tileRepository.findByTileId(tile1.getTileId());
        Optional<Tile> result2 = tileRepository.findByTileId(tileId3);


        // Assert
        assertNotNull(result1);

        assertTrue(result2.isEmpty());

        assertMapEquals(tile1, result1.get());
    }
}
