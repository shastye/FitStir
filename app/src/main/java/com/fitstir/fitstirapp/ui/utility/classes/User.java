package com.fitstir.fitstirapp.ui.utility.classes;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.utility.enums.WorkoutTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class User {
    public interface Callback {
        void onCallback(User value);
    }

    private String name, email, password, sex;
    private Integer heightFt, heightIn, weight, weightGoal, age, themeID, intervalID, rangeID, unitID;
    private List<Goal> goals;



    public User() {
        name = "foo";
        email = "bar@aol.com";
        password = "bar1234";
        sex = "male";
        heightFt = 1;
        heightIn = 1;
        weight = 123;
        weightGoal = 123;
        age = 0;
        goals = new ArrayList<>();
        goals.add(new Goal("Weight Goal", WorkoutTypes.WEIGHT_CHANGE, weightGoal));
        themeID = 0;
        intervalID = 0;
        rangeID = 2;
        unitID = 0;
    }
    public User(User user) {
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.sex = user.sex;
        this.heightFt = user.heightFt;
        this.heightIn = user.heightIn;
        this.weight = user.weight;
        this.weightGoal = user.weightGoal;
        this.age = user.age;
        this.goals = user.goals;
        this.themeID = user.themeID;
        this.intervalID = user.intervalID;
        this.rangeID = user.rangeID;
        this.unitID = user.unitID;
    }



    public void getUser(final User.Callback callback) {
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;

        DatabaseReference thisUser = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(authUser.getUid());
        thisUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        callback.onCallback(snapshot.getValue(User.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
    }

    public void saveUser() {
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference thisUser = userReference.child(authUser.getUid());
        thisUser.setValue(this);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(Integer heightFt) {
        this.heightFt = heightFt;
    }

    public Integer getHeightIn() {
        return heightIn;
    }

    public void setHeightIn(Integer heightIn) {
        this.heightIn = heightIn;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
        this.goals.get(0).addData(Calendar.getInstance().getTime(), weight);
    }

    public Integer getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(Integer weightGoal) {
        this.weightGoal = weightGoal;
        this.goals.get(0).setValue(weightGoal);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
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
}
