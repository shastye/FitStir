package com.fitstir.fitstirapp.ui.health.placesnearbyapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GooglePlaces_PlaceDetails {
    private OkHttpClient client;
    private Request request;

    private Response response;
    private int responseCode;
    private ResponseBody responseBody;
    private String responseString;
    private PlaceDetailResponse searchResponse;

    String placeId;

    public GooglePlaces_PlaceDetails(String placeId) {
        String fields =
                "place_id," +
                "name," +
                "photos," +
                "rating," +
                "user_ratings_total," +
                "opening_hours," +
                "current_opening_hours," +
                "geometry," +
                "international_phone_number," +
                "url," +
                "website";
        fields = fields.replace(",", "%2C");

        String requestBody = "https://maps.googleapis.com/maps/api/place/details/json?";
        requestBody += ("fields=" + fields);
        requestBody += ("&place_id=" + placeId);
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

    public Future<PlaceDetailResponse> getFutureSearchResponse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                PlaceDetailResponse tempResponse = objectMapper.readValue(responseString, PlaceDetailResponse.class);
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

            Future<PlaceDetailResponse> recipeFuture = getFutureSearchResponse();
            try {
                searchResponse = recipeFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getResponseCode() { return responseCode; }

    public String getResponseString() { return responseString; }

    public PlaceDetailResponse getSearchResponse() { return searchResponse; }
}
