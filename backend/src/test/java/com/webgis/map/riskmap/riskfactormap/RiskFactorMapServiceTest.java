package com.webgis.map.riskmap.riskfactormap;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RiskFactorMapServiceTest {

    @Mock
    private RiskFactorMapRepository riskFactorMapRepository;

    @InjectMocks
    private RiskFactorMapService riskFactorMapService;

    private String description = "description";
    private String title = "title";
    private RiskFactorMap riskFactorMap = new RiskFactorMap(title, description);

    @Test
    void saveRiskFactorMapTest(){
        // Arrange
        when(riskFactorMapRepository.save(any(RiskFactorMap.class))).thenReturn(riskFactorMap);

        // Act
        RiskFactorMap result = riskFactorMapService.save(riskFactorMap);

        // Assert
        assertNotNull(result);
        assertEquals(result, riskFactorMap);
        verify(riskFactorMapRepository, times(1)).save(any(RiskFactorMap.class));
        verifyNoMoreInteractions(riskFactorMapRepository);
    }

    @Test
    void nothingToSaveRiskFactorMapTest(){
        // Arrange && Act && Assert
        assertThrows( NullPointerException.class, ()->{
            riskFactorMapService.save(null);
        });
    }





}
