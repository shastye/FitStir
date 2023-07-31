package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.ArrayList;

public class EdamamAPI_FoodResponse {
    private String text;
    private ArrayList<Parsed> parsed;
    private ArrayList<Hint> hints;
    private Link _links;

    public EdamamAPI_FoodResponse() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof EdamamAPI_FoodResponse)) {
            return false;
        } else {
            EdamamAPI_FoodResponse that = (EdamamAPI_FoodResponse) o;
            return text.equals(that.text) && parsed.equals(that.parsed) && hints.equals(that.hints) && _links.equals(that._links);
        }
    }

    public String getText() { return text; }
    public ArrayList<Parsed> getParsed() { return parsed; }
    public ArrayList<Hint> getHints() { return hints; }
    public Link get_links() { return _links; }

    public void setText(String text) { this.text = text; }
    public void setParsed(ArrayList<Parsed> parsed) { this.parsed = parsed; }
    public void setHints(ArrayList<Hint> hints) { this.hints = hints; }
    public void set_links(Link _links) { this._links = _links; }
}
