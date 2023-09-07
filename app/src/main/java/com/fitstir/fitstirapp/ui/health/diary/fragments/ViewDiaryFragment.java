package com.fitstir.fitstirapp.ui.health.diary.fragments;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewDiaryBinding;
import com.fitstir.fitstirapp.ui.health.diary.DiaryData;
import com.fitstir.fitstirapp.ui.health.diary.DiaryEntry;
import com.fitstir.fitstirapp.ui.health.diary.DiaryViewModel;
import com.fitstir.fitstirapp.ui.health.diary.Task;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        DiaryData diaryData = diaryViewModel.getDiaryData().getValue();
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        //region Updating isCompleted on screen and in Firebase ////////////////////////////////////
        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>() {{
            add(binding.task01Checkbox);
            add(binding.task02Checkbox);
            add(binding.task03Checkbox);
            add(binding.task04Checkbox);
            add(binding.task05Checkbox);
            add(binding.task06Checkbox);
            add(binding.task07Checkbox);
            add(binding.task08Checkbox);
            add(binding.task09Checkbox);
            add(binding.task10Checkbox);
        }};
        ArrayList<Task> tasks = new ArrayList<Task>() {{
            add(diaryData.getTask01());
            add(diaryData.getTask02());
            add(diaryData.getTask03());
            add(diaryData.getTask04());
            add(diaryData.getTask05());
            add(diaryData.getTask06());
            add(diaryData.getTask07());
            add(diaryData.getTask08());
            add(diaryData.getTask09());
            add(diaryData.getTask10());
        }};

        if (checkBoxes.size() != tasks.size()) {
            throw new RuntimeException("TASK COUNT != TO CHECKBOX COUNT"); // debugging purposes in case the amount is changed
        }

        final int size = checkBoxes.size();

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = 0;
        if (diaryData.getTask03() == null || diaryData.getTask03().getName() == null || diaryData.getTask03().getName().equals("")) {
            pixels = (int) (75 * scale + 0.5f);
        } else if (diaryData.getTask05() == null || diaryData.getTask05().getName() == null || diaryData.getTask05().getName().equals("")) {
            pixels = (int) (115 * scale + 0.5f);
        } else if (diaryData.getTask07() == null || diaryData.getTask07().getName() == null || diaryData.getTask07().getName().equals("")) {
            pixels = (int) (155 * scale + 0.5f);
        } else if (diaryData.getTask09() == null || diaryData.getTask09().getName() == null || diaryData.getTask09().getName().equals("")) {
            pixels = (int) (195 * scale + 0.5f);
        } else {
            pixels = (int) (235 * scale + 0.5f);
        }
        binding.taskOverhang.getLayoutParams().height = pixels;

        for (int i = 0; i < size; i++) {
            if (tasks.get(i) == null || tasks.get(i).getName() == null || tasks.get(i).getName().equals("")) {
                checkBoxes.get(i).setVisibility(View.GONE);
            } else {
                checkBoxes.get(i).setVisibility(View.VISIBLE);
                checkBoxes.get(i).setText(tasks.get(i).getName());
            }

            checkBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        for (int j = 0; j < size; j++) {
                            if (buttonView.equals(checkBoxes.get(j))) {
                                tasks.get(j).addDate(Calendar.getInstance().getTime());
                            }
                        }
                    } else {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));

                        for (int j = 0; j < size; j++) {
                            if (buttonView.equals(checkBoxes.get(j))) {
                                ArrayList<Date> dates = tasks.get(j).getCompletedOn();
                                if (dates != null && dates.size() != 0) {
                                    int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

                                    Calendar cal = Calendar.getInstance();
                                    Date savedDate = dates.get(dates.size() - 1);
                                    cal.setTime(savedDate);
                                    int lastSaved = cal.get(Calendar.DAY_OF_YEAR);

                                    if (today == lastSaved && dates.remove(savedDate)) {
                                        tasks.get(j).setCompletedOn(dates);
                                    }
                                }
                            }
                        }
                    }

                    diaryData.setTask01(tasks.get(0));
                    diaryData.setTask02(tasks.get(1));
                    diaryData.setTask03(tasks.get(2));
                    diaryData.setTask04(tasks.get(3));
                    diaryData.setTask05(tasks.get(4));
                    diaryData.setTask06(tasks.get(5));
                    diaryData.setTask07(tasks.get(6));
                    diaryData.setTask08(tasks.get(7));
                    diaryData.setTask09(tasks.get(8));
                    diaryData.setTask10(tasks.get(9));

                    FirebaseDatabase.getInstance()
                            .getReference("DiaryData")
                            .child(authUser.getUid())
                            .setValue(diaryData);
                }
            });
        }
        //endregion ////////////////////////////////////////////////////////////////////////////////

        //region Updating mood on screen and in Firebase ///////////////////////////////////////////
        final boolean isRecordedToday;

        DiaryEntry potentiallyToday = diaryData.getEmotions().get(diaryData.getEmotions().size() - 1);
        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        Calendar cal = Calendar.getInstance();
        cal.setTime(potentiallyToday.getDate());
        int actual = cal.get(Calendar.DAY_OF_YEAR);

        final String todaysMood;
        final String todaysEmoji;
        if (today == actual) {
            todaysMood = potentiallyToday.getComment();
            todaysEmoji = potentiallyToday.getEmoji();
            isRecordedToday = true;
        } else {
            todaysMood = "Nothing yet.";
            todaysEmoji = "";
            isRecordedToday = false;
        }

        binding.recordedMood.setText(todaysMood);
        binding.recordedEmoji.setText(todaysEmoji);

        binding.updateMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(requireActivity());
                View popUpView = inflater.inflate(R.layout.popup_update_mood, null);
                PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                EditText moodET = popUpView.findViewById(R.id.mood_edit_text);
                moodET.setText(todaysMood);

                EditText emojiET = popUpView.findViewById(R.id.emoji_edit_text);
                emojiET.setText(todaysEmoji);

                Button updateButton = popUpView.findViewById(R.id.popup_mood_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
                        assert warningIcon != null;
                        warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());
                        warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext()));

                        String mood = moodET.getText().toString();
                        String emoji = emojiET.getText().toString();

                        if (!mood.equals("")) {
                            if (!emoji.equals("")) {
                                if (true) { // TODO: Check that it's an emoji?
                                    binding.recordedMood.setText(mood);
                                    binding.recordedEmoji.setText(emoji);

                                    DiaryEntry diaryEntry = new DiaryEntry(Calendar.getInstance().getTime(), emoji, mood);

                                    if (isRecordedToday) {
                                        diaryData.getEmotions().get(diaryData.getEmotions().size() - 1).setComment(mood);
                                        diaryData.getEmotions().get(diaryData.getEmotions().size() - 1).setEmoji(emoji);
                                    } else {
                                        diaryData.getEmotions().add(diaryEntry);
                                    }

                                    FirebaseDatabase.getInstance()
                                            .getReference("DiaryData")
                                            .child(authUser.getUid())
                                            .child("emotions")
                                            .setValue(diaryData.getEmotions());

                                    popupWindow.dismiss();
                                } else {
                                    emojiET.setError("This is not an emoji.", warningIcon);
                                }
                            } else {
                                emojiET.setError("This field is required.", warningIcon);
                            }
                        } else {
                            moodET.setError("This field is required.", warningIcon);
                        }
                    }
                });
                popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0,0);
            }
        });
        //endregion ////////////////////////////////////////////////////////////////////////////////

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