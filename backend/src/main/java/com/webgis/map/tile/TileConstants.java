package com.webgis.map.tile;

public class TileConstants {
    private static final int TILE_SIZE = 256;
    private static final int BLOCK_SIZE = 16;
    private static final int BLOCKS_PER_TILE = TILE_SIZE / BLOCK_SIZE;


    public TileConstants(){}

    public static int getBlocksPerTile() {
        return BLOCKS_PER_TILE;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getBlockSize() {
        return BLOCK_SIZE;
    }
}
