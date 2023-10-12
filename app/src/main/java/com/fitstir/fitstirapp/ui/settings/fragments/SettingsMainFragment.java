package com.fitstir.fitstirapp.ui.settings.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsMainBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SettingsMainFragment extends Fragment {

    private FragmentSettingsMainBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        settingsViewModel.setStillInSettings(false);
        settingsViewModel.setCameFromFragment(this);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        binding.userId.setText(uid);

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        UserProfileData value = snapshot.getValue(UserProfileData.class);
                        settingsViewModel.setThisUser(value);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
        });

        try{
            //access firebase storage for profile pic
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();

            StorageReference photo = storageReference.child("images/"+uid);
            photo.getBytes(Constants.MEGA_BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    settingsViewModel.setAvatar(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    settingsViewModel.setAvatar(null);
                }
            });
        }
        catch (NullPointerException e){
            Toast.makeText(requireActivity(),"Refer to Google to see Account Details", Toast.LENGTH_SHORT).show();
        }

        binding.profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SettingsProfileFragment());
            }
        });

        binding.generalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SettingsGeneralFragment());
            }
        });

        binding.goalsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SettingsGoalFragment());
            }
        });

        binding.accountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SettingsAccountFragment());
            }
        });

        binding.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SettingsContactFragment());
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
