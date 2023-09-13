package com.fitstir.fitstirapp.ui.runtracker.utilites;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;

public class RunViewModel extends ViewModel {
    //region Variables
    private final MutableLiveData<String> mText;
    private final MutableLiveData<Double> runTimeInMinutes = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> avgPace = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Float> burnedCalories = new MutableLiveData<>(0f);
    private final MutableLiveData<String> runDate = new MutableLiveData<>();
    private final MutableLiveData<Integer> weight = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> age = new MutableLiveData<>(0);
    private final MutableLiveData<UserProfileData> user = new MutableLiveData<>(new UserProfileData());
    private final MutableLiveData<String> lat = new MutableLiveData<>(" ");
    private final MutableLiveData<String> lng = new MutableLiveData<>(" ");
    private final MutableLiveData<Double> altitude = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> bearing = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> elapsedRealTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> speed = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> time = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> accuracy = new MutableLiveData<>(0.0);
   //endregion

    //region Getters
    public MutableLiveData<Double> getBearing() {return bearing;}
    public MutableLiveData<Double> getElapsedRealTime() {return elapsedRealTime;}
    public MutableLiveData<Double> getSpeed() {return speed;}
    public MutableLiveData<Double> getTime() {return time;}
    public MutableLiveData<Double> getAccuracy() {return accuracy;}
    public MutableLiveData<Double> getAltitude() {return altitude;}
    public MutableLiveData<String> getmText() {return mText;}
    public MutableLiveData<Double> getRunTimeInMintues() {return runTimeInMinutes;}
    public MutableLiveData<Double> getAvgPace() {return avgPace;}
    public MutableLiveData<Double> getTotalDistance() {return totalDistance;}
    public MutableLiveData<Float> getBurnedCalories() {return burnedCalories;}
    public MutableLiveData<String> getRunDate() {return runDate;}
    public MutableLiveData<Integer> getWeight() {return weight;}
    public MutableLiveData<Integer> getAge() {return age;}
    public MutableLiveData<UserProfileData> getUser() {return user;}
    public MutableLiveData<String> getLat() {return lat;}
    public MutableLiveData<String> getLng() {return lng;}

    //endregion

    public RunViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue(" ");
    }

    //region Setters
    public void setRunTimeInMinutes(Double runTime){this.runTimeInMinutes.setValue(runTime);}
    public void setAvgPace(Double pace){this.avgPace.setValue(pace);}
    public void setRunDistance(Double distance){this.totalDistance.setValue(distance);}
    public void setBurnedCalories(float calories){this.burnedCalories.setValue(calories);}
    public void setWeight(Integer weight){this.weight.setValue(weight);}
    public void setAge(Integer age){this.age.setValue(age);}
    public void setUser(UserProfileData user){this.user.setValue(user);}
    public void setRunDate(String date){this.runDate.setValue(date);}
    public void setLat(String lat){this.lat.setValue(lat);}
    public void setLng(String lng){this.lng.setValue(lng);}
    public void setAltitude(double alt){this.altitude.setValue(alt);}
    public void setBearing(double bear){this.bearing.setValue(bear);}
    public void setElapsedRealTime(double elapsedTime){this.elapsedRealTime.setValue(elapsedTime);}
    public void setSpeed(double speed){this.speed.setValue(speed);}
    public void setClockTime(double time){this.time.setValue(time);}
    public void setAccuracy(double accuracy){this.accuracy.setValue(accuracy);}



//endregion



}
