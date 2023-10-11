package com.fitstir.fitstirapp.ui.health.calorietracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.MealType;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.FoodResponse;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.classes.Users;

import java.util.ArrayList;
import java.util.Calendar;

public class CalorieTrackerViewModel extends ViewModel {

    private final MutableLiveData<Users> thisUser = new MutableLiveData<>(new Users());
    private final MutableLiveData<Calendar> selectedDate = new MutableLiveData<>(Calendar.getInstance()); //set to today initially
    private final MutableLiveData<String> dateString = new MutableLiveData<>("Today");
    private final MutableLiveData<ArrayList<ResponseInfo>> calorieTrackerData = new MutableLiveData<>(new ArrayList<ResponseInfo>());
    private final MutableLiveData<Integer> calorieTrackerGoal = new MutableLiveData<>(2000);
    private final MutableLiveData<Integer> suggestedGoal = new MutableLiveData<>(2000);
    private final MutableLiveData<ArrayList<ResponseInfo>> clickedArray = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Float> calorieSum = new MutableLiveData<>(0.0f);
    private final MutableLiveData<Float> carbSum = new MutableLiveData<>(0.0f);
    private final MutableLiveData<Float> fatSum = new MutableLiveData<>(0.0f);
    private final MutableLiveData<Float> proteinSum = new MutableLiveData<>(0.0f);

    private final MutableLiveData<ResponseInfo> clickedResult = new MutableLiveData<>(null);

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
    private final MutableLiveData<Integer> nutritionType = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> categoryType = new MutableLiveData<>(0);
    private final MutableLiveData<ArrayList<Parsed>> parsed = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<ArrayList<Hint>> hints = new MutableLiveData<>(new ArrayList<>());



