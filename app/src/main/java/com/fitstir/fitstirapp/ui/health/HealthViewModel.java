package com.fitstir.fitstirapp.ui.health;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.Recipe;

import java.util.ArrayList;

public class HealthViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private final MutableLiveData<String> toSearchFor = new MutableLiveData<>("");
    private final MutableLiveData<String> minIngr = new MutableLiveData<>("");
    private final MutableLiveData<String> maxIngr = new MutableLiveData<>("");
    private final MutableLiveData<String> minCal = new MutableLiveData<>("");
    private final MutableLiveData<String> maxCal = new MutableLiveData<>("");
    private final MutableLiveData<String> minTime = new MutableLiveData<>("");
    private final MutableLiveData<String> maxTime = new MutableLiveData<>("");
    private final MutableLiveData<Integer> dietType = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> healthType = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> cuisineType = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> mealType = new MutableLiveData<>(0);

    private final MutableLiveData<ArrayList<Hit>> hits = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Recipe> clickedRecipe = new MutableLiveData<>(new Recipe());
    private final MutableLiveData<Recipe> firstRecipe = new MutableLiveData<>(new Recipe());


    public HealthViewModel() {
        mText = new MutableLiveData<>();
        getText().setValue("Search for an item.");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }


    public MutableLiveData<String> getMinIngr() {
        return minIngr;
    }
    public MutableLiveData<String> getMaxIngr() {
        return maxIngr;
    }
    public MutableLiveData<String> getMinCal() {
        return minCal;
    }
    public MutableLiveData<String> getMaxCal() {
        return maxCal;
    }
    public MutableLiveData<String> getMinTime() {
        return minTime;
    }
    public MutableLiveData<String> getMaxTime() {
        return maxTime;
    }
    public MutableLiveData<Integer> getDietType() {
        return dietType;
    }
    public MutableLiveData<Integer> getHealthType() {
        return healthType;
    }
    public MutableLiveData<Integer> getCuisineType() {
        return cuisineType;
    }
    public MutableLiveData<Integer> getMealType() {
        return mealType;
    }
    public MutableLiveData<String> getToSearchFor() {
        return toSearchFor;
    }
    public MutableLiveData<ArrayList<Hit>> getHits() {
        return hits;
    }
    public MutableLiveData<Recipe> getClickedRecipe() {
        return clickedRecipe;
    }
    public MutableLiveData<Recipe> getFirstRecipe() {
        return firstRecipe;
    }

    public void setMinIngr(String minIngr) { this.minIngr.setValue(minIngr); }
    public void setMaxIngr(String maxIngr) { this.maxIngr.setValue(maxIngr); }
    public void setMinCal(String minCal) { this.minCal.setValue(minCal); }
    public void setMaxCal(String maxCal) { this.maxCal.setValue(maxCal); }
    public void setMinTime(String minTime) { this.minTime.setValue(minTime); }
    public void setMaxTime(String maxTime) { this.maxTime.setValue(maxTime); }
    public void setDietType(Integer dietType) { this.dietType.setValue(dietType); }
    public void setHealthType(Integer healthType) { this.healthType.setValue(healthType); }
    public void setCuisineType(Integer cuisineType) { this.cuisineType.setValue(cuisineType); }
    public void setMealType(Integer mealType) { this.mealType.setValue(mealType); }
    public void setToSearchFor(String toSearchFor) {
        this.toSearchFor.setValue(toSearchFor);
    }
    public void setHits(ArrayList<Hit> hits) {
        this.hits.setValue(hits);
    }
    public void setClickedRecipe(Recipe recipe) {
        this.clickedRecipe.setValue(recipe);
    }
    public void setFirstRecipe(Recipe recipe) {
        this.firstRecipe.setValue(recipe);
    }
}