package com.fitstir.fitstirapp.ui.health.weightloss.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.DialogChangeCalorieGoalBinding;
import com.fitstir.fitstirapp.ui.goals.GoalDataPair;
import com.fitstir.fitstirapp.ui.health.weightloss.WeightLossViewModel;
import com.fitstir.fitstirapp.ui.health.weightloss.fragments.WeightLossFragment;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.IBasicDialog;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class UpdateWeightDialog extends IBasicDialog {
    private int currWeight;
    private WeightLossViewModel weightLossViewModel;
    private EditText changedWeight;

    public UpdateWeightDialog() { }

    public static UpdateWeightDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, int currWeight) {
        UpdateWeightDialog frag = new UpdateWeightDialog();

        Bundle args = new Bundle();
        args.putInt("currWeight", currWeight);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        weightLossViewModel = new ViewModelProvider(requireActivity()).get(WeightLossViewModel.class);
        DialogChangeCalorieGoalBinding binding = DialogChangeCalorieGoalBinding.bind(requireView());

        UserProfileData user = weightLossViewModel.getThisUser().getValue();

        assert getArguments() != null;
        currWeight = getArguments().getInt("currWeight");

        assert getView() != null;

        TextView title = binding.dialogCalgoalTitle;
        title.setText("Update Current Weight:");

        String tUnit = "pounds";
        if (user.getUnitID() == 1) {
            tUnit = "kilograms";
        }
        String tCurrWeight = currWeight + " " + tUnit;
        String tP1 = "Your current weight is";
        binding.dialogCalgoalMessageP1.setText(tP1);

        String tP2 = currWeight + " " + tUnit;
        binding.dialogCalgoalMessageP2.setText(tP2);

        changedWeight = binding.dialogCalgoalGoal;
        changedWeight.setHint(String.valueOf(currWeight));

        binding.dialogCalgoalUnit.setText(tUnit);
    }

    @Override
    public void onAccept() {
        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);

        UserProfileData user = weightLossViewModel.getThisUser().getValue();

        int newWeight = Integer.parseInt(changedWeight.getText().toString());
        if (newWeight != currWeight) {

            // Determine if movement in correct direction //
                                                          //
            int d1 = currWeight - newWeight;              //
            // if d1 < 0; curr < newWeight                //
            // elseif d1 > 0; newWeight < curr            //
            int d2 = user.getGoal_weight() - newWeight;   //
            // if d2 < 0; goal < newWeight                //
            // elseif d2 > 0; newWeight < goal            //
                                                          //
            boolean c1 = d2 > 0 && d1 < 0;                //
            boolean c2 = d2 < 0 && d1 > 0;                //
            boolean c3 = d2 == 0;                         //
            // Correct direction if:                      //
            // curr < newWeight < goal                    //
            //          OR                                //
            // goal < newWeight < curr                    //
            //          OR                                //
            //    goal == newWeight                       //
                                                          //
            // ////////////////////////////////////////// //

            user.set_Weight(newWeight);

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            assert authUser != null;

            user.addWeightData(newWeight);
            user.set_Weight(newWeight);

            weightLossViewModel.setThisUser(user);

            int size = weightLossViewModel.getWeightData().getValue().size();
            GoalDataPair lastDateData = weightLossViewModel.getWeightData().getValue().get(size - 1);

            Calendar today = Calendar.getInstance();
            Calendar dataDate = Calendar.getInstance();
            dataDate.setTime(lastDateData.first);

            int tDOY = today.get(Calendar.DAY_OF_YEAR);
            int dDOY = dataDate.get(Calendar.DAY_OF_YEAR);

            if (tDOY != dDOY) {
                weightLossViewModel.getWeightData().getValue().add(new GoalDataPair(Calendar.getInstance().getTime(), newWeight));
            } else {
                weightLossViewModel.getWeightData().getValue().get(size - 1).second = newWeight;
            }


            if (c1 || c2 || c3) {
                LayoutInflater inflater = LayoutInflater.from(requireActivity());
                View popUpView = inflater.inflate(R.layout.dialog_generic_alert, null);
                PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                TextView congratsMessage = popUpView.findViewById(R.id.dialog_generic_message);
                int colorSecondaryVariant = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorSecondaryVariant, requireContext());
                congratsMessage.setTextColor(colorSecondaryVariant);
                congratsMessage.setText("CONGRATULATIONS!!!");

                AppCompatButton cancelButton = popUpView.findViewById(R.id.dialog_generic_cancel_button);
                cancelButton.setVisibility(View.GONE);

                AppCompatButton acceptButton = popUpView.findViewById(R.id.dialog_generic_accept_button);
                acceptButton.setText("THANKS!");
                acceptButton.setX(acceptButton.getX() - 25);
                acceptButton.setY(acceptButton.getY() - 25);
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                TextView continueMessage = popUpView.findViewById(R.id.dialog_generic_continue);
                continueMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                continueMessage.setPaddingRelative(0,0,0,180);
                continueMessage.setTextColor(0xFF000000);
                if (c1 || c2) {
                    continueMessage.setText("You made it one step\ncloser to your goal!");
                } else if (c3) {
                    continueMessage.setText("You made it!!\nYou reached your goal!!!");
                }
                popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0,0);
            }
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment parent = getParentFragment();
        if (parent instanceof WeightLossFragment) {
            ((WeightLossFragment) parent).onDismiss(dialog);
        }
    }
}
