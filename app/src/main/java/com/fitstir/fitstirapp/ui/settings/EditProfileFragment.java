package com.fitstir.fitstirapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdEdit;
        //settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        CardView saveButton = root.findViewById(R.id.savebutton_cardView_edit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: update info and navigate to profile form
            }
        });

        CardView editButton = root.findViewById(R.id.editpicturebutton_cardView_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: do the edit
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPickResult(PickResult r) {
        return;
    }
}