package com.fitstir.fitstirapp.ui.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentEditSettingsBinding;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.settings.dialogs.ChangeThemeDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditSettingsFragment extends Fragment {

    private FragmentEditSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentEditSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Add additions here

        Spinner themeSpinner = Methods.getSpinnerWithAdapter(requireActivity(), root, R.id.themeID_spinner, root.getResources().getStringArray(R.array.theme_array));
        Spinner rangeSpinner = Methods.getSpinnerWithAdapter(requireActivity(), root, R.id.rangeID_spinner, root.getResources().getStringArray(R.array.range_array));
        Spinner intervalSpinner = Methods.getSpinnerWithAdapter(requireActivity(), root, R.id.intervalID_spinner, root.getResources().getStringArray(R.array.interval_array));
        Spinner unitSpinner = Methods.getSpinnerWithAdapter(requireActivity(), root, R.id.unitsID_spinner, root.getResources().getStringArray(R.array.unit_array));

        themeSpinner.setSelection(settingsViewModel.getThemeID().getValue());
        rangeSpinner.setSelection(settingsViewModel.getRangeID().getValue());
        intervalSpinner.setSelection(settingsViewModel.getIntervalID().getValue());
        unitSpinner.setSelection(settingsViewModel.getUnitID().getValue());

        ImageView themeIV = binding.themeHint;
        themeIV.setTooltipText("This will change the theme of your entire application.");
        ImageView rangeIV = binding.rangeHint;
        rangeIV.setTooltipText("This will change how far back in time your goals will show data for.");
        ImageView intervalIV = binding.intervalHint;
        intervalIV.setTooltipText("This will change the interval of date jumps for your goal's range.");
        ImageView unitsIV = binding.unitsHint;
        unitsIV.setTooltipText("This will change the units in which everything is measured.");

        CardView saveButton = binding.savebuttonCardViewSettingsEdit;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theme = themeSpinner.getSelectedItemPosition();
                int range = rangeSpinner.getSelectedItemPosition();
                int interval = intervalSpinner.getSelectedItemPosition();
                int unit = unitSpinner.getSelectedItemPosition();

                if (settingsViewModel.getThemeID().getValue() != theme) {
                    ChangeThemeDialog.newInstance(
                            R.layout.dialog_change_theme,
                            R.id.dialog_accept_button,
                            R.id.dialog_cancel_button,
                            theme,
                            range,
                            interval,
                            unit,
                            root
                    ).show(getParentFragmentManager(), "Change Theme");
                } else {
                    settingsViewModel.setRangeID(range);
                    settingsViewModel.setIntervalID(interval);
                    settingsViewModel.setUnitID(unit);

                    Navigation.findNavController(root).navigate(R.id.action_navigation_edit_settings_to_navigation_settings);
                }
            }
        });

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        UserProfileData user = settingsViewModel.getThisUser().getValue();
        user.setThemeID(settingsViewModel.getThemeID().getValue());
        user.setIntervalID(settingsViewModel.getIntervalID().getValue());
        user.setRangeID(settingsViewModel.getRangeID().getValue());
        user.setUnitID(settingsViewModel.getUnitID().getValue());
        settingsViewModel.setThisUser(user);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(authUser.getUid());
        userReference.setValue(settingsViewModel.getThisUser().getValue());

        binding = null;
    }
}