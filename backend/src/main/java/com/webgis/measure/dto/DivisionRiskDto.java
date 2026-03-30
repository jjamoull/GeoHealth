package com.webgis.measure.dto;

import java.util.Map;

/**
 * Data Transfer Object containing the risk level for each division
 */
public record DivisionRiskDto(
        Map<String,String> divisionRiskLevel
) {}
