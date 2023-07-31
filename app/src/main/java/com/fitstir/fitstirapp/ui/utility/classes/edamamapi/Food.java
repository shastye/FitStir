package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.ArrayList;
import java.util.Objects;

public class Food {
    private String foodID;
    private String label;
    private String knownAs;
    private Nutrients nutrients;
    private String category;
    private String categoryLabel;

    private String image;
    private String foodContentsLabel;
    private ArrayList<ServingSize> servingSizes;
    private int servingsPerContainer;

    public Food() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Food)) {
            return false;
        } else {
            Food food = (Food) o;
            return servingsPerContainer == food.servingsPerContainer && Objects.equals(foodID, food.foodID) && Objects.equals(label, food.label) && Objects.equals(knownAs, food.knownAs) && Objects.equals(nutrients, food.nutrients) && Objects.equals(category, food.category) && Objects.equals(categoryLabel, food.categoryLabel) && Objects.equals(image, food.image) && Objects.equals(foodContentsLabel, food.foodContentsLabel) && Objects.equals(servingSizes, food.servingSizes);
        }
    }

    public String getFoodID() { return foodID; }
    public String getLabel() { return label; }
    public String getKnownAs() { return knownAs; }
    public Nutrients getNutrients() { return nutrients; }
    public String getCategory() { return category; }
    public String getCategoryLabel() { return categoryLabel; }
    public String getImage() { return image; }
    public String getFoodContentsLabel() { return foodContentsLabel; }
    public ArrayList<ServingSize> getServingSizes() { return servingSizes; }
    public int getServingsPerContainer() { return servingsPerContainer; }

    public void setFoodID(String foodID) { this.foodID = foodID; }
    public void setLabel(String label) { this.label = label; }
    public void setKnownAs(String knownAs) { this.knownAs = knownAs; }
    public void setNutrients(Nutrients nutrients) { this.nutrients = nutrients; }
    public void setCategory(String category) { this.category = category; }
    public void setCategoryLabel(String categoryLabel) { this.categoryLabel = categoryLabel; }
    public void setImage(String image) { this.image = image; }
    public void setFoodContentsLabel(String foodContentsLabel) { this.foodContentsLabel = foodContentsLabel; }
    public void setServingSizes(ArrayList<ServingSize> servingSizes) { this.servingSizes = servingSizes; }
    public void setServingsPerContainer(int servingsPerContainer) { this.servingsPerContainer = servingsPerContainer; }
}
