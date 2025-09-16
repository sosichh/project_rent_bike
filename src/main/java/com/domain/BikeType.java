package com.domain;

public enum BikeType {
    MOUNTAIN("горный"),
    GRAVEL("гравийный"),
    ROAD("шоссейный"),
    CITY("городской"),
    ELECTRIC("электрический");

    private final String russianType;

    BikeType(String russianType) {
        this.russianType = russianType;
    }

    public String getRussianName() {
        return russianType;
    }
}