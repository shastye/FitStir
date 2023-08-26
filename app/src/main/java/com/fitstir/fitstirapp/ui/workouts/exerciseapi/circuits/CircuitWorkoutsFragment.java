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

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentCircuitWorkoutsBinding;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;

public class CircuitWorkoutsFragment extends Fragment {

    private FragmentCircuitWorkoutsBinding binding;
    private ImageButton tCircuit, bCircuit, bbCircuit, wCircuit, mbCircuit, fitCircuit, aCircuit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel =
                new ViewModelProvider(this).get(WorkoutsViewModel.class);

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

        bCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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