    private final MutableLiveData<String> mText;
    public CalorieTrackerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is explore fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }



    public MutableLiveData<Users> getThisUser() {
        return thisUser;
    }
    public void setThisUser(Users user) {
        thisUser.setValue(user);
    }

    public MutableLiveData<Calendar> getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(Calendar calendar) {
        selectedDate.setValue(calendar);
    }

    public MutableLiveData<ArrayList<ResponseInfo>> getCalorieTrackerData() {
        return calorieTrackerData;
    }
    public void setCalorieTrackerData(ArrayList<ResponseInfo> data) {
        calorieTrackerData.setValue(data);
    }

    public MutableLiveData<String> getDateString() {
        return dateString;
    }
    public void setDateString(String dateAsString) {
        dateString.setValue(dateAsString);
    }

    public MutableLiveData<Integer> getCalorieTrackerGoal() {
        return calorieTrackerGoal;
    }
    public void setCalorieTrackerGoal(int value) {
        this.calorieTrackerGoal.setValue(value);
    }

    public MutableLiveData<Integer> getSuggestedGoal() {
        return suggestedGoal;
    }
    public void setSuggestedGoal(int goal) {
        suggestedGoal.setValue(goal);
    }

    public MutableLiveData<ArrayList<ResponseInfo>> getClickedArray() {
        return clickedArray;
    }
    public void setClickedArray(ArrayList<ResponseInfo> data) {
        this.clickedArray.setValue(data);
    }

    public MutableLiveData<Float> getCalorieSum() { return calorieSum; }
    public void setCalorieSum(float sum) { this.calorieSum.setValue(sum); }

    public MutableLiveData<Float> getCarbSum() { return carbSum; }
    public void setCarbSum(float sum) { this.carbSum.setValue(sum); }

    public MutableLiveData<Float> getFatSum() { return fatSum; }
    public void setFatSum(float sum) { this.fatSum.setValue(sum); }

    public MutableLiveData<Float> getProteinSum() { return proteinSum; }
    public void setProteinSum(float sum) { this.proteinSum.setValue(sum); }

    public void setClickedResult(ResponseInfo response) {
        this.clickedResult.setValue(response);
    }
    public MutableLiveData<ResponseInfo> getClickedResult() {
        return clickedResult;
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
    public void setNutritionType(Integer nutritionType) { this.nutritionType.setValue(nutritionType); }
    public void setCategoryType(Integer categoryType) { this.categoryType.setValue(categoryType); }
    public MutableLiveData<Integer> getNutritionType() {
        return nutritionType;
    }
    public MutableLiveData<Integer> getCategoryType() {
        return categoryType;
    }
    public void setParsed(ArrayList<Parsed> parsed) {
        this.parsed.setValue(parsed);
    }
    public void setHints(ArrayList<Hint> hints) {
        this.hints.setValue(hints);
    }
    public MutableLiveData<ArrayList<Hint>> getHints() {
        return hints;
    }
    public MutableLiveData<ArrayList<Parsed>> getParsed() {
        return parsed;
    }



    public ArrayList<ResponseInfo> getGenericData() {
        ArrayList<ResponseInfo> data = new ArrayList<ResponseInfo>();

        String R1 = "{\"text\":\"2 large eggs\"," +
                "\"parsed\":" +
                "[" +
                "{" +
                "\"food\":" +
                "{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":" +
                "{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":43.0,\"qualified\":[{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\",\"label\":\"large\"}],\"weight\":50.0}]}" +
                "}" +
                "]," + "\"hints\":[{\"food\":{\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\",\"label\":\"Egg\",\"knownAs\":\"egg\",\"nutrients\":{\"ENERC_KCAL\":143.0,\"PROCNT\":12.6,\"FAT\":9.51,\"CHOCDF\":0.72,\"FIBTG\":0.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\",\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":43.0,\"qualified\":[{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_extra_large\",\"label\":\"extra large\"}],\"weight\":56.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\",\"label\":\"large\"}],\"weight\":50.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_small\",\"label\":\"small\"}],\"weight\":38.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_medium\",\"label\":\"medium\"}],\"weight\":44.0}]},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_jumbo\",\"label\":\"Jumbo\",\"weight\":63.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_cup\",\"label\":\"Cup\",\"weight\":243.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_liter\",\"label\":\"Liter\",\"weight\":1027.10093956848}]},{\"food\":{\"foodId\":\"food_a4vwb7xagt9toib6ax2rjao2p1dv\",\"label\":\"2 Large Eggs\",\"knownAs\":\"2 Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":184.18367346938777,\"PROCNT\":11.224489795918368,\"FAT\":6.63265306122449,\"CHOCDF\":21.93877551020408,\"FIBTG\":2.5510204081632653},\"brand\":\"Mimi's Cafe\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":196.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":196.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bv61ls3bw0eon1azwl78aavqhmsp\",\"label\":\"Ener-g Egg Replacer for 2 Large Eggs\",\"knownAs\":\"egg substitute\",\"nutrients\":{\"ENERC_KCAL\":375.0,\"PROCNT\":0.0,\"FAT\":0.0,\"CHOCDF\":100.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":4.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_replacer\",\"label\":\"Replacer\",\"weight\":4.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_teaspoon\",\"label\":\"Teaspoon\",\"weight\":2.6708333334688},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_tablespoon\",\"label\":\"Tablespoon\",\"weight\":8.01249999986453},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_cup\",\"label\":\"Cup\",\"weight\":128.2}]},{\"food\":{\"foodId\":\"food_abeyqhpa8umqssbcyh1i8btnwes8\",\"label\":\"2 Large Fresh Eggs\",\"knownAs\":\"2 Large Fresh Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0,\"FIBTG\":0.0},\"brand\":\"Perkins\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":100.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":100.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_buv4ac0an6ud3cbfs1agbbhh25u6\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"PENNY SMART\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"EGGS\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bh53an9br1otxqaorzqh8axt9gtf\",\"label\":\"Large Eggs\",\"knownAs\":\"Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"The Kroger Co.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a0a42e9bdmcsiablxszuvabooien\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Safeway Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"CONTAINS: EGGS.\",\"image\":\"https://www.edamam.com/food-img/b9c/b9c26bcf7d3597633e89160f7a0ef111.jpg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a313tv1bd8hg6nbhx3srgb8b90tx\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Meijer, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"FRESH SHELL EGGS.\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}],\"servingsPerContainer\":8.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":400.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_biyyop6b6oj2f6and9n1lbk0y7y1\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"First Light Egg Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a8p11iobmkyt3da00u40lbr94c85\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"First Light Egg Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_am5hafxaiorcacamqspnka6gcky5\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Supervalu\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"image\":\"https://www.edamam.com/food-img/c7e/c7e96e053a544b8471ec5f71c2b0b2a4.jpg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":18.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":900.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a4mvulkbopk8pebmheve0at0ip76\",\"label\":\"2 Large Grade AA Eggs\",\"knownAs\":\"2 Large Grade AA Eggs\",\"nutrients\":{\"ENERC_KCAL\":164.2512077294686,\"PROCNT\":11.594202898550725,\"FAT\":12.56038647342995,\"CHOCDF\":1.932367149758454,\"FIBTG\":0.0},\"brand\":\"Perkins\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":103.5}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":103.5},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bcnm13qasb1zlpa1xg5xqah8c6tw\",\"label\":\"Kroger Large Eggs\",\"knownAs\":\"Kroger Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Kroger\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"image\":\"https://www.edamam.com/food-img/640/640b160d91f4f06549928be395451763.png\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a55wfyfat1oc0iap2e3f2bkerxns\",\"label\":\"Two Large Eggs\",\"knownAs\":\"Two Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.54054054054055,\"PROCNT\":12.612612612612613,\"FAT\":9.00900900900901,\"CHOCDF\":0.9009009009009009,\"FIBTG\":0.0},\"brand\":\"Mimi's Cafe\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":111.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":111.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a7ts6avbb4va76azubk4bazulnr1\",\"label\":\"Extra Large Eggs\",\"knownAs\":\"Extra Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":142.85714285714286,\"PROCNT\":12.5,\"FAT\":8.928571428571429,\"CHOCDF\":0.0},\"brand\":\"Gravel Ridge Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_ah17t5cbq26x6dbdyn00kbo0ma45\",\"label\":\"Sunshine, Large Eggs\",\"knownAs\":\"Sunshine, Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Sunshine\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_apwdiw9a7zp87bbtd1au7a5buy4l\",\"label\":\"12 Large Eggs\",\"knownAs\":\"12 Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Supervalu, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bln3k77b5543jnbflszbcbv0o1q3\",\"label\":\"Grade a Large Eggs\",\"knownAs\":\"Grade A Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"Bruno's Supermarkets, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_b5wh7a2baoenlrb317e3mauu19n3\",\"label\":\"Grade a Large Eggs\",\"knownAs\":\"Grade A Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"Bruno's Supermarkets, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_b2t2hi7bo6byz9ar94x4jbtkcq7m\",\"label\":\"Kroger Large Eggs\",\"knownAs\":\"Kroger Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Kroger\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"image\":\"https://www.edamam.com/food-img/640/640b160d91f4f06549928be395451763.png\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]}],\"_links\":{\"next\":{\"title\":\"Next page\",\"href\":\"https://api.edamam.com/api/food-database/v2/parser?session=40&app_id=ab4a541a&app_key=5435c61858cc52c36783f9f36fc2f1f2&ingr=2+large+eggs&nutrition-type=cooking\"}}}";

        ObjectMapper oj = new ObjectMapper();
        FoodResponse fr1;
        try {
            fr1 = oj.readValue(R1, FoodResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }

        Calendar cal = Calendar.getInstance(); // TODAY
        ResponseInfo t1 = new ResponseInfo(cal, MealType.SNACK.getSpinnerTitle(), fr1.getParsed().get(0), 1);
        ResponseInfo t2 = new ResponseInfo(cal, MealType.DINNER.getSpinnerTitle(), fr1.getParsed().get(0), 1);
        ResponseInfo t3 = new ResponseInfo(cal, MealType.BREAKFAST.getSpinnerTitle(), fr1.getParsed().get(0), 1);
        ResponseInfo t7 = new ResponseInfo(cal, MealType.BREAKFAST.getSpinnerTitle(), fr1.getParsed().get(0), 1);
        data.add(t1);
        data.add(t2);
        data.add(t3);
        data.add(t7);

        cal.add(Calendar.DATE, -1); // YESTERDAY
        ResponseInfo t4 = new ResponseInfo(cal, MealType.SNACK.getSpinnerTitle(), fr1.getParsed().get(0), 1);
        ResponseInfo t5 = new ResponseInfo(cal, MealType.DINNER.getSpinnerTitle(), fr1.getParsed().get(0), 1);
        ResponseInfo t6 = new ResponseInfo(cal, MealType.BREAKFAST.getSpinnerTitle(), fr1.getParsed().get(0), 1);
        data.add(t4);
        data.add(t5);
        data.add(t6);

        return data;
    }
}
