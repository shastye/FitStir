package com.fitstir.fitstirapp.ui.workouts.exerciseapi.circuits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentCircuitWorkoutsBinding;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;

public class CircuitWorkoutsFragment extends Fragment {

    private FragmentCircuitWorkoutsBinding binding;
    private ImageButton tCircuit, bCircuit, bbCircuit, wCircuit, mbCircuit, fitCircuit, aCircuit;
    private int circuitPage;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentCircuitWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Addition Text Here
        bCircuit = root.findViewById(R.id.circuit_Beginner);
        tCircuit = root.findViewById(R.id.circuit_Toner);
        aCircuit = root.findViewById(R.id.circuit_Active);
        bbCircuit = root.findViewById(R.id.circuit_Beach_Body);
        wCircuit = root.findViewById(R.id.circuit_Warrior);
        mbCircuit = root.findViewById(R.id.circuit_Expected_Mother);
        fitCircuit = root.findViewById(R.id.circuit_FitStir);

        setPageID();


        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setPageID()
    {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);
        bCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circuitPage =1;
                workoutsViewModel.setPageCLicked(circuitPage);
                Navigation.findNavController(v).navigate(R.id.action_navigation_circuit_workouts_to_viewCircuitFragment);
            }
        });
        tCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circuitPage =2;
                workoutsViewModel.setPageCLicked(circuitPage);
                Navigation.findNavController(v).navigate(R.id.action_navigation_circuit_workouts_to_viewCircuitFragment);
            }
        });
        aCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circuitPage =3;
                workoutsViewModel.setPageCLicked(circuitPage);
                Navigation.findNavController(v).navigate(R.id.action_navigation_circuit_workouts_to_viewCircuitFragment);
            }
        });
        bbCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circuitPage =4;
                workoutsViewModel.setPageCLicked(circuitPage);
                Navigation.findNavController(v).navigate(R.id.action_navigation_circuit_workouts_to_viewCircuitFragment);
            }
        });
        wCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circuitPage =5;
                workoutsViewModel.setPageCLicked(circuitPage);
                Navigation.findNavController(v).navigate(R.id.action_navigation_circuit_workouts_to_viewCircuitFragment);
            }
        });
        mbCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circuitPage =6;
                workoutsViewModel.setPageCLicked(circuitPage);
                Navigation.findNavController(v).navigate(R.id.action_navigation_circuit_workouts_to_viewCircuitFragment);
            }
        });
        fitCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circuitPage =7;
                workoutsViewModel.setPageCLicked(circuitPage);
                Navigation.findNavController(v).navigate(R.id.action_navigation_circuit_workouts_to_viewCircuitFragment);
            }
        });
    }
}