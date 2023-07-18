package com.fitstir.fitstirapp.ui.goals;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Date;



public class Goal {
    private String name;
    private String type; // TODO: change to enum
    private String unit; // TODO: set based on enum value of type
    private int value;
    private ArrayList<Pair<Date, Double>> data;

    private Date minDate;
    private Date maxDate;

    public Goal() {
        this.name = "Goal Name";
        this.type = "Goal Type";
        this.unit = "Goal Unit";
        this.value = 0;
        this.data = new ArrayList<>();
        this.minDate = null;
        this.maxDate = null;
    }
    public Goal(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.unit = "Miles";
        this.value = value;
        this.data = new ArrayList<>();
        this.minDate = null;
        this.maxDate = null;
    }
    public Goal(Goal goal) {
        this.name = goal.name;
        this.type = goal.type;
        this.unit = goal.unit;
        this.value = goal.value;
        this.data = new ArrayList<>(goal.data);
        this.minDate = goal.minDate;
        this.maxDate = goal.maxDate;
    }

    public void addData(Pair<Date, Double> data) {
        if (this.data.size() == 0) {
            this.maxDate = data.first;
        } else if (this.data.size() == 1) {
            Date tempDate = this.maxDate;

            if (data.first.before(tempDate)) {
                this.minDate = data.first;
            } else {
                this.maxDate = data.first;
                this.minDate = tempDate;
            }
        } else {
            if (data.first.after(this.maxDate)) {
                this.maxDate = data.first;
            } else if (data.first.before(this.minDate)) {
                this.minDate = data.first;
            }
        }

        this.data.add(data);
    }
    public void resetData() { this.data.clear(); }

    public String getName() { return this.name; }
    public String getType() { return this.type; }
    public String getUnit() { return this.unit; }
    public int getValue() { return this.value; }
    public ArrayList<Pair<Date, Double>> getData() { return this.data; }
    public Date getMaxDate() { return this.maxDate; }
    public Date getMinDate() { return this.minDate; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setUnit(String unit) { this.unit = unit; }
    public void setValue(int value) { this.value = value; }
    public void setData(ArrayList<Pair<Date, Double>> data) { this.data = new ArrayList<>(data); }
    public void setMinDate(Date minDate) { this.minDate = minDate; }
    public void setMaxDate(Date maxDate) { this.maxDate = maxDate; }
}
