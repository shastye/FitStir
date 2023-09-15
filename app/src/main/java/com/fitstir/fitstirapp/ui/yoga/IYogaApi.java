package com.fitstir.fitstirapp.ui.yoga;

import com.fitstir.fitstirapp.ui.yoga.models.CategoryModel;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IYogaApi
{
    @GET("/categories")
    Call<List<CategoryModel>> getCategories();
    @GET("/categories?id=value")
    Call<List<CategoryModel>> getCategoriesById();
    @GET("/categories?name=value")
    Call<List<CategoryModel>> getCategoriesByName();
    @GET("/categories?id=value&level=value")
    Call<List<CategoryModel>> getCategoriesByLevel();
    @GET("/poses")
    Call<List<PoseModel>> getPoses();
    @GET("/poses?id=value")
    Call<List<PoseModel>> getPosesByID();
    @GET("/poses?name=value")
    Call<List<PoseModel>> getPosesByName();
    @GET("/poses?level=value")
    Call<List<PoseModel>> getPosesByLevel();

}
