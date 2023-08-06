package com.fitstir.fitstirapp.ui.utility.enums;

public enum CuisineType {
    AMERICAN("American", 0, "american"),
    ASIAN("Asian", 1, "asian"),
    BRITISH("British", 2, "british"),
    CARIBBEAN("Caribbean", 3, "caribbean"),
    CENTRAL_EUROPE("Central Europe", 4, "central europe"),
    CHINESE("Chinese", 5, "chinese"),
    EASTERN_EUROPE("Eastern Europe", 6, "eastern europe"),
    FRENCH("French", 7, "french"),
    GREEK("Greek", 8, "greek"),
    INDIAN("Indian", 9, "indian"),
    ITALIAN("Italian", 10, "italian"),
    JAPANESE("Japanese", 11, "japanese"),
    KOREAN("Korean", 12, "korean"),
    KOSHER("Kosher", 13, "kosher"),
    MEDITERRANEAN("Mediterranean", 14, "mediterranean"),
    MEXICAN("Mexican", 15, "mexican"),
    MIDDLE_EASTERN("Middle Eastern", 16, "middle eastern"),
    NORDIC("Nordic", 17, "nordic"),
    SOUTH_AMERICAN("South American", 18, "south american"),
    SOUTH_EAST_ASIAN("South East Asian", 19, "south east asian"),
    WORLD("World", 20, "world");

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
