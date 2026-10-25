package com.thanglequoc.aqicalculator;

/**
 * Object to map to json file, contain an index and it corresponding category
 * and message
 *
 * @author thanglequoc
 */

class GeneralAQIInformation {
    
    private Index index;
    private String category;
    private String meaning;
    private String color;
    
    GeneralAQIInformation(Index index, String category, String message, String color) {
        this.index = index;
        this.category = category;
        this.meaning = message;
        this.color = color;
    }
    
    Index getIndex() {
        return index;
    }
    
    String getCategory() {
        return category;
    }
    
    String getMeaning() {
        return meaning;
    }

    String getColor() {
        return color;
    }
}
