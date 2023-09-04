package com.fitstir.fitstirapp.ui.workouts.exercises.lowerbody;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
import com.fitstir.fitstirapp.databinding.FragmentLowerBodyBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exercises.ViewWorkoutFragment;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.fitstir.fitstirapp.ui.workouts.exercises.workoutAdapter;

import java.util.ArrayList;

public class LowerBodyFragment extends Fragment implements RvInterface {

    private FragmentLowerBodyBinding binding;
    private RecyclerView recyclerView;
    private workoutAdapter adapter;
    private ArrayList<WorkoutApi> lowerBodyArrayList;
    private WorkoutApi lowerBody;
    private ArrayList<WorkoutApi> filtered;
    private SearchView searchView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentLowerBodyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        searchView = root.findViewById(R.id.searchView_Lowerbody);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtered = lowerBody.searchList(newText, lowerBodyArrayList, adapter);
                return true;
            }
        });


        {//recyclerview setup
            lowerBodyArrayList = new ArrayList<>();
            lowerBody = new WorkoutApi();
            adapter = new workoutAdapter(getActivity(),lowerBodyArrayList, this);
            recyclerView = root.findViewById(R.id.lower_Body_RV);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(adapter);
            lowerBody.fetchData(lowerBodyArrayList, Constants.WORKOUT_BODYPART.LOWER_BODY,adapter);
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
                lowerBody.getWorkoutClicked(position,filtered,workoutsViewModel);
                Fragment fragment = new ViewWorkoutFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, fragment).commit();
            }
        }catch (NullPointerException e){
            lowerBody.getWorkoutClicked(position,lowerBodyArrayList,workoutsViewModel);
            Fragment fragment = new ViewWorkoutFragment();
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.container, fragment).commit();
        }

    }
}