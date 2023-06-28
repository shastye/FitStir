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

import com.fitstir.fitstirapp.ConnectActivity;
import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentLogInBinding;

import java.util.Objects;

public class LogInFragment extends Fragment {

    private FragmentLogInBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentLogInBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button logInButton = root.findViewById(R.id.button_log_in);
        logInButton.setOnClickListener(v -> {
            // TODO: Do the log in

            // If logged in
            Intent myIntent = new Intent(getActivity(), MainActivity.class);
            Objects.requireNonNull(getActivity()).startActivity(myIntent);
        });

        Button ForgotButton = root.findViewById(R.id.button_forgot_pass);
        ForgotButton.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_navigation_log_in_to_navigation_forgot);
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