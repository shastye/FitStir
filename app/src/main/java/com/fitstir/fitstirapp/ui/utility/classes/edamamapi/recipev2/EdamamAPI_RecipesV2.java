package com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EdamamAPI_RecipesV2 {
    private OkHttpClient client;
    private Request request;

    private Response response;
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

    public EdamamAPI_RecipesV2(
            String toSearchFor,
            String minNumIngredients,
            String maxNumIngredients,
            String diet,
            String health,
            String cuisineType,
            String mealType,
            String dishType,
            String minCalories,
            String maxCalories,
            String minTime,
            String maxTime
    ) {
        this.toSearchFor = toSearchFor;

        String tNumIngredients;
        if (minNumIngredients.equals("") && maxNumIngredients.equals("")) {
            tNumIngredients = "";
        } else if (minNumIngredients.equals("")) {
            tNumIngredients = maxNumIngredients;
        } else if (maxNumIngredients.equals("")) {
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
        if (minCalories.equals("") && maxCalories.equals("")) {
            tCalories = "";
        } else if (minCalories.equals("")) {
            tCalories = maxCalories;
        } else if (maxCalories.equals("")) {
            tCalories = minCalories + "%2B";
        } else {
            tCalories = minCalories + "-" + maxCalories;
        }
        this.calories = tCalories;

        String tTime;
        if (minTime.equals("") && maxTime.equals("")) {
            tTime = "";
        } else if (minTime.equals("")) {
            tTime = maxTime;
        } else if (maxTime.equals("")) {
            tTime = minTime + "%2B";
        } else {
            tTime = minTime + "-" + maxTime;
        }
        this.time = tTime;
        String requestBody = "https://api.edamam.com/api/recipes/v2?" +
                "type=" + "public" + "&" +
                "beta=" + "false" + "&" +
                "q=" + toSearchFor + "&" +
                "app_id=" + Constants.RECIPE_V2.APP_ID + "&" +
                "app_key=" + Constants.RECIPE_V2.APP_KEY;

        if (!numIngredients.equals("")) {
            requestBody += "&" + "ingr=" + numIngredients;
        }

        requestBody +=  "&" + "random=" + "false";

        if (!diet.equals("")) {
            requestBody += "&" + "diet=" + diet;
        }
        if (!health.equals("")) {
            requestBody += "&" + "health=" + health;
        }
        if (!cuisineType.equals("")) {
            requestBody += "&" + "cuisineType=" + cuisineType;
        }
        if (!mealType.equals("")) {
            requestBody += "&" + "mealType=" + mealType;
        }
        if (!dishType.equals("")) {
            requestBody += "&" + "dishType=" + dishType;
        }
        if (!calories.equals("")) {
            requestBody += "&" + "calories=" + calories;
        }
        if (!time.equals("")) {
            requestBody += "&" + "time=" + time;
        }

        client = new OkHttpClient();
        request = new Request.Builder()
                .url(requestBody)
                .header("Accept", "application/json")
                .header("Accept-Language", "en")
                .build();
    }

    public EdamamAPI_RecipesV2(String nextHref) {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(nextHref)
                .header("Accept", "application/json")
                .header("Accept-Languagae", "en")
                .build();
    }

    public int getResponseCode() { return this.responseCode; }
    public Headers getResponseHeader() { return this.responseHeader; }
    public ResponseBody getResponseBody() { return this.responseBody; }
    public String getResponseAsString() throws IOException { return new String(this.responseBody.bytes(), StandardCharsets.UTF_8); }
    public RecipeResponse getRecipeResponse() throws IOException {
        return recipeResponse;
    }

    public Future<Response> getFutureResponse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            try {
                response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected Code: " + response);
                }

                return response;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Future<RecipeResponse> getFutureRecipe() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                recipeResponse = objectMapper.readValue(this.getResponseAsString(), RecipeResponse.class);
                return recipeResponse;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void execute() {

        Future<Response> responseFuture = getFutureResponse();
        try {
            this.response = responseFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        responseCode = response.code();
        responseHeader = response.headers();
        responseBody = response.body();

        Future<RecipeResponse> recipeFuture = getFutureRecipe();
        try {
            recipeResponse = recipeFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
