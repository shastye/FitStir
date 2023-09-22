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
import com.fitstir.fitstirapp.ui.yoga.models.CategoryModel;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.ArrayList;
import java.util.Locale;

public class YogaAdapter extends RecyclerView.Adapter<YogaAdapter.ViewHolder> {

    private final RvInterface rvInterface;
    private Context context;
    private ArrayList<PoseModel> posesList;


    public YogaAdapter(RvInterface rvInterface){
        this.rvInterface = rvInterface;
    };

    public YogaAdapter(RvInterface rvInterface, Context context, ArrayList<PoseModel> posesList) {
        this.rvInterface = rvInterface;
        this.context = context;
        this.posesList = posesList;
    }
    @NonNull
    @Override
    public YogaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yoga_category_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaAdapter.ViewHolder holder, int position) {
        PoseModel poseApi = posesList.get(position);
        holder.pose_name.setText(poseApi.getEnglish_name());
        holder.benefits.setText(poseApi.getPose_benefits());
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
        return posesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pose_name, benefits;
        private ImageView pose_Image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pose_name = itemView.findViewById(R.id.title_name_TV);
            benefits = itemView.findViewById(R.id.benefits_TV);
            pose_Image = itemView.findViewById(R.id.yogaPose_IMG);
        }
    }
}
