package com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EdamamAPI_FoodDatabaseParser {

    private int responseCode;
    private Headers responseHeader;
    private ResponseBody responseBody;
    private FoodResponse foodResponse;

    private int quantity;
    private String unit;
    private String ingredient;
    private String nutritionType;
    private String health;
    private String calories;
    private String category;

    public EdamamAPI_FoodDatabaseParser(int quantity, String unit, String ingredient, String nutritionType,
                                        String health, String minCalories, String maxCalories, String category) {
        this.quantity = quantity;
        this.unit = unit;
        this.ingredient = ingredient;
        this.nutritionType = nutritionType;
        this.health = health;

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

        this.category = category;
    }

    public int getResponseCode() { return this.responseCode; }
    public Headers getResponseHeader() { return this.responseHeader; }
    public ResponseBody getResponseBody() { return this.responseBody; }
    public String getResponseAsString() throws IOException { return new String(this.responseBody.bytes(), StandardCharsets.UTF_8); }
    public FoodResponse getFoodResponse() throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        foodResponse = objectMapper.readValue(this.getResponseAsString(), FoodResponse.class);
        return foodResponse;
    }

    public void execute() {
        String requestBody = "https://api.edamam.com/api/food-database/v2/parser?" +
                "app_id=" + Constants.FOOD_DATA_BASE_PARSER.APP_ID + "&" +
                "app_key=" + Constants.FOOD_DATA_BASE_PARSER.APP_KEY + "&" +
                "ingr=" + quantity + "%20" + unit + "%20" + ingredient
                + "&" + "nutrition-type=" + nutritionType;

        if (health != "") {
            requestBody += "&" + "health=" + health;
        }
        if (this.calories != "") {
            requestBody += "&" + "calories=" + this.calories;
        }
        if (category != "") {
            requestBody += "&" + "category=" + category;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestBody)
                .header("Accept", "application/json")
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
