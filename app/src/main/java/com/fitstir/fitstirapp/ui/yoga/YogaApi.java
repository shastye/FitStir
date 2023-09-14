package com.fitstir.fitstirapp.ui.yoga;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YogaApi {
    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://yoga-api-nzy4.onrender.com/v1")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
