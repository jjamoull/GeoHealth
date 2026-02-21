package com.Converter;



import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;

/**
 * @overview This class allow to detect a type of file ".cpg, .dbf, .prj, .shp, .shx" which be used
 * by method "transformShapeFileToGeoJsonFile(…)" in public Class : Converter/ShapeFileToGeoJsonFile
 * */
public class DetectFiles {

    static Logger logger = LoggerFactory.getLogger(DetectFiles.class);


    /**
     * Find the shp file among the set of files receive in params
     *
     * @param fileInParam : set of file to analyze
     * @return a shp file : if a shp file is detected among the set of files
     *         null : otherwise
     * */
    public static File findShpFile(File fileInParam ) throws IOException{
        for (File file:fileInParam.listFiles()){
            byte[] byteOfFile = Files.readAllBytes(file.toPath());
            if (detectShpFile(byteOfFile) == true){
                logger.info("A .shp file is detected and returned");
                return file;
            }
        }
        logger.info("No .shp file detected and returned");
        return null;
    }

    /**
     * Detect if the file in byte array is a shp file or not
     *
     * @param byteOfFile : a byte array of the file we search to detect if it is a .shp file
     * @return true  : if it is a .shp file
     *         false : otherwise
     * */
    public static boolean detectShpFile(byte[] byteOfFile) {
        return byteOfFile[0] == (byte) 0x00 &&
                byteOfFile[1] == (byte) 0x00 &&
                byteOfFile[2] == (byte) 0x27 &&
                byteOfFile[3] == (byte) 0x0A;
    }

}
