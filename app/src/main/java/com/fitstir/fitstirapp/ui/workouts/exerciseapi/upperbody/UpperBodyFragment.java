package com.fitstir.fitstirapp.ui.workouts.exerciseapi.upperbody;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentUpperBodyBinding;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.RecyclerViewInterface;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.UpperBodyAdapter;
import com.fitstir.fitstirapp.ui.workouts.exerciseapi.upperbody.UpperBodyApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class UpperBodyFragment extends Fragment implements RecyclerViewInterface {

    private FragmentUpperBodyBinding binding;
    private RecyclerView workouts_RV;
    private FirebaseFirestore db;
    private UpperBodyAdapter viewAdapter;
    private ArrayList<UpperBodyApi> upperBodyApiArrayList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel =
                new ViewModelProvider(this).get(WorkoutsViewModel.class);

        binding = FragmentUpperBodyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        db = FirebaseFirestore.getInstance();
        upperBodyApiArrayList = new ArrayList<>();

        workouts_RV = root.findViewById(R.id.upper_Body_RV);
        workouts_RV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        workouts_RV.setItemAnimator(new DefaultItemAnimator());
        workouts_RV.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        viewAdapter = new UpperBodyAdapter(this.getActivity(), upperBodyApiArrayList, this);

        workouts_RV.setAdapter(viewAdapter);
        
        fetchWorkouts();

        // End

        return root;
    }
    private void fetchWorkouts() {
        db.collection("UpperBody").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){
                                UpperBodyApi upperBodyApi = d.toObject(UpperBodyApi.class);
                                upperBodyApiArrayList.add(upperBodyApi);
                            }
                            viewAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(requireActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Error getting data", Toast.LENGTH_SHORT).show();
                    }
                });
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