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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.RvInterface;

import java.util.ArrayList;

public class circuitAdapter extends RecyclerView.Adapter<circuitAdapter.ViewHolder> {

    private ArrayList<CircuitModel> list;
    private RvInterface rvInterface;
    private Context context;

    public circuitAdapter(Context context, ArrayList<CircuitModel> circuitModels, RvInterface rvInterface) {

        this.rvInterface = rvInterface;
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

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.cardView.getContext(), R.anim.anim_recyclerview));
        holder.name.setText(circuits.getExercise());
        holder.direction.setText(circuits.getDirections());
        holder.sets.setText(circuits.getSets());
        holder.cal.setText(circuits.getCalBurned());
        holder.reps.setText(circuits.getReps());
        holder.time.setText(circuits.getDuration());

        Glide.with(context)
                .load(circuits.getGif())
                .into(holder.gif);

    }

    @Override
    public int getItemCount() {return list.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView gif;
        private TextView name, direction, sets, reps, time, cal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.circuit_Cardview);
            gif = itemView.findViewById(R.id.circuit_gif);
            name = itemView.findViewById(R.id.view_ExerciseName);
            direction = itemView.findViewById(R.id.circuit_directions);
            sets = itemView.findViewById(R.id.sets_circuit);
            reps = itemView.findViewById(R.id.reps_circuit);
            time = itemView.findViewById(R.id.dur_circuit);
            cal = itemView.findViewById(R.id.cal_circuit);
        }
    }
}
