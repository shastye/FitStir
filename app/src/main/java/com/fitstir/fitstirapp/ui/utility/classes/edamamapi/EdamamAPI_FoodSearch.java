package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import com.fitstir.fitstirapp.ui.utility.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EdamamAPI_FoodSearch {

    private final int responseCode;
    private final Headers responseHeader;
    private ResponseBody responseBody;

    public EdamamAPI_FoodSearch(int quantity, String unit, String ingredient, String nutritionType, String health, String minCalories, String maxCalories, String category) {
        String calories;
        if (minCalories == "" && maxCalories == "") {
            calories = "";
        } else if (minCalories == "") {
            calories = maxCalories;
        } else if (maxCalories == "") {
            calories = minCalories + "%2B";
        } else {
            calories = minCalories + "-" + maxCalories;
        }

        String requestBody = "https://api.edamam.com/api/food-database/v2/parser?" +
                "app_id=" + Constants.APP_ID + "&" +
                "app_key=" + Constants.APP_KEY + "&" +
                "ingr=" + quantity + "%20" + unit + "%20" + ingredient
                + "&" + "nutrition-type=" + nutritionType;

        if (health != "") {
            requestBody += "&" + "health=" + health;
        }
        if (calories != "") {
            requestBody += "&" + "calories=" + calories;
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

    public int getResponseCode() { return this.responseCode; }
    public Headers getResponseHeader() { return this.responseHeader; }
    public ResponseBody getResponseBody() { return this.responseBody; }
    public String getResponseAsString() throws IOException { return new String(this.responseBody.bytes(), StandardCharsets.UTF_8); }
}
