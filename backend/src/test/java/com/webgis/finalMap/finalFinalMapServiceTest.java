package com.webgis.finalMap;

import com.webgis.map.finalmap.FinalMapRepository;
import com.webgis.map.finalmap.FinalMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class finalFinalMapServiceTest {

    @Mock
    FinalMapRepository finalMapRepository;

    @InjectMocks
    FinalMapService finalMapService;

    @Test
    void deleteMapNotFound(){
        //Arrange
        Mockito.when(finalMapService.findById(1)).thenReturn(Optional.empty());

        //Act
        assertThrows(IllegalArgumentException.class, () -> {
            finalMapService.deleteMap(1);
        });

    }
}
