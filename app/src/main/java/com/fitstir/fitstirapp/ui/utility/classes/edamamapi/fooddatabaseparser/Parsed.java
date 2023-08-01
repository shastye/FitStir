package com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser;

public class Parsed {
    private Food food;
    private float quantity;
    private Measure measure;

    public Parsed() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Parsed)) {
            return false;
        } else {
            Parsed parsed = (Parsed) o;
            return quantity == parsed.quantity && food.equals(parsed.food) && measure.equals(parsed.measure);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"food\":" + food + "," +
                "\"quantity\":" + quantity + "," +
                "\"measure\":" + measure +
                "}";
    }

    public Food getFood() { return food; }
    public float getQuantity() { return quantity; }
    public Measure getMeasure() { return measure; }

    public void setFood(Food food) { this.food = food; }
    public void setQuantity(float quantity) { this.quantity = quantity; }
    public void setMeasure(Measure measure) { this.measure = measure; }
}
