package com.thanglequoc.aqicalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * PollutantsBreakpoint, which represent the whole AQI breakpoint table
 * <p>
 * It contain a list of smaller <tt>PollutantBreakpoint</tt>, each
 * <tt>PollutantBreakpoint</tt> in this list represent <b>breakpoint</b> of a
 * pollutant code
 *
 * @author thanglequoc
 */
class PollutantsBreakpoint {
    
    private List<PollutantBreakpoint> breakpoints;
    
    /**
     * Instantiates a new pollutants breakpoint .
     */
    PollutantsBreakpoint() {
        breakpoints = new ArrayList<>();
    }
    
    void addPollutantBreakpoint(PollutantBreakpoint pollutantBreakpoint) {
        this.breakpoints.add(pollutantBreakpoint);
    }
    
    PollutantBreakpoint getBreakpointOfPollutant(Pollutant pollutant) {
        for (PollutantBreakpoint pollutantBreakpoint : breakpoints) {
            if (pollutantBreakpoint.getPollutant().equals(pollutant)) {
                return pollutantBreakpoint;
            }
        }
        return null;
    }
}
