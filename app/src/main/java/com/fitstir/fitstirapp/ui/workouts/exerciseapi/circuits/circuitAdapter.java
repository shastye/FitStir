package com.fitstir.fitstirapp.ui.workouts.exerciseapi.circuits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.RvInterface;

import java.util.ArrayList;

public class circuitAdapter extends RecyclerView.Adapter<circuitAdapter.ViewHolder> {

    private ArrayList<CircuitModel> list;
    private Context context;

    public circuitAdapter(Context context, ArrayList<CircuitModel> circuitModels) {

        this.context = context;
        this.list = circuitModels;
    }

    @NonNull
    @Override
    public circuitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circuit_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull circuitAdapter.ViewHolder holder, int position) {

        CircuitModel circuits = list.get(position);

        holder.name.setText(circuits.getExercise());
        holder.direction.setText(circuits.getDirections());
        holder.sets.setText(Integer.toString(circuits.getSets()));
        holder.cal.setText(Integer.toString(circuits.getCalBurned()));
        holder.reps.setText(Integer.toString(circuits.getReps()));
        holder.time.setText(Integer.toString(circuits.getDuration()));
        holder.total.setText(Integer.toString(circuits.getTotalBurn()));

        holder.mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_viewCircuitFragment_to_navigation_circuit_workouts);
            }
        });

        Glide.with(context)
                .load(circuits.getMapImage())
                .into(holder.mapImage);

        Glide.with(context)
                .load(circuits.getGif())
                .into(holder.gif);

    }

    @Override
    public int getItemCount() {return list.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView gif, mapImage;
        private TextView name, direction, sets, reps, time, cal, total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gif = itemView.findViewById(R.id.circuit_gif);
            name = itemView.findViewById(R.id.view_ExerciseName);
            direction = itemView.findViewById(R.id.circuit_directions);
            sets = itemView.findViewById(R.id.sets_circuit);
            reps = itemView.findViewById(R.id.reps_circuit);
            time = itemView.findViewById(R.id.dur_circuit);
            cal = itemView.findViewById(R.id.cal_circuit);
            mapImage = itemView.findViewById(R.id.map_Image);
            total = itemView.findViewById(R.id.cal_total);

        }
    }
}
