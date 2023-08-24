package com.fitstir.fitstirapp.ui.health.edamamapi.recipev2;

public class Nutrient {

    String label;
    float quantity;
    String unit;

    Nutrient() { }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
