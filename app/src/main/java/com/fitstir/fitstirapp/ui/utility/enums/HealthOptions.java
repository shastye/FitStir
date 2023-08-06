package com.fitstir.fitstirapp.ui.utility.enums;

public enum HealthOptions {
    ALCOHOL_FREE("Alcohol Free", 0, "alcohol-free"),
    CELERY_FREE("Celery Free", 1, "celery-free"),
    CRUSTACEAN_FREE("Crustacean Free", 2, "crustacean-free"),
    DAIRY_FREE("Dairy Free", 3, "dairy-free"),
    DASH("Dietary Approaches to Stop Hypertension", 4, "DASH"),
    EGG_FREE("Egg Free", 4, "egg-free"),
    FISH_FREE("Fish Free", 5, "fish-free"),
    FODMAP_FREE("No FODMAP Foods", 6, "fodmap-free"),
    GLUTEN_FREE("Gluten Free", 7, "gluten-free"),
    KETO_FRIENDLY("Keto Friendly", 8, "keto-friendly"),
    KIDNEY_FRIENDLY("Kidney Friendly", 9, "kidney-friendly"),
    KOSHER("Kosher", 10, "kosher"),
    LOW_POTASSIUM("Low Potassium", 11, "low-potassium"),
    LUPINE_FREE("Lupine Free", 12, "lupine-free"),
    MUSTARD_FREE("Mustard Free", 13, "mustard-free"),
    LOW_FAT("Low Fat", 14, "low-fat-abs"),
    NO_ADDED_OIL("No Added Oils", 15, "No-oil-added"),
    LOW_SUGAR("No Simple Sugars", 16, "low-sugar"),
    PALEO("Paleo", 17, "paleo"),
    PEANUT_FREE("Peanut Free", 18, "peanut-free"),
    PESCATARIAN("Pescatarian", 19, "pecatarian"),
    PORK_FREE("Pork Free", 20, "pork-free"),
    RED_MEAT_FREE("Red Meat Free", 21, "red-meat-free"),
    SESAME_FREE("Sesame Free", 22, "sesame-free"),
    SHELLFISH_FREE("Shellfish Free", 23, "shellfish-free"),
    SOY_FREE("Soy Free", 24, "soy-free"),
    SUGER_CONSCIOUS("Sugar Conscientious", 25, "sugar-conscious"),
    TREE_NUT_FREE("Tree Nut Free", 26, "tree-nut-free"),
    VEGAN("Vegan", 27, "vegan"),
    VEGETARIAN("Vegetarian", 28, "vegetarian"),
    WHEAT_FREE("Wheat Free", 29, "wheat-free");


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
