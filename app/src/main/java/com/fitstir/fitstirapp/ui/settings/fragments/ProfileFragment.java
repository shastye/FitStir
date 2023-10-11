package com.fitstir.fitstirapp.ui.settings.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentProfileBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DatabaseReference reference;
    private SettingsViewModel settingsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;

        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(authUser.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users value = snapshot.getValue(Users.class);
                settingsViewModel.setThisUser(value);

                binding.textName.setText(value.getFullname());
                String tAge = value.getAge() + " years old";
                binding.textAge.setText(tAge);
                String tHeightFeet = value.getHeight_ft() + " feet ";
                binding.textHeightFt.setText(tHeightFeet);
                String tHeightInches = value.getHeight_in() + " inches";
                binding.textHeightIn.setText(tHeightInches);
                String tWeight = value.getWeight() + " lbs";
                binding.textWeight.setText(tWeight);
                binding.textEmail.setText(value.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });





        try{
            //access firebase storage for profile pic
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            String user = auth.getCurrentUser().getUid();
            StorageReference photo = storageReference.child("images/"+user);
            photo.getBytes(Constants.MEGA_BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                   Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                   binding.profileImage.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        catch (NullPointerException e){
            Toast.makeText(requireActivity(),"Refer to Google to see Account Details", Toast.LENGTH_SHORT).show();
        }


        CardView editButton = binding.editbuttonCardViewProfile;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.setCameFromProfile(true);
                Navigation.findNavController(root).navigate(R.id.action_navigation_profile_to_navigation_settings);
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