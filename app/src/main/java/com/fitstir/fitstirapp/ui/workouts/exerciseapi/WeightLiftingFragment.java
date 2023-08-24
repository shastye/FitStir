package com.fitstir.fitstirapp.ui.workouts.exerciseapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentWeightLiftingBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WeightLiftingFragment extends Fragment implements RvInterface {

    private RvInterface rvInterface;
    private FragmentWeightLiftingBinding binding;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private WorkoutApi workoutApi;
    private workoutAdapter workoutAdapter;
    private ArrayList<WorkoutApi> weights;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(this).get(WorkoutsViewModel.class);

        binding = FragmentWeightLiftingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        db = FirebaseFirestore.getInstance();
        weights = new ArrayList<>();
        workoutApi = new WorkoutApi();
        workoutAdapter = new workoutAdapter(getActivity(),weights,this);
        recyclerView = root.findViewById(R.id.weights_RV);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(workoutAdapter);
        workoutApi.fetchData(db,weights, Constants.WORKOUT_BODYPART.WEIGHT_LIFTING,workoutAdapter);


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
        workoutApi.getWorkoutClicked(position,weights,workoutsViewModel);

        Fragment fragment = new ViewWorkoutFragment();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.container, fragment).commit();
    }
}