package com.fitstir.fitstirapp.ui.workouts.exerciseapi.circuits;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentUpperBodyBinding;
import com.fitstir.fitstirapp.databinding.FragmentViewCircuitBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.ViewWorkoutFragment;

import java.util.ArrayList;

public class ViewCircuitFragment extends Fragment {
    private FragmentViewCircuitBinding binding;
    private RecyclerView rv;
    private circuitAdapter adapter;
    private ArrayList<CircuitModel> circuitList;
    private CircuitModel model;
    private ImageView completedCircuit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);
        binding = FragmentViewCircuitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        completedCircuit = root.findViewById(R.id.map_Image);

        circuitList = new ArrayList<>();
        rv = root.findViewById(R.id.circuit_RV);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        adapter = new circuitAdapter(this.getActivity(),circuitList);
        rv.setAdapter(adapter);
        model = new CircuitModel();
        findCircuitList();



        //END
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void findCircuitList(){

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        if(workoutsViewModel.getPageCLicked().getValue().intValue() == 1){
            model.fetchCircuitData(circuitList,adapter, Constants.WORKOUT_BODYPART.BEGINNER_CIRCUIT);
        }
        if(workoutsViewModel.getPageCLicked().getValue().intValue() == 2){
            model.fetchCircuitData(circuitList,adapter, Constants.WORKOUT_BODYPART.TONER_CIRCUIT);
        }
        if(workoutsViewModel.getPageCLicked().getValue().intValue() == 3){
            model.fetchCircuitData(circuitList,adapter, Constants.WORKOUT_BODYPART.ACTIVE_CIRCUIT);
        }
        if(workoutsViewModel.getPageCLicked().getValue().intValue() == 4){
            model.fetchCircuitData(circuitList,adapter, Constants.WORKOUT_BODYPART.BB_CIRCUIT);
        }
        if(workoutsViewModel.getPageCLicked().getValue().intValue() == 5){
            model.fetchCircuitData(circuitList,adapter, Constants.WORKOUT_BODYPART.WARRIORS);
        }
        if(workoutsViewModel.getPageCLicked().getValue().intValue() == 6){
            model.fetchCircuitData(circuitList,adapter, Constants.WORKOUT_BODYPART.BCCIRCUIT);
        }
        if(workoutsViewModel.getPageCLicked().getValue().intValue() == 7){
            model.fetchCircuitData(circuitList,adapter, Constants.WORKOUT_BODYPART.CHALLENGE);
        }

    }
}