package com.fitstir.fitstirapp.ui.yoga.models;

public class PoseModel {

    private int id;
    private String category_name;
    private String difficulty_level;
    private String english_name;
    private String sanskrit_name_adapted;
    private String sanskrit_name;
    private String translation_name;
    private String pose_description;
    private String pose_benefits;
    private String url_svg;
    private String url_png;
    private String url_svg_alt;

    public int getId() {return id;}
    public String getCategory_name() {return category_name;}
    public String getDifficulty_level() {return difficulty_level;}
    public String getEnglish_name() {return english_name;}
    public String getSanskrit_name_adapted() {return sanskrit_name_adapted;}
    public String getSanskrit_name() {return sanskrit_name;}
    public String getTranslation_name() {return translation_name;}
    public String getPose_description() {return pose_description;}
    public String getPose_benefits() {return pose_benefits;}
    public String getUrl_svg() {return url_svg;}
    public String getUrl_png() {return url_png;}
    public String getUrl_svg_alt() {return url_svg_alt;}

    public void setId(int id) {this.id = id;}
    public void setCategory_name(String category_name) {this.category_name = category_name;}
    public void setDifficulty_level(String difficulty_level) {this.difficulty_level = difficulty_level;}
    public void setEnglish_name(String english_name) {this.english_name = english_name;}
    public void setSanskrit_name_adapted(String sanskrit_name_adapted) {this.sanskrit_name_adapted = sanskrit_name_adapted;}
    public void setSanskrit_name(String sanskrit_name) {this.sanskrit_name = sanskrit_name;}
    public void setTranslation_name(String translation_name) {this.translation_name = translation_name;}
    public void setPose_description(String pose_description) {this.pose_description = pose_description;}
    public void setPose_benefits(String pose_benefits) {this.pose_benefits = pose_benefits;}
    public void setUrl_svg(String url_svg) {this.url_svg = url_svg;}
    public void setUrl_png(String url_png) {this.url_png = url_png;}
    public void setUrl_svg_alt(String url_svg_alt) {this.url_svg_alt = url_svg_alt;}

    public PoseModel(){}
    public PoseModel(int id, String category_name, String difficulty_level, String english_name, String sanskrit_name_adapted, String sanskrit_name,
                     String translation_name, String pose_description, String pose_benefits, String url_svg, String url_png, String url_svg_alt) {
        this.id = id;
        this.category_name = category_name;
        this.difficulty_level = difficulty_level;
        this.english_name = english_name;
        this.sanskrit_name_adapted = sanskrit_name_adapted;
        this.sanskrit_name = sanskrit_name;
        this.translation_name = translation_name;
        this.pose_description = pose_description;
        this.pose_benefits = pose_benefits;
        this.url_svg = url_svg;
        this.url_png = url_png;
        this.url_svg_alt = url_svg_alt;
    }
}
