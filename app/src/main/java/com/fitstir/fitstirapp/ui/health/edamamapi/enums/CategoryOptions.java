package com.fitstir.fitstirapp.ui.health.edamamapi.enums;

public enum CategoryOptions {
    BLANK("", 0, ""),
    GENERIC_FOODS("Generic Foods", 1, "generic-foods"),
    PACKAGED_FOODS("Packaged Foods", 2, "packaged-foods"),
    GENERIC_MEALS("Generic Meals", 3, "generic-meals"),
    FAST_FOODS("Fast Foods", 4, "fast-foods");

    private final String spinnerTitle;
    private final int value;
    private final String key;

    private CategoryOptions(String spinnerTitle, int value, String key) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.key = key;
    }

    public String getSpinnerTitle() { return this.spinnerTitle; }
    public int getValue() { return this.value; }
    public String getKey() { return this.key; }
}
