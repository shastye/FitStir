package com.fitstir.fitstirapp.ui.runtracker.utilites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RunViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> runTime = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> runAveragePace = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> runDistance = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> burnedCalories = new MutableLiveData<>(0);


    public MutableLiveData<Integer> getRunTime() {return runTime;}

    public MutableLiveData<Integer> getRunAveragePace() {return runAveragePace;}

    public MutableLiveData<Integer> getRunDistance() {return runDistance;}

    public MutableLiveData<Integer> getBurnedCalories() {return burnedCalories;}
    public RunViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue(" ");
    }

    public void setRunTime(Integer runTime){this.runTime.setValue(runTime);}
    public void setRunAveragePace(Integer pace){this.runAveragePace.setValue(pace);}
    public void setRunDistance(Integer distance){this.runDistance.setValue(distance);}
    public void setBurnedCalories(Integer calories){this.burnedCalories.setValue(calories);}



}
