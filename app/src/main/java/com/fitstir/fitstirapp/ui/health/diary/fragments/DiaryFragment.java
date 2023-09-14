package com.fitstir.fitstirapp.ui.health.diary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentDiaryBinding;
import com.fitstir.fitstirapp.ui.health.diary.DiaryData;
import com.fitstir.fitstirapp.ui.health.diary.DiaryViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;
    private DiaryViewModel diaryViewModel;

    private ConstraintLayout loadingScreen;
    private View currentFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diaryViewModel = new ViewModelProvider(requireActivity()).get(DiaryViewModel.class);

        binding = FragmentDiaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        loadingScreen = root.findViewById(R.id.generic_loading_screen);
        loadingScreen.setVisibility(View.GONE);
        currentFragment = root.findViewById(R.id.current_fragment);



        /*FirebaseDatabase.getInstance()
                .getReference("DiaryData")
                .child(authUser.getUid())
                .setValue(DiaryViewModel.getData());*/


        if (diaryViewModel.getPreviousFragment().getValue().equals("EditDiaryFragment")) {
            loadingScreen.setVisibility(View.GONE);
            currentFragment.setVisibility(View.VISIBLE);

            replaceFragment(new EditDiaryFragment());
        } else {
            loadingScreen.setVisibility(View.VISIBLE);
            currentFragment.setVisibility(View.GONE);

            FirebaseDatabase.getInstance()
                    .getReference("DiaryData")
                    .child(authUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount() == 0) {
                                toggleLoadingScreen();
                                replaceFragment(new DiarySetupFragment());
                            } else {
                                DiaryData diaryData = snapshot.getValue(DiaryData.class);
                                diaryViewModel.setOGdiaryData(diaryData);

                                toggleLoadingScreen();
                                replaceFragment(new ViewDiaryFragment());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void replaceFragment(Fragment fragment) {
        diaryViewModel.setPreviousFragment("DiaryFragment");
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.current_fragment, fragment)
                .commit();
    }

    public void toggleLoadingScreen() {
        if (loadingScreen.getVisibility() == View.VISIBLE) {
            loadingScreen.setVisibility(View.GONE);
            currentFragment.setVisibility(View.VISIBLE);
        } else {
            loadingScreen.setVisibility(View.VISIBLE);
            currentFragment.setVisibility(View.GONE);
        }
    }
}