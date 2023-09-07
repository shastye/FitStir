package com.fitstir.fitstirapp.ui.runtracker.utilites;

import java.util.ArrayList;
import java.util.Date;

public class RunnerData {

    private String imageRoute;

    private ArrayList<RunnerData> dataTracker;
    private Integer runTime, runDistance, runPace, runCalBurned, timeOfDay, runDate, latLng;


    public String getImageRoute() {return imageRoute;}
    public Integer getRunTime() {return runTime;}
    public Integer getRunDistance() {return runDistance;}
    public Integer getRunPace() {return runPace;}
    public Integer getRunCalBurned() {return runCalBurned;}
    public ArrayList<RunnerData> getDataTracker() {return dataTracker;}
    public void setDataTracker(ArrayList<RunnerData> data) {this.dataTracker = data;}
    public Integer getRunDate() {return runDate;}
    public Integer getLatLng() {return latLng;}

    public RunnerData(){}

    public RunnerData(String imageRoute, ArrayList<RunnerData> dataTracker, Integer runTime, Integer runDistance, Integer runPace, Integer runCalBurned,
                      Integer timeOfDay, Integer runDate, Integer latLng) {
        this.imageRoute = imageRoute;
        this.dataTracker = dataTracker;
        this.runTime = runTime;
        this.runDistance = runDistance;
        this.runPace = runPace;
        this.runCalBurned = runCalBurned;
        this.timeOfDay = timeOfDay;
        this.runDate = runDate;
        this.latLng = latLng;
    }

    public void setLatLng(Integer latLng) {this.latLng = latLng;}
    public void setRunDate(Integer runDate) {this.runDate = runDate;}
    public Integer getTimeOfDay() {return timeOfDay;}
    public void setTimeOfDay(Integer timeOfDay) {this.timeOfDay = timeOfDay;}
    public void setImageRoute(String imageRoute) {this.imageRoute = imageRoute;}
    public void setRunTime(Integer runTime) {this.runTime = runTime;}
    public void setRunDistance(Integer runDistance) {this.runDistance = runDistance;}
    public void setRunPace(Integer runPace) {this.runPace = runPace;}
    public void setRunCalBurned(Integer runCalBurned) {this.runCalBurned = runCalBurned;}

    public void addRunData(RunViewModel viewModel, ArrayList<RunnerData> data){
        //TODO: method to add data to firebase's current user
    }
    public void fetchRunData(ArrayList<RunnerData> data){
        //TODO: method to get data from current users file
    }
    public void sortByDataType(){
        //TODO: method to compare and sort data array list
    }


}
