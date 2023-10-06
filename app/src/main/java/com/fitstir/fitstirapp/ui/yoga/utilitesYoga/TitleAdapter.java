package com.fitstir.fitstirapp.ui.yoga.utilitesYoga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.CustomInterface;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.ArrayList;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {

    private ArrayList<PoseModel> list;
    private Context context;
    private final CustomInterface rvInterface;

    public TitleAdapter(ArrayList<PoseModel> list, Context context, CustomInterface rvInterface) {
        this.list = list;
        this.context = context;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public TitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_expandable_cardview, parent, false);
        ViewHolder parentView = new ViewHolder(view);

        return parentView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PoseModel data = list.get(position);

        //set data
        holder.name.setText(data.getEnglish_name());
        holder.pose_Total.setText(String.valueOf(list.size()));

    }
    @Override
    public int getItemCount() {
        return list.size();
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

