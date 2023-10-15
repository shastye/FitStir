package com.fitstir.fitstirapp.ui.workouts.exercises.circuits;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewCircuitBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewCircuitFragment extends Fragment implements RvInterface {
    private FragmentViewCircuitBinding binding;
    private RecyclerView rv;
    private RvInterface rvInterface;
    private circuitAdapter adapter;
    private ArrayList<CircuitModel> circuitList;
    private CircuitModel model;
    private Date currentDate;
    private SimpleDateFormat formatter;
    private ImageView completedCircuit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);
        binding = FragmentViewCircuitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        completedCircuit = root.findViewById(R.id.map_Image);
        rvInterface = this;

        circuitList = new ArrayList<>();
        rv = root.findViewById(R.id.circuit_RV);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        adapter = new circuitAdapter(this.getActivity(),circuitList, rvInterface);
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
    @Override
    public void onItemClick(int position) {

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        currentDate = new Date();
        formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        String date = formatter.format(currentDate);

        //create data model and filepath
        model.getWorkoutClicked(position, circuitList,workoutsViewModel);
        String exerciseName = workoutsViewModel.getExercise().getValue().toString().trim();
        int totalBurned = workoutsViewModel.getTotalBurned().getValue().intValue();

        model = new CircuitModel(exerciseName, totalBurned, date);

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference runRef = FirebaseDatabase.getInstance()
                    .getReference("CompletedWorkout")
                    .child(authUser.getUid())
                    .child(date)
                    .child("Circuit::" +exerciseName);

            runRef.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(requireActivity(), "Workout Completed and Saved", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main)
                            .navigate(R.id.action_viewCircuitFragment_to_navigation_circuit_workouts);
                }
            });


    }
}