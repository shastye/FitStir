package com.fitstir.fitstirapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentEditProfileBinding;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileFragment extends Fragment implements IPickResult {

    private FragmentEditProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdProfileEdit;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        ImageView profileImage = root.findViewById(R.id.profile_image_edit);
        profileImage.setImageBitmap(SettingsViewModel.avatar);

        EditText name = root.findViewById(R.id.text_name_edit);
        EditText age = root.findViewById(R.id.text_age_edit);
        EditText feet = root.findViewById(R.id.text_height_feet_edit);
        EditText inches = root.findViewById(R.id.text_height_inches_edit);
        EditText weight = root.findViewById(R.id.text_weight_edit);
        EditText email = root.findViewById(R.id.text_email_edit);

        name.setText(SettingsViewModel.name);
        age.setText(String.valueOf(SettingsViewModel.age));
        feet.setText(String.valueOf(SettingsViewModel.height_feet));
        inches.setText(String.valueOf(SettingsViewModel.height_inches));
        weight.setText(String.valueOf(SettingsViewModel.weight));
        email.setText(SettingsViewModel.email);

        CardView saveButton = root.findViewById(R.id.savebutton_cardView_edit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (true) {
                    Vector<EditText> editTexts = new Vector<>();

                    editTexts.add(root.findViewById(R.id.text_name_edit));
                    editTexts.add(root.findViewById(R.id.text_age_edit));
                    editTexts.add(root.findViewById(R.id.text_height_feet_edit));
                    editTexts.add(root.findViewById(R.id.text_height_inches_edit));
                    editTexts.add(root.findViewById(R.id.text_weight_edit));
                    editTexts.add(root.findViewById(R.id.text_email_edit));

                    if (!isEmpty(editTexts)) {
                        SettingsViewModel.name = editTexts.get(0).getText().toString();
                        SettingsViewModel.age = Integer.parseInt(editTexts.get(1).getText().toString());
                        SettingsViewModel.height_feet = Integer.parseInt(editTexts.get(2).getText().toString());
                        SettingsViewModel.height_inches = Integer.parseInt(editTexts.get(3).getText().toString());
                        SettingsViewModel.weight = Integer.parseInt(editTexts.get(4).getText().toString());
                        String tEmail = editTexts.get(5).getText().toString();

                        Pattern pattern = Pattern.compile(getString(R.string.email_validation));
                        Matcher mail = pattern.matcher(tEmail);

                        if (mail.find()) {
                            SettingsViewModel.email = tEmail;

                            break;
                        } else {
                            Toast.makeText(getContext(), "Email address isn't a valid format.", Toast.LENGTH_LONG).show();
                        }
                    }

                    Toast.makeText(getContext(), "Please fill in all of the fields.", Toast.LENGTH_LONG).show();
                }

                Navigation.findNavController(root).navigate(R.id.action_navigation_edit_profile_to_navigation_profile);
            }
        });

        CardView editButton = root.findViewById(R.id.editpicturebutton_cardView_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                if (r.getError() == null) {
                                    SettingsViewModel.avatar = r.getBitmap();
                                    ImageView profileImage = root.findViewById(R.id.profile_image_edit);
                                    profileImage.setImageBitmap(SettingsViewModel.avatar);

                                    Toast.makeText(getContext(), "Image chosen", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .show(getParentFragmentManager());
            }
        });

        // End

        return root;
    }

    private boolean isEmpty(Vector<EditText> _vet) {
        boolean isEmpty = false;

        for (EditText et : _vet) {
            isEmpty = (et.getText().toString().trim().length() == 0);

            if (isEmpty) {
                break;
            }
        }

        return isEmpty;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // TODO: save to database

        binding = null;
    }

    @Override
    public void onPickResult(PickResult r) {
        return;
    }
}