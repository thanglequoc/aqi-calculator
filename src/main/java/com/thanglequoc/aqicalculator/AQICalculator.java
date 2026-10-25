package com.thanglequoc.aqicalculator;

import java.io.IOException;
import java.util.Optional;

/**
 * A calculator use to calculate AQI from pollutant concentration, support both
 * <b>regular AQI</b> calculation and <b>NowCast AQI</b> calculation.
 * <p>
 * To use the <i>AQICalculator</i> object, get its instance by calling
 * <code>getAQICalculatorInstance() </code> method directly
 *
 * @author thanglequoc
 * @see <a href="https://github.com/thanglequoc/aqi-calculator"> AQI Calculator Documentation</a>
 */

public class AQICalculator {
    
    private PollutantsBreakpointGenerator breakpointGenerator;
    
    private AQIMessageGenerator messageGenerator;
    
    private PollutantsBreakpoint pollutantsBreakpoint;
    
    private PollutantBreakpoint pollutantBreakpoint;
    
    private Optional<PollutantConcentration> targetPollutantConcentration;
    
    private NowCastCalculator nowCastCalculator;
    
    private PollutantConcentrationTruncator truncator;

    private AQICustomSettings customSettings;
    
    private static AQICalculator uniqueAQICalculatorInstance;
    
    /**
     * Get the AQI calculator instance.
     *
     * @return the AQI calculator instance
     */
    public static AQICalculator getAQICalculatorInstance() {
        if (uniqueAQICalculatorInstance == null) {
            uniqueAQICalculatorInstance = new AQICalculator();
        }
        return uniqueAQICalculatorInstance;
    }
    
