package com.fitstir.fitstirapp.ui.health.edamamapi.recipev2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Objects;

public class Recipe {
    private String uri;
    private String label;
    private String image;
    private Images images;
    private String source;
    private String url;
    private float yield;
    @JsonProperty("dietLabels")
    private ArrayList<String> dietLabels;
    @JsonProperty("healthLabels")
    private ArrayList<String> healthLabels;
    @JsonProperty("ingredientLines")
    private ArrayList<String> ingredientLines;
    private float calories;
    @JsonProperty("totalTime")
    private float totalTime;
    @JsonProperty("cuisineType")
    private ArrayList<String> cuisineType;
    @JsonProperty("mealType")
    private ArrayList<String> mealType;
    @JsonProperty("dishType")
    private ArrayList<String> dishType;
    private ArrayList<String> tags;

    public Recipe() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Recipe)) {
            return false;
        } else {
            Recipe recipe = (Recipe) o;
            int index = recipe.image.indexOf('?');
            return Float.compare(recipe.yield, yield) == 0 && Float.compare(recipe.calories, calories) == 0 && Float.compare(recipe.totalTime, totalTime) == 0 && Objects.equals(uri, recipe.uri) && Objects.equals(label, recipe.label) && Objects.equals(image.substring(0, index), recipe.image.substring(0, index)) && Objects.equals(images, recipe.images) && Objects.equals(source, recipe.source) && Objects.equals(url, recipe.url) && Objects.equals(dietLabels, recipe.dietLabels) && Objects.equals(healthLabels, recipe.healthLabels) && Objects.equals(ingredientLines, recipe.ingredientLines) && Objects.equals(cuisineType, recipe.cuisineType) && Objects.equals(mealType, recipe.mealType) && Objects.equals(dishType, recipe.dishType) && Objects.equals(tags, recipe.tags);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"uri\"=\"" + uri + "\"," +
                "\"label\"=\"" + label + "\"," +
                "\"image\"=\"" + image + "\"," +
                "\"images\"=" + images + "," +
                "\"source\"=\"" + source + "\"," +
                "\"url\"=\"" + url + "\"," +
                "\"yield\"=" + yield + "," +
                "\"dietLabels\"=" + dietLabels + "," +
                "\"healthLabels\"=" + healthLabels + "," +
                "\"ingredientLines\"=" + ingredientLines + "," +
                "\"calories\"=" + calories + "," +
                "\"totalTime\"=" + totalTime + "," +
                "\"cuisineType\"=" + cuisineType + "," +
                "\"mealType\"=" + mealType + "," +
                "\"dishType\"=" + dishType + "," +
                "\"tags\"=" + tags +
                "}";
    }

    public String getUri() { return uri; }
    public String getLabel() { return label; }
    public String getImage() { return image; }
    public Images getImages() { return images; }
    public String getSource() { return source; }
    public String getUrl() { return url; }
    public float getYield() { return yield; }
    public ArrayList<String> getDietLabels() { return dietLabels; }
    public ArrayList<String> getHealthLabels() { return healthLabels; }
    public ArrayList<String> getIngredientLines() { return ingredientLines; }
    public float getCalories() { return calories; }
    public float getTotalTime() { return totalTime; }
    public ArrayList<String> getCuisineType() { return cuisineType; }
    public ArrayList<String> getMealType() { return mealType; }
    public ArrayList<String> getDishType() { return dishType; }
    public ArrayList<String> getTags() { return tags; }

    public void setUri(String uri) { this.uri = uri; }
    public void setLabel(String label) { this.label = label; }
    public void setImage(String image) { this.image = image; }
    public void setImages(Images images) { this.images = images; }
    public void setSource(String source) { this.source = source; }
    public void setUrl(String url) { this.url = url; }
    public void setYield(float yield) { this.yield = yield; }
    public void setDietLabels(ArrayList<String> dietLabels) { this.dietLabels = dietLabels; }
    public void setHealthLabels(ArrayList<String> healthLabels) { this.healthLabels = healthLabels; }
    public void setIngredientLines(ArrayList<String> ingredientLines) { this.ingredientLines = ingredientLines; }
    public void setCalories(float calories) { this.calories = calories; }
    public void setTotalTime(float totalTime) { this.totalTime = totalTime; }
    public void setCuisineType(ArrayList<String> cuisineType) { this.cuisineType = cuisineType; }
    public void setMealType(ArrayList<String> mealType) { this.mealType = mealType; }
    public void setDishType(ArrayList<String> dishType) { this.dishType = dishType; }
    public void setTags(ArrayList<String> tags) { this.tags = tags; }
}
