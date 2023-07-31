package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.ArrayList;

public class Measure {
    private String uri;
    private String label;
    private int weight;
    private ArrayList<Qualified> qualified;

    public Measure() {
        this.uri = "";
        this.label = "";
        this.weight = 0;
        this.qualified = new ArrayList<>();
    }
    public Measure(String uri, String label, int weight) {
        this.uri = uri;
        this.label = label;
        this.weight = weight;
        this.qualified = null;
    }
    public Measure(String uri, String label, int weight, ArrayList<Qualified> qualified) {
        this.uri = uri;
        this.label = label;
        this.weight = weight;
        this.qualified = qualified;
    }
    public Measure(Measure measure) {
        this.uri = measure.uri;
        this.label = measure.label;
        this.weight = measure.weight;
        this.qualified = measure.qualified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Measure)) {
            return false;
        } else {
            Measure measure = (Measure) o;
            return weight == measure.weight && uri.equals(measure.uri) && label.equals(measure.label) && qualified.equals(measure.qualified);
        }
    }

    public String getUri() { return uri; }
    public String getLabel() { return label; }
    public int getWeight() { return weight; }
    public ArrayList<Qualified> getQualified() { return qualified; }

    public void setUri(String uri) { this.uri = uri; }
    public void setLabel(String label) { this.label = label; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setQualified(ArrayList<Qualified> qualified) { this.qualified = qualified; }
}
