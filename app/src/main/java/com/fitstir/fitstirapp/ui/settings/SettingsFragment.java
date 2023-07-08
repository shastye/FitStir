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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.Timer;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

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
                boolean success = clearApplicationData();

                if (success) {
                    // show dialog saying everything deleted
                    Toast.makeText(getContext(), "Application Data Deleted", Toast.LENGTH_LONG).show();
                } else {
                    // show dialog saying error
                    Toast.makeText(getContext(), "Application Data **NOT** Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*Button deactivateButton = root.findViewById(R.id.deactivate_button);
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDatabase();
                deleteUser();
            }
        });*/

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private boolean clearApplicationData() {
        boolean success = true;

        File cache = getActivity().getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    success = deleteDir(new File(appDir, s));

                    if (!success) {
                        return false;
                    }
                }
            }
        }

        return success;
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    private void deleteFromDatabase() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("deleteFromDatabase Error", "An error occurred: " + error.getMessage());
            }
        };
        databaseReference.addValueEventListener(listener);
    }

    private void deleteUser() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            OnCompleteListener<Void> listener = new  OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            };
            user.delete().addOnCompleteListener(listener);
        }
    }

    private void navigateToLogInActivity() {
        Context context = getContext();
        Intent intent = getContext()
                .getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
}