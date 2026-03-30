package com.webgis.riskFactorMap;

import com.webgis.map.finalmap.FinalMapRepository;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapController;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapRepository;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapService;
import com.webgis.map.riskmap.tile.TileRepository;
import com.webgis.map.riskmap.tile.TileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class riskFactorMapTest {

    private final int TILE_SIZE = 256;
    private byte[] testData;
    private RiskFactorMapService riskFactorMapService;


    @Mock
    private RiskFactorMapRepository riskFactorMapRepository;

    @Mock
    private TileService tileService;

    @Mock
    private TileRepository tileRepository;

    @BeforeEach
    void init(){
        testData = generateRandomTileMeans();
        riskFactorMapService = new RiskFactorMapService(riskFactorMapRepository, tileService);
        tileService = new TileService(tileRepository, riskFactorMapRepository);
    }



    public byte[] generateRandomTileMeans() {
        byte[] data = new byte[TILE_SIZE*TILE_SIZE];
        Random random = new Random();

        for (int i = 0; i < data.length; i++) {
            int value = random.nextInt(256); // 0..255
            data[i] = (byte) value;
        }

        return data;
    }

}
