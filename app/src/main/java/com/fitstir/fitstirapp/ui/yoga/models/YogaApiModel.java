package com.fitstir.fitstirapp.ui.yoga.models;

import com.bumptech.glide.request.transition.Transition;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.YogaAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YogaApiModel {
    @SerializedName("id")
    private int id;
    @SerializedName("english_name")
    private String english_name;
    @SerializedName("sanskrit_name_adapted")
    private String sanskrit_name_adapted;
    @SerializedName("sanskrit_name")
    private String sanskrit_name;
    @SerializedName("translation_named")
    private String[] translation_named;
    @SerializedName("pose_description")
    private String[] pose_description;
    @SerializedName("pose_benefits")
    private String pose_benefits;
    @SerializedName("url_svg")
    private String url_svg;
    @SerializedName("url_png")
    private String url_png;
    @SerializedName("url_svg_alt")
    private String url_svg_alt;
    @SerializedName("difficulty_level")
    private String difficulty_level;
    @SerializedName("poses")
    private String poses;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("category_description")
    private String category_description;
    @SerializedName("categories")
    private String categories;
    private ArrayList<YogaApiModel> responseBody;



    public String getCategory_name() {return category_name;}
    public String getCategories() {return categories;}
    public String getCategory_description() {return category_description;}
    public ArrayList<YogaApiModel> getResponseBody() {return responseBody;}
    public int getId() {return id;}
    public String getEnglish_name() {return english_name;}
    public String getSanskrit_name_adapted() {return sanskrit_name_adapted;}
    public String getSanskrit_name() {return sanskrit_name;}
    public String[] getTranslation_named() {return translation_named;}
    public String[] getPose_description() {return pose_description;}
    public String getPose_benefits() {return pose_benefits;}
    public String getUrl_svg() {return url_svg;}
    public String getUrl_png() {return url_png;}
    public String getUrl_svg_alt() {return url_svg_alt;}
    public String getDifficulty_level() {return difficulty_level;}
    public String getPoses() {return poses;}


    public YogaApiModel() {}

    public YogaApiModel(int id, String english_name, String sanskrit_name_adapted,
                        String sanskrit_name, String[] translation_named, String[] pose_description, String pose_benefits,
                        String url_svg, String url_png, String url_svg_alt, String difficulty_level, String poses, String category_name,
                        String category_description,String categories, ArrayList<YogaApiModel> responseBody)
    {
        this.id = id;
        this.english_name = english_name;
        this.sanskrit_name_adapted = sanskrit_name_adapted;
        this.sanskrit_name = sanskrit_name;
        this.translation_named = translation_named;
        this.pose_description = pose_description;
        this.pose_benefits = pose_benefits;
        this.url_svg = url_svg;
        this.url_png = url_png;
        this.url_svg_alt = url_svg_alt;
        this.difficulty_level = difficulty_level;
        this.poses = poses;
        this.category_name = category_name;
        this.category_description = category_description;
        this.responseBody = responseBody;
        this.categories = categories;
    }

    public void setId(int id) {this.id = id;}
    public void setCategories(String categories) {this.categories = categories;}
    public void setEnglish_name(String english_name) {this.english_name = english_name;}
    public void setSanskrit_name_adapted(String sanskrit_name_adapted) {this.sanskrit_name_adapted = sanskrit_name_adapted;}
    public void setSanskrit_name(String sanskrit_name) {this.sanskrit_name = sanskrit_name;}
    public void setTranslation_named(String[] translation_named) {this.translation_named = translation_named;}
    public void setPose_description(String[] pose_description) {this.pose_description = pose_description;}
    public void setPose_benefits(String pose_benefits) {this.pose_benefits = pose_benefits;}
    public void setUrl_svg(String url_svg) {this.url_svg = url_svg;}
    public void setUrl_png(String url_png) {this.url_png = url_png;}
    public void setUrl_svg_alt(String url_svg_alt) {this.url_svg_alt = url_svg_alt;}
    public void setDifficulty_level(String difficulty_level) {this.difficulty_level = difficulty_level;}
    public void setPoses(String poses) {this.poses = poses;}
    public void setResponseBody(ArrayList<YogaApiModel> responseBody) {this.responseBody = responseBody;}
    public void setCategory_name(String category_name) {this.category_name = category_name;}
    public void setCategory_description(String category_description) {this.category_description = category_description;}

    public void callYogaApi(ArrayList<YogaApiModel> list, YogaAdapter adapter){
        //TODO: call api to get data and filter from there
    }
    public void saveRoutine(){
        //TODO: save poses to separate list for custom routines
    }
    public void favoriteList(){
        //TODO: save favorited items to new list
    }
}
