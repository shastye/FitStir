package com.fitstir.fitstirapp.ui.health.placesnearbyapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.FoodResponse;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.RecipeResponse;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
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

public class GooglePlaces_NearbySearch {
    private OkHttpClient client;
    private Request request;

    private Response response;
    private int responseCode;
    private ResponseBody responseBody;
    private String responseString;
    private NearbySearchResponse searchResponse;

    private String longitude, latitude, radius, keyword;
    private int minRating, maxRating;

    public GooglePlaces_NearbySearch(String latitude, String longitude, String radius, String keyword, int minRating, int maxRating) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.keyword = keyword;

        this.minRating = minRating;
        this.maxRating = maxRating;

        String requestBody = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        requestBody += ("location=" + latitude + "%2C" + longitude);
        requestBody += ("&radius=" + radius);
        requestBody += ("&keyword=" + keyword);
        requestBody += ("&key=" + Constants.MAPS_API_KEY);

        client = new OkHttpClient();
        request = new Request.Builder()
                .url(requestBody)
                .build();
    }

    public Future<Response> getFutureResponse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            try {
                Response tempResponse = client.newCall(request).execute();

                if (!tempResponse.isSuccessful()) {
                    throw new IOException("Unexpected Code: " + tempResponse);
                }

                return tempResponse;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Future<NearbySearchResponse> getFutureSearchResponse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                NearbySearchResponse tempResponse = objectMapper.readValue(responseString, NearbySearchResponse.class);
                return tempResponse;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void execute() throws Exception {
        try {
            Future<Response> responseFuture = getFutureResponse();
            try {
                this.response = responseFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            responseCode = response.code();
            responseBody = response.body();
            responseString = responseBody.string();

            Future<NearbySearchResponse> recipeFuture = getFutureSearchResponse();
            try {
                searchResponse = recipeFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < searchResponse.getResults().size(); i++) {
                if (searchResponse.getResults().get(i) != null && searchResponse.getResults().get(i).getRating() != null) {
                    ArrayList<Place> results = searchResponse.getResults();
                    Place place = results.get(i);
                    float rating = place.getRating();

                    if (rating < minRating || rating > maxRating) {
                        results.remove(place);
                        i--;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getResponseCode() { return responseCode; }

    public String getResponseString() { return responseString; }

    public NearbySearchResponse getSearchResponse() { return searchResponse; }
}
