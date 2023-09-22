package com.fitstir.fitstirapp.ui.yoga.models;

import java.util.List;

public class CategoryModel {
    private int id;
    private String category_name;
    private String category_description;
    private List<PoseModel> poses;

    public int getId() {return id;}
    public String getCategory_name() {return category_name;}
    public String getCategory_description() {return category_description;}
    public List<PoseModel> getPoses() {return poses;}

    public void setId(int id) {this.id = id;}
    public void setCategory_name(String category_name) {this.category_name = category_name;}
    public void setCategory_description(String category_description) {this.category_description = category_description;}
    public void setPoses(List<PoseModel> poses) {this.poses = poses;}

    public CategoryModel(){}

    public CategoryModel(int id, String category_name, String category_description, List<PoseModel> poses) {
        this.id = id;
        this.category_name = category_name;
        this.category_description = category_description;
        this.poses = poses;
    }
}
