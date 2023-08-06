package com.fitstir.fitstirapp.ui.utility.enums;

public enum NutritionType {
    COOKING("Cooking", 0, "cooking"),
    LOGGING("Logging", 1, "logging");

    private final String spinnerTitle;
    private final int value;
    private final String key;

    private NutritionType(String spinnerTitle, int value, String key) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.key = key;
    }

    public String getSpinnerTitle() { return this.spinnerTitle; }
    public int getValue() { return this.value; }
    public String getKey() { return this.key; }
}
