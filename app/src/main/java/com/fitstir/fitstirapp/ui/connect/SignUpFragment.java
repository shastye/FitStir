package com.fitstir.fitstirapp.ui.connect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSignUpBinding;

import java.util.Objects;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button signUpButton = root.findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(v -> {
            // TODO: Do the sign up

            // If signed up
            Navigation.findNavController(v).navigate(R.id.action_navigation_sign_up_to_navigation_question);
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