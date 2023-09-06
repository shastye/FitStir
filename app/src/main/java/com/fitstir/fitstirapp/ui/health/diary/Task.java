package com.fitstir.fitstirapp.ui.health.diary;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    private String name;
    private ArrayList<Date> completedOn;



    public Task() {
        name = null;
        completedOn = new ArrayList<>();
    }
    public Task(String name) {
        this.name = name;
        this.completedOn = new ArrayList<>();
    }



    public void addDate(Date date) {
        completedOn.add(date);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Date> getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(ArrayList<Date> completedOn) {
        this.completedOn = completedOn;
    }
}
