package com.fitstir.fitstirapp.ui.settings.fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentEditProfileBinding;
import com.fitstir.fitstirapp.ui.connect.UserProfileData;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;

public class EditProfileFragment extends Fragment implements IPickResult {

    private FragmentEditProfileBinding binding;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdProfileEdit;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        ImageView profileImage = binding.profileImageEdit;
        profileImage.setImageBitmap(settingsViewModel.getAvatar().getValue());

        EditText name = binding.textNameEdit;
        EditText age = binding.textAgeEdit;
        EditText feet = binding.textHeightFeetEdit;
        EditText inches = binding.textHeightInchesEdit;
        EditText weight = binding.textWeightEdit;
        EditText email = binding.textEmailEdit;

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        name.setText(settingsViewModel.getName().getValue());
        age.setText(String.valueOf(settingsViewModel.getAge().getValue()));
        feet.setText(String.valueOf(settingsViewModel.getHeightInFeet().getValue()));
        inches.setText(String.valueOf(settingsViewModel.getHeightInInches().getValue()));
        weight.setText(String.valueOf(settingsViewModel.getWeight().getValue()));
        email.setText(settingsViewModel.getEmail().getValue());

        CardView saveButton = binding.savebuttonCardViewProfileEdit;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
                assert warningIcon != null;
                warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());

                warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorSecondaryVariant, requireContext()));

                String t_name = name.getText().toString();
                String t_age = age.getText().toString();
                String t_feet = feet.getText().toString();
                String t_inches = inches.getText().toString();
                String t_weight = weight.getText().toString();
                String t_email = email.getText().toString();
                String uid = user.getUid();
                if (!t_name.isEmpty()) {
                    if (!t_age.isEmpty()) {
                        if (!t_feet.isEmpty()) {
                            if (!t_inches.isEmpty()) {
                                if (!t_weight.isEmpty()) {
                                    if (!t_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(t_email).matches()) {
                                        settingsViewModel.setName(t_name);
                                        settingsViewModel.setAge(Integer.parseInt(t_age));
                                        settingsViewModel.setHeightInFeet(Integer.parseInt(t_feet));
                                        settingsViewModel.setHeightInInches(Integer.parseInt(t_inches));
                                        settingsViewModel.setWeight(Integer.parseInt(t_weight));
                                        settingsViewModel.setEmail(t_email);

                                        Navigation.findNavController(root).navigate(R.id.action_navigation_edit_profile_to_navigation_profile);
                                    } else if (t_email.isEmpty()) {
                                        email.setError("Email cannot be empty. Please try again.", warningIcon);
                                    } else {
                                        email.setError("Email must be a valid email format. Please try again.", warningIcon);
                                    }
                                } else {
                                    weight.setError("Weight cannot be empty. Please try again.", warningIcon);
                                }
                            } else {
                                inches.setError("Height in inches cannot be empty. Please try again.", warningIcon);
                            }
                        } else {
                            feet.setError("Height in feet cannot be empty. Please try again.", warningIcon);
                        }
                    } else {
                        age.setError("Age cannot be empty. Please try again.", warningIcon);
                    }
                } else {
                    name.setError("Name cannot be empty. Please try again.", warningIcon);
                }
            }
        });

        CardView editButton = binding.editpicturebuttonCardViewEdit;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                if (r.getError() == null) {
                                    settingsViewModel.setAvatar(r.getBitmap());
                                    ImageView profileImage = binding.profileImageEdit;
                                    profileImage.setImageBitmap(settingsViewModel.getAvatar().getValue());

                                    //Add image to database storage
                                    filePath = Uri.fromFile(new File(r.getPath()));
                                    assert user != null;
                                    StorageReference photo = storageReference.child("images/"+user.getUid());
                                    UploadTask uploadTask = photo.putFile(filePath);
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(getContext(), "Image chosen", Toast.LENGTH_LONG).show();
                                            taskSnapshot.getMetadata();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        })
                        .show(getParentFragmentManager());
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
        user.setFullname(settingsViewModel.getName().getValue());
        user.setAge(settingsViewModel.getAge().getValue());
        user.setHeight_ft(settingsViewModel.getHeightInFeet().getValue());
        user.setHeight_in(settingsViewModel.getHeightInInches().getValue());
        user.set_Weight(settingsViewModel.getWeight().getValue());
        user.setEmail(settingsViewModel.getEmail().getValue());
        settingsViewModel.setThisUser(user);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(authUser.getUid());
        userReference.setValue(settingsViewModel.getThisUser().getValue());

        binding = null;
    }

    @Override
    public void onPickResult(PickResult r) {
        return;
    }
}