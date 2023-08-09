package com.fitstir.fitstirapp.ui.utility.enums;

public enum MealType {
    BLANK("", 0, ""),
    BREAKFAST("Breakfast", 1, "breakfast"),
    TEATIME("Tea Time", 2, "teatime"),
    BRUNCH("Brunch", 3, "brunch"),
    LUNCH("Lunch", 4, "lunch/dinner"),
    SNACK("Snack", 5, "snack"),
    DINNER("Dinner", 6, "lunch/dinner");

    private final String spinnerTitle;
    private final int value;
    private final String key;

    private MealType(String spinnerTitle, int value, String key) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.key = key;
    }

    public String getSpinnerTitle() { return this.spinnerTitle; }
    public int getValue() { return this.value; }
    public String getKey() { return this.key; }
}
