package com.webgis.map.tile;

import com.webgis.exception.CanDecompress;
import com.webgis.map.raster.RasterMapRepository;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TileServiceTest {

    private TileService tileService = new TileService(null, null);


    private int x = 10;
    private int y = 10;
    private int z = 10;


    @Test
    void getMeanIndex_isOK_test(){
        // Arrange & Act
        int result = tileService.getMeanIndex(x,y);
        // Assert
        assertNotNull(result);
        assertEquals(170, result);
    }

    @Test
    void getMeanIndex_isKOWithParametersOutOfRange_test(){
        // Arrange && Act
        int resultWithNegative = tileService.getMeanIndex(x,(-y));
        int resultWithBlocSize = tileService.getMeanIndex(x, y + tileService.blockSize);
        // Assert
        assertEquals(-1, resultWithNegative);
        assertEquals(-1, resultWithBlocSize);
    }

    @Test
    void getTileCoordinates_isOK_test(){
        // Arrange & Act
        int[] result = tileService.getTileCoordinates(z,x,y);

        // Assert
        assertNotNull(result);
        assertEquals(540, result[0]);
        assertEquals(483,result[1] );
    }


    @Test
    void getTileCoordinates_isOKWithHighParameters_test(){
        // Arrange & Act
        int[] result = tileService.getTileCoordinates(z,100000,10000000);

        // Assert
        assertNotNull(result);
    }

    @Test
    void getBlockCoordinates_isOK_test(){
        // Arrange & Act
        int[] result = tileService.getBlockCoordinates(z,x,y);

        // Assert
        assertNotNull(result);
        assertEquals(7, result[0]);
        assertEquals(6,result[1] );
    }


    @Test
    void getBlockCoordinates_isOKWithHighParameters_test(){
        // Arrange & Act
        int[] result = tileService.getBlockCoordinates(z,100000,10000000);

        // Assert
        assertNotNull(result);
    }

    @Test
    void getTileMeanBlock_isOKWithAllBlocksEqual10_test(){
        // Arrange
        byte[] tileData = new byte[256 * 256];

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                tileData[y * 256 + x] = (byte) 10;
            }
        }

        // Act
        float resultAt_0_0 = tileService.getTileMeanBlock(tileData, new int[]{0,0});
        float resultAt_16_16 = tileService.getTileMeanBlock(tileData, new int[]{0,0});

        // Assert
        assertEquals( ((float) 10 /255), resultAt_0_0);
        assertEquals( ((float) 10 /255), resultAt_16_16);
    }

    @Test
    void _isOK_test() throws IOException {

        // Arrange
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int value = 120;
                img.getRaster().setSample(x, y, 0, value);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        byte[] pngBytes = baos.toByteArray();

        // Act
        byte[] result = tileService.decompressPNGFile(pngBytes);

        // Assert
        assertNotNull(result);
        assertEquals(16 * 16, result.length);
        assertEquals(120, result[0] & 0xFF);
    }

    @Test
    void decompressPNG_isKO_shouldThrowException_onInvalidData() {
        byte[] badArray = {1,1,1,1,2,2,2,2};

        assertThrows(CanDecompress.class, () -> {
            tileService.decompressPNGFile(badArray);
        });
    }

}
