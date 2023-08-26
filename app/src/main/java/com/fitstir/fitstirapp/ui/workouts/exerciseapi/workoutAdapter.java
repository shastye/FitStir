package com.fitstir.fitstirapp.ui.workouts.exerciseapi;

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

    public void setFilterList(ArrayList<WorkoutApi> filtered){
        this.apiList = filtered;
        notifyDataSetChanged();
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
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.cardView.getContext(), R.anim.anim_recyclerview));

        Glide.with(context)
                .load(body.getImage())
                .into(holder.image);

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
        private TextView name, bodyPart, equipment, target;
        private ImageView image;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            name = itemView.findViewById(R.id.tvTitle);
            bodyPart = itemView.findViewById(R.id.tvBodyPart);
            equipment = itemView.findViewById(R.id.tvEquipment);
            target = itemView.findViewById(R.id.tvTarget);
            image = itemView.findViewById(R.id.tvImage);

        }
    }
}


