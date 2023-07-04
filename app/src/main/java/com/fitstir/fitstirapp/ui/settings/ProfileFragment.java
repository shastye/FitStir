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

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

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

        name.setText(SettingsViewModel.name);
        String tAge = String.valueOf(SettingsViewModel.age) + " years old";
        age.setText(tAge);
        String tHeight = String.valueOf(SettingsViewModel.height_feet) + " feet " +
                String.valueOf(SettingsViewModel.height_inches) + " inches";
        height.setText(tHeight);
        String tWeight = String.valueOf(SettingsViewModel.weight) + " lbs";
        weight.setText(tWeight);
        email.setText(SettingsViewModel.email);



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
}