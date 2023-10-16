package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentFavoriteYogaBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.fitstir.fitstirapp.ui.workouts.exercises.workoutAdapter;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.FavoriteAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FavoriteYogaFragment extends Fragment implements RvInterface {

    private FragmentFavoriteYogaBinding binding;
    private ArrayList<PoseModel> favoriteList;
    private RecyclerView rv;
    private FavoriteAdapter adapter;
    private RvInterface rvI;
    private View dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call  yoga view model
        YogaViewModel yogaView = new ViewModelProvider(this).get(YogaViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentFavoriteYogaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialog = root.findViewById(R.id.dialog_faves);
        if(dialog != null){
            dialog.setVisibility(View.INVISIBLE);
        }

        rvI = this;
        favoriteList = new ArrayList<>();

        rv = root.findViewById(R.id.fave_RV);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new FavoriteAdapter(rvI, requireActivity(), favoriteList);
        rv.setAdapter(adapter);

        favoriteList.clear();
        yogaView.fetchFavorites(favoriteList, adapter, dialog);

        return root;
    }

    @Override
    public void onItemClick(int position) {

        //PoseModel poseApi = new PoseModel();
        YogaViewModel yogaView = new ViewModelProvider(this).get(YogaViewModel.class);
        yogaView.getClickedItem(position, favoriteList);

        //delete item from firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("FavoriteItemYoga")
                .child(user.getUid())
                .child(yogaView.getEnglish_Name().getValue().toString());
        dataRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                favoriteList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(requireActivity(), "Favorite unsaved", Toast.LENGTH_LONG).show();
            }
        });
    }
}