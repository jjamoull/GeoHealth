package com.converter;

import com.converter.ZipFiles;
import com.webgis.map.finalmap.FinalMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ZipFilesTest {

    @TempDir
    File tempDir;

    // Create a temporary zip files to be tested
    private byte[] createZip(String fileName, String content) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        ZipEntry entry = new ZipEntry(fileName);
        zos.putNextEntry(entry);
        zos.write(content.getBytes());
        zos.closeEntry();
        zos.close();

        return baos.toByteArray();
    }

    @Test
    void shouldUnzipFileCorrectly() throws Exception {
        byte[] zip = createZip("test.txt", "hello");

        FinalMap map = Mockito.mock(FinalMap.class);
        when(map.getZipFile()).thenReturn(zip);

        ZipFiles zipFiles = new ZipFiles();
        zipFiles.unzip(map, tempDir);

        File extracted = new File(tempDir, "test.txt");

        assertTrue(extracted.exists());
        String content = Files.readString(extracted.toPath());
        assertEquals("hello", content);
    }

    @Test
    void shouldCreateDirectoryIfNotExists() throws Exception {
        byte[] zip = createZip("file.txt", "data");

        FinalMap map = Mockito.mock(FinalMap.class);
        when(map.getZipFile()).thenReturn(zip);

        File newDir = new File(tempDir, "newFolder");

        ZipFiles zipFiles = new ZipFiles();
        zipFiles.unzip(map, newDir);

        assertTrue(newDir.exists());
    }

    @Test
    void shouldThrowExceptionOnZipSlipAttack() {
        byte[] zip;

        try {
            zip = createZip("../evil.txt", "hack");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FinalMap map = Mockito.mock(FinalMap.class);
        when(map.getZipFile()).thenReturn(zip);

        ZipFiles zipFiles = new ZipFiles();

        assertThrows(IOException.class, () -> {
            zipFiles.unzip(map, tempDir);
        });
    }

    @Test
    void shouldThrowExceptionWhenDirectoryCannotBeCreated() {
        FinalMap map = Mockito.mock(FinalMap.class);
        when(map.getZipFile()).thenReturn(new byte[]{});

        File invalidDir = new File("/root/forbidden"); // souvent interdit

        ZipFiles zipFiles = new ZipFiles();

        assertThrows(IOException.class, () -> {
            zipFiles.unzip(map, invalidDir);
        });
    }

    @Test
    void shouldUnzipDirectoryEntry() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        zos.putNextEntry(new ZipEntry("folder/"));
        zos.closeEntry();
        zos.close();

        FinalMap map = Mockito.mock(FinalMap.class);
        when(map.getZipFile()).thenReturn(baos.toByteArray());

        ZipFiles zipFiles = new ZipFiles();
        zipFiles.unzip(map, tempDir);

        File folder = new File(tempDir, "folder");

        assertTrue(folder.exists());
        assertTrue(folder.isDirectory());
    }
}