package com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser;

import java.util.ArrayList;
import java.util.Objects;

public class Food {
    private String foodId;
    private String label;
    private String knownAs;
    private Nutrients nutrients;
    private String category;
    private String categoryLabel;

    private String brand;
    private String image;
    private String foodContentsLabel;
    private ArrayList<ServingSize> servingSizes;
    private float servingsPerContainer;

    public Food() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Food)) {
            return false;
        } else {
            Food food = (Food) o;
            return servingsPerContainer == food.servingsPerContainer && Objects.equals(foodId, food.foodId) && Objects.equals(label, food.label) && Objects.equals(knownAs, food.knownAs) && Objects.equals(nutrients, food.nutrients) && Objects.equals(category, food.category) && Objects.equals(categoryLabel, food.categoryLabel) && Objects.equals(image, food.image) && Objects.equals(foodContentsLabel, food.foodContentsLabel) && Objects.equals(servingSizes, food.servingSizes);
        }
    }

    @Override
    public String toString() {
        String string = "{";

        if (foodId != null) {
            string += "\"foodId\":\"" + foodId + "\",";
        }
        if (label != null) {
            string += "\"label\":\"" + label + "\",";
        }
        if (knownAs != null) {
            string += "\"knownAs\":\"" + knownAs + "\",";
        }
        if (nutrients != null) {
            string += "\"nutrients\":" + nutrients + ",";
        }
        if (brand != null) {
            string += "\"brand\":\"" + brand + "\",";
        }
        if (category != null) {
            string += "\"category\":\"" + category + "\",";
        }
        if (categoryLabel != null) {
            string += "\"categoryLabel\":\"" + categoryLabel + "\",";
        }
        if (image != null) {
            string += "\"image\":\"" + image + "\",";
        }
        if (foodContentsLabel != null) {
            string += "\"foodContentsLabel\":\"" + foodContentsLabel + "\",";
        }
        if (servingSizes != null) {
            string += "\"servingSizes\":" + servingSizes + ",";
        }
        if (servingsPerContainer != 0) {
            string += "\"servingsPerContainer\":" + servingsPerContainer + ",";
        }

        if (string.length() > 1) {
            string = string.substring(0, string.length() - 1);
        }

        string += "}";

        return string;
    }

    public String getFoodId() { return foodId; }
    public String getLabel() { return label; }
    public String getKnownAs() { return knownAs; }
    public Nutrients getNutrients() { return nutrients; }
    public String getCategory() { return category; }
    public String getCategoryLabel() { return categoryLabel; }
    public String getBrand() { return brand; }
    public String getImage() { return image; }
    public String getFoodContentsLabel() { return foodContentsLabel; }
    public ArrayList<ServingSize> getServingSizes() { return servingSizes; }
    public float getServingsPerContainer() { return servingsPerContainer; }

    public void setFoodId(String foodID) { this.foodId = foodID; }
    public void setLabel(String label) { this.label = label; }
    public void setKnownAs(String knownAs) { this.knownAs = knownAs; }
    public void setNutrients(Nutrients nutrients) { this.nutrients = nutrients; }
    public void setCategory(String category) { this.category = category; }
    public void setCategoryLabel(String categoryLabel) { this.categoryLabel = categoryLabel; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setImage(String image) { this.image = image; }
    public void setFoodContentsLabel(String foodContentsLabel) { this.foodContentsLabel = foodContentsLabel; }
    public void setServingSizes(ArrayList<ServingSize> servingSizes) { this.servingSizes = servingSizes; }
    public void setServingsPerContainer(float servingsPerContainer) { this.servingsPerContainer = servingsPerContainer; }
}
