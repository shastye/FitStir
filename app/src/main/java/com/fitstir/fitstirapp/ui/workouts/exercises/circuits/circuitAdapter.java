package com.fitstir.fitstirapp.ui.workouts.exercises.circuits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class circuitAdapter extends RecyclerView.Adapter<circuitAdapter.ViewHolder> {

    private ArrayList<CircuitModel> list;
    private Context context;
    private RvInterface rvInterface;

    public circuitAdapter(Context context, ArrayList<CircuitModel> circuitModels, RvInterface rvInterface) {

        this.context = context;
        this.list = circuitModels;
        this.rvInterface = rvInterface;
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
               if(rvInterface != null){
                   rvInterface.onItemClick(holder.getAdapterPosition());
               }
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
