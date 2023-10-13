package com.fitstir.fitstirapp.ui.yoga.utilitesYoga;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.CustomInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.ArrayList;

public class PoseAdapter extends RecyclerView.Adapter<PoseAdapter.ViewHolder> {

    private ArrayList<PoseModel> list;
    private Context context;


    public PoseAdapter(ArrayList<PoseModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PoseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_routine_cardview, parent, false);
        return new ViewHolder(view);
    }

    public void setPoseList(ArrayList<PoseModel> newList){
        this.list = newList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull PoseAdapter.ViewHolder holder, int position) {
        PoseModel data = list.get(position);

        Log.d("What is the view doing", "Look"+holder);
        String video = data.getUrl_Vid();
        holder.name.setText(data.getEnglish_name());
        holder.type.setText(data.getPose_Type());
        holder.level.setText(data.getDifficulty_level());
        Glide.with(context)
                .load(data.getUrl_png())
                .into(holder.img);
        holder.vid.loadData(video, "text/html", "utf-8");
        holder.vid.getSettings().setJavaScriptEnabled(true);
        holder.vid.setWebChromeClient(new WebChromeClient());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, level, type;
        private ImageView img;
        private WebView vid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_Routine);
            img = itemView.findViewById(R.id.img_Routine);
            vid = itemView.findViewById(R.id.vid_Routine);
            level = itemView.findViewById(R.id.name_level);
            type = itemView.findViewById(R.id.pose_Type);


        }

    }
}