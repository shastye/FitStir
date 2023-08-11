package com.fitstir.fitstirapp.ui.health.edamamapi.enums;

public enum DishType {
    ALCOHOL_COCKTAIL("Alcoholic Cocktails", 0, "alcohol cocktail"),
    BISCUITS_AND_COOKIES("Biscuits/Cookies", 1, "biscuits and cookies"),
    BREAD("Bread", 2, "bread"),
    CEREALS("Cereals", 3, "cereals"),
    CONDIMENTS_AND_SAUCES("Condiments/Sauces", 4, "condiments and sauces"),
    DESSERTS("Desserts", 5, "desserts"),
    DRINKS("Drinks", 6, "drinks"),
    EGG("Egg", 7, "egg"),
    ICE_CREAM_AND_CUSTARD("Ice Cream/Custards", 8, " ice cream and custard"),
    MAIN_COURSE("Main Courses", 9, "main course"),
    PANCAKE("Pancakes", 10, "pancake"),
    PASTA("Pasta", 11, "pasta"),
    PASTRY("Pastries", 12, "pastry"),
    PIES_AND_TARTS("Pies/Tarts", 13, "pies and tarts"),
    PIZZA("Pizza", 14, "pizza"),
    PREPS("Preps", 15, "preps"),
    PRESERVE("Preserves", 16, "preserve"),
    SALAD("Salads", 17, "salad"),
    SANDWICHES("Sandwiches", 18, "sandwiches"),
    SEAFOOD("Seafood", 19, "seafood"),
    SIDE_DISH("Side Dishes", 20, "side dish"),
    SOUP("Soups", 21, "soup"),
    SPECIAL_OCCASIONS("Special Occasions", 22, "special occasions"),
    STARTER("Starters", 23, "starter"),
    SWEETS("Sweets", 24, "sweets");

    private final String spinnerTitle;
    private final int value;
    private final String key;

    private DishType(String spinnerTitle, int value, String key) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.key = key;
    }

    public String getSpinnerTitle() { return this.spinnerTitle; }
    public int getValue() { return this.value; }
    public String getKey() { return this.key; }
}
