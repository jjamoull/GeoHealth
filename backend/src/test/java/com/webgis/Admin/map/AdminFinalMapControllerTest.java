package com.webgis.Admin.map;

import com.webgis.MessageDto;
import com.webgis.admin.map.AdminFinalMapController;
import com.webgis.exception.NotFound;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.map.raster.RasterMap;
import com.webgis.map.raster.RasterMapService;
import com.webgis.map.service.TransformTifFiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminFinalMapControllerTest {

    @Mock
    private FinalMapService finalMapService;

    @Mock
    private RasterMapService rasterMapService;

    @Mock
    private TransformTifFiles transformTifFiles;

    @InjectMocks
    private AdminFinalMapController controller;

    private MockMultipartFile makeTif() {
        return new MockMultipartFile("tifFile", "map.tif", "image/tiff", "tif-data".getBytes());
    }

    private MockMultipartFile makeZip() {
        return new MockMultipartFile("zipFile", "shapes.zip", "application/zip", "zip-data".getBytes());
    }

    private FinalMap stubFinalMap(Long id) {
        FinalMap fm = new FinalMap("Title", "Desc", List.of("dry"), "zip-data".getBytes(), null);
        fm.setId(id);
        return fm;
    }


    private RasterMap stubRasterMap(Long id) {
        RasterMap rm = new RasterMap("Title", "Desc");
        rm.setId(id);
        return rm;
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    @Test
    void deleteMap_success_returns200() {
        doNothing().when(finalMapService).deleteMap(1L);

        ResponseEntity<Object> response = controller.deleteMap(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(MessageDto.class, response.getBody());
        assertEquals("Map deleted successfully", ((MessageDto) response.getBody()).getMessage());
        verify(finalMapService).deleteMap(1L);
    }

    @Test
    void deleteMap_notFound_returns404() {
        doThrow(new IllegalArgumentException("Map not found"))
                .when(finalMapService).deleteMap(99L);

        ResponseEntity<Object> response = controller.deleteMap(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertInstanceOf(MessageDto.class, response.getBody());
        assertEquals("Map not found", ((MessageDto) response.getBody()).getMessage());
    }


    @Test
    void postShapeFile_nullId_afterFirstSave_returns400() throws Exception {
        when(finalMapService.save(any(FinalMap.class))).thenReturn(stubFinalMap(null));

        ResponseEntity<Object> response = controller.postShapeFile(
                "Title", "Desc", List.of("dry"), makeZip(), makeTif(), null);

        assertEquals(400, response.getStatusCodeValue());
        assertInstanceOf(MessageDto.class, response.getBody());
        assertTrue(((MessageDto) response.getBody()).getMessage().contains("There is no id"));
    }


    @Test
    void postShapeFile_ioException_returns500() throws Exception {
        MultipartFile badZip = mock(MultipartFile.class);
        when(badZip.getBytes()).thenThrow(new IOException("disk error"));

        ResponseEntity<Object> response = controller.postShapeFile(
                "Title", "Desc", List.of("dry"), badZip, makeTif(), null);

        assertEquals(500, response.getStatusCodeValue());
        assertInstanceOf(MessageDto.class, response.getBody());
        assertEquals("disk error", ((MessageDto) response.getBody()).getMessage());
    }



    @Test
    void postShapeFile_ioExceptionOnGeoJson_returns500() throws Exception {
        MultipartFile badGeoJson = mock(MultipartFile.class);
        when(badGeoJson.getBytes()).thenThrow(new IOException("geojson read error"));

        when(finalMapService.save(any(FinalMap.class))).thenReturn(stubFinalMap(1L));

        ResponseEntity<Object> response = controller.postShapeFile(
                "Title", "Desc", List.of("dry"), makeZip(), makeTif(), badGeoJson);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("geojson read error", ((MessageDto) response.getBody()).getMessage());
    }


    @Test
    void postShapeFile_notFoundFromZipConversion_returns400() throws Exception {
        doAnswer(invocation -> {
            FinalMap fm = invocation.getArgument(0);
            fm.setId(5L);
            return fm;
        }).when(finalMapService).save(any(FinalMap.class));

        doAnswer(invocation -> {
            sneakyThrow(new NotFound("Conversion failed"));
            return null;
        }).when(finalMapService).zipToGeoJsonFile(5L);

        ResponseEntity<Object> response = controller.postShapeFile(
                "Title", "Desc", List.of("dry"), makeZip(), makeTif(), null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Conversion failed", ((MessageDto) response.getBody()).getMessage());
    }


}