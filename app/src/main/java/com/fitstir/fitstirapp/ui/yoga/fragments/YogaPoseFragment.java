package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentYogaPoseBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;


public class YogaPoseFragment extends Fragment {

    private TextView english_Name, adapted_Name, san_Name, tran_Name, descript,benefits;
    private ImageView png,svg,alt;
    private FragmentYogaPoseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call  yoga view model
        YogaViewModel yogaView = new ViewModelProvider(this.requireActivity()).get(YogaViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentYogaPoseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        english_Name = root.findViewById(R.id.pose_EnglishName);
        adapted_Name = root.findViewById(R.id.pose_AdaptedName);
        san_Name = root.findViewById(R.id.pose_SanskritName);
        tran_Name = root.findViewById(R.id.pose_Translation);
        descript = root.findViewById(R.id.pose_description);
        benefits = root.findViewById(R.id.pose_benefits);
        png = root.findViewById(R.id.url_PNG);
        svg = root.findViewById(R.id.url_SVG);
        alt = root.findViewById(R.id.url_ALT);

        english_Name.setText(yogaView.getEnglish_Name().getValue());
        san_Name.setText(yogaView.getSanskrit_Name().getValue());
        tran_Name.setText(yogaView.getTranslated_Name().getValue());
        descript.setText(yogaView.getPose_Description().getValue());
        benefits.setText(yogaView.getPose_Benefits().getValue());
        Glide.with(this)
                .load(yogaView.getUrl_PNG().getValue())
                .into(png);

        Glide.with(this)
                .load(yogaView.getVideo().getValue())
                .into(alt);





        return root;
    }
}