package com.fitstir.fitstirapp.ui.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.settings.dialogs.DeactivateAccountDialog;
import com.fitstir.fitstirapp.ui.settings.dialogs.HardResetDialog;
import com.fitstir.fitstirapp.ui.settings.dialogs.ResetApplicationDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private Timer t = new Timer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
                if (auth == null) {
                    Methods.navigateToLogInActivity(getContext());
                }
            }
        }, 0, 1000); // run immediately, then every 1 second
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        TextView theme = root.findViewById(R.id.themeID);
        TextView range = root.findViewById(R.id.rangeID);
        TextView interval = root.findViewById(R.id.intervalID);
        TextView unit = root.findViewById(R.id.unitID);

        theme.setText(root.getResources().getStringArray(R.array.theme_array)[settingsViewModel.getThemeID().getValue()]);
        range.setText(root.getResources().getStringArray(R.array.range_array)[settingsViewModel.getRangeID().getValue()]);
        interval.setText(root.getResources().getStringArray(R.array.interval_array)[settingsViewModel.getIntervalID().getValue()]);
        unit.setText(root.getResources().getStringArray(R.array.unit_array)[settingsViewModel.getUnitID().getValue()]);

        CardView editButton = root.findViewById(R.id.editbutton_cardView_settings);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_settings_to_navigation_edit_settings);
            }
        });

        Button resetButton = root.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetApplicationDialog.newInstance(
                        R.layout.dialog_generic_alert,
                        R.id.dialog_generic_accept_button,
                        R.id.dialog_generic_cancel_button,
                        "This action cannot be undone."
                ).show(getParentFragmentManager(), "Reset Application");
            }
        });

        Button deactivateButton = root.findViewById(R.id.deactivate_button);
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeactivateAccountDialog.newInstance(
                        R.layout.dialog_generic_alert,
                        R.id.dialog_generic_accept_button,
                        R.id.dialog_generic_cancel_button,
                        "This action cannot be undone."
                ).show(getParentFragmentManager(), "Reset Application");
            }
        });

        Button hardResetButton = root.findViewById(R.id.hard_reset_button);
        hardResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HardResetDialog.newInstance(
                        R.layout.dialog_generic_alert,
                        R.id.dialog_generic_accept_button,
                        R.id.dialog_generic_cancel_button,
                        "This action cannot be undone."
                ).show(getParentFragmentManager(), "Reset Application");
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