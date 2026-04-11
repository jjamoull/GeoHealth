package com.webgis.map.Service;

import com.webgis.map.service.TransformTifFiles;
import com.webgis.map.tile.TileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransformTifFilesTests {
    /*@Mock
    private TileService tileService;

    @InjectMocks
    private TransformTifFiles transformTifFiles;


    private MultipartFile tifFile;
    Path path = Path.of("src/test/resources/fileTest/mosquito.tif");


    @BeforeEach
    void setup() throws IOException {
        tifFile = new MockMultipartFile(
                "file",
                "test.tif",
                "text/tiff",
                Files.readAllBytes(path)
        );
    }

    @Test
    void transformIntoTileFile_isOK_test(){

        // Arrange && Act
        transformTifFiles.transformIntoTileFile(1L, tifFile);

        // Assert
        verify(tileService, atLeastOnce())
                .save(
                        eq(1L),
                        anyInt(),   // zoom
                        anyInt(),   // x
                        anyInt(),   // y
                        any(byte[].class)
                );
    }

    @Test
    void transformTifFile_isOK_test() throws IOException {
        // Arrange && Act
        Path pathResult = transformTifFiles.transformTifFile(tifFile);

        // Assert
        assertNotNull(pathResult);
        assertTrue(Files.exists(pathResult));
    }

    @Test
    void transformTifFile_isKOWithBadMutlipartFile_test() throws IOException {
        // Arrange
        tifFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                Files.readAllBytes(path)
        );
        // Act && Assert
        assertThrows(IOException.class, ()-> {
            transformTifFiles.transformTifFile(tifFile);
        });
    }*/


}
