package com.fitstir.fitstirapp.ui.utility.classes;

public class SectionItem {
    final int icon;
    final String label;

    public SectionItem(int _drawable, String _label) {
        icon = _drawable;
        label = _label;
    }

    int getIcon() {
        return icon;
    }

    String getLabel() {
        return label;
    }
}
