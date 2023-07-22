package com.fitstir.fitstirapp.ui.goals;

import androidx.core.util.Pair;

import com.fitstir.fitstirapp.ui.utility.enums.WorkoutTypes;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;


public class Goal {
    private String name;
    private WorkoutTypes type;
    private String unit;
    private int value;
    private ArrayList<Pair<Date, Double>> data;
    private final String id;

    private Date minDate;
    private Date maxDate;

    public Goal() {
        this.name = "Goal Name";
        this.type = WorkoutTypes.RUN_CLUB_DISTANCE;
        this.unit = this.type.getImperialUnit();
        this.value = 0;
        this.data = new ArrayList<>();
        this.minDate = null;
        this.maxDate = null;
        this.id = RandomStringUtils.randomAlphanumeric(12); // ID is 12 characters long
    }
    public Goal(String name, WorkoutTypes type, int value) {
        this.name = name;
        this.type = type;
        this.unit = this.type.getImperialUnit();
        this.value = value;
        this.data = new ArrayList<>();
        this.minDate = null;
        this.maxDate = null;
        this.id = RandomStringUtils.randomAlphanumeric(12); // ID is 12 characters long
    }
    public Goal(Goal goal) {
        this.name = goal.name;
        this.type = goal.type;
        this.unit = goal.unit;
        this.value = goal.value;
        this.data = new ArrayList<>(goal.data);
        this.minDate = goal.minDate;
        this.maxDate = goal.maxDate;
        this.id = goal.id;
    }

    public void addData(Date date, double value) {
        Pair<Date, Double> data = new Pair<>(date, value);

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
    public WorkoutTypes getType() { return this.type; }
    public String getUnit() { return this.unit; }
    public int getValue() { return this.value; }
    public ArrayList<Pair<Date, Double>> getData() { return this.data; }
    public Date getMaxDate() { return this.maxDate; }
    public Date getMinDate() { return this.minDate; }
    public String getID() { return id; }

    public void setName(String name) { this.name = name; }
    public void setType(WorkoutTypes type) { this.type = type; }
    public void setValue(int value) { this.value = value; }
    public void setData(ArrayList<Pair<Date, Double>> data) { this.data = new ArrayList<>(data); }
}
