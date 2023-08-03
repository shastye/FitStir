package com.fitstir.fitstirapp.ui.utility.enums;

public enum MealType {
    BREAKFAST("Breakfast", 0, "breakfast"),
    TEATIME("Tea Time", 1, "teatime"),
    BRUNCH("Brunch", 2, "brunch"),
    LUNCH("Lunch", 3, "lunch/dinner"),
    SNACK("Snack", 4, "snack"),
    DINNER("Dinner", 5, "lunch/dinner");

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
