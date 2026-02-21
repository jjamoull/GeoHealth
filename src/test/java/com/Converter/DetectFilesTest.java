package com.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DetectFilesTest {

    @TempDir
    Path tempDir;

    
    @Test
    void shouldDetectAShpFile() throws IOException {
        ClassPathResource resource = new ClassPathResource("fileTest/shpFileTest.shp");
        File file = resource.getFile();
        byte[] byteOfFile = Files.readAllBytes(file.toPath());

        boolean fileDetected = DetectFiles.detectShpFile(byteOfFile);
        assertTrue(fileDetected);
    }


    @Test
    void findShpFileTest() throws IOException{
        Files.write(
                tempDir.resolve("test.txt"),
                "False txt file".getBytes()
        );
        Files.write(
                tempDir.resolve("test.dbf"),
                new byte[]{0x01, 0x02, 0x03}
        );
        Files.write(
                tempDir.resolve("test.shp"),
                new byte[]{
                        0x00, 0x00, 0x27, 0x0A,
                        0x00, 0x00, 0x00, 0x00
                }
        );

        File result = DetectFiles.findShpFile(tempDir.toFile());

        assertNotNull(result);
        assertEquals(result.getName(), "test.shp");
    }

    @Test
    void NotSupposeToFindShpFileTest() throws IOException{
        Files.write(
                tempDir.resolve("test.txt"),
                "False txt file".getBytes()
        );
        Files.write(
                tempDir.resolve("test.dbf"),
                new byte[]{0x01, 0x02, 0x03}
        );

        File result = DetectFiles.findShpFile(tempDir.toFile());

        assertNull(result);
    }
}
