package com.fitstir.fitstirapp.ui.health.edamamapi.enums;

public enum HealthOptions {
    BLANK("", 0, ""),
    ALCOHOL_FREE("Alcohol Free", 1, "alcohol-free"),
    CELERY_FREE("Celery Free", 2, "celery-free"),
    CRUSTACEAN_FREE("Crustacean Free", 3, "crustacean-free"),
    DAIRY_FREE("Dairy Free", 4, "dairy-free"),
    DASH("Dietary Approaches to Stop Hypertension", 5, "DASH"),
    EGG_FREE("Egg Free", 6, "egg-free"),
    FISH_FREE("Fish Free", 7, "fish-free"),
    FODMAP_FREE("No FODMAP Foods", 8, "fodmap-free"),
    GLUTEN_FREE("Gluten Free", 9, "gluten-free"),
    KETO_FRIENDLY("Keto Friendly", 10, "keto-friendly"),
    KIDNEY_FRIENDLY("Kidney Friendly", 11, "kidney-friendly"),
    KOSHER("Kosher", 12, "kosher"),
    LOW_POTASSIUM("Low Potassium", 13, "low-potassium"),
    LUPINE_FREE("Lupine Free", 14, "lupine-free"),
    MUSTARD_FREE("Mustard Free", 15, "mustard-free"),
    LOW_FAT("Low Fat", 16, "low-fat-abs"),
    NO_ADDED_OIL("No Added Oils", 17, "No-oil-added"),
    LOW_SUGAR("No Simple Sugars", 18, "low-sugar"),
    PALEO("Paleo", 19, "paleo"),
    PEANUT_FREE("Peanut Free", 20, "peanut-free"),
    PESCATARIAN("Pescatarian", 21, "pecatarian"),
    PORK_FREE("Pork Free", 22, "pork-free"),
    RED_MEAT_FREE("Red Meat Free", 23, "red-meat-free"),
    SESAME_FREE("Sesame Free", 24, "sesame-free"),
    SHELLFISH_FREE("Shellfish Free", 25, "shellfish-free"),
    SOY_FREE("Soy Free", 26, "soy-free"),
    SUGER_CONSCIOUS("Sugar Conscientious", 27, "sugar-conscious"),
    TREE_NUT_FREE("Tree Nut Free", 28, "tree-nut-free"),
    VEGAN("Vegan", 29, "vegan"),
    VEGETARIAN("Vegetarian", 30, "vegetarian"),
    WHEAT_FREE("Wheat Free", 31, "wheat-free");


    private final String spinnerTitle;
    private final int value;
    private final String key;

    private HealthOptions(String spinnerTitle, int value, String key) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.key = key;
    }

    public String getSpinnerTitle() { return this.spinnerTitle; }
    public int getValue() { return this.value; }
    public String getKey() { return this.key; }
}
