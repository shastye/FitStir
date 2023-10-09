package com.fitstir.fitstirapp.ui.settings.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsAccountBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.settings.dialogs.DeactivateAccountDialog;
import com.fitstir.fitstirapp.ui.settings.dialogs.HardResetDialog;
import com.fitstir.fitstirapp.ui.settings.dialogs.ResetApplicationDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.IOnBackPressed;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class SettingsAccountFragment extends Fragment {

    private FragmentSettingsAccountBinding binding;
    private String uid;
    private UserProfileData user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        settingsViewModel.setStillInSettings(true);
        settingsViewModel.setCameFromFragment(this);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = settingsViewModel.getThisUser().getValue();

        binding.userId.setText(uid);

        binding.passwordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popUpView = inflater.inflate(R.layout.popup_change_password, null);
                PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
                assert warningIcon != null;
                warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());

                warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext()));

                Button submit = popUpView.findViewById(R.id.submit_password_change);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText oldPassword = popUpView.findViewById(R.id.old_password);
                        String oldPassText = oldPassword.getText().toString();

                        EditText newPassword = popUpView.findViewById(R.id.new_password);
                        String newPassText = newPassword.getText().toString();

                        EditText confirmPassword = popUpView.findViewById(R.id.confirm_password);
                        String confirmPassText = confirmPassword.getText().toString();

                        if (oldPassText.equals(user.getPassword())) {
                            if (newPassText.equals(confirmPassText)) {
                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), user.getPassword());
                                currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            currentUser.updatePassword(newPassText).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(!task.isSuccessful()){
                                                        Toast.makeText(getContext(), "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        user.setPassword(newPassText);
                                                        settingsViewModel.setThisUser(user);

                                                        FirebaseDatabase.getInstance()
                                                                        .getReference("Users")
                                                                        .child(uid)
                                                                        .child("password")
                                                                        .setValue(newPassText);

                                                        Toast.makeText(getContext(), "Password Successfully Modified.", Toast.LENGTH_LONG).show();
                                                        popupWindow.dismiss();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getContext(), "Authentication Failed. If issue persists, log out and reset password.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                confirmPassword.setError("Passwords do not match.", warningIcon);
                            }
                        } else {
                            oldPassword.setError("Password incorrect.", warningIcon);
                        }
                    }
                });

                Button cancel = popUpView.findViewById(R.id.cancel_password_change);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER, 0,0);
            }
        });

        binding.deleteView.setOnClickListener(new View.OnClickListener() {
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

        binding.deactivateView.setOnClickListener(new View.OnClickListener() {
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

        binding.hardResetView.setOnClickListener(new View.OnClickListener() {
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

    public void replaceFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.settings_container_fragment, fragment, "Settings")
                .commit();
    }
}
