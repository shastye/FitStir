package com.fitstir.fitstirapp.ui.yoga.utilitesYoga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.CustomInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {
    private Map<String, List<PoseModel>> folderPoseMap;
    private ArrayList<PoseModel> list;
    private Context context;
    private final CustomInterface rvInterface;

    public void setFolderPoseMap(Map<String, List<PoseModel>> folderPoseMap){
        this.folderPoseMap = folderPoseMap;
        notifyDataSetChanged();
    }
    public TitleAdapter(Map<String, List<PoseModel>> folderPoseMap, CustomInterface rvInterface, Context context) {
        this.folderPoseMap = folderPoseMap;
        this.rvInterface = rvInterface;
        this.context = context;
    }

    /*public TitleAdapter(ArrayList<PoseModel> list, Context context, CustomInterface rvInterface) {
        this.list = list;
        this.context = context;
        this.rvInterface = rvInterface;
    }*/

    @NonNull
    @Override
    public TitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_expandable_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set data
        /*if(!list.isEmpty()){
            PoseModel data = list.get(position);
            holder.name.setText(data.getRoutineName());
            holder.pose_Total.setText(String.valueOf(list.size()));
        }*/
        if(folderPoseMap != null){
            List<PoseModel> routinePicked = new ArrayList<>(folderPoseMap.values()).get(position);
            PoseModel data = routinePicked.get(0);
            holder.name.setText(data.getRoutineName());
            holder.pose_Total.setText(String.valueOf(folderPoseMap.size()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rvInterface != null){
                       rvInterface.onItemClick(position, false);
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount() {

        /*if(!list.isEmpty()){
            return list.size();
        }*/

       return folderPoseMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, pose_Total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.routine_Name2);
            pose_Total = itemView.findViewById(R.id.pose_Total);

        }
    }
}

