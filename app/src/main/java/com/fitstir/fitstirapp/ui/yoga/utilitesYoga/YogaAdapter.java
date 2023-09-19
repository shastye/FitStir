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
import com.fitstir.fitstirapp.ui.yoga.models.YogaApiModel;

import java.util.ArrayList;
import java.util.Locale;

public class YogaAdapter extends RecyclerView.Adapter<YogaAdapter.ViewHolder> {

    private final RvInterface rvInterface;
    private Context context;
    private ArrayList<YogaApiModel> apiCallList;

    public YogaAdapter(RvInterface rvInterface, Context context, ArrayList<YogaApiModel> apiCallList) {
        this.rvInterface = rvInterface;
        this.context = context;
        this.apiCallList = apiCallList;
    }

    @NonNull
    @Override
    public YogaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yoga_category_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaAdapter.ViewHolder holder, int position) {
        YogaApiModel apiCall = new YogaApiModel();
        holder.pose_name.setText(apiCall.getEnglish_name().toLowerCase(Locale.ROOT));
        holder.benefits.setText(apiCall.getPose_benefits().toLowerCase(Locale.ROOT));

        Glide.with(context)
                .load(apiCall.getUrl_png())
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
    public int getItemCount() {return apiCallList.size();}

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
