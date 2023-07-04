package com.fitstir.fitstirapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        CardView saveButton = root.findViewById(R.id.savebutton_cardView_settings_edit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: edit the stuff

                int theme = themeSpinner.getSelectedItemPosition();
                int range = rangeSpinner.getSelectedItemPosition();
                int interval = intervalSpinner.getSelectedItemPosition();
                int unit = unitSpinner.getSelectedItemPosition();

                SettingsViewModel.themeID = theme;
                SettingsViewModel.rangeID = range;
                SettingsViewModel.intervalID = interval;
                SettingsViewModel.unitID = unit;

                Navigation.findNavController(root).navigate(R.id.action_navigation_edit_settings_to_navigation_settings);
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