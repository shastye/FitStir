package com.fitstir.fitstirapp.ui.health.edamamapi.enums;

public enum SearchOptions {
    FOOD("Food Item", 0),
    RECIPES("Recipes", 1);

    private final String spinnerTitle;
    private final int value;

    private SearchOptions(String spinnerTitle, int value) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
    }

    public String getSpinnerTitle() { return this.spinnerTitle; }
    public int getValue() { return this.value; }
}
