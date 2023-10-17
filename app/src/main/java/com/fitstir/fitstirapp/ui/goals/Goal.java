package com.fitstir.fitstirapp.ui.goals;

import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;


public class Goal {
    private GoalTypes type;
    private String unit;
    private int value;
    private ArrayList<GoalDataPair> data;
    private final String id;

    private Date minDate;
    private Date maxDate;

    public Goal() {
        this.type = GoalTypes.RUN_CLUB_DISTANCE;
        this.unit = this.type.getImperialUnit();
        this.value = 0;
        this.data = new ArrayList<>();
        this.minDate = null;
        this.maxDate = null;
        this.id = RandomStringUtils.randomAlphanumeric(12); // ID is 12 characters long
    }
    public Goal(GoalTypes type, int value) {
        this.type = type;
        this.unit = this.type.getImperialUnit();
        this.value = value;
        this.data = new ArrayList<>();
        this.minDate = null;
        this.maxDate = null;
        this.id = RandomStringUtils.randomAlphanumeric(12); // ID is 12 characters long
    }
    public Goal(Goal goal) {
        this.type = goal.type;
        this.unit = goal.unit;
        this.value = goal.value;
        this.data = new ArrayList<>(goal.data);
        this.minDate = goal.minDate;
        this.maxDate = goal.maxDate;
        this.id = goal.id;
    }

    public void addData(Date date, double value) {
        GoalDataPair  data = new GoalDataPair (date, value);

        if (this.data.size() == 0) {
            this.maxDate = data.first;
        } else if (this.data.size() == 1) {
            Date tempDate = this.maxDate;

            if (Methods.firstIsAfterSecond(tempDate, data.first)) {
                this.minDate = data.first;
            } else {
                this.maxDate = data.first;
                this.minDate = tempDate;
            }
        } else {
            if (Methods.firstIsAfterSecond(data.first, this.maxDate)) {
                this.maxDate = data.first;
            } else if (data.first.before(this.minDate)) {
                this.minDate = data.first;
            }
        }

        this.data.add(data);
    }
    public void resetData() { this.data.clear(); }

    public GoalTypes getType() { return this.type; }
    public String getUnit() { return this.unit; }
    public int getValue() { return this.value; }
    public ArrayList<GoalDataPair> getData() { return this.data; }
    public Date getMaxDate() { return this.maxDate; }
    public Date getMinDate() { return this.minDate; }
    public String getID() { return id; }

    public void setType(GoalTypes type) { this.type = type; }
    public void setValue(int value) { this.value = value; }
    public void setData(ArrayList<GoalDataPair> data) { this.data = new ArrayList<>(data); }
}
