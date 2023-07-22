package com.fitstir.fitstirapp.ui.goals.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewGoalBinding;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.goals.dialogs.DeleteGoalDialog;
import com.fitstir.fitstirapp.ui.goals.dialogs.EditGoalDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewGoalFragment extends Fragment {

    private FragmentViewGoalBinding binding;
    GoalsViewModel goalsViewModel;
    private Goal goal;

    private TextView nameTextView, typeTextView, valueTextView, idTextView;
    private GraphView graphView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel = new ViewModelProvider(requireActivity()).get(GoalsViewModel.class);

        binding = FragmentViewGoalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Additional Text Here

        this.nameTextView = binding.viewGoalNameLabel;
        this.typeTextView = binding.viewGoalTypeLabel;
        this.valueTextView = binding.viewGoalValueLabel;
        this.idTextView = binding.viewGoalIdLabel;
        this.graphView = binding.viewGoalGridGraph;
        this.goal = goalsViewModel.getClickedGoal().getValue();

        bind();

        ImageButton moreButton = binding.viewGoalMoreButton;
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireActivity(), moreButton);
                popupMenu.getMenuInflater().inflate(R.menu.goal_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_goal:
                                EditGoalDialog.newInstance(
                                        R.layout.dialog_create_goal,
                                        R.id.dialog_create_goal_accept_button,
                                        R.id.dialog_create_goal_cancel_button,
                                        ViewGoalFragment.this
                                ).show(getParentFragmentManager(), "Edit Goal");
                                break;
                            case R.id.delete_goal:
                                DeleteGoalDialog.newInstance(
                                        R.layout.dialog_generic_alert,
                                        R.id.dialog_generic_accept_button,
                                        R.id.dialog_generic_cancel_button,
                                        R.id.dialog_generic_message,
                                        "This action cannot be undone."
                                ).show(getParentFragmentManager(), "Delete Goal");
                                break;
                        }

                        return true;
                    }
                });
                popupMenu.show();
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

    public void bind() {
        this.graphView.removeAllSeries();

        this.nameTextView.setText(this.goal.getName());
        this.typeTextView.setText(this.goal.getType().getSpinnerTitle());
        String valueText = String.valueOf(this.goal.getValue()) + " " + this.goal.getUnit();
        this.valueTextView.setText(valueText);
        String idText = "Goal ID: " + String.valueOf(this.goal.getID());
        this.idTextView.setText(idText);



        int colorPrimaryVariant = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext());
        int colorOnPrimary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorOnPrimary, requireContext());
        int colorSecondary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorSecondary, requireContext());

        int range = goalsViewModel.getGoalRange().getValue();

        Date startDate = this.goal.getMinDate();
        Date endDate = this.goal.getMaxDate();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        if (startDate != null && endDate != null) {
            startCal.setTime(startDate);
            endCal.setTime(endDate);
        } else if (endDate != null) {
            startCal.setTime(endDate);
            endCal.setTime(endDate);
        }

        Calendar rangeStart = Calendar.getInstance();
        rangeStart.setTime(endCal.getTime());
        rangeStart.add(Calendar.DATE, -1 * range);
        if (startCal.getTime().after(rangeStart.getTime())) {
            rangeStart.setTime(startCal.getTime());
        }

        Calendar windowWidth = Calendar.getInstance();
        windowWidth.setTime(rangeStart.getTime());
        windowWidth.add(Calendar.DATE, 15);

        ArrayList<Pair<Date, Double>> inRange = new ArrayList<>();
        for (int i = 0; i < this.goal.getData().size(); i++) {
            if (!this.goal.getData().get(i).first.after(endCal.getTime()) && !this.goal.getData().get(i).first.before(rangeStart.getTime())) {
                inRange.add(new Pair<>(this.goal.getData().get(i).first, this.goal.getData().get(i).second));
            }
        }

        DataPoint[] dataPoints = new DataPoint[inRange.size()];
        for (int i = 0; i < inRange.size(); i++) {
            dataPoints[i] = new DataPoint(inRange.get(i).first, inRange.get(i).second);
        }
        LineGraphSeries<DataPoint> dataLine = new LineGraphSeries<>(dataPoints);
        dataLine.setColor(colorPrimaryVariant);
        dataLine.setThickness(15);
        dataLine.setBackgroundColor(colorOnPrimary);
        dataLine.setDrawBackground(true);
        this.graphView.addSeries(dataLine);

        DataPoint[] valuePoints = new DataPoint[] {
                new DataPoint(dataLine.getLowestValueX(), this.goal.getValue()),
                new DataPoint(endCal.getTime(), this.goal.getValue())
        };
        LineGraphSeries<DataPoint> valueLine = new LineGraphSeries<>(valuePoints);
        valueLine.setColor(colorSecondary);
        this.graphView.addSeries(valueLine);

        this.graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

        this.graphView.getGridLabelRenderer().setVerticalAxisTitle(this.goal.getUnit());
        this.graphView.getGridLabelRenderer().setVerticalAxisTitleColor(colorOnPrimary);

        this.graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                String toReturn = "";

                if (isValueX) {
                    Date dateVal = new Date((long) value);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateVal);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DATE);

                    if (day == 1) {
                        switch (month) {
                            case Calendar.JANUARY:
                                toReturn = "Jan";
                                break;
                            case Calendar.FEBRUARY:
                                toReturn = "Feb";
                                break;
                            case Calendar.MARCH:
                                toReturn = "Mar";
                                break;
                            case Calendar.APRIL:
                                toReturn = "Apr";
                                break;
                            case Calendar.MAY:
                                toReturn = "May";
                                break;
                            case Calendar.JUNE:
                                toReturn = "Jun";
                                break;
                            case Calendar.JULY:
                                toReturn = "Jul";
                                break;
                            case Calendar.AUGUST:
                                toReturn = "Aug";
                                break;
                            case Calendar.SEPTEMBER:
                                toReturn = "Sep";
                                break;
                            case Calendar.OCTOBER:
                                toReturn = "Oct";
                                break;
                            case Calendar.NOVEMBER:
                                toReturn = "Nov";
                                break;
                            case Calendar.DECEMBER:
                                toReturn = "Dec";
                                break;
                            default:
                                toReturn = "oops";
                                break;
                        }
                    } else {
                        toReturn = "";

                        if (day % 2 == 1) {

                            if (day < 10) {
                                toReturn = "0";
                            }

                            toReturn += String.valueOf(day);
                        }
                    }
                } else {
                    if ((((int) value) % 2) == 0) {
                        toReturn = String.valueOf((int) value);
                    } else {
                        toReturn = "";
                    }
                }

                return toReturn;
            }
        });
        this.graphView.getGridLabelRenderer().setNumHorizontalLabels(18);
        this.graphView.getGridLabelRenderer().setHumanRounding(false, true);
        this.graphView.getGridLabelRenderer().setHorizontalLabelsColor(colorOnPrimary);

        this.graphView.getViewport().setYAxisBoundsManual(true);
        double minVal = dataLine.getLowestValueY();
        double maxVal = dataLine.getHighestValueY();
        if (this.goal.getValue() > maxVal) {
            maxVal = this.goal.getValue();
        } else if (this.goal.getValue() < minVal) {
            minVal = this.goal.getValue();
        }
        this.graphView.getViewport().setMinY(minVal - 2);
        this.graphView.getViewport().setMaxY(maxVal + 2);

        this.graphView.getViewport().setXAxisBoundsManual(true);
        rangeStart.add(Calendar.DATE, -2);
        this.graphView.getViewport().setMinX(rangeStart.getTime().getTime());
        this.graphView.getViewport().setMaxX(windowWidth.getTime().getTime());

        this.graphView.getViewport().setScrollable(true);
        this.graphView.getViewport().scrollToEnd();
    }
}