package com.fitstir.fitstirapp.ui.connect;

public class UserProfileData {

    String firstname, lastname,email, password;
    Integer height_ft,height_in, weight, goal_weight, age;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
    }

    public Integer getGoal_weight() {
        return goal_weight;
    }

    public void setGoal_weight(Integer goal_weight) {
        this.goal_weight = goal_weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserProfileData(String firstname, String lastname, String email, String password, Integer height_ft, Integer height_in, Integer weight, Integer goal_weight, Integer age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.height_ft = height_ft;
        this.height_in = height_in;
        this.weight = weight;
        this.goal_weight = goal_weight;
        this.age = age;
    }
    public UserProfileData(){

    }

}
