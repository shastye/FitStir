package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentFavoriteYogaBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.fitstir.fitstirapp.ui.workouts.exercises.workoutAdapter;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.FavoriteAdapter;

import java.util.ArrayList;


public class FavoriteYogaFragment extends Fragment implements RvInterface {

private FragmentFavoriteYogaBinding binding;
private ArrayList<PoseModel> favoriteList;
private RecyclerView rv;
private FavoriteAdapter adapter;

private PoseModel yogaPose;
private RvInterface rvI;

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
        yogaPose = new PoseModel();
        rvI = this;
        favoriteList = new ArrayList<>();


        rv = root.findViewById(R.id.fave_RV);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new FavoriteAdapter(rvI, requireActivity(), favoriteList);
        rv.setAdapter(adapter);


        yogaView.fetchFavorites(favoriteList, adapter, requireActivity());


        return root;
    }

    @Override
    public void onItemClick(int position) {

    }
}