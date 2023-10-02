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
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;

import java.util.ArrayList;

public class CustomsAdapterView extends RecyclerView.Adapter<CustomsAdapterView.ViewHolder> {

    private CustomInterface rvInterface;
    private Context context;
    private ArrayList<PoseModel> viewList;
    private YogaViewModel viewModel;

    public CustomsAdapterView(CustomInterface rvInterface, Context context, ArrayList<PoseModel> viewList, YogaViewModel viewModel) {
        this.rvInterface = rvInterface;
        this.context = context;
        this.viewList = viewList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CustomsAdapterView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_window, parent, false);
        return new CustomsAdapterView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomsAdapterView.ViewHolder holder, int position) {

        PoseModel data = viewList.get(position);

        holder.name_view.setText(data.getEnglish_name());
        holder.cat_View.setText(data.getPose_Type());
        Glide.with(context)
                .load(data.getUrl_png())
                .into(holder.img_View);
        holder.subtract_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    rvInterface.onItemClick(holder.getAdapterPosition(), false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name_view, cat_View;
        private ImageButton subtract_From;
        private ImageView img_View;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name_view = itemView.findViewById(R.id.name_Window);
            cat_View = itemView.findViewById(R.id.cat_Window);
            subtract_From = itemView.findViewById(R.id.subtract_from_BTN);
            img_View = itemView.findViewById(R.id.yoga_Image_Window);
        }
    }
}
