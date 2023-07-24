package com.fitstir.fitstirapp.ui.utility.classes;

public class SectionItem {
    final int icon;
    final String label;

    public SectionItem(int drawable, String label) {
        this.icon = drawable;
        this.label = label;
    }

    int getIcon() {
        return icon;
    }

    String getLabel() {
        return label;
    }
}
