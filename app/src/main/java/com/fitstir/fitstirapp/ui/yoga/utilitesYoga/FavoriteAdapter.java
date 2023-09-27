package com.fitstir.fitstirapp.ui.yoga.utilitesYoga;

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
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>  {
    private final RvInterface rvInterface;
    private Context context;
    private ArrayList<PoseModel> faveList;


    public FavoriteAdapter(RvInterface rvInterface, Context context, ArrayList<PoseModel> list) {
        this.rvInterface = rvInterface;
        this.context = context;
        this.faveList = list;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorite_yoga, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        PoseModel poseApi = faveList.get(position);

        holder.pose_name.setText(poseApi.getEnglish_name());
        holder.pose_Type.setText(poseApi.getPose_Type());
        holder.pose_Level.setText(poseApi.getDifficulty_level());

        Glide.with(context)
                .load(poseApi.getUrl_png())
                .into(holder.pose_Image);
        holder.pose_Image.setOnClickListener(new View.OnClickListener() {
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
        return faveList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pose_name, pose_Level, pose_Type;
        private ImageView pose_Image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pose_name = itemView.findViewById(R.id.fave_YogaName);
            pose_Image = itemView.findViewById(R.id.yoga_Image);
            pose_Level = itemView.findViewById(R.id.fave_Difficulty);
            pose_Type = itemView.findViewById(R.id.fave_Type);
        }
    }
}
