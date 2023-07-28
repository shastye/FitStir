package com.fitstir.fitstirapp.ui.utility;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EdamamAPI_FoodSearch {

    private int responseCode;
    private String responseHeader;
    private String responseBody;

    public EdamamAPI_FoodSearch(int quantity, String unit, String ingredient, String nutritionType, String health, String minCalories, String maxCalories, String category) {
        String calories;
        if (minCalories == "" && maxCalories == "") {
            calories = "";
        } else if (minCalories == "") {
            calories = maxCalories;
        } else if (maxCalories == "") {
            calories = minCalories + "2%B";
        } else {
            calories = minCalories + "-" + maxCalories;
        }

        String requestBody = "https://api.edamam.com/api/food-database/v2/parser?" +
                "app_id=" + Constants.APP_ID + "&" +
                "app_key=" + Constants.APP_KEY + "&" +

                "ingr=" + quantity + " " + unit + " " + ingredient + "&" +
                "nutrition-type=" + nutritionType + "&" +
                "health=" + health + "&" +
                "calories=" + calories + "&" +
                "category=" + category;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestBody)
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Code: " + response);
            }

            responseCode = response.code();
            responseHeader = response.headers().toString();

            if (response.body() != null) {
                responseBody = response.body().string();
            } else {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
