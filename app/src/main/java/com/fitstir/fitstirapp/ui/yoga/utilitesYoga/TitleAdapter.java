package com.fitstir.fitstirapp.ui.yoga.utilitesYoga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private Context context;
    private final ICustoms rvInterface;

    public void setFolderPoseMap(Map<String, List<PoseModel>> folderPoseMap){
        this.folderPoseMap = folderPoseMap;
        notifyDataSetChanged();
    }
    public TitleAdapter(Map<String, List<PoseModel>> folderPoseMap, ICustoms rvInterface, Context context) {
        this.folderPoseMap = folderPoseMap;
        this.rvInterface = rvInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public TitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_expandable_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(folderPoseMap != null){

            PoseModel model = new PoseModel();
            String routineName = new ArrayList<>(folderPoseMap.keySet()).get(position);

            holder.open_Name.setText(routineName);
            holder.closed_Name.setText(routineName);
            //holder.opened_PoseTotal.setText(model.getRoutineSize().toString());
            //holder.closed_PoseTotal.setText(model.getRoutineSize().toString());

            holder.open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rvInterface != null){
                        holder.opened_CardView.setVisibility(View.VISIBLE);
                        holder.closed_CardView.setVisibility(View.INVISIBLE);
                       rvInterface.customOnItemClick(holder.getAdapterPosition());
                    }
                }
            });
            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rvInterface != null){
                        holder.opened_CardView.setVisibility(View.INVISIBLE);
                        holder.closed_CardView.setVisibility(View.VISIBLE);
                        rvInterface.customOnItemClick(holder.getAdapterPosition());
                    }
                }
            });

            PoseAdapter adapter = new PoseAdapter((ArrayList<PoseModel>) folderPoseMap.get(routineName), context);
            holder.pose_RV.setLayoutManager(new LinearLayoutManager(context));
            holder.pose_RV.setAdapter(adapter);
        }
    }
    @Override
    public int getItemCount() {
       return folderPoseMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView closed_Name, open_Name, opened_PoseTotal, closed_PoseTotal;
        private ImageView open, close;
        private CardView opened_CardView, closed_CardView;
        private RecyclerView pose_RV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            open_Name = itemView.findViewById(R.id.routine_Name2);
            opened_PoseTotal = itemView.findViewById(R.id.pose_Total);
            closed_PoseTotal = itemView.findViewById(R.id.pose_count);
            closed_Name = itemView.findViewById(R.id.routine_Name);
            open = itemView.findViewById(R.id.open_cardview);
            close = itemView.findViewById(R.id.close_cardview);
            opened_CardView = itemView.findViewById(R.id.custon_cardView_opened);
            closed_CardView = itemView.findViewById(R.id.custon_cardView_closed);
            pose_RV = itemView.findViewById(R.id.opened_cardview_RV);
        }
    }
}

