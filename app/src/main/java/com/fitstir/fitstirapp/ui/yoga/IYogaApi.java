package com.fitstir.fitstirapp.ui.yoga;

import com.fitstir.fitstirapp.ui.yoga.models.YogaApiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IYogaApi
{
    @GET("/categories")
    Call<List<YogaApiModel>> getCategories();
    @GET("/poses")
    Call<List<YogaApiModel>> getPoses();
    @GET("/poses?name=value")
    Call<List<YogaApiModel>> getPosesByName();
    @GET("/poses?level=value")
    Call<List<YogaApiModel>> getPosesByLevel();

}
