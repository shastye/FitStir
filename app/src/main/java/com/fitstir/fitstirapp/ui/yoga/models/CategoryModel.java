package com.fitstir.fitstirapp.ui.yoga.models;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class CategoryModel {

    @SerializedName("id")
    private int id;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("category_description")
    private String category_description;
    @SerializedName("poses")
    private String[] poses;

    public int getId() {return id;}
    public String getCategory_name() {return category_name;}
    public String getCategory_description() {return category_description;}
    public String[] getPoses() {return poses;}

    public CategoryModel() {}

    public CategoryModel(int id, String category_name, String category_description, String[] poses) {
        this.id = id;
        this.category_name = category_name;
        this.category_description = category_description;
        this.poses = poses;
    }

    public void setId(int id) {this.id = id;}

    public void setCategory_name(String category_name) {this.category_name = category_name;}

    public void setCategory_description(String category_description) {this.category_description = category_description;}

    public void setPoses(String[] poses) {this.poses = poses;}
}
