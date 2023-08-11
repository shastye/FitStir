package com.fitstir.fitstirapp.ui.health.edamamapi.recipev2;

import com.fitstir.fitstirapp.ui.health.edamamapi.Link;

import java.util.Objects;

public class Hit {
    private Recipe recipe;
    private Link _links;

    public Hit() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Hit)) {
            return false;
        } else {
            Hit hit = (Hit) o;
            return Objects.equals(recipe, hit.recipe) && Objects.equals(_links, hit._links);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"recipe\"=" + recipe + "," +
                "\"_links\"=" + _links +
                "}";
    }

    public Recipe getRecipe() { return recipe; }
    public Link get_links() { return _links; }

    public void setRecipe(Recipe recipe) { this.recipe = recipe; }
    public void set_links(Link _links) { this._links = _links; }
}
