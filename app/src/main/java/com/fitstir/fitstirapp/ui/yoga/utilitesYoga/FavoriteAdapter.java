package com.fitstir.fitstirapp.ui.yoga.utilitesYoga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    poseApi.setFavoriteClickID(0);
                    rvInterface.onItemClick(holder.getAdapterPosition());

                }
            }
        });
        holder.pose_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    poseApi.setFavoriteClickID(1);
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
        private ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pose_name = itemView.findViewById(R.id.fave_YogaName);
            pose_Image = itemView.findViewById(R.id.yoga_Image);
            pose_Level = itemView.findViewById(R.id.fave_Difficulty);
            pose_Type = itemView.findViewById(R.id.fave_Type);
            delete = itemView.findViewById(R.id.delete_Favorite);
        }
    }
}