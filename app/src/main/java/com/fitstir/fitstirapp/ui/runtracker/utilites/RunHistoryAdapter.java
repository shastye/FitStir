package com.fitstir.fitstirapp.ui.runtracker.utilites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;

import java.util.ArrayList;
import java.util.Date;

public class RunHistoryAdapter extends RecyclerView.Adapter<RunHistoryAdapter.ViewHolder> {

    private ArrayList<RunnerData> data;
    private Context context;

    public RunHistoryAdapter(ArrayList<RunnerData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RunHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunHistoryAdapter.ViewHolder holder, int position) {
        RunnerData runner = new RunnerData();

        holder.dayTime.setText(Integer.toString(runner.getTimeOfDay()));
        holder.date.setText(Integer.toString(runner.getRunDate()));
        holder.area.setText(Integer.toString(runner.getLatLng()));
        holder.distance.setText(Integer.toString(runner.getRunDistance()));
    }

    @Override
    public int getItemCount() {return data.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EditText distance, date, dayTime,area;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            distance = itemView.findViewById(R.id.run_distance);
            date = itemView.findViewById(R.id.run_date);
            dayTime = itemView.findViewById(R.id.run_timeOfDay);
            area = itemView.findViewById(R.id.run_Latlng);
        }
    }
}
