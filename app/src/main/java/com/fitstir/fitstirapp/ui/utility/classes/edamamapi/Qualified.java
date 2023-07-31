package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.ArrayList;
import java.util.Objects;

public class Qualified {
    private ArrayList<Qualify> qualifiers;
    private int weight;

    public Qualified() {
        setQualifiers(new ArrayList<>());
        setWeight(0);
    }
    public Qualified(ArrayList<Qualify> qualifiers, int weight) {
        this.setQualifiers(qualifiers);
        this.setWeight(weight);
    }
    public Qualified(Qualified qualified) {
        this.setQualifiers(qualified.getQualifiers());
        this.setWeight(qualified.getWeight());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Qualified)) {
            return false;
        } else {
            Qualified qualified = (Qualified) o;
            return (Objects.equals(this.getQualifiers(), qualified.getQualifiers()) && Objects.equals(this.getWeight(), qualified.getWeight()));
        }
    }

    public ArrayList<Qualify> getQualifiers() { return qualifiers; }
    public int getWeight() { return weight; }

    public void setQualifiers(ArrayList<Qualify> qualifiers) { this.qualifiers = qualifiers; }
    public void setWeight(int weight) { this.weight = weight; }
}
