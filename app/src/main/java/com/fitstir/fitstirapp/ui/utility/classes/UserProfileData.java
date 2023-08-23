package com.fitstir.fitstirapp.ui.utility.classes;

import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.health.calorietracker.DataTuple;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Recipe;
import com.fitstir.fitstirapp.ui.utility.enums.WorkoutTypes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserProfileData {

    String fullname, email, password, sex;
    Integer height_ft,height_in, weight, goal_weight, age, themeID, intervalID, rangeID, unitID, calorieTrackerGoal;
    ArrayList<Goal> goals;
    ArrayList<Recipe> likedRecipes;
    ArrayList<DataTuple> calorieTrackerData;




    public Integer getCalorieTrackerGoal() {
        return calorieTrackerGoal;
    }

    public void setCalorieTrackerGoal(Integer calorieTrackerGoal) {
        this.calorieTrackerGoal = calorieTrackerGoal;
    }

    public ArrayList<DataTuple> getCalorieTrackerData() {
        return calorieTrackerData;
    }

    public void setCalorieTrackerData(ArrayList<DataTuple> calorieTrackerData) {
        this.calorieTrackerData = calorieTrackerData;
    }

    public ArrayList<Recipe> getLikedRecipes() {
        return likedRecipes;
    }

    public void setLikedRecipes(ArrayList<Recipe> likedRecipes) {
        this.likedRecipes = likedRecipes;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    public Integer getThemeID() {
        return themeID;
    }

    public void setThemeID(Integer themeID) {
        this.themeID = themeID;
    }

    public Integer getIntervalID() {
        return intervalID;
    }

    public void setIntervalID(Integer intervalID) {
        this.intervalID = intervalID;
    }

    public Integer getRangeID() {
        return rangeID;
    }

    public void setRangeID(Integer rangeID) {
        this.rangeID = rangeID;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public void setUnitID(Integer unitID) {
        this.unitID = unitID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getHeight_ft() {
        return height_ft;
    }

    public void setHeight_ft(Integer height_ft) {
        this.height_ft = height_ft;
    }

    public Integer getHeight_in() {
        return height_in;
    }

    public void setHeight_in(Integer height_in) {
        this.height_in = height_in;
    }

    public Integer get_Weight() {
        return weight;
    }

    public void set_Weight(Integer weight) {
        this.weight = weight;
        this.goals.get(0).addData(Calendar.getInstance().getTime(), weight);
    }

    public Integer getGoal_weight() {
        return goal_weight;
    }

    public void setGoal_weight(Integer goal_weight) {
        this.goal_weight = goal_weight;
        this.goals.get(0).setValue(goal_weight);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }



    public UserProfileData(){
        this.goals = new ArrayList<>();
        goals.add(new Goal("Weight Goal", WorkoutTypes.WEIGHT_CHANGE, 0));
        this.likedRecipes = new ArrayList<>();
        this.calorieTrackerData = new ArrayList<>();
    }
    public UserProfileData(String fullname, String email, String password,String sex,
                           Integer height_ft, Integer height_in, Integer weight,
                           Integer goal_weight, Integer age) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.height_ft = height_ft;
        this.height_in = height_in;
        this.weight = weight;
        this.goal_weight = goal_weight;
        this.age = age;
        this.sex = sex;

        goals = new ArrayList<>();
        goals.add(new Goal("Weight Goal", WorkoutTypes.WEIGHT_CHANGE, goal_weight));
        this.likedRecipes = new ArrayList<>();
        this.calorieTrackerData = new ArrayList<>();
    }


}