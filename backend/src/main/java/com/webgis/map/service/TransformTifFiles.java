package com.webgis.map.service;

import com.converter.TiffFiles;
import com.webgis.map.finalrastermap.FinalRasterMapRepository;
import com.webgis.map.raster.RasterMapRepository;
import com.webgis.map.tile.TileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TransformTifFiles {
    static Logger logger = LoggerFactory.getLogger(TransformTifFiles.class);


    private final RasterMapRepository riskFactorMapRepository;
    private final TileService tileService;
    private final FinalRasterMapRepository finalRasterMapRepository;


    public TransformTifFiles (
            RasterMapRepository riskFactorMapRepository,
            TileService tileService,
            FinalRasterMapRepository finalRasterMapRepository){
        this.riskFactorMapRepository = riskFactorMapRepository;
        this.tileService= tileService;
        this.finalRasterMapRepository= finalRasterMapRepository;

    }

    /**
     * Main method to transform tif files into tiles and stored them into the DB
     *
     * @param mapId : the id of the risk factor map that the result will be linked
     * @param tifFile : the fil file to transform
     * */
    public void transformIntoTileFile(long mapId, MultipartFile tifFile){
        Path tiles = null;
        try {
            tiles = transformTifFile(tifFile);

            try (Stream<Path> stream = Files.walk(tiles)) {
                final List<Path> tileFiles = stream.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".png"))
                        .toList();

                tileFiles.forEach(path -> {
                    try {
                        final byte[] data = Files.readAllBytes(path);

                        final int y = Integer.parseInt(path.getFileName().toString().replace(".png",""));
                        final int x = Integer.parseInt(path.getParent().getFileName().toString());
                        final int zoom = Integer.parseInt(path.getParent().getParent().getFileName().toString());

                        tileService.save(mapId, zoom, x, y, data);
                    } catch (IOException e) {
                        logger.info("1) Issue with the uploading\n");
                    }
                });
                logger.info("Tile generation and mean computation successfully conducted");
            }
        } catch (IOException e) {
            logger.info("2) Issue with the uploading\n");
        } finally {
            try {
                if (tiles != null) {
                    deleteDirectoryRecursively(tiles);
                }
                logger.info("Tile generation and mean computation successfully deleted from the disk memory");

            } catch (IOException e) {
                logger.error("There is an issue with deletion of tiles in disk memory", e);
            }
        }
    }


    /**
     * Update the type of param tifFile to be interpretable by the method that is called
     *  after to transform tif into Tiles
     *
     * @param tifFile : the file to transform into Tiles
     * @return the path of the file transformed
     * @throws IOException : if there is an issue to execute method called in return
     * */
    public Path transformTifFile(MultipartFile tifFile)throws IOException{
        try{
            final byte[] byteTifFile = tifFile.getBytes();
            final TiffFiles tifToTransform = new TiffFiles(byteTifFile);
            return tifToTransform.executeTransformationCommand();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }


    public void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
