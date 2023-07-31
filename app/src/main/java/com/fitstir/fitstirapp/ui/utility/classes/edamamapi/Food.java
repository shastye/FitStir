package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

import java.util.ArrayList;

public class Food {

    public static class ServingSize {
        String uri;
        String label;
        int quantity;
    }

    public static class Nutrients {
        int ca;
        int chocdf;
        int chocdf_net;
        int chole;
        int enerc_kcal;
        int fams;
        int fapu;
        int fasat;
        int fat;
        int fatrn;
        int fe;
        int fibtg;
        int folac;
        int foldfe;
        int k;
        int mg;
        int na;
        int nia;
        int p;
        int procnt;
        int ribf;
        int sugar;
        int sugar_added;
        int sugar_alcohol;
        int thia;
        int tocpha;
        int vita_rae;
        int vitb12;
        int vitb6a;
        int vitc;
        int vitd;
        int vitk1;
        int water;
        int zn;
    }

    String foodID;
    String label;
    String knownAs;
    Nutrients nutrients;
    String category;
    String categoryLabel;

    String image;
    String foodContentsLabel;
    ArrayList<ServingSize> servingSizes;
    int servingsPerContainer;
}
