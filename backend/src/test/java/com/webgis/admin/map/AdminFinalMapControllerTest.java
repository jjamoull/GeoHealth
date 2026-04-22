package com.webgis.admin.map;

import com.webgis.config.TestSecurityConfig;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.map.raster.RasterMapService;
import com.webgis.map.service.TransformTifFiles;
import com.webgis.security.CookieService;
import com.webgis.security.JwtService;
import com.webgis.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminFinalMapController.class)
@Import(TestSecurityConfig.class)
class AdminFinalMapControllerTest {

    @Autowired
    MockMvc mockMvc;

    // Controller dependencies
    @MockBean FinalMapService finalMapService;
    @MockBean RasterMapService rasterMapService;
    @MockBean TransformTifFiles transformTifFiles;

    // Security filter dependencies
    @MockBean JwtService jwtService;
    @MockBean CookieService cookieService;
    @MockBean UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteMapSuccess() throws Exception {
        doNothing().when(finalMapService).deleteMap(1L);

        mockMvc.perform(delete("/admin/finalMaps/1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value("Map deleted successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void DeleteMapFails() throws Exception {
        doThrow(new IllegalArgumentException("Map not found")).when(finalMapService).deleteMap(1L);

        mockMvc.perform(delete("/admin/finalMaps/1"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("Map not found"));
    }
}