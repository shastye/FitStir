package com.fitstir.fitstirapp.ui.health.calorietracker;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentCalorieTrackerBinding;

import java.util.Calendar;

public class CalorieTrackerFragment extends Fragment {

    private FragmentCalorieTrackerBinding binding;
    private CalorieTrackerViewModel calorieTrackerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);

        binding = FragmentCalorieTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCalorieTracker;
        //recipesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        TextView dateName = root.findViewById(R.id.calendar_date_label);
        dateName.setText(calorieTrackerViewModel.getDateString().getValue());




        AppCompatImageView dateSelectorButton = root.findViewById(R.id.calendar_toolbar_dropdown_button);
        dateSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popUpView = inflater.inflate(R.layout.popup_date_picker, null);
                PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                Calendar selectedDate = Calendar.getInstance();
                final String[] dateString = {"Today"};

                CalendarView calendarView = popUpView.findViewById(R.id.popup_calender_view);
                calendarView.setDate(calorieTrackerViewModel.getSelectedDate().getValue().getTime().getTime());
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        Calendar today = Calendar.getInstance();

                        if (!(today.get(Calendar.YEAR) == year && today.get(Calendar.MONTH) == month && today.get(Calendar.DAY_OF_MONTH) == dayOfMonth)) {
                            dateString[0] = "";
                            switch (month) {
                                case Calendar.JANUARY:
                                    dateString[0] += "Jan.";
                                    break;
                                case Calendar.FEBRUARY:
                                    dateString[0] += "Feb.";
                                    break;
                                case Calendar.MARCH:
                                    dateString[0] += "Mar.";
                                    break;
                                case Calendar.APRIL:
                                    dateString[0] += "Apr.";
                                    break;
                                case Calendar.MAY:
                                    dateString[0] += "May";
                                    break;
                                case Calendar.JUNE:
                                    dateString[0] += "June";
                                    break;
                                case Calendar.JULY:
                                    dateString[0] += "July";
                                    break;
                                case Calendar.AUGUST:
                                    dateString[0] += "Aug.";
                                    break;
                                case Calendar.SEPTEMBER:
                                    dateString[0] += "Sept.";
                                    break;
                                case Calendar.OCTOBER:
                                    dateString[0] += "Oct.";
                                    break;
                                case Calendar.NOVEMBER:
                                    dateString[0] += "Nov.";
                                    break;
                                case Calendar.DECEMBER:
                                    dateString[0] += "Dec.";
                                    break;
                            }
                            dateString[0] += " " + dayOfMonth + ", " + year;
                        }

                        dateName.setText(dateString[0]);
                        selectedDate.set(year, month, dayOfMonth);
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        calorieTrackerViewModel.setSelectedDate(selectedDate);
                        calorieTrackerViewModel.setDateString(dateString[0]);
                    }
                });
                popupWindow.showAtLocation(dateSelectorButton, Gravity.TOP, 0,425);
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
}