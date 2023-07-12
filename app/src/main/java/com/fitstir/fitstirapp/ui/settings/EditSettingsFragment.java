package com.fitstir.fitstirapp.ui.settings;

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
import com.fitstir.fitstirapp.ui.utility.Methods;

public class EditSettingsFragment extends Fragment {

    private FragmentEditSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentEditSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdSettingsEdit;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        Spinner themeSpinner = Methods.getSpinnerWithAdapter(getActivity(), root, R.id.themeID_spinner, root.getResources().getStringArray(R.array.theme_array));
        Spinner rangeSpinner = Methods.getSpinnerWithAdapter(getActivity(), root, R.id.rangeID_spinner, root.getResources().getStringArray(R.array.range_array));
        Spinner intervalSpinner = Methods.getSpinnerWithAdapter(getActivity(), root, R.id.intervalID_spinner, root.getResources().getStringArray(R.array.interval_array));
        Spinner unitSpinner = Methods.getSpinnerWithAdapter(getActivity(), root, R.id.unitsID_spinner, root.getResources().getStringArray(R.array.unit_array));

        themeSpinner.setSelection(SettingsViewModel.themeID);
        rangeSpinner.setSelection(SettingsViewModel.rangeID);
        intervalSpinner.setSelection(SettingsViewModel.intervalID);
        unitSpinner.setSelection(SettingsViewModel.unitID);

        ImageView themeIV = root.findViewById(R.id.theme_hint);
        themeIV.setTooltipText("This will change the theme of your entire application.");
        ImageView rangeIV = root.findViewById(R.id.range_hint);
        rangeIV.setTooltipText("This will change how far back in time your goals will show data for.");
        ImageView intervalIV = root.findViewById(R.id.interval_hint);
        intervalIV.setTooltipText("This will change the interval of date jumps for your goal's range.");
        ImageView unitsIV = root.findViewById(R.id.units_hint);
        unitsIV.setTooltipText("This will change the units in which everything is measured.");

        CardView saveButton = root.findViewById(R.id.savebutton_cardView_settings_edit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theme = themeSpinner.getSelectedItemPosition();
                int range = rangeSpinner.getSelectedItemPosition();
                int interval = intervalSpinner.getSelectedItemPosition();
                int unit = unitSpinner.getSelectedItemPosition();

                if (SettingsViewModel.themeID != theme) {
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
                    SettingsViewModel.rangeID = range;
                    SettingsViewModel.intervalID = interval;
                    SettingsViewModel.unitID = unit;

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

        // TODO: save to database

        binding = null;
    }
}