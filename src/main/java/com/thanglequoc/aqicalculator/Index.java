package com.thanglequoc.aqicalculator;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represent <i>upper bound index</i> (<b>I-high</b>) and <i>lower bound
 * index</i> (<b>I-low</b>) of a corresponding range in
 * <tt>PollutantBreakpoint</tt> of a pollutant
 *
 * @author thanglequoc
 */
class Index {
    
    private int min;
    private int max;
    
    Index(int min, int max) {
        this.min = min;
        this.max = max;
    }
    
    int getMinIndex() {
        return min;
    }
    
    int getMaxIndex() {
        return max;
    }
    
    static Index fromIndexNode(JsonNode indexNode) {
        int minIndex = indexNode.path(AQICalculatorConstants.MIN).asInt();
        int maxIndex = indexNode.path(AQICalculatorConstants.MAX).asInt();
        return new Index(minIndex, maxIndex);
    }
    
}
