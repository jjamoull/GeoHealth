package com.webgis.map.finalMap;


import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapRepository;
import com.webgis.map.finalmap.FinalMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class finalFinalMapServiceTest {

    @Mock
    FinalMapRepository finalMapRepository;

    @InjectMocks
    FinalMapService finalMapService;

    @Test
    void deleteMapNotFound(){
        //Arrange
        when(finalMapService.findById(1)).thenReturn(Optional.empty());

        //Act
        assertThrows(IllegalArgumentException.class, () -> {
            finalMapService.deleteMap(1);
        });

    }

    @Test
    void findAllMapsWorking(){

        //Arrange
        byte[] bytea = new byte[0];
        FinalMap fm1 = new FinalMap("test1", "testdescription", bytea, "fakefile");
        FinalMap fm2 = new FinalMap("test2", "testdescription", bytea, "fakefile");
        FinalMap fm3 = new FinalMap("test3", "testdescription", bytea, "fakefile");

        List<FinalMap> mapList = List.of(fm1, fm2, fm3);

        when(finalMapRepository.findAll()).thenReturn(mapList);

        //Act
        List<FinalMap> results = finalMapService.findAll();

        //Asserts
        assertEquals(3, results.size());
        assertTrue(results.contains(fm1));
        assertTrue(results.contains(fm2));
        assertTrue(results.contains(fm3));
    }
}