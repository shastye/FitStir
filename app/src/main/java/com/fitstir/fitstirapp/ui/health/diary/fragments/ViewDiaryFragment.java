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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewDiaryBinding;
import com.fitstir.fitstirapp.ui.health.diary.DiaryData;
import com.fitstir.fitstirapp.ui.health.diary.DiaryEntry;
import com.fitstir.fitstirapp.ui.health.diary.DiaryViewModel;
import com.fitstir.fitstirapp.ui.health.diary.Task;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        diaryViewModel.setPreviousFragment("ViewDiaryFragment");

        DiaryData diaryData = diaryViewModel.getOGdiaryData().getValue();
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        int numTasks = 0;
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
        ArrayList<Task> tasks = diaryData.getTasks();

        if (tasks.size() > checkBoxes.size()) {
            throw new RuntimeException("TOO MANY TASKS?"); // debugging purposes in case the amount is changed
        }

        ArrayList<DiaryEntry> diaryEntries = diaryData.getEmotions();
        if (diaryEntries == null) {
            diaryEntries = new ArrayList<>();
        }
        ArrayList<TextView> emojiGridDays = new ArrayList<TextView>() {{
            add(binding.emojiDay1);
            add(binding.emojiDay2);
            add(binding.emojiDay3);
            add(binding.emojiDay4);
            add(binding.emojiDay5);
            add(binding.emojiDay6);
            add(binding.emojiDay7);
        }};

        Calendar cal = Calendar.getInstance();
        final int today = cal.get(Calendar.DAY_OF_YEAR);

        //region Updating saved lists to only include last seven days //////////////////////////////
        cal = Calendar.getInstance(); // just in case I add something before this that
                                      // manipulates cal since I'm not using cal.setTime()
        cal.add(Calendar.DATE, -7);
        final int lastDate = cal.get(Calendar.DAY_OF_YEAR);

        if (diaryEntries.size() != 0) {
            for (int i = 0; i < diaryEntries.size(); i++) {
                DiaryEntry entry = diaryEntries.get(i);

                Calendar temp = Calendar.getInstance();
                temp.setTime(entry.getDate());
                if (lastDate > temp.get(Calendar.DAY_OF_YEAR)) {
                    diaryEntries.remove(entry);
                }
            }

            diaryData.setEmotions(diaryEntries);
        }

        FirebaseDatabase.getInstance()
                .getReference("DiaryData")
                .child(authUser.getUid())
                .child("emotions")
                .setValue(diaryData.getEmotions());

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task != null && task.getCompletedOn() != null && task.getCompletedOn().size() != 0) {
                ArrayList<Date> dates = task.getCompletedOn();
                for (int k = 0; k < dates.size(); k++) {

                    Calendar temp = Calendar.getInstance();
                    temp.setTime(dates.get(k));
                    if (lastDate > temp.get(Calendar.DAY_OF_YEAR)) {
                        dates.remove(dates.get(k));
                        k--;
                    }
                }
                task.setCompletedOn(dates);
            }
            tasks.set(i, task);
            diaryData.setTasks(tasks);
        }

        FirebaseDatabase.getInstance()
                .getReference("DiaryData")
                .child(authUser.getUid())
                .setValue(diaryData);
        //endregion ////////////////////////////////////////////////////////////////////////////////

        //region Updating isCompleted on screen and in Firebase ////////////////////////////////////
        final int size = checkBoxes.size();

        for (int i = 0; i < size; i++) {
            Calendar finalCal = cal;
            checkBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int index = -1;
                    int lastCompleted = -1;

                    for (int j = 0; j < size; j++) {
                        if (buttonView.equals(checkBoxes.get(j))) {
                            index = j;

                            if (tasks.get(j).getCompletedOn() != null && tasks.get(j).getCompletedOn().size() != 0) {
                                finalCal.setTime(tasks.get(j).getCompletedOn().get(tasks.get(j).getCompletedOn().size() - 1));
                                lastCompleted = finalCal.get(Calendar.DAY_OF_YEAR);
                            }

                            break;
                        }
                    }

                    if (isChecked) {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        if (today != lastCompleted) {
                            tasks.get(index).addDate(Calendar.getInstance().getTime());
                        }
                    } else {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));

                        ArrayList<Date> dates = tasks.get(index).getCompletedOn();

                        if (dates != null && dates.size() != 0) {
                            Date savedDate = dates.get(dates.size() - 1);
                            if (index != -1 && lastCompleted != -1 && today == lastCompleted && dates.remove(savedDate)) {
                                tasks.get(index).setCompletedOn(dates);
                            }
                        }
                    }

                    diaryData.setTasks(tasks);

                    FirebaseDatabase.getInstance()
                            .getReference("DiaryData")
                            .child(authUser.getUid())
                            .setValue(diaryData);
                }
            });

            Task task = null;
            try {
                task = tasks.get(i);

                numTasks++;

                int lastCompleted = -1;
                if (task.getCompletedOn() != null && task.getCompletedOn().size() != 0) {
                    cal.setTime(task.getCompletedOn().get(task.getCompletedOn().size() - 1));
                    lastCompleted = cal.get(Calendar.DAY_OF_YEAR);
                }

                checkBoxes.get(i).setChecked(lastCompleted != -1 && today == lastCompleted);
                checkBoxes.get(i).setVisibility(View.VISIBLE);
                checkBoxes.get(i).setText(tasks.get(i).getName());
            } catch (IndexOutOfBoundsException e) {
                checkBoxes.get(i).setVisibility(View.GONE);
            }
        }

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = 0;
        if (numTasks < 3) {
            pixels = (int) (75 * scale + 0.5f);
        } else if (numTasks < 5) {
            pixels = (int) (115 * scale + 0.5f);
        } else if (numTasks < 7) {
            pixels = (int) (155 * scale + 0.5f);
        } else if (numTasks < 9) {
            pixels = (int) (195 * scale + 0.5f);
        } else {
            pixels = (int) (235 * scale + 0.5f);
        }
        binding.taskOverhang.getLayoutParams().height = pixels;
        //endregion ////////////////////////////////////////////////////////////////////////////////

        //region Updating mood on screen and in Firebase ///////////////////////////////////////////
        final boolean isRecordedToday;

        final String todaysMood;
        final String todaysEmoji;
        final DiaryEntry potentiallyToday;

        if (diaryData.getEmotions() != null && diaryData.getEmotions().size() != 0) {
            potentiallyToday = diaryData.getEmotions().get(diaryData.getEmotions().size() - 1);
            cal.setTime(potentiallyToday.getDate());
            int actual = cal.get(Calendar.DAY_OF_YEAR);

            if (today == actual) {
                todaysMood = potentiallyToday.getComment();
                todaysEmoji = potentiallyToday.getEmoji();
                isRecordedToday = true;
            } else {
                todaysMood = "Nothing yet.";
                todaysEmoji = "";
                isRecordedToday = false;
            }
        } else {
            potentiallyToday = null;
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
                EditText emojiET = popUpView.findViewById(R.id.emoji_edit_text);
                if (todaysMood.equals("Nothing yet.") && todaysEmoji.equals("")) {
                    moodET.setText(todaysMood);
                    emojiET.setText(todaysEmoji);
                } else {
                    moodET.setText(potentiallyToday.getComment());
                    emojiET.setText(potentiallyToday.getEmoji());
                }

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
                                if (isEmoji(emoji)) {
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

        //region Updating the Wellness Tracker using Firebase information //////////////////////////
        final ArrayList<String> emojis = new ArrayList<String>() {{
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
        }};
        for (int i = 0; i < diaryEntries.size(); i++) {
            cal.setTime(diaryEntries.get(i).getDate());
            int recorded = cal.get(Calendar.DAY_OF_YEAR);

            for (int comparingDay = today - 1; comparingDay > today - 8; comparingDay--) {
                if (comparingDay == recorded) {
                    emojis.set(-(comparingDay - (today - 1)), diaryEntries.get(i).getEmoji());
                    break;
                }
            }
        }
        for (int i = 0; i < emojiGridDays.size(); i++) {
            emojiGridDays.get(i).setText(emojis.get(i));
        }

        final ArrayList<Boolean> sevenFalse = new ArrayList<Boolean>() {{
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
        }};
        final ArrayList<ArrayList<Boolean>> wellnessTracker = new ArrayList<>();
        final ArrayList<ArrayList<Date>> allCompleted = new ArrayList<>();
        for (int i = 0; i < numTasks; i++) {
            allCompleted.add(tasks.get(i).getCompletedOn());
            wellnessTracker.add(new ArrayList<>(sevenFalse));
        }

        cal = Calendar.getInstance();

        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        binding.startingMonthLabel.setText(month);

        cal.add(Calendar.DATE, -1);
        binding.date1.setText(getDateAsString(cal));
        cal.add(Calendar.DATE, -1);
        binding.date2.setText(getDateAsString(cal));
        cal.add(Calendar.DATE, -1);
        binding.date3.setText(getDateAsString(cal));
        cal.add(Calendar.DATE, -1);
        binding.date4.setText(getDateAsString(cal));
        cal.add(Calendar.DATE, -1);
        binding.date5.setText(getDateAsString(cal));
        cal.add(Calendar.DATE, -1);
        binding.date6.setText(getDateAsString(cal));
        cal.add(Calendar.DATE, -1);
        binding.date7.setText(getDateAsString(cal));

        for (int i = 0; i < numTasks; i++) {
            for (int comparingDay = today - 1; comparingDay > today - 8; comparingDay--) {
                for (int k = 0; k < allCompleted.get(i).size(); k++) {
                    cal.setTime(allCompleted.get(i).get(k));
                    int recorded = cal.get(Calendar.DAY_OF_YEAR);

                    if (comparingDay == recorded) {
                        wellnessTracker.get(i).set(-(comparingDay - (today - 1)), true);
                    }
                }
            }
        }

        RecyclerView wellnessTrackerGrid = binding.wellnessTrackerGrid;
        wellnessTrackerGrid.setLayoutManager(new LinearLayoutManager(requireActivity()));
        WellnessTrackerAdapter adapter = new WellnessTrackerAdapter(wellnessTracker);

        int OGheight = binding.wellnessTrackerGrid.getLayoutParams().height;
        int modifier;
        if (numTasks < 3) {
            modifier = 4;
        } else if (numTasks < 5) {
            modifier = 3;
        } else if (numTasks < 7) {
            modifier = 2;
        } else if (numTasks < 9) {
            modifier = 1;
        } else {
            modifier = 0;
        }
        int changeInHeight = (int) (modifier * 40 * scale + 0.5f);
        binding.wellnessTrackerGrid.getLayoutParams().height = OGheight + changeInHeight;

        wellnessTrackerGrid.setAdapter(adapter);
        //endregion ////////////////////////////////////////////////////////////////////////////////

        //region Navigate to edit tasks ////////////////////////////////////////////////////////////
        binding.editTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryViewModel.setOGdiaryData(diaryData);
                diaryViewModel.setNewDiaryData(null);
                replaceFragment(new EditDiaryFragment());
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
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.current_fragment, fragment)
                .commit();
    }

    public String getDateAsString(Calendar calendar) {
        if (calendar.get(Calendar.DATE) == 31) {
            return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.ENGLISH);
        } else {
            return String.valueOf(calendar.get(Calendar.DATE));
        }
    }

    public boolean isEmoji(String emoji) {
        char[] temp = new char[emoji.length()];
        emoji.getChars(0, emoji.length() - 1, temp, 0);

        for (int i = 0; i < emoji.length(); i++) {
            if (Character.isLetterOrDigit(temp[i]) || Character.isSpaceChar(temp[i]) || Character.isWhitespace(temp[i])) {
                return false;
            }
        }

        return true;
    }

    private class WellnessTrackerHolder extends RecyclerView.ViewHolder {
        private ArrayList<Boolean> completed;

        private TextView taskLabel;
        private AppCompatImageView day1, day2, day3, day4, day5, day6, day7;

        public WellnessTrackerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_wellness_tracker_grid_row, parent, false));

            diaryViewModel = new ViewModelProvider(requireActivity()).get(DiaryViewModel.class);

            taskLabel = itemView.findViewById(R.id.label_task);

            day1 = itemView.findViewById(R.id.day1);
            day2 = itemView.findViewById(R.id.day2);
            day3 = itemView.findViewById(R.id.day3);
            day4 = itemView.findViewById(R.id.day4);
            day5 = itemView.findViewById(R.id.day5);
            day6 = itemView.findViewById(R.id.day6);
            day7 = itemView.findViewById(R.id.day7);
        }

        public void bind(ArrayList<Boolean> completed, String label) {
            this.completed = completed;

            Drawable blank = ContextCompat.getDrawable(requireContext(), R.drawable.grid_outline_24dp);
            Drawable check = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_24dp);

            AppCompatImageView[] days = new AppCompatImageView[] {
                    day1,
                    day2,
                    day3,
                    day4,
                    day5,
                    day6,
                    day7
            };

            taskLabel.setText(label);

            for (int i = 0; i < 7; i++) {
                if (this.completed.get(i)) {
                    days[i].setForeground(check);
                } else {
                    days[i].setForeground(blank);
                }
            }
        }
    }

    private class WellnessTrackerAdapter extends RecyclerView.Adapter<WellnessTrackerHolder> {
        private ArrayList<ArrayList<Boolean>> completed;

        public WellnessTrackerAdapter(ArrayList<ArrayList<Boolean>> completed) {
            this.completed = completed;
        }

        @NonNull
        @Override
        public WellnessTrackerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
            return new WellnessTrackerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull WellnessTrackerHolder holder, int position) {
            String label = diaryViewModel.getOGdiaryData().getValue().getTasks().get(position).getName();
            ArrayList<Boolean> row = completed.get(position);
            holder.bind(row, label);
        }

        @Override
        public int getItemCount() {
            return completed.size();
        }
    }
}