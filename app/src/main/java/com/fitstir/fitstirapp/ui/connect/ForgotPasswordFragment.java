package com.fitstir.fitstirapp.ui.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentForgotPasswordBinding;
import com.fitstir.fitstirapp.databinding.FragmentSignUpBinding;

import java.util.Objects;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentForgotPasswordBinding.inflate(inflater,container, false);
        View root = binding.getRoot();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button submitButton = root.findViewById(R.id.submit_password_forgot_button);
        submitButton.setOnClickListener(v->{
            //TODO: Reset Password

            //After submit return to login
            Navigation.findNavController(v).navigate(R.id.action_forgotPasswordFragment_to_navigation_log_in);
        });

        Button cancelButton = root.findViewById(R.id.cancel_password_forgot_button);
        cancelButton.setOnClickListener(v->{
          // cancelled password reset request
            Navigation.findNavController(v).navigate(R.id.action_forgotPasswordFragment_to_navigation_log_in);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}