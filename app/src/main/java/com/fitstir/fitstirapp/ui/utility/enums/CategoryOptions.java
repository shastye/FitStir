package com.fitstir.fitstirapp.ui.utility.enums;

public enum CategoryOptions {
    GENERIC_FOODS("Generic Foods", 0, "generic-foods"),
    PACKAGED_FOODS("Packaged Foods", 1, "packaged-foods"),
    GENERIC_MEALS("Generic Meals", 2, "generic-meals"),
    FAST_FOODS("Fast Foods", 3, "fast-foods");

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
