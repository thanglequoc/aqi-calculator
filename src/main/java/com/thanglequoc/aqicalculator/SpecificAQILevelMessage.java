package com.thanglequoc.aqicalculator;

/**
 * Object to store information for a specific pollutant code at different AQI
 * level
 *
 * @author thanglequoc
 */
class SpecificAQILevelMessage {
    
    private Index index;
    private String category;
    private String healthEffectsStatements;
    private String cautionaryStatements;
    
    SpecificAQILevelMessage(Index index, String category, String healthEffectsStatements, String cautionaryStatements) {
        this.index = index;
        this.category = category;
        this.healthEffectsStatements = healthEffectsStatements;
        this.cautionaryStatements = cautionaryStatements;
    }
    
    Index getIndex() {
        return index;
    }
    
    String getCategory() {
        return category;
    }
    
    String getHealthEffectsStatements() {
        return healthEffectsStatements;
    }
    
    String getCautionaryStatements() {
        return cautionaryStatements;
    }
    
}
