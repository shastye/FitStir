package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentFavoriteYogaBinding;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;


public class FavoriteYogaFragment extends Fragment {

private FragmentFavoriteYogaBinding binding;
    private View dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call  yoga view model
        YogaViewModel yogaView = new ViewModelProvider(this).get(YogaViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentFavoriteYogaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialog = root.findViewById(R.id.dialog_faves);
        dialog.setVisibility(View.INVISIBLE);


        return root;
    }
}