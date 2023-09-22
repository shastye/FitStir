package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentYogaBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;

public class YogaFragment extends Fragment {

    private FragmentYogaBinding binding;
    private CardView beginner, interm, expert, explore, standing, balance, learn, quickStart;
    private ImageView recent, custom, favorite;
    private YogaViewModel yogaViews;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentYogaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Addition Text Here
        beginner = root.findViewById(R.id.beginner_Yoga);
        interm = root.findViewById(R.id.intermediate_Yoga);
        expert = root.findViewById(R.id.expert_Yoga);
        explore = root.findViewById(R.id.search_Yoga);
        standing = root.findViewById(R.id.standing_Yoga);
        balance = root.findViewById(R.id.balance_Yoga);
        learn = root.findViewById(R.id.learn_Yoga);
        quickStart = root.findViewById(R.id.quickStart);
        recent = root.findViewById(R.id.recents_Tab);
        custom = root.findViewById(R.id.custom_Tab);
        favorite = root.findViewById(R.id.favorite_Tab);
        yogaViews = new ViewModelProvider(this.requireActivity()).get(YogaViewModel.class);


        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.BEGINNER);
                yogaViews.setCat_Name(Constants.YOGA_ID.BEGINNER_POSE);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);
            }
        });
        interm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.MEDIUM);
                yogaViews.setCat_Name(Constants.YOGA_ID.INTERMEDIATE_POSE);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);
            }
        });
        expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.EXPERT);
                yogaViews.setCat_Name(Constants.YOGA_ID.EXPERT_POSE);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);
            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.EXPLORE);
                yogaViews.setCat_Name(Constants.YOGA_ID.EXPERT_POSE);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);
            }
        });
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.LEARN);
                yogaViews.setCat_Name(Constants.YOGA_ID.LEARN_POSES);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);

            }
        });
        quickStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.QUICK_START);
                yogaViews.setCat_Name(Constants.YOGA_ID.QUICK_START_POSES);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);

            }
        });
        standing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.STANDING);
                yogaViews.setCat_Name(Constants.YOGA_ID.STANDING_POSE);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);
            }
        });
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaViews.setCat_Id(Constants.YOGA_ID.BALANCE);
                yogaViews.setCat_Name(Constants.YOGA_ID.BALANCE_POSE);
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_categoryViewFragment);
            }
        });
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_recentYogaFragment);
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_customYogaFragment);
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_yoga_to_favoriteYogaFragment);
            }
        });
        // End
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}