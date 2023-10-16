package com.fitstir.fitstirapp.ui.yoga.models;

import java.util.ArrayList;

public class PoseModel {


    private String difficulty_level,english_name,sanskrit_name,
            translation_name,pose_description,pose_benefits,url_png,url_Vid,pose_Type, routineName;
    private PoseModel yogaPose;
    private Integer routineSize, favoriteClickID;
    private ArrayList<PoseModel>yogaList;


    public Integer getFavoriteClickID() {return favoriteClickID;}
    public ArrayList<PoseModel> getYogaList() {
        return yogaList;
    }
    public Integer getRoutineSize() {return routineSize;}
    public String getRoutineName() {return routineName;}
    public PoseModel getYoga() {return yogaPose;}
    public String getDifficulty_level() {return difficulty_level;}
    public String getEnglish_name() {return english_name;}
    public String getSanskrit_name() {return sanskrit_name;}
    public String getTranslation_name() {return translation_name;}
    public String getPose_description() {return pose_description;}
    public String getPose_benefits() {return pose_benefits;}
    public String getUrl_png() {return url_png;}
    public String getUrl_Vid() {return url_Vid;}
    public String getPose_Type() {return pose_Type;}

    public PoseModel(){};

    public PoseModel(String english_name, String url_png, String url_Vid) {
        this.english_name = english_name;
        this.url_png = url_png;
        this.url_Vid = url_Vid;
    }

    public PoseModel(String pose_Type, String difficulty_level, String english_name, String sanskrit_name,
                     String translation_name, String pose_description, String pose_benefits, String url_png, String url_Vid, String routineName) {
        this.difficulty_level = difficulty_level;
        this.english_name = english_name;
        this.sanskrit_name = sanskrit_name;
        this.translation_name = translation_name;
        this.pose_description = pose_description;
        this.pose_benefits = pose_benefits;
        this.url_png = url_png;
        this.url_Vid = url_Vid;
        this.pose_Type = pose_Type;
        this.routineName = routineName;
    }

    public PoseModel(String difficulty_level, String english_name, String sanskrit_name, String translation_name, String pose_description,
                     String pose_benefits, String url_png, String url_Vid, String pose_Type, PoseModel yogaPose) {
        this.difficulty_level = difficulty_level;
        this.english_name = english_name;
        this.sanskrit_name = sanskrit_name;
        this.translation_name = translation_name;
        this.pose_description = pose_description;
        this.pose_benefits = pose_benefits;
        this.url_png = url_png;
        this.url_Vid = url_Vid;
        this.pose_Type = pose_Type;
        this.yogaPose = yogaPose;
    }


    public void setFavoriteClickID(Integer favoriteClickID) {this.favoriteClickID = favoriteClickID;}
    public void setYogaList(ArrayList<PoseModel> yogaList) {
        this.yogaList = yogaList;
    }
    public void setRoutineName(String routineName) {this.routineName = routineName;}
    public void setPose_Type(String pose_Type) {this.pose_Type = pose_Type;}
    public void setDifficulty_level(String difficulty_level) {this.difficulty_level = difficulty_level;}
    public void setEnglish_name(String english_name) {this.english_name = english_name;}
    public void setSanskrit_name(String sanskrit_name) {this.sanskrit_name = sanskrit_name;}
    public void setTranslation_name(String translation_name) {this.translation_name = translation_name;}
    public void setPose_description(String pose_description) {this.pose_description = pose_description;}
    public void setPose_benefits(String pose_benefits) {this.pose_benefits = pose_benefits;}
    public void setUrl_png(String url_png) {this.url_png = url_png;}
    public void setUrl_Vid(String url_Vid) {this.url_Vid = url_Vid;}
    public void setYoga(PoseModel yoga) {this.yogaPose = yoga;}
    public void setRoutineSize(Integer routineSize) {this.routineSize = routineSize;}
}
