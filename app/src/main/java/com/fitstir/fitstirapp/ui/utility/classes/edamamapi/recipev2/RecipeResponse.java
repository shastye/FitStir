package com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2;

import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.Link;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeResponse {
    private int from;
    private int to;
    private int count;
    private Link _links;
    private ArrayList<Hit> hits;

    public RecipeResponse() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof RecipeResponse)) {
            return false;
        } else {
            RecipeResponse that = (RecipeResponse) o;
            return from == that.from && to == that.to && count == that.count && Objects.equals(_links, that._links) && Objects.equals(hits, that.hits);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"from\"=" + from + "," +
                "\"to\"=" + to + "," +
                "\"count\"=" + count + "," +
                "\"_links\"=" + _links + "," +
                "\"hits\"=" + hits +
                "}";
    }

    public int getFrom() { return from; }
    public int getTo() { return to; }
    public int getCount() { return count; }
    public Link get_links() { return _links; }
    public ArrayList<Hit> getHits() { return hits; }

    public void setFrom(int from) { this.from = from; }
    public void setTo(int to) { this.to = to; }
    public void setCount(int count) { this.count = count; }
    public void set_links(Link _links) { this._links = _links; }
    public void setHits(ArrayList<Hit> hits) { this.hits = hits; } 
}
