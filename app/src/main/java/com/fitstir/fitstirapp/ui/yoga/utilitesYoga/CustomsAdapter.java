package com.fitstir.fitstirapp.ui.yoga.utilitesYoga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.CustomInterface;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;

import java.util.ArrayList;

public class CustomsAdapter extends RecyclerView.Adapter<CustomsAdapter.ViewHolder> {

    private final CustomInterface rvInterface;
    private Context context;
    private ArrayList<PoseModel> poseList;

    public CustomsAdapter(CustomInterface rvInterface, Context context, ArrayList<PoseModel> poseList) {
        this.rvInterface = rvInterface;
        this.context = context;
        this.poseList = poseList;
    }

    @NonNull
    @Override
    public CustomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_yoga_list, parent,false);
        return new CustomsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomsAdapter.ViewHolder holder, int position) {

        PoseModel data = poseList.get(position);

        holder.name_List.setText(data.getEnglish_name());
        holder.cat_List.setText(data.getPose_Type());
        holder.levels_List.setText(data.getDifficulty_level());
        Glide.with(context)
                .load(data.getUrl_png())
                .into(holder.img_List);
        holder.add_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    rvInterface.onItemClick(holder.getAdapterPosition(), true);
                }
            }
        });

    }

    public void setFilterList(ArrayList<PoseModel> custom){
        this.poseList = custom;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return poseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_List, cat_List, levels_List;
        private ImageView img_List;
        private ImageButton add_List;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_List = itemView.findViewById(R.id.name_Customs);
            cat_List = itemView.findViewById(R.id.cat_Customs);
            levels_List = itemView.findViewById(R.id.levels_Customs);
            img_List = itemView.findViewById(R.id.yoga_Image_Customs);
            add_List = itemView.findViewById(R.id.add_To_BTN);

        }
    }
}
