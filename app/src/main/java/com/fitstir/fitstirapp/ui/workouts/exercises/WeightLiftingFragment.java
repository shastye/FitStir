package com.fitstir.fitstirapp.ui.workouts.exercises;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentWeightLiftingBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;

import java.util.ArrayList;

public class WeightLiftingFragment extends Fragment implements RvInterface {

    private FragmentWeightLiftingBinding binding;
    private RecyclerView recyclerView;
    private WorkoutApi workoutApi;
    private workoutAdapter workoutAdapter;
    private ArrayList<WorkoutApi> weights;
    private ArrayList<WorkoutApi> filtered;
    private SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(this).get(WorkoutsViewModel.class);

        binding = FragmentWeightLiftingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        searchView = root.findViewById(R.id.searchView_Weights);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtered = workoutApi.searchList(newText, weights, workoutAdapter);
                return true;
            }
        });


        {//recyclerview setup
            weights = new ArrayList<>();
            workoutApi = new WorkoutApi();
            workoutAdapter = new workoutAdapter(getActivity(),weights,this);
            recyclerView = root.findViewById(R.id.weights_RV);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(workoutAdapter);
            workoutApi.fetchData(weights, Constants.WORKOUT_BODYPART.WEIGHT_LIFTING,workoutAdapter);
        }

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        try {
            if (filtered.size() >= 1) {
                workoutApi.getWorkoutClicked(position,filtered,workoutsViewModel);
                workoutsViewModel.setFavoriteItemPosition(position);
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_weight_lifting_to_viewWorkoutFragment);
            }
        }catch (NullPointerException e){
            workoutApi.getWorkoutClicked(position,weights,workoutsViewModel);
            workoutsViewModel.setFavoriteItemPosition(position);
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_weight_lifting_to_viewWorkoutFragment);
        }

    }
}