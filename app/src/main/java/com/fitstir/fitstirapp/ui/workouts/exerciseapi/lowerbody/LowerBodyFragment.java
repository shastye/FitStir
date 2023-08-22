package com.fitstir.fitstirapp.ui.workouts.exerciseapi.lowerbody;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.WorkoutApi;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.workoutAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LowerBodyFragment extends Fragment implements RvInterface {

    private RvInterface rvInterface;
    private FragmentLowerBodyBinding binding;
    private RecyclerView recyclerView;
    private workoutAdapter adapter;
    private ArrayList<WorkoutApi> lowerBodyArrayList;
    private WorkoutApi lowerBody;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentLowerBodyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        db = FirebaseFirestore.getInstance();
        lowerBodyArrayList = new ArrayList<>();
        lowerBody = new WorkoutApi();
        adapter = new workoutAdapter(getActivity(),lowerBodyArrayList, this);
        recyclerView = root.findViewById(R.id.lower_Body_RV);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);
        lowerBody.fetchData(db,lowerBodyArrayList, Constants.WORKOUT_BODYPART.LOWER_BODY,adapter);


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

    }
}