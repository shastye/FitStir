package com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser;

import com.fitstir.fitstirapp.ui.health.edamamapi.ISearchResult;

import java.util.ArrayList;
import java.util.Objects;

public class Hint implements ISearchResult {
    private Food food;
    private ArrayList<Measure> measures;

    public Hint() {    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Hint)) {
            return false;
        } else {
            Hint hint = (Hint) o;
            return Objects.equals(food, hint.food) && Objects.equals(measures, hint.measures);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"food\":" + food + "," +
                "\"measures\":" + measures +
                "}";
    }

    public Food getFood() { return food; }
    public ArrayList<Measure> getMeasures() { return measures; }

    public void setFood(Food food) { this.food = food; }
    public void setMeasures(ArrayList<Measure> measures) { this.measures = measures; }

    @Override
    public ISearchResult getItem() {
        return this;
    }

    @Override
    public void setItem(ISearchResult item) {
        this.measures = ((Hint) item).measures;
        this.food = ((Hint) item).food;
    }
}
