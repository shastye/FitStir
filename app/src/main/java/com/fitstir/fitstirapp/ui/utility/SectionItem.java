package com.fitstir.fitstirapp.ui.utility;

public class SectionItem {
    int icon;
    String label;

    SectionItem(int _drawable, String _label) {
        icon = _drawable;
        label = _label;
    }

    int getIcon() { return icon; }
    String getLabel() { return label; }
}
