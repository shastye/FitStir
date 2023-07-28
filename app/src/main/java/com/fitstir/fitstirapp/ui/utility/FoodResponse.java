package com.fitstir.fitstirapp.ui.utility;

import java.util.ArrayList;

public class FoodResponse {
    public class Parsed {
        public class Food {
            public class Nutrients {
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

            public class ServingSize {
                String uri;
                String label;
                int quantity;
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

        public class Measure {
            public class Qualified {
                public class Qualifiers {
                    String uri;
                    String label;
                }

                Qualifiers qualifiers;
                int weight;
            }

            String uri;
            String label;
            int weight;
            Qualified qualified;
        }

        Food food;
        int quanitity;
        Measure measure;
    }

    public class Hint {
        Parsed.Food food;
        ArrayList<Parsed.Measure> measures;
    }

    public class Link {
        public class Next {
            String title;
            String href;
        }

        Next next;
    }

    String text;
    Parsed parsed;
    ArrayList<Hint> hints;
    ArrayList<Link> _links;
}
