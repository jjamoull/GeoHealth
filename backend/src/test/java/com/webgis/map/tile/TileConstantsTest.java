package com.webgis.map.tile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileConstantsTest {

    @Test
    void getBlocksPerTile_isOK_test(){
        // Arrange && Act
        int result = TileConstants.getBlocksPerTile();

        // Arrange
        assertEquals(16, result);
    }

    @Test
    void getTileSize_isOK_test(){
        // Arrange && Act
        int result = TileConstants.getTileSize();

        // Arrange
        assertEquals(256, result);
    }

    @Test
    void getBlockSize_isOK_test(){
        // Arrange && Act
        int result = TileConstants.getBlockSize();

        // Arrange
        assertEquals(16, result);
    }
}
