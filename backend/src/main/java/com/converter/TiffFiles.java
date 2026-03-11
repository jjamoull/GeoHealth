package com.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class TiffFiles {
    static Logger logger = LoggerFactory.getLogger(TiffFiles.class);

    private Path tempFile;
    private Path temp8bit;
    private Path outputDir;

    /* Represent the line of command to transform tif file */
    private String[] transformIntoXYZFile;

    /* Represent the line of command to
        - translate tif file in 16 or 32 bits into 8 bits
        - and manage NaN data in files
    */
    private String[] convertTo8bitCmd;

    /**
     * Constructor that init global variable
     *
     * @param tifFile : byte array for tif file
     * @throws IOException : if there is an issue with the creation of the temporary file
     * */
    public TiffFiles( byte[] tifFile){
        try{
            this.tempFile = Files.createTempFile("files_uploaded_", ".tif");
            this.temp8bit = Files.createTempFile("files_uploaded_8bit_", ".vrt");
            Files.write(tempFile, tifFile);

            //create the folder/directory where the result will be stocked
            outputDir = Paths.get("tiles", UUID.randomUUID().toString());
            Files.createDirectories(outputDir);

            this.convertTo8bitCmd = new String[] {
                    "gdal_translate",
                    "-of", "VRT",
                    "-ot", "Byte",
                    "-scale",
                    "-a_nodata", "0",
                    tempFile.toAbsolutePath().toString(),
                    temp8bit.toAbsolutePath().toString()
            };
            this.runProcessFor8Bits(this.convertTo8bitCmd, "runProcessFor8Bits");


            this.transformIntoXYZFile = new String[]{
                    "gdal2tiles.py", // the command
                    "--xyz", // format to be used by leaflet on OSM
                    "-z",
                    "0-10", // represent zoom from 0 to 10 on Z (<-> "-z")
                    temp8bit.toAbsolutePath().toString(), // file in 8 bit from
                    outputDir.toAbsolutePath().toString() // Destination of the file
            };


        } catch (IOException | InterruptedException e) {
            logger.info("The temporary file for .tif file can not be created : \n");
        }
    }

    /**
     * Execute lines of commands to transform tif files into XYZ files
     *
     * @throws IOException : if there is a problem with execution of pb.start();
     * @throws InterruptedException : if there is a problem with execution of process.waitFor();
     * */
    private void createAndRunProcess() {
        try {
            final ProcessBuilder pb = new ProcessBuilder(this.transformIntoXYZFile);
            pb.redirectErrorStream(true); // combine stdout et stderr
            final Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info("[gdal2tiles] " + line);
                }
            }

            final int exitCode = process.waitFor();
            if (exitCode != 0) {
                logger.info("gdal2tiles exited with code {}", exitCode);
            }

        } catch (IOException | InterruptedException e) {
            logger.info("Failed to run gdal2tiles.py");
        }
    }

    /**
     * Run the method to transform tif file and return his output directory
     *
     * @return the path of the directory where results are stored
     * @throws IOException if there is an issue with the directory
     * */
    public Path executeTransformationCommand() throws IOException {
        createAndRunProcess();

        if (this.outputDir != null){
            return this.outputDir;
        }else {
            throw new IOException("The tif is not transformed into a set of tile files after execution.");
        }
    }


    /**
     * Execute the process to transform tif file in 16 or 32 bits into 8 bits to be interpreted by the command
     *
     * @param command : execute the line of command to transform the tif file into a set of 8 bits file
     * @param logPrefix : String to recognize the process thanks to his prefix
     * @throws IOException : if there is a problem with execution of pb.start() (E.g. Command that doesn't exist) ;
     * @throws InterruptedException : if there is a problem with execution of process.waitFor();
     *  */
    public static void runProcessFor8Bits(String[] command, String logPrefix) throws IOException, InterruptedException {
        final ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        final Process process = pb.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("{} {}", logPrefix, line);
            }
        }
        final int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException(logPrefix + " exited with code " + exitCode);
        }
    }


}
