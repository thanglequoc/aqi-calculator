package com.thanglequoc.aqicalculator.aqi;

import com.thanglequoc.aqicalculator.AQICalculator;
import com.thanglequoc.aqicalculator.AQICustomSettings;
import com.thanglequoc.aqicalculator.AQIResult;
import com.thanglequoc.aqicalculator.Pollutant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AQIMessageTest {
    AQICalculator calculator;

    static String UNCATEGORIZED = "Uncategorized";
    static String INVALID_GENERAL_MESSAGE = "Invalid pollutant concentration range for calculation";
    static String NOT_APPLICABLE = "N/A";
    
    @BeforeEach
    public void begin() throws IOException, Exception {
        calculator = AQICalculator.getAQICalculatorInstance();
    }
    
    /**
     * ----- Begin test for Good Category level
     * ---------------------------------------------------------------------
     */
    
    @Test
    public void should_ReturnGoodAQILevel_When_AQIPM10Is30() {
        double avgConcentration = 32;

        AQIResult result = calculator.getAQI(Pollutant.PM10, avgConcentration);

        assertEquals(AQILevel.GOOD.getLiteral(), result.getCategory());
        assertEquals(AQILevel.GOOD.getColor(), result.getColor());
        assertEquals(AQILevelGeneralMessages.GOOD.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM10.GOOD.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM10.GOOD.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM10), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnGoodAQILevel_When_AQIPM25Is40() {
        double avgConcentration = 9.6;
        AQIResult result = calculator.getAQI(Pollutant.PM25, avgConcentration);

        assertEquals(AQILevel.GOOD.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.GOOD.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM25.GOOD.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM25.GOOD.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM25), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnGoodAQILevel_When_AQIO3Is10() {

        double avgConcentration = 10;
        AQIResult result = calculator.getAQI(Pollutant.O3, avgConcentration);

        assertEquals(AQILevel.GOOD.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.GOOD.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForO3.GOOD.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForO3.GOOD.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.O3), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnGoodAQILevel_When_AQICOIs17() {

        double avgConcentration = 1400;
        AQIResult result = calculator.getAQI(Pollutant.CO, avgConcentration);
        
        assertEquals(AQILevel.GOOD.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.GOOD.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForCO.GOOD.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForCO.GOOD.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.CO), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnGoodAQILevel_When_AQISO2Is49() {

        double avgConcentration = 34;
        AQIResult result = calculator.getAQI(Pollutant.SO2, avgConcentration);

        assertEquals(AQILevel.GOOD.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.GOOD.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForSO2.GOOD.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForSO2.GOOD.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.SO2), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnGoodAQILevel_When_AQINO2Is37() {

        double avgConcentration = 39;
        AQIResult result = calculator.getAQI(Pollutant.NO2, avgConcentration);

        assertEquals(AQILevel.GOOD.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.GOOD.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForNO2.GOOD.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForNO2.GOOD.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.NO2), result.getSensitiveGroups());
    }
    
    /**
     * ----- Begin test for Moderate level
     * ---------------------------------------------------------------------
     */
    
    @Test
    public void should_ReturnModerateAQILevel_When_AQIPM10Is73() {

        double avgConcentration = 99;
        AQIResult result = calculator.getAQI(Pollutant.PM10, avgConcentration);

        assertEquals(AQILevel.MODERATE.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.MODERATE.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM10.MODERATE.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM10.MODERATE.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM10), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnModerateAQILevel_When_AQIPM25Is85() {

        double avgConcentration = 28.2;
        AQIResult result = calculator.getAQI(Pollutant.PM25, avgConcentration);

        assertEquals(AQILevel.MODERATE.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.MODERATE.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM25.MODERATE.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM25.MODERATE.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM25), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnModerateAQILevel_When_AQIO3Is93() {

        double avgConcentration = 67;
        AQIResult result = calculator.getAQI(Pollutant.O3, avgConcentration);

        assertEquals(AQILevel.MODERATE.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.MODERATE.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForO3.MODERATE.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForO3.MODERATE.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.O3), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnModerateAQILevel_When_AQICOIs61() {

        double avgConcentration = 5500;
        AQIResult result = calculator.getAQI(Pollutant.CO, avgConcentration);

        assertEquals(AQILevel.MODERATE.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.MODERATE.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForCO.MODERATE.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForCO.MODERATE.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.CO), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnModerateAQILevel_When_AQISO2Is51() {

        double avgConcentration = 36;
        AQIResult result = calculator.getAQI(Pollutant.SO2, avgConcentration);

        assertEquals(AQILevel.MODERATE.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.MODERATE.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForSO2.MODERATE.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForSO2.MODERATE.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.SO2), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnModerateAQILevel_When_AQINO2Is97() {

        double avgConcentration = 99;
        AQIResult result = calculator.getAQI(Pollutant.NO2, avgConcentration);

        assertEquals(AQILevel.MODERATE.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.MODERATE.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForNO2.MODERATE.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForNO2.MODERATE.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.NO2), result.getSensitiveGroups());
    }
    
    /**
     * ----- Begin test for Unhealthy for Sensitive Groups level
     * ---------------------------------------------------------------------
     */
    
    @Test
    public void should_ReturnUnhealthyForSensitiveGroupsAQILevel_When_AQIPM10Is104() {

        double avgConcentration = 161;
        AQIResult result = calculator.getAQI(Pollutant.PM10, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY_FOR_SENSITIVE_GROUPS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY_FOR_SENSITIVE_GROUPS.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM10.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM10.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM10), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyForSensitiveGroupsAQILevel_When_AQIPM25Is112() {

        double avgConcentration = 39.9;
        AQIResult result = calculator.getAQI(Pollutant.PM25, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY_FOR_SENSITIVE_GROUPS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM25.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM25.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM25), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyForSensitiveGroupsAQILevel_When_AQIO3Is126() {

        double avgConcentration = 78;
        AQIResult result = calculator.getAQI(Pollutant.O3, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY_FOR_SENSITIVE_GROUPS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForO3.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForO3.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.O3), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyForSensitiveGroupsAQILevel_When_AQICOIs138() {

        double avgConcentration = 11600;
        AQIResult result = calculator.getAQI(Pollutant.CO, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY_FOR_SENSITIVE_GROUPS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForCO.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForCO.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.CO), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyForSensitiveGroupsAQILevel_When_AQISO2Is137() {

        double avgConcentration = 156;
        AQIResult result = calculator.getAQI(Pollutant.SO2, avgConcentration);
        
        assertEquals(AQILevel.UNHEALTHY_FOR_SENSITIVE_GROUPS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForSO2.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForSO2.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.SO2), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyForSensitiveGroupsAQILevel_When_AQINO2Is149() {

        double avgConcentration = 354;
        AQIResult result = calculator.getAQI(Pollutant.NO2, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY_FOR_SENSITIVE_GROUPS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForNO2.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForNO2.UNHEALTHY_FOR_SENSITIVE_GROUPS
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.NO2), result.getSensitiveGroups());
    }
    
    /**
     * ----- Begin test for Unhealthy Category level
     * ---------------------------------------------------------------------
     */
    
    @Test
    public void should_ReturnUnhealthyAQILevel_When_AQIPM10Is151() {

        double avgConcentration = 255;
        AQIResult result = calculator.getAQI(Pollutant.PM10, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM10.UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM10.UNHEALTHY.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM10), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyAQILevel_When_AQIPM25Is168() {

        double avgConcentration = 88.4;
        AQIResult result = calculator.getAQI(Pollutant.PM25, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM25.UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM25.UNHEALTHY.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM25), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyAQILevel_When_AQIO3Is161() {

        double avgConcentration = 89;
        AQIResult result = calculator.getAQI(Pollutant.O3, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForO3.UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForO3.UNHEALTHY.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.O3), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyAQILevel_When_AQICOIs178() {

        double avgConcentration = 14000;
        AQIResult result = calculator.getAQI(Pollutant.CO, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForCO.UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForCO.UNHEALTHY.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.CO), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyAQILevel_When_AQISO2Is185() {

        double avgConcentration = 267;
        AQIResult result = calculator.getAQI(Pollutant.SO2, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForSO2.UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForSO2.UNHEALTHY.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.SO2), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnUnhealthyAQILevel_When_AQINO2Is198() {

        double avgConcentration = 637;
        AQIResult result = calculator.getAQI(Pollutant.NO2, avgConcentration);

        assertEquals(AQILevel.UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForNO2.UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForNO2.UNHEALTHY.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.NO2), result.getSensitiveGroups());
    }
    
    /**
     * ----- Begin test for Very Unhealthy Category level
     * ---------------------------------------------------------------------
     */
    
    @Test
    public void should_ReturnVeryUnhealthyAQILevel_When_AQIPM10Is233() {

        double avgConcentration = 377;
        AQIResult result = calculator.getAQI(Pollutant.PM10, avgConcentration);
        
        assertEquals(AQILevel.VERY_UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.VERY_UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM10.VERY_UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM10.VERY_UNHEALTHY
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM10), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnVeryUnhealthyAQILevel_When_AQIPM25Is248() {

        double avgConcentration = 197.9;
        AQIResult result = calculator.getAQI(Pollutant.PM25, avgConcentration);
        
        assertEquals(AQILevel.VERY_UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.VERY_UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM25.VERY_UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM25.VERY_UNHEALTHY
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM25), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnVeryUnhealthyAQILevel_When_AQIO3Is263() {

        double avgConcentration = 164;
        AQIResult result = calculator.getAQI(Pollutant.O3, avgConcentration);

        assertEquals(AQILevel.VERY_UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.VERY_UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForO3.VERY_UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForO3.VERY_UNHEALTHY
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.O3), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnVeryUnhealthyAQILevel_When_AQICOIs279() {

        double avgConcentration = 27200;
        AQIResult result = calculator.getAQI(Pollutant.CO, avgConcentration);
        
        assertEquals(AQILevel.VERY_UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.VERY_UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForCO.VERY_UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForCO.VERY_UNHEALTHY
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.CO), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnVeryUnhealthyAQILevel_When_AQINO2Is299() {

        double avgConcentration = 1238;
        AQIResult result = calculator.getAQI(Pollutant.NO2, avgConcentration);

        assertEquals(AQILevel.VERY_UNHEALTHY.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.VERY_UNHEALTHY.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForNO2.VERY_UNHEALTHY.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForNO2.VERY_UNHEALTHY
                .getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.NO2), result.getSensitiveGroups());
    }
    
    /**
     * ----- Begin test for Hazardous Category level
     * ---------------------------------------------------------------------
     */
    
    @Test
    public void should_ReturnHazardousAQILevel_When_AQIPM10Is358() {

        double avgConcentration = 470;
        AQIResult result = calculator.getAQI(Pollutant.PM10, avgConcentration);

        assertEquals(AQILevel.HAZARDOUS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.HAZARDOUS.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM10.HAZARDOUS.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM10.HAZARDOUS.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM10), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnHazardousAQILevel_When_AQIPM25Is399() {

        double avgConcentration = 349.3;
        AQIResult result = calculator.getAQI(Pollutant.PM25, avgConcentration);

        assertEquals(AQILevel.HAZARDOUS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.HAZARDOUS.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForPM25.HAZARDOUS.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForPM25.HAZARDOUS.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.PM25), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnHazardousAQILevel_When_AQICOIs431() {

        double avgConcentration = 43500;
        AQIResult result = calculator.getAQI(Pollutant.CO, avgConcentration);

        assertEquals(AQILevel.HAZARDOUS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.HAZARDOUS.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForCO.HAZARDOUS.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForCO.HAZARDOUS.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.CO), result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnHazardousAQILevel_When_AQINO2Is500() {

        double avgConcentration = 2044;
        AQIResult result = calculator.getAQI(Pollutant.NO2, avgConcentration);
        
        assertEquals(AQILevel.HAZARDOUS.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.HAZARDOUS.getMeaning(), result.getMeaning());
        assertEquals(AQILevelSpecificMessageForNO2.HAZARDOUS.getCautionaryStatements(), result.getCautionaryStatements());
        assertEquals(AQILevelSpecificMessageForNO2.HAZARDOUS.getHealthEffectsStatements(), result.getHealthEffectsStatements());
        assertEquals(SensitiveGroups.resolveSensitiveGroupsFromPollutant(Pollutant.NO2), result.getSensitiveGroups());
    }
    
    /**
     * ----- Begin test for Invalid Concentration Range
     * ---------------------------------------------------------------------
     */
    
    @Test
    public void should_ReturnInvalidMessage_When_ConcentrationRangeExceeded() {

        AQIResult result = calculator.getAQI(Pollutant.PM10, -10);

        assertEquals(AQILevel.INVALID.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.INVALID.getMeaning(), result.getMeaning());
        assertEquals(NOT_APPLICABLE, result.getCautionaryStatements());
        assertEquals(NOT_APPLICABLE, result.getHealthEffectsStatements());
        assertEquals(NOT_APPLICABLE, result.getSensitiveGroups());
    }
    
    @Test
    public void should_ReturnInvalidMessage_When_ConcentrationRangeIsNegative() {
        AQIResult result = calculator.getAQI(Pollutant.PM25, -1000000);
        
        assertEquals(AQILevel.INVALID.getLiteral(), result.getCategory());
        assertEquals(AQILevelGeneralMessages.INVALID.getMeaning(), result.getMeaning());
        assertEquals(NOT_APPLICABLE, result.getCautionaryStatements());
        assertEquals(NOT_APPLICABLE, result.getHealthEffectsStatements());
        assertEquals(NOT_APPLICABLE, result.getSensitiveGroups());
    }

    @Test
    public void should_ReturnCustomMessages_When_UserSettingIsEnable() {
        AQICustomSettings userSettings = new AQICustomSettings().withCustomMessagesMode(true)
                .withGeneralMessageResourcePath("AQIresource/custom-aqi-general-messages_de.json")
                .withSpecificMessageResourcePath("AQIresource/custom-aqi-specific-messages_de.json")
                .withSensitiveGroupsResourcePath("AQIresource/custom-aqi-sensitive-groups_de.json");
        calculator.applyCustomSettings(userSettings);

        double avgConcentration = 197.9;
        AQIResult result = calculator.getAQI(Pollutant.PM25, avgConcentration);

        String expectedCategory = "Sehr ungesund";
        String expectedMeaning = "Gesundheitswarnung: Jeder kann schwerwiegendere gesundheitliche Auswirkungen haben";
        String expectedCautionaryStatements = "Menschen mit Herz- oder Lungenerkrankungen, ältere Erwachsene, Kinder und Menschen mit einem niedrigeren sozioökonomischen Status sollten jegliche körperliche Aktivität im Freien vermeiden. Alle anderen sollten eine längere oder schwere Anstrengung vermeiden";
        String expectedHealthEffectsStatements = "Signifikante Verschlechterung der Atemwegsbeschwerden in sensiblen Gruppen, einschließlich älterer Erwachsener, Kinder und Personen mit niedrigerem sozioökonomischen Status; signifikante Verschlechterung der Herz- oder Lungenerkrankung und vorzeitige Sterblichkeit bei Menschen mit Herz- oder Lungenerkrankung; signifikante Zunahme der Atemwegserkrankungen in der Allgemeinbevölkerung";
        String expectedSensitiveGroups = "Am stärksten gefährdet sind Menschen mit Atemwegs- oder Herzerkrankungen, ältere Menschen und Kinder";

        assertEquals(expectedCategory, result.getCategory());
        assertEquals(expectedMeaning, result.getMeaning());
        assertEquals(expectedHealthEffectsStatements, result.getHealthEffectsStatements());
        assertEquals(expectedCautionaryStatements, result.getCautionaryStatements());
        assertEquals(expectedSensitiveGroups, result.getSensitiveGroups());

        calculator.resetDefaultSettings();
    }
    
}
