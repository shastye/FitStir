package com.fitstir.fitstirapp.ui.utility.enums;

public enum DietOptions {
    BALANCED("Balanced", 0, "balanced"),
    HIGH_FIBER("High Fiber", 1, "high-fiber"),
    HIGH_PROTEIN("High Protein", 2, "high-protein"),
    LOW_CARB("Low Carb", 3, "low-carb"),
    LOW_FAT("Low Fat", 4, "low-fat"),
    LOW_SODIUM("Low Sodium", 5, "low-sodium");

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
