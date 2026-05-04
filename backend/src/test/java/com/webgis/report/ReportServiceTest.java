package com.webgis.report;

import com.webgis.evaluationform.EvaluationFormService;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.MapTag;
import com.webgis.measure.RiskLevel;
import com.webgis.measure.measureservice.EvaluatorAgreementMeasureService;
import com.webgis.measure.measureservice.MeanMesureService;
import com.webgis.measure.measureservice.ModelEvaluationMeasureService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private  EvaluationFormService evaluationFormService;

    @Mock
    private EvaluatorAgreementMeasureService evaluatorAgreementMeasureService;

    @Mock
    private MeanMesureService meanMesureService;

    @Mock
    private ModelEvaluationMeasureService modelEvaluationMeasureService;

    @InjectMocks
    private ReportService reportService;

    private FinalMap finalMap;
    private Map<String, String> riskForDivision;

    @BeforeEach
    void setUp() {
       // Mock final Map
        finalMap = mock(FinalMap.class);
        when(finalMap.getTitle()).thenReturn("TestMap");
        when(finalMap.getTags()).thenReturn(List.of(MapTag.RIFT_VALLEY_FEVER));


        // Risk for each division
        riskForDivision = new HashMap<>();
        riskForDivision.put("Djerem", "HIGH");
        riskForDivision.put("Haut Nyong", "LOW");

        // Mock for each metrics computation

        //Mock user count function
        when(evaluationFormService.getNumberOfPublicFormForAMap(finalMap))
                .thenReturn(10L);

        when(evaluationFormService.getNumberOfPublicFormForADivisionOfAMap("Djerem",finalMap))
                .thenReturn(4L);
        when(evaluationFormService.getNumberOfPublicFormForADivisionOfAMap("Haut Nyong",finalMap))
                .thenReturn(6L);


        //Mock Evaluator Agreement Measure Service
        when(evaluatorAgreementMeasureService.computekrippendorffAlpha(any()))
                .thenReturn(0.82);

        when(evaluatorAgreementMeasureService.computeNationalAverageEntropy(any()))
                .thenReturn(1.45);

        when(evaluatorAgreementMeasureService.computeNationalConsensusScore(any()))
                .thenReturn(0.67);

        when(evaluatorAgreementMeasureService.computeDivisionalWeightedEntropy(any(), eq("Djerem")))
                .thenReturn(1.10);
        when(evaluatorAgreementMeasureService.computeDivisionalWeightedEntropy(any(), eq("Haut Nyong")))
                .thenReturn(0.75);

        when(evaluatorAgreementMeasureService.computeDivisionalConsensusScore(any(), eq("Djerem")))
                .thenReturn(0.70);
        when(evaluatorAgreementMeasureService.computeDivisionalConsensusScore(any(), eq("Haut Nyong")))
                .thenReturn(0.85);

        //Mock Mean Mesure Service
        when(meanMesureService.computeMeanDivisionalAgreementScore(any(), eq("Djerem")))
                .thenReturn(0.65);
        when(meanMesureService.computeMeanDivisionalAgreementScore(any(), eq("Haut Nyong")))
                .thenReturn(0.90);

        when(meanMesureService.computeMeanCertaintyForMapForDivision(any(), eq("Djerem")))
                .thenReturn(0.55);
        when(meanMesureService.computeMeanCertaintyForMapForDivision(any(), eq("Haut Nyong")))
                .thenReturn(0.80);

        when(meanMesureService.computeDominantPerceivedRiskLevelForMapForDivision(any(), eq("Djerem")))
                .thenReturn(RiskLevel.HIGH);
        when(meanMesureService.computeDominantPerceivedRiskLevelForMapForDivision(any(), eq("Haut Nyong")))
                .thenReturn(RiskLevel.LOW);

        // Mock Evaluation Measure Service
        when(modelEvaluationMeasureService.computeNationalModelFieldAgreementScore(any(), any()))
                .thenReturn(0.78);

        when(modelEvaluationMeasureService.computeWeightedDivisionalLevelAgreementScore(
                any(), eq("Djerem"), eq("HIGH")))
                .thenReturn(0.60);
        when(modelEvaluationMeasureService.computeWeightedDivisionalLevelAgreementScore(
                any(), eq("Haut Nyong"), eq("LOW")))
                .thenReturn(0.95);
    }

    @Test
    void createReportForMapShouldReturnNonEmptyByteArrayTest() throws IOException {
        //Act
        byte[] result = reportService.createReportForMap(finalMap, riskForDivision);

        //Assert
        assertThat(result).isNotNull().isNotEmpty();
    }


    @Test
    void createReportForMapShouldProduceValidXlsxWithCorrectSheetNameTest() throws IOException {
        //Act
        byte[] result = reportService.createReportForMap(finalMap, riskForDivision);

        //Test
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
            assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
            assertThat(workbook.getSheetAt(0).getSheetName()).isEqualTo("TestMap");
        }
    }


    @Test
    void createReportForMapShouldWriteCorrectGlobalMetricValuesTest() throws IOException {

        //Act
        byte[] result = reportService.createReportForMap(finalMap, riskForDivision);

        //Assert
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
            Sheet sheet = workbook.getSheetAt(0);

            assertRowContains(sheet, 2, "Krippendorff", 0.82);
            assertRowContains(sheet, 3, "National Average Entropy", 1.45);
            assertRowContains(sheet, 4, "National Consensus Score", 0.67);
            assertRowContains(sheet, 5, "National Model-Field Agreement Score", 0.78);
            assertRowContains(sheet, 6, "Total Number Of Evaluators", 10);
        }
    }


    @Test
    void createReportForMapShouldWriteCorrectDivisionalMetricsTest() throws IOException {

        //Act
        byte[] result = reportService.createReportForMap(finalMap, riskForDivision);

        //Assert
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
            Sheet sheet = workbook.getSheetAt(0);


            Row divisionDjerem = findRowByFirstCell(sheet, "Djerem");
            assertThat(divisionDjerem).isNotNull();
            assertThat(divisionDjerem.getCell(1).getNumericCellValue()).isEqualTo(1.10); // entropy
            assertThat(divisionDjerem.getCell(2).getNumericCellValue()).isEqualTo(0.70); // consensus
            assertThat(divisionDjerem.getCell(3).getNumericCellValue()).isEqualTo(0.65); // mean agreement
            assertThat(divisionDjerem.getCell(4).getNumericCellValue()).isEqualTo(0.55); // mean certainty
            assertThat(divisionDjerem.getCell(5).getStringCellValue()).isEqualTo("HIGH"); // dominant risk
            assertThat(divisionDjerem.getCell(6).getNumericCellValue()).isEqualTo(0.60); //weighted agreement score
            assertThat(divisionDjerem.getCell(7).getNumericCellValue()).isEqualTo(4); //number of eval


            Row divisionHautNyong = findRowByFirstCell(sheet, "Haut Nyong");
            assertThat(divisionHautNyong).isNotNull();
            assertThat(divisionHautNyong.getCell(1).getNumericCellValue()).isEqualTo(0.75); // entropy
            assertThat(divisionHautNyong.getCell(2).getNumericCellValue()).isEqualTo(0.85); // consensus
            assertThat(divisionHautNyong.getCell(3).getNumericCellValue()).isEqualTo(0.90); // mean agreement
            assertThat(divisionHautNyong.getCell(4).getNumericCellValue()).isEqualTo(0.80); // mean certainty
            assertThat(divisionHautNyong.getCell(5).getStringCellValue()).isEqualTo("LOW"); // dominant risk
            assertThat(divisionHautNyong.getCell(6).getNumericCellValue()).isEqualTo(0.95); //weighted agreement score
            assertThat(divisionHautNyong.getCell(7).getNumericCellValue()).isEqualTo(6); //number of eval


        }
    }

   //Test Helper

    /**
     * Check the content of a Row
     * @param sheet the sheet in which is the row you want to check
     * @param rowIndex the index of the row you want to check
     * @param label the label that should be in the first cell of the row
     * @param value the value that should be in the seconde cell of the row
     */
    private void assertRowContains(Sheet sheet, int rowIndex, String label, double value) {
        Row row = sheet.getRow(rowIndex);
        assertThat(row).isNotNull();
        assertThat(row.getCell(0).getStringCellValue()).isEqualTo(label);
        assertThat(row.getCell(1).getNumericCellValue()).isEqualTo(value);
    }

    /**
     * Find the row in a sheet based on the content of the first cell of the row
     * @param sheet the sheet in which you want to find the row
     * @param label the label that should be written in the first cell of the row
     *
     * @return the row (with the first cell containing label) if it exists, null otherwise
     */
    private Row findRowByFirstCell(Sheet sheet, String label) {
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.STRING
                    && label.equals(cell.getStringCellValue())) {
                return row;
            }
        }
        return null;
    }
}