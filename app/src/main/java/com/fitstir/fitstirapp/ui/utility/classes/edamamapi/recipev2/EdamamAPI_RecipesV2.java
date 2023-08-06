package com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EdamamAPI_RecipesV2 {
    private int responseCode;
    private Headers responseHeader;
    private ResponseBody responseBody;
    private RecipeResponse recipeResponse;

    private String toSearchFor; // api = q
    private String numIngredients; // api = ingr
    private String diet;
    private String health;
    private String cuisineType;
    private String mealType;
    private String dishType;
    private String calories;
    private String time; // in minutes

    private final ArrayList<String> field = new ArrayList<String>() {{
        add("uri");
        add("label");
        add("image");
        add("images");
        add("source");
        add("url");
        add("yield");
        add("dietLabels");
        add("healthLabels");
        add("ingredientLines");
        add("calories");
        add("totalTime");
        add("cuisineType");
        add("mealType");
        add("dishType");
        add("tags");
    }};

    public EdamamAPI_RecipesV2(String toSearchFor, String minNumIngredients, String maxNumIngredients,
                               String diet, String health, String cuisineType, String mealType,
                               String dishType, String minCalories, String maxCalories, String minTime,
                               String maxTime) {
        this.toSearchFor = toSearchFor;

        String tNumIngredients;
        if (minNumIngredients == "" && maxNumIngredients == "") {
            tNumIngredients = "";
        } else if (minNumIngredients == "") {
            tNumIngredients = maxNumIngredients;
        } else if (maxNumIngredients == "") {
            tNumIngredients = minNumIngredients + "%2B";
        } else {
            tNumIngredients = minNumIngredients + "-" + maxNumIngredients;
        }
        this.numIngredients = tNumIngredients;

        this.diet = diet;
        this.health = health;
        this.cuisineType = cuisineType;
        this.mealType = mealType;
        this.dishType = dishType;

        String tCalories;
        if (minCalories == "" && maxCalories == "") {
            tCalories = "";
        } else if (minCalories == "") {
            tCalories = maxCalories;
        } else if (maxCalories == "") {
            tCalories = minCalories + "%2B";
        } else {
            tCalories = minCalories + "-" + maxCalories;
        }
        this.calories = tCalories;

        String tTime;
        if (minTime == "" && maxTime == "") {
            tTime = "";
        } else if (minTime == "") {
            tTime = maxTime;
        } else if (maxTime == "") {
            tTime = minTime + "%2B";
        } else {
            tTime = minTime + "-" + maxTime;
        }
        this.time = tTime;
    }

    public int getResponseCode() { return this.responseCode; }
    public Headers getResponseHeader() { return this.responseHeader; }
    public ResponseBody getResponseBody() { return this.responseBody; }
    public String getResponseAsString() throws IOException { return new String(this.responseBody.bytes(), StandardCharsets.UTF_8); }
    public RecipeResponse getRecipeResponse() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        recipeResponse = objectMapper.readValue(this.getResponseAsString(), RecipeResponse.class);
        return recipeResponse;
    }

    public void execute() {
        String requestBody = "https://api.edamam.com/api/recipes/v2?" +
                "type=" + "public" + "&" +
                "beta=" + "false" + "&" +
                "q=" + toSearchFor + "&" +
                "app_id=" + Constants.RECIPE_V2.APP_ID + "&" +
                "app_key=" + Constants.RECIPE_V2.APP_KEY;

        if (numIngredients != "") {
            requestBody += "&" + "ingr=" + numIngredients;
        }

        requestBody +=  "&" + "random=" + "false";

        if (diet != "") {
            requestBody += "&" + "diet=" + diet;
        }
        if (health != "") {
            requestBody += "&" + "health=" + health;
        }
        if (cuisineType != "") {
            requestBody += "&" + "cuisineType=" + cuisineType;
        }
        if (mealType != "") {
            requestBody += "&" + "mealType=" + mealType;
        }
        if (dishType != "") {
            requestBody += "&" + "dishType=" + dishType;
        }
        if (calories != "") {
            requestBody += "&" + "calories=" + calories;
        }
        if (time != "") {
            requestBody += "&" + "time=" + time;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestBody)
                .header("Accept", "application/json")
                .header("Accept-Language", "en")
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Code: " + response);
            }

            responseCode = response.code();
            responseHeader = response.headers();

            this.responseBody = response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
