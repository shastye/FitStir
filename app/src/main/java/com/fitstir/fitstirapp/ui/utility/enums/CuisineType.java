package com.fitstir.fitstirapp.ui.utility.enums;

public enum CuisineType {
    BLANK("", 0, ""),
    AMERICAN("American", 1, "american"),
    ASIAN("Asian", 2, "asian"),
    BRITISH("British", 3, "british"),
    CARIBBEAN("Caribbean", 4, "caribbean"),
    CENTRAL_EUROPE("Central Europe", 5, "central europe"),
    CHINESE("Chinese", 6, "chinese"),
    EASTERN_EUROPE("Eastern Europe", 7, "eastern europe"),
    FRENCH("French", 8, "french"),
    GREEK("Greek", 9, "greek"),
    INDIAN("Indian", 10, "indian"),
    ITALIAN("Italian", 11, "italian"),
    JAPANESE("Japanese", 12, "japanese"),
    KOREAN("Korean", 13, "korean"),
    KOSHER("Kosher", 14, "kosher"),
    MEDITERRANEAN("Mediterranean", 15, "mediterranean"),
    MEXICAN("Mexican", 16, "mexican"),
    MIDDLE_EASTERN("Middle Eastern", 17, "middle eastern"),
    NORDIC("Nordic", 18, "nordic"),
    SOUTH_AMERICAN("South American", 19, "south american"),
    SOUTH_EAST_ASIAN("South East Asian", 20, "south east asian"),
    WORLD("World", 21, "world");

    private final String spinnerTitle;
    private final int value;
    private final String key;

    private CuisineType(String spinnerTitle, int value, String key) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.key = key;
    }

    public String getSpinnerTitle() { return this.spinnerTitle; }
    public int getValue() { return this.value; }
    public String getKey() { return this.key; }
}
