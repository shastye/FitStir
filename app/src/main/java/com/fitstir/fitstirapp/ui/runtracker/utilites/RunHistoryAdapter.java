package com.fitstir.fitstirapp.ui.runtracker.utilites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RunHistoryAdapter extends RecyclerView.Adapter<RunHistoryAdapter.ViewHolder> {

    private ArrayList<RunnerData> rundata;
    private Context context;
    private DecimalFormat decimalFormat;
    private SimpleDateFormat formatted;
    private Date runDate;

    public RunHistoryAdapter(ArrayList<RunnerData> data, Context context) {
        this.rundata = data;
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
        RunnerData runner = rundata.get(position);

        runDate = new Date();
        decimalFormat = new DecimalFormat("##.##");
        formatted = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        String distanceFormatted = decimalFormat.format(runner.getTotalDistance());
        holder.date.setText(formatted.format(runDate));
        holder.distance.setText(distanceFormatted);
        holder.calories.setText(runner.getBurnedCalories());
        holder.areaLat.setText(runner.getLatitude());
        holder.areaLng.setText(runner.getLongitude());

    }

    @Override
    public int getItemCount() {return rundata.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView distance, date,areaLat, calories,areaLng;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calories = itemView.findViewById(R.id.calories_burned);
            distance = itemView.findViewById(R.id.run_distance);
            date = itemView.findViewById(R.id.run_date);
            areaLat = itemView.findViewById(R.id.run_Lat);
            areaLng = itemView.findViewById(R.id.run_Lng);
        }
    }
}
