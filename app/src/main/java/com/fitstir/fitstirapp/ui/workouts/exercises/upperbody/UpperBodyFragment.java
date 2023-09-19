package com.fitstir.fitstirapp.ui.workouts.exercises.upperbody;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentUpperBodyBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exercises.ViewWorkoutFragment;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.fitstir.fitstirapp.ui.workouts.exercises.workoutAdapter;

import java.util.ArrayList;

public class UpperBodyFragment extends Fragment implements RvInterface {

    private FragmentUpperBodyBinding binding;
    private RecyclerView workouts_RV;
    private workoutAdapter viewAdapter;
    private ArrayList<WorkoutApi> upperBodyApiArrayList;
    private ArrayList<WorkoutApi> filtered;
    private WorkoutApi upperBody;
    private SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentUpperBodyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        // Addition Text Here
        searchView = root.findViewById(R.id.searchView_Upperbody);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtered = upperBody.searchList(newText, upperBodyApiArrayList, viewAdapter);
                return true;
            }
        });

        {//recyclerview setup
            upperBodyApiArrayList = new ArrayList<>();

            workouts_RV = root.findViewById(R.id.upper_Body_RV);
            workouts_RV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            //workouts_RV.setItemAnimator(new DefaultItemAnimator());
            workouts_RV.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

            viewAdapter = new workoutAdapter(this.getActivity(), upperBodyApiArrayList, this);

            workouts_RV.setAdapter(viewAdapter);
            upperBody = new WorkoutApi();
            upperBody.fetchData(upperBodyApiArrayList, Constants.WORKOUT_BODYPART.UPPER_BODY, viewAdapter);
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
             upperBody.getWorkoutClicked(position,filtered,workoutsViewModel);
             Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main)
                     .navigate(R.id.action_navigation_upper_body_to_viewWorkoutFragment);
         }
     }catch (NullPointerException e){
         upperBody.getWorkoutClicked(position,upperBodyApiArrayList,workoutsViewModel);


         Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main)
                 .navigate(R.id.action_navigation_upper_body_to_viewWorkoutFragment);
     }

    }
}