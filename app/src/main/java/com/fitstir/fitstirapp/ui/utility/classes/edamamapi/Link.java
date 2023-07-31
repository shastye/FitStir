package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.Objects;

public class Link {

    private Next next;

    public Link() {
        this.next = new Next();
    }
    public Link(Next next) {
        this.next = next;
    }
    public Link(Link link) {
        this.next = link.next;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Link)) {
            return false;
        } else {
            Link link = (Link) o;
            return Objects.equals(this.next, link.next);
        }
    }

    public Next getNext() { return this.next; }
    public void setNext(Next next) { this.next = next; }
}
