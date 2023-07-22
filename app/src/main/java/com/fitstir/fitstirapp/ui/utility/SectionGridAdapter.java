package com.fitstir.fitstirapp.ui.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitstir.fitstirapp.R;

import java.util.ArrayList;

public class SectionGridAdapter extends BaseAdapter {
    public static class SectionItem {
        final int icon;
        final String label;

        SectionItem(int _drawable, String _label) {
            icon = _drawable;
            label = _label;
        }

        int getIcon() { return icon; }
        String getLabel() { return label; }
    }



    private final ArrayList<SectionItem> options;
    private final View root;

    public SectionGridAdapter(ArrayList<SectionItem> _options, View _root) {
        super();

        options = _options;
        root = _root;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int _position) {
        return options.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return (long)_position;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        SectionItem item = options.get(_position);

        LayoutInflater inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_section, _parent, false);

        ImageView icon = view.findViewById(R.id.layout_section_icon);
        icon.setImageResource(item.getIcon());

        TextView label = view.findViewById(R.id.layout_section_label);
        label.setText(item.getLabel());

        return view;
    }

    public View getRoot() { return root; }
    public ArrayList<SectionItem> getOptions() { return options; }
}
