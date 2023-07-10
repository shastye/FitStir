package com.fitstir.fitstirapp.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsBinding;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Objects;
import java.util.Set;
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
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        TextView theme = root.findViewById(R.id.themeID);
        TextView range = root.findViewById(R.id.rangeID);
        TextView interval = root.findViewById(R.id.intervalID);
        TextView unit = root.findViewById(R.id.unitID);

        theme.setText(root.getResources().getStringArray(R.array.theme_array)[SettingsViewModel.themeID]);
        range.setText(root.getResources().getStringArray(R.array.range_array)[SettingsViewModel.rangeID]);
        interval.setText(root.getResources().getStringArray(R.array.interval_array)[SettingsViewModel.intervalID]);
        unit.setText(root.getResources().getStringArray(R.array.unit_array)[SettingsViewModel.unitID]);

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
                        R.id.dialog_generic_message,
                        "You are about to delete all saved application data.\nThis cannot be undone."
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
                        R.id.dialog_generic_message,
                        "You are about to delete your FitStir account.\nThis cannot be undone."
                ).show(getParentFragmentManager(), "Reset Application");
            }
        });

        Button hardResetButton = root.findViewById(R.id.hard_reset_button);
        hardResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = Methods.clearApplicationData(getActivity());

                if (success) {
                    success = Methods.deleteFromDatabase();

                    if (success) {
                        success = Methods.deleteUser();

                        if (success) {

                        } else {
                            Toast.makeText(getContext(), "Account Data **NOT** Deleted", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Database Data **NOT** Deleted", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Application Data **NOT** Deleted", Toast.LENGTH_LONG).show();
                }
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