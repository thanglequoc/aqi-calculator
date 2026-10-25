package com.thanglequoc.aqicalculator;

/**
 * PollutantConcentrationTruncator which will support the truncation for
 * pollutant based on <b>US EPA</b> rule
 * <p>
 *
 * @author thanglequoc
 * @see <a href="https://github.com/thanglequoc/aqi-calculator"> AQI Calculator
 * Guide</a> , section <b>AQI Calculation Turtorial </b>
 */
class PollutantConcentrationTruncator {
    
    /**
     * Gets the truncated pollutant concentration on pollutant code.
     * <p>
     * For <i>PM10, SO2, NO2</i>, truncate to integer <br>
     * For <i>PM2.5, CO</i>, truncate to 1 decimal place <br>
     * For <i>Ozone</i>, truncate to 3 decimal place
     *
     * @param pollutant     the pollutant (<i>PM10, PM2.5, O3, CO, NO2, SO2</i>)
     * @param concentration the concentration
     * @return the truncated concentration base on pollutant
     */
    double getTruncatedPollutantConcentrationBaseOnPollutant(Pollutant pollutant, double concentration) {
        if (Pollutant.PM10.equals(pollutant) || Pollutant.SO2.equals(pollutant) || Pollutant.NO2.equals(pollutant)) {
            return Math.round(concentration);
        }
        if (Pollutant.CO.equals(pollutant) || Pollutant.PM25.equals(pollutant)) {
            return Math.round(concentration * 10d) / 10d;
        }
        return Math.round(concentration * 1000d) / 1000d;
        
    }
    
}
