package com.fitstir.fitstirapp.ui.goals.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentGoalsBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.goals.dialogs.CreateGoalDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GoalsFragment extends Fragment {

    private GoalsViewModel goalsViewModel;
    private FragmentGoalsBinding binding;
    private RecyclerView goalRecyclerView;
    private GoalAdapter goalAdapter;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel = new ViewModelProvider(requireActivity()).get(GoalsViewModel.class);

        binding = FragmentGoalsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Addition Text Here
        goalsViewModel.setGoals(new ArrayList<>());

        goalRecyclerView = binding.goalRecyclerView;
        goalRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        goalAdapter = new GoalAdapter(goalsViewModel.getGoals().getValue());
        goalRecyclerView.setAdapter(goalAdapter);

        ((SettingsViewModel) new ViewModelProvider(requireActivity()).get(SettingsViewModel.class)).setPreviousPage(0);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;

        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(authUser.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileData value = snapshot.getValue(UserProfileData.class);
                goalsViewModel.setThisUser(value);

                int tRange = 0;
                switch (value.getRangeID()) {
                    case 0:
                        tRange = 7;
                        break;
                    case 1:
                        tRange = 14;
                        break;
                    case 2:
                        tRange = 30;
                        break;
                    case 3:
                        tRange = 91;
                        break;
                    case 4:
                        tRange = 182;
                        break;
                    case 5:
                        tRange = 273;
                        break;
                    case 6:
                        tRange = 365;
                        break;
                    default:
                        tRange = 30;
                        break;
                }
                goalsViewModel.setGoalRange(tRange);




                DatabaseReference goalsRef = FirebaseDatabase.getInstance()
                        .getReference("GoalsData").child(authUser.getUid());
                goalsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();

                        goalsViewModel.getGoals().getValue().clear();
                        for (DataSnapshot child : children) {
                            Goal goal = child.getValue(Goal.class);
                            goalsViewModel.addGoal(goal);
                        }

                        goalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        FloatingActionButton createGoalButton = binding.createGoalButton;
        createGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGoalDialog.newInstance(
                        R.layout.dialog_generic_goal,
                        R.id.dialog_create_goal_accept_button,
                        R.id.dialog_create_goal_cancel_button,
                        requireContext(),
                        goalAdapter
                ).show(getParentFragmentManager(), "Create Goal");
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

    private class GoalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Goal goal;
        private final TextView nameTextView;
        private final TextView valueTextView;
        private final GraphView graphView;

        public GoalHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_goals_grid, parent, false));
            itemView.setOnClickListener(this);

            nameTextView = (TextView) itemView.findViewById(R.id.layout_goal_name_label);
            valueTextView = (TextView) itemView.findViewById(R.id.layout_goal_value_label);
            graphView = (GraphView) itemView.findViewById(R.id.goal_grid_graph);
        }

        public void bind(Goal goal) {
            this.goal = goal;



            this.nameTextView.setText(this.goal.getType().getSpinnerTitle());
            String valueText = String.valueOf(this.goal.getValue()) + " " + this.goal.getUnit();
            this.valueTextView.setText(valueText);



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
                            toReturn = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.ENGLISH);
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

        @Override
        public void onClick(View v) {
            goalsViewModel.setClickedGoal(goal);
            Navigation.findNavController(v).navigate(R.id.action_navigation_goals_to_navigation_view_goal);
        }
    }

    public class GoalAdapter extends RecyclerView.Adapter<GoalHolder> {
        private final ArrayList<Goal> goals;

        public GoalAdapter(ArrayList<Goal> goals) {
            this.goals = goals;
        }

        @NonNull
        @Override
        public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());

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