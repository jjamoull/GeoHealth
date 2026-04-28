package com.webgis.report;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.measure.dto.DivisionRiskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private FinalMapService finalMapService;

    @InjectMocks
    private ReportController reportController;

    private FinalMap finalMap;
    private Map<String, String> riskForDivision;
    private DivisionRiskDto divisionRiskDto;

    @BeforeEach
    void setUp() {
        // Mock final Map
        finalMap = mock(FinalMap.class);
        when(finalMap.getId()).thenReturn(1L);

        // Risk for each division
        riskForDivision = new HashMap<>();
        riskForDivision.put("Djerem", "HIGH");
        riskForDivision.put("Haut Nyong", "LOW");

        //Dto
         divisionRiskDto= new DivisionRiskDto(riskForDivision);
    }

    @Test
    void getReportShouldReturnNotFound(){
        //Arrange
        when(finalMapService.findById(finalMap.getId())).thenReturn(Optional.empty());

        //Act
        ResponseEntity<Object> response= reportController.getReport(finalMap.getId(),divisionRiskDto);

        //Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getReportShouldReturnExcelFile() throws IOException {

        // Arrange
        byte[] fakeExcel = "fake-excel-content".getBytes();
        when(finalMapService.findById(finalMap.getId())).thenReturn(Optional.of(finalMap));
        when(reportService.createReportForMap(finalMap, riskForDivision)).thenReturn(fakeExcel);

        // Act
        ResponseEntity<Object> response = reportController.getReport(finalMap.getId(), divisionRiskDto);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("attachment; filename=report.xlsx",
                response.getHeaders().getFirst("Content-Disposition"));
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                response.getHeaders().getFirst("Content-Type"));
        assertArrayEquals(fakeExcel, (byte[]) response.getBody());
    }

}
