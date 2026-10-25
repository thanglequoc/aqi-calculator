package com.thanglequoc.aqicalculator.aqi;

import com.thanglequoc.aqicalculator.AQICalculator;
import com.thanglequoc.aqicalculator.AQIResult;
import com.thanglequoc.aqicalculator.Pollutant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AQICalculatorTest {
    
    AQICalculator calculator;
    
    private Pollutant pollutant;
    
    @BeforeEach
    public void begin() {
        calculator = AQICalculator.getAQICalculatorInstance();
    }
    
    /**
     * --- Begin of AQI Test for PM10 ---------------------
     **/
    
    @Test
    public void should_ReturnPM10AQI_When_InputConcentration() {
        pollutant = Pollutant.PM10;
        double concentration = 135;
        int expectedAQI = 91;
        
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 175;
        expectedAQI = 111;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 357;
        expectedAQI = 204;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnPM10NoAQI_When_InputInvalidConcentration() {
        this.pollutant = Pollutant.PM10;
        double concentration = -1;
        int expectedAQI = -1;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnEqualsPM10NowCastAQI_When_InputListOfConcentration() {
        /**
         * Example Data for NowCast PM10 12h period - 64, 63, 72, 77, 65, 61,
         * 70, 71, 64, 57, 58, 64
         **/
        this.pollutant = Pollutant.PM10;
        double[] data = {64, 63, 72, 77, 65, 61, 70, 71, 64, 57, 58, 64};
        int expectedAQI = 57;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnEqualsPM10NowCastAQI_When_InputValidListOfMissingConcentration() {
        
        this.pollutant = Pollutant.PM10;
        double[] data = {64, -1, 62, 77, 65, 61, 70, 71, 64, 57, 58, 64};
        int expectedAQI = 56;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
        
        // Only some data available
        double[] data2 = {165, 123, 45, 12, -1, -1, -1, -1, -1, 123, 154, -1};
        expectedAQI = 87;
        result = this.calculator.getNowCastAQI(pollutant, data2);
        assertEquals(expectedAQI, result.getAQI());
        
    }
    
    @Test
    public void should_ReturnPM10NoNowCastAQI_When_InputInvalidListOfMissingConcentration() {
        this.pollutant = Pollutant.PM10;
        // missing 3 nearest hour of data
        double[] data = {-1, -1, -1, 77, 65, 61, 70, 71, 64, 57, 58, 64};
        int expectedAQI = -1;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        
        assertEquals(expectedAQI, result.getAQI());
        
        /**
         * Example Data for NowCast PM10 12h period - 64, -1, -1, 77, 65, 61,
         * 70, 71, 64, 57, 58, 64 , missing 2 of 3 nearest data -> invalid
         **/
        this.pollutant = Pollutant.PM10;
        double[] data2 = {64, -1, -1, 77, 65, 61, 70, 71, 64, 57, 58, 64};
        expectedAQI = -1;
        result = this.calculator.getNowCastAQI(pollutant, data2);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnPM10EmptyAQIResult_When_ConcentrationIsOutOfRange() {
        this.pollutant = Pollutant.PM10;
        
        double concentration = 700;
        int expectedAQI = -1;
        AQIResult result = calculator.getAQI(pollutant, concentration);
        
        assertEquals(expectedAQI, result.getAQI());
    }
    
    /**
     * --------------Begin of AQI Test for
     * PM2.5-------------------------------------------
     **/
    
    @Test
    public void should_ReturnPM25AQI_When_InputConcentration() {
        this.pollutant = Pollutant.PM25;
        
        int concentration = 35;
        int expectedAQI = 99;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 78;
        expectedAQI = 163;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 12;
        expectedAQI = 50;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnPM25NoAQI_When_InputInvalidConcentration() {
        this.pollutant = Pollutant.PM25;
        int concentration = -1;
        int expectedAQI = -1;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnPM25NowCastAQI_When_InputListOfConcentration() {
        /***
         *
         * */
        this.pollutant = Pollutant.PM25;
        double[] data = {30.5, 28.8, 29.5, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5};
        int expectedAQI = 89;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
        
        double[] data2 = {165, 123, 45, 12, 45, 12, 56, 42, 12, 123, 154, 32};
        expectedAQI = 185;
        result = this.calculator.getNowCastAQI(pollutant, data2);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void shoud_ReturnPM25NowCastAQI_When_InputValidListOfMissingConcentration() {
        this.pollutant = Pollutant.PM25;
        
        /**
         * Example Data for NowCast PM2.5 12h period - missing 1 of the nearest
         * 3 hours -> still valid
         **/
        double[] data = {30.5, 12.5, -1, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5};
        int expectedAQI = 79;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void shoud_ReturnPM25NoNowCastAQI_When_InputInvalidListOfMissingConcentration() {
        this.pollutant = Pollutant.PM25;
        double[] data = {30.5, -1, -1, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5};
        int expectedAQI = -1; // no AQI
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
        
        double[] data2 = {-1, -1, -1, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5};
        expectedAQI = -1;
        result = this.calculator.getNowCastAQI(pollutant, data2);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnPM25EmptyAQIResult_When_ConcentrationIsOutOfRange() {
        this.pollutant = Pollutant.PM25;
        
        double concentration = 700;
        int expectedAQI = -1;
        AQIResult result = calculator.getAQI(pollutant, concentration);
        
        assertEquals(expectedAQI, result.getAQI());
    }
    
    /***
     * --------------Begin of AQI Test for
     * Ozone-------------------------------------------
     */
    @Test
    public void should_ReturnOzoneAQI_When_InputConcentration() {
        this.pollutant = Pollutant.O3;
        double concentration = 45;
        int expectedAQI = 42;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 89;
        expectedAQI = 159;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 72.71875;
        expectedAQI = 107;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnOzoneNoAQI_When_InputInvalidConcentration() {
        this.pollutant = Pollutant.O3;
        double concentration = -1;
        int expectedAQI = -1;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnOzoneNowCastAQI_When_InputListOfConcentration() {
        this.pollutant = Pollutant.O3;
        double[] data = {38.611, 47.406, 54.469, 52.377, 50.754, 43.373, 39.143, 35.334};
        int expectedAQI = 42;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
        
        double[] data1 = {42.12, 32.12, 34.12, -1, -1, -1, -1, -1};
        result = this.calculator.getNowCastAQI(pollutant, data1);
        expectedAQI = 34;
        assertEquals(expectedAQI, result.getAQI());
        
    }
    
    @Test
    public void shoud_ReturnOzoneNowCastAQI_When_InputValidListOfMissingConcentration() {
        this.pollutant = Pollutant.O3;
        
        double[] data = {145.32, 167.54, 187.12, 123.12, -1, -1, -1, -1};
        int expectedAQI = 254;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
        
        double[] data2 = {145.32, 167.54, -1, 123.12, 0, 183.2, 32.4, 145.3};
        expectedAQI = 242;
        result = this.calculator.getNowCastAQI(pollutant, data2);
        assertEquals(expectedAQI, result.getAQI());
        
        double[] data3 = {-1, 47.406, 54.469, 52.377, 50.754, 43.373, 39.143, 35.334};
        expectedAQI = 46;
        result = this.calculator.getNowCastAQI(pollutant, data3);
        assertEquals(expectedAQI, result.getAQI());
        
    }
    
    @Test
    public void shoud_ReturnOzoneNoNowCastAQI_When_InputInvalidListOfMissingConcentration() {
        this.pollutant = Pollutant.O3;
        double[] data = {-1, -1, -1, 12, 45, 12, 45, 56};
        int expectedAQI = -1;
        AQIResult result = this.calculator.getNowCastAQI(pollutant, data);
        assertEquals(expectedAQI, result.getAQI());
        
        double[] data2 = {-1, -1, 1, 12, 45, 12, 45, 56};
        expectedAQI = -1;
        result = this.calculator.getNowCastAQI(pollutant, data2);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnOzoneEmptyAQIResult_When_ConcentrationIsOutOfRange() {
        this.pollutant = Pollutant.O3;
        
        double concentration = 700;
        int expectedAQI = -1;
        AQIResult result = calculator.getAQI(pollutant, concentration);
        
        assertEquals(expectedAQI, result.getAQI());
    }
    
    /***
     * --------------Begin of AQI Test for
     * CO-------------------------------------------
     */
    @Test
    public void should_ReturnCOAQI_When_InputConcentration() {
        this.pollutant = Pollutant.CO;
        double concentration = 12000;
        int expectedAQI = 143;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 7000;
        expectedAQI = 76;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnCONoAQI_When_InputInvalidConcentration() {
        this.pollutant = Pollutant.CO;
        double concentration = -1;
        int expectedAQI = -1;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnCOEmptyAQIResult_When_ConcentrationIsOutOfRange() {
        this.pollutant = Pollutant.CO;
        
        double concentration = 70000;
        int expectedAQI = -1;
        AQIResult result = calculator.getAQI(pollutant, concentration);
        
        assertEquals(expectedAQI, result.getAQI());
    }
    
    /**
     * --------------Begin of AQI Test for
     * NO2-------------------------------------------
     */
    
    @Test
    public void should_ReturnNO2AQI_When_InputConcentration() {
        this.pollutant = Pollutant.NO2;
        double concentration = 145.12;
        int expectedAQI = 109;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 43;
        expectedAQI = 41;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnNO2NoAQI_When_InputInvalidConcentration() {
        this.pollutant = Pollutant.NO2;
        double concentration = -1;
        int expectedAQI = -1;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnNO2EmptyAQIResult_When_ConcentrationIsOutOfRange() {
        this.pollutant = Pollutant.NO2;
        
        double concentration = 4000;
        int expectedAQI = -1;
        AQIResult result = calculator.getAQI(pollutant, concentration);
        
        assertEquals(expectedAQI, result.getAQI());
    }
    
    /***
     * --------------Begin of AQI Test for
     * SO2-------------------------------------------
     */
    
    @Test
    public void should_ReturnSO2AQI_When_InputConcentration() {
        this.pollutant = Pollutant.SO2;
        double concentration = 132;
        int expectedAQI = 126;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
        
        concentration = 42;
        expectedAQI = 59;
        result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnSO2EmtyAQI_When_InputInvalidConcentration() {
        this.pollutant = Pollutant.SO2;
        double concentration = -1;
        int expectedAQI = -1;
        AQIResult result = this.calculator.getAQI(pollutant, concentration);
        assertEquals(expectedAQI, result.getAQI());
    }
    
    @Test
    public void should_ReturnSO2EmptyAQIResult_When_ConcentrationIsOutOfRange() {
        this.pollutant = Pollutant.SO2;
        
        double concentration = 2000;
        int expectedAQI = -1;
        AQIResult result = calculator.getAQI(pollutant, concentration);
        
        assertEquals(expectedAQI, result.getAQI());
    }
}
