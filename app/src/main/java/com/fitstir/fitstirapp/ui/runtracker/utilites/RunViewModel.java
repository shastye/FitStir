package com.fitstir.fitstirapp.ui.runtracker.utilites;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;

public class RunViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private final MutableLiveData<Double> runTimeInMinutes = new MutableLiveData<>();
    private final MutableLiveData<Double> avgPace = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> burnedCalories = new MutableLiveData<>(0.0);
    private final MutableLiveData<String> runDate = new MutableLiveData<>();
    private final MutableLiveData<Integer> weight = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> age = new MutableLiveData<>(0);
    private final MutableLiveData<UserProfileData> user = new MutableLiveData<>(new UserProfileData());
    private final MutableLiveData<double[]> location = new MutableLiveData<>();

    //region Getters
    public MutableLiveData<String> getmText() {return mText;}

    public MutableLiveData<Double> getRunTimeInMintues() {return runTimeInMinutes;}

    public MutableLiveData<Double> getAvgPace() {return avgPace;}

    public MutableLiveData<Double> getTotalDistance() {return totalDistance;}

    public MutableLiveData<Double> getBurnedCalories() {return burnedCalories;}

    public MutableLiveData<String> getRunDate() {return runDate;}

    public MutableLiveData<Integer> getWeight() {return weight;}

    public MutableLiveData<Integer> getAge() {return age;}

    public MutableLiveData<UserProfileData> getUser() {return user;}

    public MutableLiveData<double[]> getLocation() {return location;}

    //endregion

    public RunViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue(" ");
    }

    public void setRunTimeInMinutes(Double runTime){this.runTimeInMinutes.setValue(runTime);}
    public void setAvgPace(Double pace){this.avgPace.setValue(pace);}
    public void setRunDistance(Double distance){this.totalDistance.setValue(distance);}
    public void setBurnedCalories(Double calories){this.burnedCalories.setValue(calories);}
    public void setWeight(Integer weight){this.weight.setValue(weight);}
    public void setAge(Integer age){this.age.setValue(age);}
    public void setUser(UserProfileData user){this.user.setValue(user);}
    public void setRunDate(String date){this.runDate.setValue(date);}
    public void setLocation(double[] location){this.location.setValue(location);}



}
