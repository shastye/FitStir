package com.fitstir.fitstirapp.ui.health.weightloss.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentWeightLossBinding;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalDataPair;
import com.fitstir.fitstirapp.ui.health.weightloss.dialogs.ChangeWeightGoalDialog;
import com.fitstir.fitstirapp.ui.health.weightloss.dialogs.DeleteConformationDialog;
import com.fitstir.fitstirapp.ui.health.weightloss.WeightLossViewModel;
import com.fitstir.fitstirapp.ui.health.weightloss.dialogs.UpdateWeightDialog;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeightLossFragment extends Fragment {

    private FragmentWeightLossBinding binding;
    private WeightLossViewModel weightLossViewModel;
    private ConstraintLayout loadingScreen, sectionScreen;
    private RecyclerView dataRecyclerView;
    private TextView goalWeightTextView, currWeightTextView;
    private FloatingActionButton addDataButton;
    private ImageButton changeGoalButton;

    private boolean isLoading;
    private DecimalFormat decimalFormat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        weightLossViewModel = new ViewModelProvider(requireActivity()).get(WeightLossViewModel.class);

        binding = FragmentWeightLossBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        decimalFormat = new DecimalFormat("####.##");

        sectionScreen = binding.weightLossScreen;
        sectionScreen.setVisibility(View.VISIBLE);

        loadingScreen = root.findViewById(R.id.generic_loading_screen);
        loadingScreen.setVisibility(View.GONE);
        isLoading = false;

        dataRecyclerView = binding.dataRecyclerView;
        dataRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        goalWeightTextView = binding.weightLossGoalAmount;
        currWeightTextView = binding.weightLossWeightAmount;

        changeGoalButton = binding.weightLossGoalMoreButton;
        changeGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeWeightGoalDialog dialog = ChangeWeightGoalDialog.newInstance(
                        R.layout.dialog_change_calorie_goal,
                        R.id.dialog_calgoal_accept_button,
                        R.id.dialog_calgoal_cancel_button,
                        weightLossViewModel.getThisUser().getValue().get_Weight()
                );
                dialog.showNow(getChildFragmentManager(), "Change Weight Goal");
            }
        });


        addDataButton = binding.weightLossAddButton;
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateWeightDialog dialog = UpdateWeightDialog.newInstance(
                        R.layout.dialog_change_calorie_goal,
                        R.id.dialog_calgoal_accept_button,
                        R.id.dialog_calgoal_cancel_button,
                        weightLossViewModel.getThisUser().getValue().get_Weight()
                );
                dialog.showNow(getChildFragmentManager(), "Update Weight Data");
            }
        });



        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        DatabaseReference thisUser = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(authUser.getUid());

        toggleLoadingScreen();
        thisUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileData value = snapshot.getValue(UserProfileData.class);
                weightLossViewModel.setThisUser(value);
                weightLossViewModel.setWeightGoal(value.getGoal_weight());



                DatabaseReference listRef = FirebaseDatabase.getInstance()
                        .getReference("GoalsData").child(authUser.getUid());
                listRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        ArrayList<GoalDataPair> data = new ArrayList<>();

                        Goal weightLossGoal = null;
                        for (DataSnapshot child : children) {
                            Goal goal = child.getValue(Goal.class);

                            if (goal.getType().equals(GoalTypes.WEIGHT_CHANGE)) {
                                weightLossGoal = goal;
                                weightLossViewModel.setGoalID(goal.getID());
                            }
                        }

                        if (weightLossGoal != null && !weightLossGoal.getData().isEmpty()){
                            data = weightLossGoal.getData();

                            if (data == null) {
                                data = new ArrayList<>();
                            }
                        }


                        weightLossViewModel.setWeightData(data);

                        updateUI();
                        toggleLoadingScreen();
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


        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onDismiss(DialogInterface dialog) {
        updateUI();
    }

    private void toggleLoadingScreen() {
        if (isLoading) {
            sectionScreen.setVisibility(View.VISIBLE);
            loadingScreen.setVisibility(View.GONE);

            isLoading = false;
        } else {
            sectionScreen.setVisibility(View.GONE);
            loadingScreen.setVisibility(View.VISIBLE);

            isLoading = true;
        }
    }

    private void updateUI() {
        int goal = weightLossViewModel.getWeightGoal().getValue();
        goalWeightTextView.setText(String.valueOf(goal));

        int index = weightLossViewModel.getWeightData().getValue().size() - 1 ;
        double curr = weightLossViewModel.getWeightData().getValue().get(index).second;
        currWeightTextView.setText(decimalFormat.format(curr));

        ArrayList<GoalDataPair> dataArray = weightLossViewModel.getWeightData().getValue();
        DataAdapter dataAdapter = new WeightLossFragment.DataAdapter(dataArray);
        dataRecyclerView.setAdapter(dataAdapter);
    }

    private class DataHolder extends RecyclerView.ViewHolder {
        private GoalDataPair data;

        private TextView dateTextView, weightTextView, unitsTextView;
        private ImageButton deleteButton;

        public DataHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_weight_loss_data_section, parent, false));

            dateTextView = itemView.findViewById(R.id.layout_date);
            weightTextView = itemView.findViewById(R.id.layout_weight);
            unitsTextView = itemView.findViewById(R.id.layout_units);

            deleteButton = itemView.findViewById(R.id.layout_delete_button);
            if (weightLossViewModel.getWeightData().getValue().size() == 1) {
                deleteButton.setVisibility(View.GONE);
            }
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteConformationDialog dialog = DeleteConformationDialog.newInstance(
                            R.layout.dialog_generic_alert,
                            R.id.dialog_generic_accept_button,
                            R.id.dialog_generic_cancel_button,
                            data.getDateAsString(),
                            data
                    );
                    dialog.showNow(getChildFragmentManager(), "Delete Weight Data");
                }
            });
        }

        public void bind(GoalDataPair data) {
            this.data = data;

            UserProfileData user = weightLossViewModel.getThisUser().getValue();
            String unitString;
            if (user.getUnitID() == 0) {
                unitString = "lbs";
            } else {
                unitString = "kg";
            }
            unitsTextView.setText(unitString);

            String dateString = data.getDateAsString();
            dateTextView.setText(dateString);

            String weightString = decimalFormat.format(data.second);
            weightTextView.setText(weightString);
        }
    }

    private class DataAdapter extends RecyclerView.Adapter<DataHolder> {
        private ArrayList<GoalDataPair> dataArray;

        public DataAdapter(ArrayList<GoalDataPair> dataArray) {
            // Flip array to show newest first
            ArrayList<GoalDataPair> flippedArray = new ArrayList<>();

            for (int i = (dataArray.size() - 1); i >= 0; i--) {
                flippedArray.add(dataArray.get(i));
                // dataArray.get(i) == flippedArray.get(dataArray.size() - 1 - i)
            }

            this.dataArray = flippedArray;
        }

        @NonNull
        @Override
        public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
            return new DataHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DataHolder holder, int position) {
            GoalDataPair dataPair = this.dataArray.get(position);
            holder.bind(dataPair);
        }

        @Override
        public int getItemCount() {
            return this.dataArray.size();
        }
    }
}