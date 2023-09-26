package com.fitstir.fitstirapp.ui.health.recipes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Recipe;

import java.util.ArrayList;

public class RecipesViewModel extends ViewModel {

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
    private final MutableLiveData<Hit> firstHit = new MutableLiveData<>(new Hit());
    private final MutableLiveData<ArrayList<Recipe>> likedRecipes = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> instructionsList;
    private final MutableLiveData<UserProfileData> thisUser = new MutableLiveData<>(new UserProfileData());


    public RecipesViewModel() {
        mText = new MutableLiveData<>();
        getText().setValue("Search for an item.");

        instructionsList = new MutableLiveData<>();
        instructionsList.setValue(
            "*** These are generic instructions ***\n\n\n" +
            "\u2460 Preheat oven to 350\u00B0F.\n\n" +
            "\u2461 Prepare the marinade:\n" +
                "Whisk together the lime rind, lime juice, oil, ginger, and jalapeno.\n" +
                "Set aside a teaspoon of the mixture.\n\n" +
            "\u2462 Place salmon steaks into a dish just large enough to hold them.\n\n" +
            "\u2463 Pour remaining marinade mixture over salmon and turn to coat.\n\n" +
            "\u2464 Marinate at room temperature for 15 minutes, turning once.\n" +
                "(Do not marinate longer than 30 minutes or salmon will become mushy.)\n\n" +
            "\u2465 Bake for 15 minutes.\n" +
                "Turn oven to broil and broil for 3-4 minutes.\n" +
                "(Fish should flake easily with a fork.)\n\n" +
            "\u2466 Remove to platter and spoon reserved marinade over salmon.\n\n" +
            "\u2467 Serve immediately."
        );
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
    public MutableLiveData<String> getInstructionsList() {
        return instructionsList;
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
    public MutableLiveData<Hit> getFirstHit() {
        return firstHit;
    }
    public MutableLiveData<ArrayList<Recipe>> getLikedRecipes() {
        return likedRecipes;
    }

    public MutableLiveData<UserProfileData> getThisUser() {
        return thisUser;
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
    public void setFirstHit(Hit hit) {
        this.firstHit.setValue(hit);
    }
    public void setLikedRecipes(ArrayList<Recipe> likedRecipes) {
        this.likedRecipes.setValue(likedRecipes);
    }
    public void setThisUser(UserProfileData user) {
        this.thisUser.setValue(user);
    }
}