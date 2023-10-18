package com.fitstir.fitstirapp.ui.health.diary.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentEditDiaryBinding;
import com.fitstir.fitstirapp.ui.health.diary.DiaryData;
import com.fitstir.fitstirapp.ui.health.diary.DiaryViewModel;
import com.fitstir.fitstirapp.ui.health.diary.Task;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class EditDiaryFragment extends Fragment {

    private FragmentEditDiaryBinding binding;
    private DiaryViewModel diaryViewModel;

    private DiaryData newData;
    private DiaryData diaryData;
    private FirebaseUser authUser;

    private ArrayList<AppCompatEditText> editTexts;
    private ArrayList<Task> tasks;
    ArrayList<ImageButton> deleteButtons;
    private ArrayList<ImageButton> editButtons;
    private int greyColor, colorOnPrimary, colorPrimary, colorPrimaryVariant;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diaryViewModel = new ViewModelProvider(requireActivity()).get(DiaryViewModel.class);

        binding = FragmentEditDiaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Edit Diary");
        diaryViewModel.setPreviousFragment("EditDiaryFragment");

        greyColor = ContextCompat.getColor(requireContext(), R.color.mid_grey);
        colorPrimary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimary, requireContext());
        colorPrimaryVariant = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext());
        colorOnPrimary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorOnPrimary, requireContext());

        diaryData = diaryViewModel.getOGdiaryData().getValue();
        newData = diaryViewModel.getNewDiaryData().getValue();
        if (newData == null) {
            newData = new DiaryData();
            newData.setTasks(new ArrayList<>(diaryData.getTasks()));
            newData.setEmotions(new ArrayList<>(diaryData.getEmotions()));
        }

        authUser = FirebaseAuth.getInstance().getCurrentUser();

        editTexts = new ArrayList<AppCompatEditText>() {{
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
        deleteButtons = new ArrayList<ImageButton>() {{
            add(binding.deleteTask01);
            add(binding.deleteTask02);
            add(binding.deleteTask03);
            add(binding.deleteTask04);
            add(binding.deleteTask05);
            add(binding.deleteTask06);
            add(binding.deleteTask07);
            add(binding.deleteTask08);
            add(binding.deleteTask09);
            add(binding.deleteTask10);
        }};
        editButtons = new ArrayList<ImageButton>() {{
            add(binding.editTask01);
            add(binding.editTask02);
            add(binding.editTask03);
            add(binding.editTask04);
            add(binding.editTask05);
            add(binding.editTask06);
            add(binding.editTask07);
            add(binding.editTask08);
            add(binding.editTask09);
            add(binding.editTask10);
        }};

        tasks = new ArrayList<>(newData.getTasks());

        if (deleteButtons.size() != editTexts.size() && editTexts.size() != editButtons.size()) {
            throw new RuntimeException();   // debug purposes if amount of allowed tasks are changed
        }

        for (int i = 0; i < deleteButtons.size(); i++) {
            final int index = i;

            deleteButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasks.remove(tasks.get(index));
                    newData.setTasks(new ArrayList<>(tasks));

                    diaryViewModel.setNewDiaryData(newData);

                    replaceFragment(new DiaryFragment());
                }
            });

            editButtons.get(i).setOnClickListener(getEditListener(i));

            try {
                editTexts.get(i).setEnabled(false);
                editTexts.get(i).setText(tasks.get(i).getName());

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) editTexts.get(i).getLayoutParams();
                params.addRule(RelativeLayout.START_OF, deleteButtons.get(i).getId());
                params.addRule(RelativeLayout.END_OF, editButtons.get(i).getId());
                editTexts.get(i).setLayoutParams(params);

                deleteButtons.get(i).setVisibility(View.VISIBLE);
                editButtons.get(i).setVisibility(View.VISIBLE);
            } catch (IndexOutOfBoundsException e) {
                editTexts.get(i).setEnabled(true);
                editTexts.get(i).setText("");

                editTexts.get(i).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

                deleteButtons.get(i).setVisibility(View.GONE);
                editButtons.get(i).setVisibility(View.GONE);
            }
        }

        binding.updateTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numOGtasks = newData.getTasks().size();

                for (int i = numOGtasks; i < editTexts.size(); i++) {
                    String newTaskName = editTexts.get(i).getText().toString();
                    if (!newTaskName.isEmpty()) {
                        newData.addTask(new Task(newTaskName));
                    }
                }

                if (newData.getTasks().size() > 0) {
                    FirebaseDatabase.getInstance()
                            .getReference("DiaryData")
                            .child(authUser.getUid())
                            .setValue(newData);

                    diaryViewModel.setOGdiaryData(newData);
                    diaryViewModel.setNewDiaryData(newData);

                    replaceFragment(new ViewDiaryFragment());
                } else {
                    final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
                    assert warningIcon != null;
                    warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());

                    warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext()));

                    binding.task01.setError("You must set at least one task.", warningIcon);
                }
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryViewModel.setOGdiaryData(diaryData);
                diaryViewModel.setNewDiaryData(diaryData);
                replaceFragment(new ViewDiaryFragment());
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

    private View.OnClickListener getEditListener(int index) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable save = ContextCompat.getDrawable(requireContext(), R.drawable.ic_save_black_200dp);

                editTexts.get(index).setEnabled(true);

                binding.cancelButton.getBackground().setTint(ColorUtils.blendARGB(colorOnPrimary, greyColor, 0.50f));
                binding.cancelButton.setTextColor(ColorUtils.blendARGB(colorPrimary, greyColor, 0.50f));
                binding.cancelButton.setEnabled(false);

                binding.updateTasksButton.getBackground().setTint(ColorUtils.blendARGB(colorPrimary, greyColor, 0.50f));
                binding.updateTasksButton.setTextColor(ColorUtils.blendARGB(colorOnPrimary, greyColor, 0.50f));
                binding.updateTasksButton.setEnabled(false);

                for (int i = 0; i < editTexts.size(); i++) {
                    if (i != index) {
                        editTexts.get(i).setEnabled(false);
                        editTexts.get(i).setTextColor(greyColor);
                        editTexts.get(i).setHintTextColor(greyColor);

                        editButtons.get(i).getBackground().setTint(greyColor);
                        editButtons.get(i).setEnabled(false);
                    }

                    deleteButtons.get(i).getBackground().setTint(greyColor);
                    deleteButtons.get(i).setEnabled(false);
                }

                v.setBackground(save);
                v.setOnClickListener(getSaveListener(index));
            }
        };
    }

    private View.OnClickListener getSaveListener(int index) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable pencil = ContextCompat.getDrawable(requireContext(), R.drawable.ic_pencil_black_200dp);

                String newName = editTexts.get(index).getText().toString();
                if (!newName.equals("")) {
                    tasks.get(index).setName(newName);
                }

                for (int i = 0; i < editTexts.size(); i++) {
                    editTexts.get(i).setEnabled(true);
                    editTexts.get(i).setTextColor(Color.BLACK);
                    editTexts.get(i).setHintTextColor(Color.DKGRAY);

                    editButtons.get(i).getBackground().setTint(colorPrimaryVariant);
                    editButtons.get(i).setEnabled(true);

                    deleteButtons.get(i).getBackground().setTint(colorPrimaryVariant);
                    deleteButtons.get(i).setEnabled(true);
                }

                binding.cancelButton.getBackground().setTint(colorOnPrimary);
                binding.cancelButton.setTextColor(colorPrimary);
                binding.cancelButton.setEnabled(true);

                binding.updateTasksButton.getBackground().setTint(colorPrimary);
                binding.updateTasksButton.setTextColor(colorOnPrimary);
                binding.updateTasksButton.setEnabled(true);

                v.setBackground(pencil);
                v.setOnClickListener(getEditListener(index));
            }
        };
    }
}