package com.fitstir.fitstirapp.ui.utility;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EdamamAPI_FoodSearch {

    private int responseCode;
    private String responseHeader;
    private String responseBody;

    public EdamamAPI_FoodSearch(int quantity, String unit, String ingredient, String nutritionType, String health, String calories, String category) {
        String appKey = "5435c61858cc52c36783f9f36fc2f1f2";
        String appID = "ab4a541a";

        String requestBody = "https://api.edamam.com/api/food-database/v2/parser?" +
                "app_id=" + appID + "&" +
                "app_key=" + appKey + "&" +

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
