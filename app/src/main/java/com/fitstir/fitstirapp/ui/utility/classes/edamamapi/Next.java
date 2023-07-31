package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.Objects;

public class Next {
    private String title;
    private String href;

    public Next() {
        title = "";
        href = "";
    }
    public Next(String title, String href) {
        this.title = title;
        this.href = href;
    }
    public Next(Next next) {
        this.title = next.title;
        this.href = next.href;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Next)) {
            return false;
        } else {
            Next next = (Next) o;
            return (Objects.equals(this.title, next.title) && Objects.equals(this.href, next.href));
        }
    }

    public String getTitle() { return this.title; }

    public String getHref() { return this.href; }

    public void setTitle(String title) { this.title = title; }

    public void setHref(String href) { this.href = href; }
}
