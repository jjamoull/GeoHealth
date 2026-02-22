package com.Converter;

import com.webgis.map.Map;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility class responsible for extracting ZIP archives and compressing files
 * stored with byte[] in DB
 *
 * Able to manage Zip Slip Attacks thanks to method newFile
 */
public class ZipFiles {

    /**
     * Extract zip archives stored in DB
     *
     * @param map : MapComponent entity that contains all information about the map
     * @param destFilePath : path where the extracted files should be placed
     * @throws IOException if there is an issue with extraction or folder creation
     */
    public void unzip(Map map, File destFilePath) throws IOException {
        final byte[] zipFile = map.getZipFile();

        if (!destFilePath.exists() && !destFilePath.mkdirs()){throw new IOException("unzipped folder wasn't created");}

        final byte[] buffer = new byte[1024];

        try(ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipFile))){
            ZipEntry zipEntry = zis.getNextEntry();

            // iterate through files and folders (entries) in zipfile
            while (zipEntry != null){
                unzipOneEntry(zipEntry, buffer, destFilePath, zis);
                zipEntry = zis.getNextEntry();
            }
        }
        catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * unzip the given zip entry and writes a new file or folder depending on its nature
     *
     * @param zipEntry : zip entry to unzip
     * @param buffer : byte array to store each segment of the entry (if file)
     * @param destFilePath : path where the extracted files should be placed
     * @param zis : ZipInputStream to read the current entry
     * @throws IOException if there is an issue in creation of a directory
     * */
    private void unzipOneEntry(ZipEntry zipEntry,
                              byte[] buffer,
                              File destFilePath,
                              ZipInputStream zis) throws IOException {

        final File newFile = newFile(destFilePath, zipEntry);

        if (zipEntry.isDirectory()){
            if (!newFile.isDirectory() && !newFile.mkdirs()) {
                throw new IOException("Unable to create directory " + newFile);
            }
        } else {
            final File parent = newFile.getParentFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
                throw new IOException("Unable to create parent folder" + parent);
            }

            try (FileOutputStream fos = new FileOutputStream(newFile)) {
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
            } catch(Exception e) {
                throw new IOException();
            }
        }
    }

    /**
     * Create a new file to increase security and avoid Zip Slip Attacks
     *
     * @param destinationDir : Target directory
     * @param zipEntry : zip entry to unzip
     * @return a new secure File which avoid Zip Slip Attacks
     * @throws IOException if the entry is outside the target directory
     * */
    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        final File destFile = new File(destinationDir, zipEntry.getName());

        final String destDirPath = destinationDir.getCanonicalPath();
        final String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }




    /**
     *
     */
    public void zip(){ /* TODO document why this method is empty */ }


}
