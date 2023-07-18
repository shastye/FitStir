package com.fitstir.fitstirapp.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentGoalsBinding;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GoalsFragment extends Fragment {

    private FragmentGoalsBinding binding;
    private RecyclerView goalRecyclerView;
    private GoalAdapter goalAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GoalsViewModel goalsViewModel =
                new ViewModelProvider(this).get(GoalsViewModel.class);

        binding = FragmentGoalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGoals;
        goalsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        goalRecyclerView = root.findViewById(R.id.goal_recycler_view);
        goalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        // End
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateUI() {
        ArrayList<Goal> goals = new ArrayList<>(); // TODO: retrieve from database

        Calendar calendar1 = Calendar.getInstance();

        Goal g1 = new Goal("Test1", "Type1", 16);
        calendar1.set(Calendar.MONTH, Calendar.MAY);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);
        calendar1.set(Calendar.YEAR, 2023);
        g1.addData(new Pair<>(calendar1.getTime(), 1.1));
        calendar1.add(Calendar.DATE, 2);
        g1.addData(new Pair<>(calendar1.getTime(), 3.6));
        calendar1.add(Calendar.DATE, 1);
        g1.addData(new Pair<>(calendar1.getTime(), 4.7));
        calendar1.add(Calendar.DATE, 5);
        g1.addData(new Pair<>(calendar1.getTime(), 7.0));
        calendar1.add(Calendar.DATE, 2);
        g1.addData(new Pair<>(calendar1.getTime(), 8.9));
        calendar1.add(Calendar.DATE, 6);
        g1.addData(new Pair<>(calendar1.getTime(), 7.5));
        calendar1.add(Calendar.DATE, 6);
        g1.addData(new Pair<>(calendar1.getTime(), 7.7));
        calendar1.add(Calendar.DATE, 6);
        g1.addData(new Pair<>(calendar1.getTime(), 7.0));
        calendar1.add(Calendar.DATE, 6);
        g1.addData(new Pair<>(calendar1.getTime(), 7.9));
        calendar1.add(Calendar.DATE, 6);
        g1.addData(new Pair<>(calendar1.getTime(), 7.2));
        calendar1.add(Calendar.DATE, 5);
        g1.addData(new Pair<>(calendar1.getTime(), 7.9));
        calendar1.add(Calendar.DATE, 2);
        g1.addData(new Pair<>(calendar1.getTime(), 8.4));
        calendar1.add(Calendar.DATE, 2);
        g1.addData(new Pair<>(calendar1.getTime(), 12.9));
        calendar1.add(Calendar.DATE, 2);
        g1.addData(new Pair<>(calendar1.getTime(), 11.1));
        calendar1.add(Calendar.DATE, 2);
        g1.addData(new Pair<>(calendar1.getTime(), 8.6));
        calendar1.add(Calendar.DATE, 2);
        g1.addData(new Pair<>(calendar1.getTime(), 14.7));

        goals.add(g1);




        Calendar calendar2 = Calendar.getInstance();

        Goal g2 = new Goal("Test2", "Type2", 14);
        calendar2.set(Calendar.MONTH, Calendar.JUNE);
        calendar2.set(Calendar.DAY_OF_MONTH, 1);
        calendar2.set(Calendar.YEAR, 2023);
        g2.addData(new Pair<>(calendar2.getTime(), 3.6));
        calendar2.add(Calendar.DATE, 1);
        g2.addData(new Pair<>(calendar2.getTime(), 4.7));
        calendar2.add(Calendar.DATE, 2);
        g2.addData(new Pair<>(calendar2.getTime(), 8.9));
        calendar2.add(Calendar.DATE, 6);
        g2.addData(new Pair<>(calendar2.getTime(), 7.5));
        calendar2.add(Calendar.DATE, 3);
        g2.addData(new Pair<>(calendar2.getTime(), 7.7));
        calendar2.add(Calendar.DATE, 4);
        g2.addData(new Pair<>(calendar2.getTime(), 7.0));
        calendar2.add(Calendar.DATE, 6);
        g2.addData(new Pair<>(calendar2.getTime(), 7.9));
        calendar2.add(Calendar.DATE, 6);
        g2.addData(new Pair<>(calendar2.getTime(), 7.2));
        calendar2.add(Calendar.DATE, 5);
        g2.addData(new Pair<>(calendar2.getTime(), 7.9));
        calendar2.add(Calendar.DATE, 2);
        g2.addData(new Pair<>(calendar2.getTime(), 8.4));
        calendar2.add(Calendar.DATE, 2);
        g2.addData(new Pair<>(calendar2.getTime(), 12.9));
        calendar2.add(Calendar.DATE, 2);
        g2.addData(new Pair<>(calendar2.getTime(), 11.1));
        calendar2.add(Calendar.DATE, 2);
        g2.addData(new Pair<>(calendar2.getTime(), 8.6));

        goals.add(g2);


        goalAdapter = new GoalAdapter(goals);
        goalRecyclerView.setAdapter(goalAdapter);
    }

    private class GoalHolder extends RecyclerView.ViewHolder {
        private Goal goal;
        private final TextView nameTextView;
        private final TextView typeTextView;
        private final TextView valueTextView;
        private final GraphView graphView;

        public GoalHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_goals_grid, parent, false));

            nameTextView = (TextView) itemView.findViewById(R.id.layout_goal_name_label);
            typeTextView = (TextView) itemView.findViewById(R.id.layout_goal_type_label);
            valueTextView = (TextView) itemView.findViewById(R.id.layout_goal_value_label);
            graphView = (GraphView) itemView.findViewById(R.id.goal_grid_graph);
        }

        public void bind(Goal goal) {
            this.goal = goal;



            this.nameTextView.setText(this.goal.getName());
            this.typeTextView.setText(this.goal.getType());
            String valueText = String.valueOf(this.goal.getValue()) + " " + this.goal.getUnit();
            this.valueTextView.setText(valueText);



            int colorPrimaryVariant = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext());
            int colorOnPrimary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorOnPrimary, requireContext());
            int colorSecondary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorSecondary, requireContext());

            int range = 30; // TODO: get from settings in days

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
                        if ((((int) value) % 4) == 0) {
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

    private class GoalAdapter extends RecyclerView.Adapter<GoalHolder> {
        private final ArrayList<Goal> goals;

        public GoalAdapter(ArrayList<Goal> goals) {
            this.goals = goals;
        }

        @NonNull
        @Override
        public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new GoalHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
            Goal goal = this.goals.get(position);
            holder.bind(goal);
        }

        @Override
        public int getItemCount() {
            return goals.size();
        }
    }
}