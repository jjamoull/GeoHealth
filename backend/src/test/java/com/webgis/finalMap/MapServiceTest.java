package com.webgis.finalMap;

import com.webgis.map.finalMap.MapRepository;
import com.webgis.map.finalMap.MapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MapServiceTest {

    @Mock
    MapRepository mapRepository;

    @InjectMocks
    MapService mapService;

    @Test
    void deleteMapNotFound(){
        //Arrange
        Mockito.when(mapService.findById(1)).thenReturn(Optional.empty());

        //Act
        assertThrows(IllegalArgumentException.class, () -> {
            mapService.deleteMap(1);
        });

    }
}
