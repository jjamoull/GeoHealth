package com.webgis.transformationFiles;

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
     * @param map : Map entity that contains all information about the map
     * @param urlInDB : target directory in DB where files will be extracted
     * @throws RuntimeException if there is an issue with extraction
     */
    public void unzip(Map map, File urlInDB){
        byte[] zipFile = map.getZipFile();

        if (urlInDB.exists() == false){urlInDB.mkdirs();}

        byte[] buffer = new byte[1024];

        try(ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipFile))){
            ZipEntry zipEntry = zis.getNextEntry();

            // iterate through files and folders (entries) in zipfile
            while (zipEntry != null){
                unzipOneEntry(zipEntry, buffer, urlInDB, zis);
                zipEntry = zis.getNextEntry();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * unzip the given zip entry and writes a new file or folder depending on its nature
     *
     * @param zipEntry : zip entry to unzip
     * @param buffer : byte array to store each segment of the entry (if file)
     * @param urlInDB : url of the zipfile
     * @param zis : ZipInputStream to read the current entry
     * @throws IOException if there is an issue in creation of a directory
     * */
    private void unzipOneEntry(ZipEntry zipEntry,
                              byte[] buffer,
                              File urlInDB,
                              ZipInputStream zis) throws IOException {

        File newFile = newFile(urlInDB, zipEntry);

        if (zipEntry.isDirectory()){
            if (!newFile.isDirectory() && !newFile.mkdirs()) {
                throw new IOException("Unable to create directory " + newFile);
            }
        } else {
            File parent = newFile.getParentFile();
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
     * @param destinationDir : Traget directory
     * @param zipEntry : zip entry to unzip
     * @return a new secure File which avoid Zip Slip Attacks
     * @throws IOException if the entry is outside the target directory
     * */
    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

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
