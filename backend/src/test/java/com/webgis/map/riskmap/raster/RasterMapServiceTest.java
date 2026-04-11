package com.webgis.map.riskmap.raster;


import com.webgis.map.raster.RasterMap;
import com.webgis.map.raster.RasterMapRepository;
import com.webgis.map.raster.RasterMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RasterMapServiceTest {

    @Mock
    private RasterMapRepository riskFactorMapRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private RasterMapService riskFactorMapService;

    private String description = "description";
    private String title = "title";
    private RasterMap riskFactorMap = new RasterMap(title, description);



    @Test
    void saveRiskFactorMapTest(){
        // Arrange
        when(riskFactorMapRepository.save(any(RasterMap.class))).thenReturn(riskFactorMap);

        // Act
        RasterMap result = riskFactorMapService.save(riskFactorMap);

        // Assert
        assertNotNull(result);
        assertEquals(result, riskFactorMap);
        verify(riskFactorMapRepository, times(1)).save(any(RasterMap.class));
        verifyNoMoreInteractions(riskFactorMapRepository);
    }



    @Test
    void findAllRiskFactorMapIsOKTest(){
        // Arrange
        RasterMap riskFactorMap2 = new RasterMap("Title 2", "description 2");
        List<RasterMap> expectedResult = List.of(new RasterMap[]{
                riskFactorMap,
                riskFactorMap2
        });
        when(riskFactorMapRepository.findAll()).thenReturn(expectedResult);

        // Act
        List<RasterMap> result = riskFactorMapService.findAll();

        //Assert
        assertNotNull(result);
        assertEquals(result.size(), expectedResult.size());
        assertEquals(result, expectedResult);
    }





}