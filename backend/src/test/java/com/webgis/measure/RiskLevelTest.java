package com.webgis.measure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskLevelTest {

    @Test
    void getScoreTest(){
        //Arrange
        RiskLevel riskLow = RiskLevel.LOW;
        RiskLevel riskMed = RiskLevel.MEDIUM;
        RiskLevel riskHigh = RiskLevel.HIGH;

        //Assert
        assertEquals(1, riskLow.getScore());
        assertEquals(2, riskMed.getScore());
        assertEquals(3, riskHigh.getScore());
    }

    @Test
    void fromStringTest(){
        //Act
        RiskLevel riskLow = RiskLevel.fromString("LOw");
        RiskLevel riskMed = RiskLevel.fromString("MeDIum    ");
        RiskLevel riskHigh = RiskLevel.fromString("high");

        //Assert
        assertEquals(RiskLevel.LOW, riskLow);
        assertEquals(RiskLevel.MEDIUM, riskMed);
        assertEquals(RiskLevel.HIGH, riskHigh);
    }
}
