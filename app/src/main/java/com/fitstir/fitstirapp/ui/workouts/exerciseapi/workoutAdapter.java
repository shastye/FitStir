package com.fitstir.fitstirapp.ui.workouts.exerciseapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import java.util.ArrayList;

public class workoutAdapter extends RecyclerView.Adapter<workoutAdapter.ViewHolder>{
    private ArrayList<WorkoutApi> apiList;
    private Context context;

    private RvInterface rvInterface;

    public workoutAdapter(Context context, ArrayList<WorkoutApi> workout_List, RvInterface rvInterface)
    {
        this.context = context;
        this.apiList = workout_List;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_workout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WorkoutApi body = apiList.get(position);

        holder.name.setText(body.getExercise());
        holder.target.setText(body.getTarget());
        holder.equipment.setText(body.getEquipment());
        holder.bodyPart.setText(body.getBodyPart());
        holder.directions.setText(body.getDirections());

        Glide.with(context)
                .load(body.getImage())
                .into(holder.image);
        Glide.with(context)
                .load(body.getGifURL())
                .into(holder.gifImage);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    rvInterface.onItemClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return apiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, bodyPart, equipment, target, directions;
        private ImageView image, gifImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvTitle);
            bodyPart = itemView.findViewById(R.id.tvBodyPart);
            equipment = itemView.findViewById(R.id.tvEquipment);
            target = itemView.findViewById(R.id.tvTarget);
            directions = itemView.findViewById(R.id.tvDirections);
            gifImage = itemView.findViewById(R.id.tvGifImage);
            image = itemView.findViewById(R.id.tvImage);

        }
    }
}


