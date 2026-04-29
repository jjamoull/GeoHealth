package com.webgis.report;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.MapTag;
import com.webgis.measure.RiskLevel;
import com.webgis.measure.measureservice.EvaluatorAgreementMeasureService;
import com.webgis.measure.measureservice.MeanMesureService;
import com.webgis.measure.measureservice.ModelEvaluationMeasureService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ReportService {

    private final EvaluatorAgreementMeasureService evaluatorAgreementMeasureService;
    private final  MeanMesureService meanMesureService;
    private final ModelEvaluationMeasureService modelEvaluationMeasureService;

    public ReportService(
            EvaluatorAgreementMeasureService evaluatorAgreementMeasureService,
            MeanMesureService meanMesureService,
            ModelEvaluationMeasureService modelEvaluationMeasureService){

        this.evaluatorAgreementMeasureService=evaluatorAgreementMeasureService;
        this.meanMesureService=meanMesureService;
        this.modelEvaluationMeasureService= modelEvaluationMeasureService;
    }

    /**
     * Create an .xlsx report containing all the metrics for a map (as a byte array)
     *
     * @param finalMap the map you are interested in
     * @param riskForDivision a map containing the risk evaluated in the model(original map)  for each division
     *
     * @return an .xlsx report in a byte array format
     *
     * @throws IOException if error while writing byte into the byte array
     */
    public byte[] createReportForMap(FinalMap finalMap, Map<String, String> riskForDivision) throws IOException {


        // Computing all the measure
        final MeasureHolder measureHolder =new MeasureHolder(
                evaluatorAgreementMeasureService,
                meanMesureService,
                modelEvaluationMeasureService);

        measureHolder.computeAllMeasure(finalMap,riskForDivision);


        //Creating xlsx report
        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet(finalMap.getTitle());

        int rowIndex= 0; //Track the current row of the sheet

        // Creating Global Metrics Section
        final Row titleRow = sheet.createRow(rowIndex++);
        titleRow.createCell(0).setCellValue("Global Metrics");

        //Creating global header row
        rowIndex = addRow(sheet, rowIndex,
                "Metric",
                "Value"
        );

        rowIndex = addRow(sheet, rowIndex, "Krippendorff", measureHolder.getKrippendorff());
        rowIndex = addRow(sheet, rowIndex, "National Average Entropy", measureHolder.getNationalAverageEntropy());
        rowIndex = addRow(sheet, rowIndex, "National Consensus Score", measureHolder.getNationalConsensusScore());

        if(!finalMap.getTags().contains(MapTag.EBOLA)){
            rowIndex = addRow(sheet, rowIndex, "National Model-Field Agreement Score", measureHolder.getNationalModelFieldAgreementScore());
        }

        rowIndex++; //space row between section

        // Creating Divisional Metrics section
        final Row divisionTitleRow = sheet.createRow(rowIndex++);
        divisionTitleRow.createCell(0).setCellValue("Divisional Metrics");

        //Creating division header row
        rowIndex = addRow(sheet, rowIndex,
                "Division",
                "Divisional Weighted Entropy",
                "Divisional Consensus Score",
                "Mean Agreement",
                "Mean Certainty",
                "Dominant Perceived Risk"
        );

        if(!finalMap.getTags().contains(MapTag.EBOLA)){
           final Row row = sheet.getRow(rowIndex-1);
           row.createCell(6).setCellValue("Weighted Divisional-Level Agreement Score");
        }

        // For each division compute the metrics
        for (String division : riskForDivision.keySet()) {

            rowIndex = addRow(sheet, rowIndex,
                    division,
                    measureHolder.getDivisionalWeightedEntropy().get(division),
                    measureHolder.getDivisionalConsensusScore().get(division),
                    measureHolder.getMeanAgreementScoreForDivison().get(division),
                    measureHolder.getMeanCertaintyForForDivision().get(division),
                    measureHolder.getDominantPerceivedRiskLevelForDivison().get(division)
            );

            if(!finalMap.getTags().contains(MapTag.EBOLA)){
                final Row row = sheet.getRow(rowIndex-1);
                final Double value=  measureHolder.getWeightedDivisionalLevelAgreementScore().get(division);
                if (value == null) {
                    row.createCell(6).setBlank();
                }
                else{
                    row.createCell(6).setCellValue(value);
                }
            }
        }

        // Auto-size
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        //Writting workbook content into a byte array
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }

    /**
     * Create a new row in a sheet and increase the row index by one
     *
     * @param sheet the sheet in which you want to add the row
     * @param rowIndex the index of the row you want to add
     * @param name name that would be written in the first cell of the row
     * @param values values that you want to write in all the cells following the first one
     *
     * @return the new row index and add a row to the sheet
     */
    private int addRow(Sheet sheet, int rowIndex, String name, Object... values) {

        final Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(name);

        int cellIndex = 1;
        for (Object value : values) {

            if (value == null) {
                row.createCell(cellIndex).setBlank();
            } else if (value instanceof Double) {
                row.createCell(cellIndex).setCellValue((Double) value);
            } else if (value instanceof RiskLevel) {

                if(value == RiskLevel.UNDEFINED){
                    row.createCell(cellIndex).setBlank();
                }
                else{
                    row.createCell(cellIndex).setCellValue(value.toString());
                }

            } else {
                row.createCell(cellIndex).setCellValue(value.toString());
            }
            cellIndex++;
        }

        return rowIndex;
    }
}
