package com.fitstir.fitstirapp.ui.health.edamamapi.enums;

public enum DietOptions {
    BLANK("", 0, ""),
    BALANCED("Balanced", 1, "balanced"),
    HIGH_FIBER("High Fiber", 2, "high-fiber"),
    HIGH_PROTEIN("High Protein", 3, "high-protein"),
    LOW_CARB("Low Carb", 4, "low-carb"),
    LOW_FAT("Low Fat", 5, "low-fat"),
    LOW_SODIUM("Low Sodium", 6, "low-sodium");

    private final String spinnerTitle;
    private final int value;
    private final String key;

    private DietOptions(String spinnerTitle, int value, String key) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.key = key;
    }

    public String getSpinnerTitle() { return spinnerTitle; }
    public int getValue() { return value; }
    public String getKey() { return key; }
}
