package com.fitstir.fitstirapp.ui.utility.classes;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalDataPair;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Calendar;

public class Users {
    private String fullname, email, password, sex;
    private Integer height_ft, height_in, weight, goal_weight, age,
            themeID, intervalID, rangeID, unitID,
            calorieTrackerGoal;

    private FirebaseUser user;

    //public Users() {}
    public Users() {
        fullname = "";
        email = "";
        password = "";
        sex = "";
        height_ft = 0;
        height_in = 0;
        weight = 0;
        goal_weight = 0;
        age = 0;

        themeID = 0;
        intervalID = 0;
        rangeID = 2;
        unitID = 0;

        calorieTrackerGoal = 2000;
    }

    public Users(String fullname, String email, String password, String sex, Integer height_ft,
                 Integer height_in, Integer weight, Integer goal_weight, Integer age) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.height_ft = height_ft;
        this.height_in = height_in;
        this.weight = weight;
        this.goal_weight = goal_weight;
        this.age = age;

        themeID = 0;
        intervalID = 0;
        rangeID = 0;
        unitID = 0;
        calorieTrackerGoal = 2000;
    }

    public FirebaseUser getUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }
    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public Integer getHeight_ft() {
        return height_ft;
    }

    public Integer getHeight_in() {
        return height_in;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getGoal_weight() {return goal_weight;}

    public Integer getAge() {
        return age;
    }

    public Integer getThemeID() {
        return themeID;
    }

    public Integer getIntervalID() {
        return intervalID;
    }

    public Integer getRangeID() {
        return rangeID;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public Integer getCalorieTrackerGoal() {
        return calorieTrackerGoal;
    }


    public void addWeightData(Integer newWeight) throws RuntimeException {
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        // update data on GoalsData collection
        DatabaseReference goalsRef = FirebaseDatabase.getInstance()
                .getReference("GoalsData").child(authUser.getUid());
        goalsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Goal goal = child.getValue(Goal.class);

                    if (goal.getType().equals(GoalTypes.WEIGHT_CHANGE)) {
                        ArrayList<GoalDataPair> data = goal.getData();

                        int size = data.size();
                        GoalDataPair lastDateData = data.get(size - 1);

                        if (!Methods.isToday(lastDateData.first)) {
                            goal.addData(Calendar.getInstance().getTime(), newWeight);
                        } else {
                            goal.getData().get(size - 1).second = newWeight;
                        }

                        goalsRef.child(child.getKey())
                                .setValue(goal);

                        // update data on Users collection
                        FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(authUser.getUid())
                                .child("_Weight")
                                .setValue(newWeight);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateWeightGoal(Integer newGoal) {
        this.goal_weight = newGoal;

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        // update data on GoalsData collection
        DatabaseReference goalsRef = FirebaseDatabase.getInstance()
                .getReference("GoalsData").child(authUser.getUid());
        goalsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Goal goal = child.getValue(Goal.class);

                    if (goal.getType().equals(GoalTypes.WEIGHT_CHANGE)) {
                        goalsRef.child(goal.getID())
                                .child("value")
                                .setValue(newGoal);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // update data on Users collection
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(authUser.getUid())
                .child("goal_weight")
                .setValue(newGoal);

    }



    public void setUser(FirebaseUser user) {this.user = user;}
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setHeight_ft(Integer height_ft) {
        this.height_ft = height_ft;
    }

    public void setHeight_in(Integer height_in) {
        this.height_in = height_in;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setGoal_weight(Integer goal_weight) {
        this.goal_weight = goal_weight;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setThemeID(Integer themeID) {
        this.themeID = themeID;
    }

    public void setIntervalID(Integer intervalID) {
        this.intervalID = intervalID;
    }

    public void setRangeID(Integer rangeID) {
        this.rangeID = rangeID;
    }

    public void setUnitID(Integer unitID) {
        this.unitID = unitID;
    }

    public void setCalorieTrackerGoal(Integer calorieTrackerGoal) {
        this.calorieTrackerGoal = calorieTrackerGoal;
    }
}