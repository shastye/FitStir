package com.fitstir.fitstirapp.ui.settings.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsContactBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsContactFragment extends Fragment {

    private FragmentSettingsContactBinding binding;
    private String uid;
    private UserProfileData user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        settingsViewModel.setStillInSettings(true);
        settingsViewModel.setCameFromFragment(this);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = settingsViewModel.getThisUser().getValue();

        binding.userId.setText(uid);

        String[] sectionArray = getResources().getStringArray(R.array.section_list);
        Spinner sectionSpinner = Methods.getSpinnerWithAdapter(requireActivity(), root, binding.sectionSpinner.getId(), sectionArray);

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messageText.getText().toString();

                if (!message.isEmpty()) {
                    String subject = "Issue with ";
                    subject += sectionSpinner.getSelectedItem().toString() + ". ";
                    subject += binding.subjectText.getText().toString();

                    if (!message.endsWith(". ")) {
                        if (message.endsWith(" ")) {
                            message = message.substring(0, message.length() - 2);
                            message += ". ";
                        } else if (message.endsWith(".")) {
                            message += " ";
                        }
                    }
                    String ids = binding.idText.getText().toString();
                    message += "\n\n\nRelevant IDs: ";
                    if (ids.isEmpty()) {
                        message += "None";
                    } else {
                        ids = ids.replace("\n", ", ");
                        message += ids;
                    }

                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"fitstir.student@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                    emailIntent.setType("message/rfc822");

                    try {
                        startActivity(Intent.createChooser(emailIntent,
                                "Send email using..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(),
                                "No email clients installed.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
                    assert warningIcon != null;
                    warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());

                    warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext()));

                    binding.messageText.setError("You must provide a message.", warningIcon);
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

    public void replaceFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.settings_container_fragment, fragment, "Settings")
                .commit();
    }
}
