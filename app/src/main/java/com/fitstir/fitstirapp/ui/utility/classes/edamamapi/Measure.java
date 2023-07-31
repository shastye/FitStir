package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.ArrayList;

public class Measure {
    public static class Qualified {
        public class Qualifier {
            public String uri;
            public String label;
        }

        ArrayList<Qualifier> qualifiers;
        int weight;
    }

    String uri;
    String label;
    int weight;
    ArrayList<Qualified> qualified;
}
