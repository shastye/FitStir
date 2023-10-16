package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentYogaPoseBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.FavoriteAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class YogaPoseFragment extends Fragment implements RvInterface {

    private TextView english_Name, san_Name, tran_Name, descript,benefits;
    private ImageView png;
    private ImageView favorite_Button;
    private int favoriteButtonColorOn, favoriteButtonColorOff;
    private FragmentYogaPoseBinding binding;
    private WebView youtube;
    private ArrayList<PoseModel> faveList;
    private RvInterface rvInterface;
    private SharedPreferences sharedPreferences;
    private Boolean isFavoriteButtonOn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call  yoga view model
        YogaViewModel yogaView = new ViewModelProvider(this.requireActivity()).get(YogaViewModel.class);

        // saving favorite button state
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        isFavoriteButtonOn = sharedPreferences.getBoolean("is_favorite", false);


        // Inflate the layout for this fragment
        binding = FragmentYogaPoseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Finding of views
        english_Name = root.findViewById(R.id.pose_EnglishName);
        san_Name = root.findViewById(R.id.pose_SanskritName);
        tran_Name = root.findViewById(R.id.pose_Translation);
        descript = root.findViewById(R.id.pose_description);
        benefits = root.findViewById(R.id.pose_benefits);
        png = root.findViewById(R.id.url_PNG);
        youtube = root.findViewById(R.id.youtube_View);
        favorite_Button = root.findViewById(R.id.favoriteButton);
        favoriteButtonColorOff = R.drawable.baseline_favorite_24;
        favoriteButtonColorOn = R.drawable.baseline_favorite_purple;
        faveList = new ArrayList<>();
        rvInterface = this;

        // Setting the textviews with clicked items
        english_Name.setText(yogaView.getEnglish_Name().getValue());
        san_Name.setText(yogaView.getSanskrit_Name().getValue());
        tran_Name.setText(yogaView.getTranslated_Name().getValue());
        descript.setText(yogaView.getPose_Description().getValue());
        benefits.setText(yogaView.getPose_Benefits().getValue());
        Glide.with(this)
                .load(yogaView.getUrl_PNG().getValue())
                .into(png);

        // Youtube video embedding
        String video = yogaView.getVideo().getValue();
        youtube.loadData(video,"text/html", "utf-8" );
        youtube.getSettings().setJavaScriptEnabled(true);
        youtube.setWebChromeClient(new WebChromeClient());


        favorite_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toggle favorite state
                isFavoriteButtonOn = !isFavoriteButtonOn;

                // Save button state
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("is_favorite", isFavoriteButtonOn);
                editor.apply();

                updateFavorite(isFavoriteButtonOn);
                onItemClick(yogaView.getFavoriteItemPosition().getValue());
            }
        });

        return root;
    }


    private void updateFavorite(boolean isFavorite) {
        if(isFavoriteButtonOn){
            if(favorite_Button != null){
                favorite_Button.setImageResource(R.drawable.baseline_favorite_purple);
            }

        }
        else{
            if(favorite_Button != null){
                favorite_Button.setImageResource(R.drawable.baseline_favorite_24);
            }

        }
    }

    private void toggleFavorite(View view){
        favorite_Button.callOnClick();
    }


    @Override
    public void onItemClick(int position) {
        YogaViewModel view = new ViewModelProvider(requireActivity()).get(YogaViewModel.class);
        ArrayList<PoseModel> favoriteList = new ArrayList<>();
        PoseModel model = view.getYoga().getValue().get(position);

        String pathFolder = "FavoriteItemYoga";
        String filePathName = model.getEnglish_name().toString();

        if(isFavoriteButtonOn) {

            view.saveYogaData(model, requireActivity(),pathFolder,filePathName);
            favoriteList.add(model);

        }
        else {

            favoriteList.remove(model);

            //delete item from firebase
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("FavoriteItemYoga")
                    .child(user.getUid())
                    .child(model.getEnglish_name());
            dataRef.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(requireActivity(), "Favorite unsaved", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);

        // Saving the favorite state
        updateFavorite(isFavoriteButtonOn);
        outState.putBoolean("is_favorite", isFavoriteButtonOn);

    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);

        //restoring the favorite button state
        if(savedInstanceState != null){
            updateFavorite(isFavoriteButtonOn);
            isFavoriteButtonOn = savedInstanceState.getBoolean("is_favorite");


        }
    }

}