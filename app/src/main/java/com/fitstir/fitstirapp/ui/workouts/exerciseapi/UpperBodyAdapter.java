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
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.upperbody.UpperBodyApi;

import java.util.ArrayList;

public class UpperBodyAdapter extends RecyclerView.Adapter<UpperBodyAdapter.ViewHolder>{

    ArrayList<UpperBodyApi> apiList;
    Context context;

    public UpperBodyAdapter(Context context, ArrayList<UpperBodyApi> workout_List)
    {
        this.context = context;
        this.apiList = workout_List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_workout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UpperBodyApi body = apiList.get(position);
        holder.name.setText(body.getExercise());
        holder.target.setText(body.getTarget());
        holder.equipment.setText(body.getEquipment());
        holder.bodyPart.setText(body.getBodyPart());

        Glide.with(context)
                .load(body.getGifURL())
                .into(holder.gif);
        //holder.directions.setText(body.getDirections());
    }

    @Override
    public int getItemCount() {
        return apiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, bodyPart, equipment, target, directions;
        private ImageView gif;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvTitle);
            bodyPart = itemView.findViewById(R.id.tvBodyPart);
            equipment = itemView.findViewById(R.id.tvEquipment);
            target = itemView.findViewById(R.id.tvTarget);
            //directions = itemView.findViewById(R.id.tvDirections);
            gif = itemView.findViewById(R.id.tvImage);
        }
    }
}


