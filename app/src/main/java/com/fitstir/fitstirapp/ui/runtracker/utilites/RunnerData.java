package com.fitstir.fitstirapp.ui.runtracker.utilites;

import java.util.ArrayList;
import java.util.Date;

public class RunnerData {

    private String imageRoute;

    private ArrayList<RunnerData> dataTracker;
    private Integer runTime, runDistance, runPace, runCalBurned;
    private Date runDate;
    public Date getRunDate() {return runDate;}
    public String getImageRoute() {return imageRoute;}

    public Integer getRunTime() {return runTime;}

    public Integer getRunDistance() {return runDistance;}

    public Integer getRunPace() {return runPace;}

    public Integer getRunCalBurned() {return runCalBurned;}

    public ArrayList<RunnerData> getDataTracker() {return dataTracker;}

    public void setDataTracker(ArrayList<RunnerData> data) {this.dataTracker = data;}

    public RunnerData(){}

    public RunnerData(String imageRoute, Integer runTime, Integer runDistance, Integer runPace, Integer runCalBurned, Date runDate) {
        this.imageRoute = imageRoute;
        this.runTime = runTime;
        this.runDistance = runDistance;
        this.runPace = runPace;
        this.runCalBurned = runCalBurned;
        this.runDate = runDate;
    }

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
