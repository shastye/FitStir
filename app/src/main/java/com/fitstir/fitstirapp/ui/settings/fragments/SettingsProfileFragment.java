package com.fitstir.fitstirapp.ui.settings.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsProfileBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.settings.customviews.SettingsTopicView;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.classes.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

public class SettingsProfileFragment extends Fragment {

    private FragmentSettingsProfileBinding binding;
    private LayoutInflater inflater;
    private String uid;
    private Users user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        this.inflater = inflater;
        settingsViewModel.setStillInSettings(true);
        settingsViewModel.setCameFromFragment(this);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = settingsViewModel.getThisUser().getValue();

        binding.userId.setText(uid);

        if (settingsViewModel.getAvatar().getValue() != null) {
            binding.profileImage.setImageBitmap(settingsViewModel.getAvatar().getValue());
        }
        binding.editPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                if (r.getError() == null) {
                                    settingsViewModel.setAvatar(r.getBitmap());
                                    binding.profileImage.setImageBitmap(settingsViewModel.getAvatar().getValue());

                                    //Add image to database storage
                                    Uri filePath = Uri.fromFile(new File(r.getPath()));
                                    assert user != null;
                                    StorageReference photo = FirebaseStorage.getInstance().getReference().child("images/"+uid);
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

        String unitWeightText = "";
        String unitHeightText1 = "";
        String unitHeightText2 = "";
        switch (user.getUnitID()) {
            case 0:
                unitWeightText += " pounds";
                unitHeightText1 += " ft. ";
                unitHeightText2 += " in.";
                break;
            case 1:
                unitWeightText += " kilograms";
                unitHeightText1 += " m. ";
                unitHeightText2 += " cm.";
                break;
        }

        binding.nameView.setTitle(user.getFullname());
        binding.nameView.setOnClickListener(getListener(user.getFullname(), ""));

        String ageText = user.getAge().toString() + " years old";
        binding.ageView.setTitle(ageText);
        binding.ageView.setOnClickListener(getListener(user.getAge().toString(), " years old"));


        String sexText = user.getSex().substring(0, 1).toUpperCase() + user.getSex().substring(1);
        SwitchCompat chooser = binding.sexView.getSwitch();
        chooser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String reference = binding.sexView.getReference();
                DatabaseReference toSet = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(uid)
                        .child(reference);

                if (isChecked) {
                    toSet.setValue("male");
                } else {
                    toSet.setValue("female");
                }
            }
        });
        if (user.getSex().equals("female")) {
            chooser.setChecked(false);
        }   else {
                chooser.setChecked(true);
        }


        String heightText = user.getHeight_ft() + unitHeightText1 + user.getHeight_in() + unitHeightText2;
        binding.heightView.setTitle(heightText);
        binding.heightView.setOnClickListener(getListener(user.getHeight_ft().toString(), unitHeightText1, user.getHeight_in().toString(), unitHeightText2));


        String weightText = user.getWeight().toString() + unitWeightText;
        binding.weightView.setTitle(weightText);
        binding.weightView.setOnClickListener(getListener(user.getWeight().toString(), unitWeightText));


        binding.emailView.setTitle(user.getEmail());
        binding.emailView.setOnClickListener(getListener(user.getEmail(), ""));


        String weightGoalText = user.getGoal_weight().toString() + unitWeightText;
        binding.weightGoalView.setTitle(weightGoalText);
        binding.weightGoalView.setOnClickListener(getListener(user.getGoal_weight().toString(), unitWeightText));

        // End

        return root;
    }

    public View.OnClickListener getListener(String value, String unit) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsTopicView view = (SettingsTopicView) v;
                openSinglePopup(view, view.getDescription(), value, unit, view.getReference());
            }
        };
    }
    public View.OnClickListener getListener(String value1, String unit1, String value2, String unit2) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsTopicView view = (SettingsTopicView) v;
                openDoublePopup(view, view.getDescription(), value1, unit1, value2, unit2, "height_ft", "height_in");
            }
        };
    }

    public void openSinglePopup(View view, String changeYour_what_, String currentValue, String unitValue, String reference) {
        View popUpView = inflater.inflate(R.layout.popup_generic_single_edittext, null);
        PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


        TextView title = popUpView.findViewById(R.id.dialog_title);
        String titleText = "Change Your " + changeYour_what_;
        title.setText(titleText);

        EditText something = popUpView.findViewById(R.id.dialog_changed);
        something.setText(currentValue);

        TextView units = popUpView.findViewById(R.id.dialog_unit);
        units.setText(unitValue);


        AppCompatButton cancelButton = popUpView.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        AppCompatButton acceptButton = popUpView.findViewById(R.id.dialog_accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = something.getText().toString().trim();
                if (!currentValue.trim().equals(newValue)) {
                    DatabaseReference toSet = FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(uid)
                            .child(reference);

                    if (reference.equals("height")) {
                        // do nothing
                    } else if (reference.equals("age")) {
                        try {
                            int newValueInt = Integer.parseInt(newValue);

                            ((SettingsTopicView) view).setTitle(newValue);
                            toSet.setValue(newValueInt);

                            popupWindow.dismiss();
                        } catch (Exception e) {
                            something.setError("Must be a valid number.");
                        }
                    } else if (reference.equals("weight")) {
                        try {
                            int newValueInt = Integer.parseInt(newValue);

                            ((SettingsTopicView) view).setTitle(newValue);
                            user.addWeightData(newValueInt);

                            popupWindow.dismiss();
                        } catch (Exception e) {
                            something.setError("Must be a valid number.");
                        }
                    } else if (reference.equals("goal_weight")) {
                        try {
                            int newValueInt = Integer.parseInt(newValue);

                            ((SettingsTopicView) view).setTitle(newValue);
                            user.updateWeightGoal(newValueInt);

                            popupWindow.dismiss();
                        } catch (Exception e) {
                            something.setError("Must be a valid number.");
                        }
                    } else if (reference.equals("email")) {
                        if (!newValue.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(newValue).matches()) {
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(currentValue, user.getPassword())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task1) {
                                            if (task1.isSuccessful()) {
                                                FirebaseAuth.getInstance().getCurrentUser().updateEmail(newValue)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task2) {
                                                                if (task2.isSuccessful()) {
                                                                    ((SettingsTopicView) view).setTitle(newValue);
                                                                    toSet.setValue(newValue);
                                                                } else {
                                                                    Toast.makeText(getActivity(), "An error occurred. Email not updated.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(getActivity(), "Please log out and back in to perform this action.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            popupWindow.dismiss();
                        }
                    } else {
                        ((SettingsTopicView) view).setTitle(newValue);
                        toSet.setValue(newValue);
                        popupWindow.dismiss();
                    }
                }
            }
        });

        popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
    }
    public void openDoublePopup(View view, String changeYour_what_, String currentValue1, String unitValue1, String currentValue2, String unitValue2, String reference1, String reference2) {
        View popUpView = inflater.inflate(R.layout.popup_generic_double_edittext, null);
        PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


        TextView title = popUpView.findViewById(R.id.dialog_title);
        String titleText = "Change Your " + changeYour_what_;
        title.setText(titleText);

        EditText something1 = popUpView.findViewById(R.id.dialog_changed1);
        something1.setText(currentValue1);

        TextView units1 = popUpView.findViewById(R.id.dialog_unit1);
        units1.setText(unitValue1);

        EditText something2 = popUpView.findViewById(R.id.dialog_changed2);
        something2.setText(currentValue2);

        TextView units2 = popUpView.findViewById(R.id.dialog_unit2);
        units2.setText(unitValue2);


        AppCompatButton cancelButton = popUpView.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        AppCompatButton acceptButton = popUpView.findViewById(R.id.dialog_accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue1 = something1.getText().toString().trim();
                String newValue2 = something2.getText().toString().trim();

                if (!currentValue1.trim().equals(newValue1) || !currentValue2.trim().equals(newValue2)) {
                    DatabaseReference toSet1 = FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(uid)
                            .child(reference1);

                    DatabaseReference toSet2 = FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(uid)
                            .child(reference2);

                    try {
                        int newValueInt1 = Integer.parseInt(newValue1);
                        int newValueInt2 = Integer.parseInt(newValue2);

                        String heightText = newValueInt1 + unitValue1 + newValueInt2 + unitValue2;
                        ((SettingsTopicView) view).setTitle(heightText);

                        toSet1.setValue(newValueInt1);
                        toSet2.setValue(newValueInt2);

                        popupWindow.dismiss();
                    } catch (Exception e) {
                        something1.setError("Must be a valid number.");
                    }
                }
            }
        });

        popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
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
