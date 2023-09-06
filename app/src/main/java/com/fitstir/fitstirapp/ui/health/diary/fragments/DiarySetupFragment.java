package com.fitstir.fitstirapp.ui.health.diary.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentDiarySetupBinding;
import com.fitstir.fitstirapp.ui.health.diary.DiaryData;
import com.fitstir.fitstirapp.ui.health.diary.DiaryViewModel;
import com.fitstir.fitstirapp.ui.health.diary.Task;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiarySetupFragment extends Fragment {

    private FragmentDiarySetupBinding binding;
    private DiaryViewModel diaryViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diaryViewModel = new ViewModelProvider(requireActivity()).get(DiaryViewModel.class);

        binding = FragmentDiarySetupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Diary Setup");

        binding.updateTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<TextView> potentialTasks = new ArrayList<TextView>() {{
                    add(binding.task01);
                    add(binding.task02);
                    add(binding.task03);
                    add(binding.task04);
                    add(binding.task05);
                    add(binding.task06);
                    add(binding.task07);
                    add(binding.task08);
                    add(binding.task09);
                    add(binding.task10);
                }};
                int size = potentialTasks.size();

                DiaryData diaryData = new DiaryData();

                ArrayList<Boolean> areTasks = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    if (potentialTasks.get(i).getText().toString().equals("")) {
                        areTasks.add(Boolean.FALSE);
                    } else {
                        areTasks.add(Boolean.TRUE);
                    }
                }

                if (areTasks.contains(Boolean.TRUE)) {
                    int count = 0;
                    for (int i = 0; i < size; i++) {
                        if (areTasks.get(i) == Boolean.TRUE) {
                            count++;
                            String taskName = potentialTasks.get(i).getText().toString();
                            Task newTask = new Task(taskName);

                            switch(count) {
                                case 1:
                                    diaryData.setTask01(newTask);
                                    break;
                                case 2:
                                    diaryData.setTask02(newTask);
                                    break;
                                case 3:
                                    diaryData.setTask03(newTask);
                                    break;
                                case 4:
                                    diaryData.setTask04(newTask);
                                    break;
                                case 5:
                                    diaryData.setTask05(newTask);
                                    break;
                                case 6:
                                    diaryData.setTask06(newTask);
                                    break;
                                case 7:
                                    diaryData.setTask07(newTask);
                                    break;
                                case 8:
                                    diaryData.setTask08(newTask);
                                    break;
                                case 9:
                                    diaryData.setTask09(newTask);
                                    break;
                                case 10:
                                    diaryData.setTask10(newTask);
                                    break;
                            }
                        }
                    }

                    FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase.getInstance()
                                    .getReference("DiaryData")
                                    .child(authUser.getUid())
                                    .setValue(diaryData);

                    replaceFragment(new DiaryFragment());
                } else {
                    final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
                    assert warningIcon != null;
                    warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());

                    warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext()));

                    binding.task01.setError("You must set at least one task.", warningIcon);
                }
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

    public void replaceFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.current_fragment, fragment)
                .commit();
    }
}