package com.fitstir.fitstirapp.ui.health.diary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewDiaryBinding;
import com.fitstir.fitstirapp.ui.health.diary.DiaryViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDiaryFragment extends Fragment {

    private FragmentViewDiaryBinding binding;
    private DiaryViewModel diaryViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diaryViewModel = new ViewModelProvider(requireActivity()).get(DiaryViewModel.class);

        binding = FragmentViewDiaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Your Diary");



        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void replaceFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.current_fragment, fragment)
                .commit();
    }
}