package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.Objects;

public class Qualify {
    private String uri;
    private String label;

    public Qualify() {
        this.uri = "";
        this.label = "";
    }
    public Qualify(String uri, String label) {
        this.uri = uri;
        this.label = label;
    }
    public Qualify(Qualify qualify) {
        this.uri = qualify.uri;
        this.label = qualify.label;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Qualify)) {
            return false;
        } else {
            Qualify qualify = (Qualify) o;
            return (Objects.equals(this.uri, qualify.uri) && Objects.equals(this.label, qualify.label));
        }
    }

    public String getUri() { return uri; }
    public String getLabel() { return label; }

    public void setUri(String uri) { this.uri = uri; }
    public void setLabel(String label) { this.label = label; }
}
