package com.fitstir.fitstirapp.ui.runtracker.utilites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RunHistoryAdapter extends RecyclerView.Adapter<RunHistoryAdapter.ViewHolder> {

    private ArrayList<RunnerData> rundata;
    private Context context;
    private DecimalFormat decimalFormat;
    private SimpleDateFormat formatted;
    private final RvInterface rvInterface;

    public RunHistoryAdapter(Context context,ArrayList<RunnerData> data, RvInterface rvInterface) {
        this.rundata = data;
        this.context = context;
        this.rvInterface = rvInterface;
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

        decimalFormat = new DecimalFormat("##.##");
        String distanceFormatted = decimalFormat.format(runner.getTotalDistance());
        holder.date.setText(runner.getCompletedDate());
        holder.distance.setText(distanceFormatted);
        String calories = decimalFormat.format(runner.getBurnedCalories());
        holder.calories.setText(calories);
        String lat = String.valueOf(runner.getLatitude());
        holder.areaLat.setText(lat);
        String lng = String.valueOf(runner.getLongitude());
        holder.areaLng.setText(lng);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    rvInterface.onItemClick(holder.getAdapterPosition());
                }
            }
        });

    }
    @Override
    public int getItemCount() {return rundata.size();}
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView distance, date,areaLat, calories,areaLng;
        private CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.runHistory_cardview);
            calories = itemView.findViewById(R.id.calories_burned);
            distance = itemView.findViewById(R.id.run_distance);
            date = itemView.findViewById(R.id.run_date);
            areaLat = itemView.findViewById(R.id.run_Lat);
            areaLng = itemView.findViewById(R.id.run_Lng);
        }
    }
}
