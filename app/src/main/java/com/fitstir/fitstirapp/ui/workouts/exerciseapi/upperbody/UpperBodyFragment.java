package com.fitstir.fitstirapp.ui.workouts.exerciseapi.upperbody;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentUpperBodyBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.ViewWorkoutFragment;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.WorkoutApi;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.workoutAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class UpperBodyFragment extends Fragment implements RvInterface {

    private FragmentUpperBodyBinding binding;
    private RecyclerView workouts_RV;
    private FirebaseFirestore db;
    private workoutAdapter viewAdapter;
    private ArrayList<WorkoutApi> upperBodyApiArrayList;
    private WorkoutApi upperBody;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentUpperBodyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        // Addition Text Here
        db = FirebaseFirestore.getInstance();
        upperBodyApiArrayList = new ArrayList<>();

        workouts_RV = root.findViewById(R.id.upper_Body_RV);
        workouts_RV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        workouts_RV.setItemAnimator(new DefaultItemAnimator());
        workouts_RV.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        viewAdapter = new workoutAdapter(this.getActivity(), upperBodyApiArrayList, this);

        workouts_RV.setAdapter(viewAdapter);
        upperBody = new WorkoutApi();
        upperBody.fetchData(db,upperBodyApiArrayList, Constants.WORKOUT_BODYPART.UPPER_BODY, viewAdapter);
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
     upperBody.getWorkoutClicked(position,upperBodyApiArrayList,workoutsViewModel);

      Fragment fragment = new ViewWorkoutFragment();
      FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
      fm.replace(R.id.container, fragment).commit();
    }
}