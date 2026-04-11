package com.webgis.map.riskmap.tile;


import com.webgis.map.raster.RasterMap;
import com.webgis.map.raster.RasterMapRepository;
import com.webgis.map.tile.Tile;
import com.webgis.map.tile.TileId;
import com.webgis.map.tile.TileRepository;
import com.webgis.map.tile.TileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TileServiceTest {

    @Mock
    private TileRepository tileRepository;

    @Mock
    private RasterMapRepository riskFactorMapRepository;

    @InjectMocks
    private TileService tileService;


    private long mapId = 1L;
    private int zoom = 2;
    private int x = 3;
    private int y = 4;
    private final byte[] data = new byte[]{1,2,3};
    private final RasterMap riskFactorMap = new RasterMap("Title1", "Description1");

    private final TileId expectedId = new TileId(mapId, zoom, x, y);
    private final Tile expectedTile = new Tile(expectedId, data, riskFactorMap);
    private final RasterMap expectedRiskFactorMap = new RasterMap("Title2", "Description2");

    @Test
    void testSaveTile() {
        tileService = new TileService(tileRepository, riskFactorMapRepository);
        when(tileRepository.save(any(Tile.class))).thenReturn(expectedTile);
        when(riskFactorMapRepository.findById(mapId)).thenReturn(Optional.of(expectedRiskFactorMap));

        Tile result = tileService.save(mapId, zoom, x, y, data);

        assertNotNull(result);
        assertEquals(mapId, result.getTileId().getMapId());
        assertEquals(zoom, result.getTileId().getZoom());
        assertEquals(x, result.getTileId().getX());
        assertEquals(y, result.getTileId().getY());

        verify(tileRepository, times(1)).save(any(Tile.class));
    }
}