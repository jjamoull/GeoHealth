package com.webgis.map.riskmap.tile;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TileServiceTest {

    @Mock
    private TileRepository tileRepository;

    @InjectMocks
    private TileService tileService;


    private long mapId = 1L;
    private int zoom = 2;
    private int x = 3;
    private int y = 4;
    private byte[] data = new byte[]{1,2,3};

    private TileId expectedId = new TileId(mapId, zoom, x, y);
    private Tile expectedTile = new Tile(expectedId, data);

    @Test
    void testSaveTile() {

        when(tileRepository.save(any(Tile.class))).thenReturn(expectedTile);

        Tile result = tileService.save(mapId, zoom, x, y, data);

        assertNotNull(result);
        assertEquals(mapId, result.getTileId().getMapId());
        assertEquals(zoom, result.getTileId().getZoom());
        assertEquals(x, result.getTileId().getX());
        assertEquals(y, result.getTileId().getY());

        verify(tileRepository, times(1)).save(any(Tile.class));
    }
}