    /**
     * Instantiates a new AQI calculator.
     */
    private AQICalculator() {
        /*
         * AQI Calculator will generate the following thing
         * PollutantBreakpointGenerator: Generator to get the breakpoints table
         * from JSON File PollutantsBreakpoint: Store a list of pollutant
         * breakpoint NowCast Calculator: calculator to get avgAQI at present
         * for PM10, PM2.5, Ozone
         */
        
        try {
            this.breakpointGenerator = new PollutantsBreakpointGenerator();
            this.messageGenerator = new AQIMessageGenerator();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.pollutantsBreakpoint = breakpointGenerator.getPollutantsBreakpoint();
        this.nowCastCalculator = new NowCastCalculator();
        this.truncator = new PollutantConcentrationTruncator();
        this.customSettings = new AQICustomSettings();
    }
    
    private int calculateAQI(Pollutant pollutant, double avgConcentration) {
        pollutantBreakpoint = this.pollutantsBreakpoint.getBreakpointOfPollutant(pollutant);
        double truncatedConcentration = this.truncator.getTruncatedPollutantConcentrationBaseOnPollutant(pollutant,
                avgConcentration);
        targetPollutantConcentration = pollutantBreakpoint
                .getConcentrationRangeWithAvgConcentration(truncatedConcentration);
        
        if (targetPollutantConcentration.isPresent()) {
            return calculateAQIWithIndexAndConcentrationRange(avgConcentration, targetPollutantConcentration.get());
        } else {
            return -1;
        }
        
    }
    
    /**
     * Calculate the AQI result
     *
     * @param pollutant        the pollutant code
     * @param avgConcentration the avg concentration
     * @return the AQIResult
     */
    public AQIResult getAQI(Pollutant pollutant, double avgConcentration) {
        pollutantBreakpoint = this.pollutantsBreakpoint.getBreakpointOfPollutant(pollutant);
        
        double truncatedConcentration = this.truncator.getTruncatedPollutantConcentrationBaseOnPollutant(pollutant,
                avgConcentration);
        targetPollutantConcentration = pollutantBreakpoint
                .getConcentrationRangeWithAvgConcentration(truncatedConcentration);
        int aqi = -1;
        String category = AQICalculatorConstants.UNCATEGORIZED;
        String meaning = AQICalculatorConstants.INVALID_CONCENTRATION_RANGE_MSG;
        String healthEffectsStatements = AQICalculatorConstants.NOT_APPLICABLE;
        String cautionaryStatements = AQICalculatorConstants.NOT_APPLICABLE;
        String color = AQICalculatorConstants.NOT_APPLICABLE;
        String sensitiveGroups = AQICalculatorConstants.NOT_APPLICABLE;

        if (targetPollutantConcentration.isPresent()) {
            aqi = calculateAQI(pollutant, avgConcentration);
            GeneralAQIInformation generalMessage = messageGenerator.getGeneralAQIMessageObjectOnAQILevel(aqi);
            SpecificAQILevelMessage specificAQILevelMessage = messageGenerator
                    .getSpecificAQILevelMessageOnAQILevelOfPollutant(pollutant, aqi);
            SensitiveGroups sensitiveGroupsInfo = messageGenerator.getSensitiveGroupsOfPollutant(pollutant);

            category = generalMessage.getCategory();
            meaning = generalMessage.getMeaning();
            color = generalMessage.getColor();
            healthEffectsStatements = specificAQILevelMessage.getHealthEffectsStatements();
            cautionaryStatements = specificAQILevelMessage.getCautionaryStatements();
            sensitiveGroups = sensitiveGroupsInfo.getSensitiveGroups();
        }
        return new AQIResult(pollutant, avgConcentration, aqi, category, meaning, color, healthEffectsStatements,
                cautionaryStatements, sensitiveGroups);
    }
    
    /**
     * Calculate the NowCast AQI result
     *
     * @param pollutant the pollutant code
     * @param data      the average concentration at the hours. The first value in the array is the avg value in the current hour, and the upcoming element in the array represent one step hour before current hour.
     * If the hour doesn't have data, replace missing data in the hour with -1.
     * @return the NowCast AQI result. The concentration in AQIResult is the NowCast concentration.
     * @see <a href="https://github.com/thanglequoc/aqi-calculator"> AQI Calculator Documentation</a> for example of usage
     */
    public AQIResult getNowCastAQI(Pollutant pollutant, double[] data) {
        pollutantBreakpoint = this.pollutantsBreakpoint.getBreakpointOfPollutant(pollutant);
        double nowCastConcentration = nowCastCalculator.getNowCastConcentration(pollutant, data);
        int aqi = -1;
        String category = AQICalculatorConstants.UNCATEGORIZED;
        String meaning = AQICalculatorConstants.INVALID_CONCENTRATION_RANGE_MSG;
        String healthEffectsStatements = AQICalculatorConstants.NOT_APPLICABLE;
        String cautionaryStatements = AQICalculatorConstants.NOT_APPLICABLE;
        String color = AQICalculatorConstants.NOT_APPLICABLE;
        String sensitiveGroups = AQICalculatorConstants.NOT_APPLICABLE;

        if (nowCastConcentration >= 0) {
            // find the target Concentration with it corresponding Index level
            targetPollutantConcentration = pollutantBreakpoint
                    .getConcentrationRangeWithAvgConcentration(nowCastConcentration);
            if (targetPollutantConcentration.isPresent()) {
                aqi = calculateAQIWithIndexAndConcentrationRange(nowCastConcentration,
                        targetPollutantConcentration.get());
                GeneralAQIInformation generalMessage = messageGenerator.getGeneralAQIMessageObjectOnAQILevel(aqi);
                SpecificAQILevelMessage specificAQILevelMessage = messageGenerator
                        .getSpecificAQILevelMessageOnAQILevelOfPollutant(pollutant, aqi);
                SensitiveGroups sensitiveGroupsInfo = messageGenerator.getSensitiveGroupsOfPollutant(pollutant);

                category = generalMessage.getCategory();
                meaning = generalMessage.getMeaning();
                color = generalMessage.getColor();
                healthEffectsStatements = specificAQILevelMessage.getHealthEffectsStatements();
                cautionaryStatements = specificAQILevelMessage.getCautionaryStatements();
                sensitiveGroups = sensitiveGroupsInfo.getSensitiveGroups();
            }
        }
        return new AQIResult(pollutant, nowCastConcentration, aqi, category, meaning, color, healthEffectsStatements,
                cautionaryStatements, sensitiveGroups);
        
    }
    
    private int calculateAQIWithIndexAndConcentrationRange(double rawConcentration,
                                                           PollutantConcentration concentration) {
        int iHigh = concentration.getIndex().getMaxIndex();
        int iLow = concentration.getIndex().getMinIndex();
        double cLow = concentration.getMinConcentration();
        double cHigh = concentration.getMaxConcentration();
        double rawAQI = (iHigh - iLow) / (cHigh - cLow) * (rawConcentration - cLow) + iLow;
        return (int) Math.round(rawAQI);
    }

    /**
     * Apply custom settings configuration and reinitialize AQI Messages base on custom settings file path
     * */
    public synchronized void applyCustomSettings(AQICustomSettings customSettings) {
        if (customSettings == null) {
            throw new IllegalArgumentException("User Settings must not be null");
        }
        if (customSettings.isInOverrideSettingMode()) {
            // reinitialized AQI messages dictionary
            try {
                this.messageGenerator = new AQIMessageGenerator(customSettings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reset the AQI Calculator to default setting, use the default message bundle files path. Also reset the current
     * custom user settings (if any) in the instance
     * */
    public synchronized void resetDefaultSettings() {
        this.customSettings = new AQICustomSettings();
        try {
            this.messageGenerator = new AQIMessageGenerator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the current setting of the AQI Calculator instance
     * @return the current user setting that being used by AQICalculator
     * */
    public AQICustomSettings getCustomSettings() {
        return this.customSettings;
    }
    
}
