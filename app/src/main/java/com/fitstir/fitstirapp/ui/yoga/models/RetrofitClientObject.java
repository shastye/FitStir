package com.fitstir.fitstirapp.ui.yoga.models;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientObject {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://yoga-api-nzy4.onrender.com/v1/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
