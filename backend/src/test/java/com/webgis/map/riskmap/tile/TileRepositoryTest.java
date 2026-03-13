package com.webgis.map.riskmap.tile;


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


    @Test
    void findByIdIsOKTest(){

        // Arrange
         TileId tileId1 = new TileId(1L,1,1,1);
         TileId tileId2 = new TileId(2L,2,2,2);
         byte[] byteArray1 = {62};
         byte[] byteArray2 = {32};

         Tile tile1 = new Tile(tileId1, byteArray1);
         Tile tile2 = new Tile(tileId2, byteArray2);

        // Act
        tileRepository.save(tile1);
        tileRepository.save(tile2);

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

        // Arrange
        TileId tileId1 = new TileId(1L,1,1,1);
        TileId tileId2 = new TileId(2L,2,2,2);
        byte[] byteArray1 = {62};
        byte[] byteArray2 = {32};

        Tile tile1 = new Tile(tileId1, byteArray1);
        Tile tile2 = new Tile(tileId2, byteArray2);

        // Act
        tileRepository.save(tile1);

        Optional<Tile> result1 = tileRepository.findByTileId(tile1.getTileId());
        Optional<Tile> result2 = tileRepository.findByTileId(tileId2);

        // Assert
        assertNotNull(result1);

        assertTrue(result1.isPresent());
        assertTrue(result2.isEmpty());

        assertMapEquals(tile1, result1.get());
    }
}
