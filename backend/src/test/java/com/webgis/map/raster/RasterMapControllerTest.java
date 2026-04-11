package com.webgis.map.raster;

import com.webgis.security.JwtAuthenticationFilter;
import com.webgis.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RasterMapController.class)
@AutoConfigureMockMvc(addFilters = false)
class RasterMapControllerTest {

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RasterMapService rasterMapService;


    @Test
    void getRiskFactorMap_isOK_shouldReturn200() throws Exception {

        RasterMap map = new RasterMap();
        map.setId(1L);

        when(rasterMapService.findById(1L))
                .thenReturn(Optional.of(map));

        mockMvc.perform(get("/rasterMaps/file/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getRiskFactorMap_isKO_shouldReturn404() throws Exception {
        long longId = 9L;

        RasterMap map = new RasterMap();
        map.setId(longId);

        when(rasterMapService.findById(longId))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/rasterMaps/file/1"))
                .andExpect(status().is(404));
    }

    @Test
    void getAllRasterMaps_isOK_shouldReturn200() throws Exception {

        RasterMap r1 = new RasterMap();
        r1.setId(1L);
        r1.setTitle("map1");

        RasterMap r2 = new RasterMap();
        r2.setId(2L);
        r2.setTitle("map2");

        when(rasterMapService.findRasters())
                .thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/rasterMaps/rasters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("map1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("map2"));
    }

    @Test
    void getAllRasterMaps_isOK_shouldReturn200withEmptyList() throws Exception {
        when(rasterMapService.findRasters())
                .thenReturn(List.of());

        mockMvc.perform(get("/rasterMaps/rasters"))
                .andExpect(status().isOk());

    }


    @Test
    void getRiskFactors_isOK_shouldReturn200() throws Exception {

        RasterMap r1 = new RasterMap();
        r1.setId(1L);
        r1.setTitle("map1");

        RasterMap r2 = new RasterMap();
        r2.setId(2L);
        r2.setTitle("map2");

        when(rasterMapService.findRiskFactors())
                .thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/rasterMaps/riskFactors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("map1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("map2"));
    }

    @Test
    void getRiskFactors_isOK_shouldReturn200withEmptyList() throws Exception {
        when(rasterMapService.findRiskFactors())
                .thenReturn(List.of());

        mockMvc.perform(get("/rasterMaps/riskFactors"))
                .andExpect(status().isOk());

    }

}
