package com.fitstir.fitstirapp.ui.yoga;

import com.fitstir.fitstirapp.ui.yoga.models.CategoryModel;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IYogaApi
{
    @GET("categories")
    Call<List<CategoryModel>> getCategories();
    @GET("poses")
    Call<List<PoseModel>> getPoses();
    @GET("poses?level=value")
    Call<List<PoseModel>> getPoseLevel(@Query("level") String level);


}
