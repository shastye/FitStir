package com.fitstir.fitstirapp.ui.utility.classes;

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
    private final ArrayList<SectionItem> options;
    private final View root;

    public SectionGridAdapter(ArrayList<SectionItem> options, View root) {
        super();

        this.options = options;
        this.root = root;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SectionItem item = options.get(position);

        LayoutInflater inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_section, parent, false);

        ImageView icon = view.findViewById(R.id.layout_section_icon);
        icon.setImageResource(item.getIcon());

        TextView label = view.findViewById(R.id.layout_section_label);
        label.setText(item.getLabel());

        return view;
    }

    public View getRoot() { return root; }
    public ArrayList<SectionItem> getOptions() { return options; }
}
