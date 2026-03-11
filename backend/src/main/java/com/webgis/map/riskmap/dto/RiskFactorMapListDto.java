package com.webgis.map.riskmap.dto;

import com.webgis.map.riskmap.riskfactormap.RiskFactorMap;

/**
 * DTO for risk factor map list
 **/
public record RiskFactorMapListDto(
        String title) {

    public RiskFactorMapListDto(RiskFactorMap riskFactorMap) {
        this(riskFactorMap.getTitle());}

}
