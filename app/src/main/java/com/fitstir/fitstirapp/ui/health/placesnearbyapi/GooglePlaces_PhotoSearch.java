package com.fitstir.fitstirapp.ui.health.placesnearbyapi;

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

public class GooglePlaces_PhotoSearch {
    private OkHttpClient client;
    private Request request;

    private Response response;
    private int responseCode;
    private ResponseBody responseBody;
    private String responseString;
    private NearbySearchResponse searchResponse;

    private String photoReference;

    public GooglePlaces_PhotoSearch(String photoReference) {
        this.photoReference = photoReference;

        String requestBody = "https://maps.googleapis.com/maps/api/place/photo?";
        requestBody += ("maxwidth=400");
        requestBody += ("&photo_reference=" + photoReference);
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

            int x = 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
