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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.CustomInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;

import java.util.ArrayList;

public class PoseAdapter extends RecyclerView.Adapter<PoseAdapter.ViewHolder> {

    private ArrayList<PoseModel> list;
    private Context context;
    private final CustomInterface rvInterface;

    public PoseAdapter(ArrayList<PoseModel> list, Context context, CustomInterface rvInterface) {
        this.list = list;
        this.context = context;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public PoseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_routine_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoseAdapter.ViewHolder holder, int position) {
        PoseModel data = list.get(position);

        String video = data.getUrl_Vid();
        holder.name.setText(data.getEnglish_name());
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

        private TextView name;
        private ImageView img;
        private WebView vid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_Routine);
            img = itemView.findViewById(R.id.img_Routine);
            vid = itemView.findViewById(R.id.vid_Routine);

        }

    }
}
