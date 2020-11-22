package org.assignmentSeera.dataProvider;

public enum DateFormatProvider {
    ACCEPTED_FORMAT("dd-MM-YYYY"),
    UNACCEPTED_FORMAT("dd.MM.YYYY"),
    INVALID_FORMAT("D-M-YYY");

    private String dateFormat;

    DateFormatProvider(String dateFormatType) {
        this.dateFormat = dateFormatType;
    }

    public String getFormat(){
        return dateFormat;
    }
}
