package com.webgis.map.finalMap;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapController;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.map.finalmap.dto.FinalMapDto;
import com.webgis.map.finalmap.dto.FinalMapListDto;
import com.webgis.map.raster.RasterMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class finalMapControllerTest {

    @Mock
    private FinalMapService finalMapService;

    @InjectMocks
    FinalMapController finalMapController;

    private FinalMap fm1;
    private FinalMap fm2;
    private long id;
    private FinalMapDto finalMapDto;

    @BeforeEach
    void setUp() {
        byte[] byteaEmpty = new byte[0];
        fm1 = new FinalMap("test1", "testdescription", byteaEmpty, "fakefile");
        fm2 = new FinalMap("test2", "testdescription2", byteaEmpty, "fakefile2");
        RasterMap rasterMap = new RasterMap("raster1", "raster description");
        rasterMap.setFinalMap(fm1);
        fm1.setRasterMap(rasterMap);
        id = 1;
    }

    @Test
    void getGeoJsonFileValid(){
        //Arrange
        when(finalMapService.findById(id)).thenReturn(Optional.of(fm1));

        //Act
        ResponseEntity<Object> response = finalMapController.getGeoJsonFile(id);

        //Assert
        assertNotNull(response.getBody());
        assertEquals(FinalMapDto.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        FinalMapDto result = (FinalMapDto) response.getBody();

        assertEquals(result.title(), fm1.getTitle());
        assertEquals(result.id(), fm1.getId());
        assertEquals(result.description(), fm1.getDescription());
        assertEquals(result.fileGeoJson(), fm1.getFileGeoJson());
        assertEquals(result.rasterMapId(), fm1.getRasterMap().getId());
    }

    @Test
    void getAllMapsValid(){
        //Arrange
        when(finalMapService.findAll()).thenReturn(Arrays.asList(fm1,fm2));

        //Act
        ResponseEntity<Object> response = finalMapController.getAllMaps();

        //Asserts
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<FinalMapListDto> results = (List<FinalMapListDto>) response.getBody();

        assertEquals(2, results.size());
        assertEquals(results.get(0).title(), fm1.getTitle());
        assertEquals(results.get(0).id(), fm1.getId());
        assertEquals(results.get(0).description(), fm1.getDescription());

        assertEquals(results.get(1).title(), fm2.getTitle());
        assertEquals(results.get(1).id(), fm2.getId());
        assertEquals(results.get(1).description(), fm2.getDescription());
    }
}
