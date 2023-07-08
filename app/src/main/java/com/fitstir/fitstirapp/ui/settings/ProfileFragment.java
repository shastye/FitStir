package com.fitstir.fitstirapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DatabaseReference reference;

    private FirebaseAuth auth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdProfile;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        ImageView profileImage = root.findViewById(R.id.profile_image);
        profileImage.setImageBitmap(SettingsViewModel.avatar);

        TextView name = root.findViewById(R.id.text_name);
        TextView age = root.findViewById(R.id.text_age);
        TextView height = root.findViewById(R.id.text_height);
        TextView weight = root.findViewById(R.id.text_weight);
        TextView email = root.findViewById(R.id.text_email);
        TextView height_in = root.findViewById(R.id.text_height_in);

        name.setText(SettingsViewModel.name);
        String tAge = String.valueOf(SettingsViewModel.age) + " years old";
        age.setText(tAge);
        String tHeight = String.valueOf(SettingsViewModel.height_feet) + " feet " +
                String.valueOf(SettingsViewModel.height_inches) + " inches";
        height.setText(tHeight);
        String tWeight = String.valueOf(SettingsViewModel.weight) + " lbs";
        weight.setText(tWeight);
        email.setText(SettingsViewModel.email);

        auth = FirebaseAuth.getInstance();
        String user = auth.getCurrentUser().getUid();
        reference =  FirebaseDatabase.getInstance().getReference("Users");
        reference.child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot snapshot = task.getResult();
                        String fullName = String.valueOf(snapshot.child("fullname").getValue());
                        String email = String.valueOf(snapshot.child("email").getValue());
                        String age = String.valueOf(snapshot.child("age").getValue());
                        String height = String.valueOf(snapshot.child("height_ft").getValue());
                        //String height_In = String.valueOf(snapshot.child("height_in"));
                        String weight = String.valueOf(snapshot.child("_Weight").getValue());

                        binding.textName.setText(fullName);
                        binding.textEmail.setText(email);
                        binding.textAge.setText(age);
                        binding.textHeight.setText(height);
                        //binding.textHeightIn.setText(height_In);
                        binding.textWeight.setText(weight);
                    }
                }
            }
        });


        CardView editButton = root.findViewById(R.id.editbutton_cardView_profile);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(root).navigate(R.id.action_navigation_profile_to_navigation_edit_profile);
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
    public void editProfile()
    {
        //TODO: edit and save changes to firebase
    }
}