package com.fitstir.fitstirapp.ui.connect;

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

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentInitialBinding;

import java.util.Objects;

public class InitialFragment extends Fragment {

    private FragmentInitialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentInitialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInitial;
        connectViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button logInButton = root.findViewById(R.id.initial_log_in_button);
        logInButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_navigation_initial_to_navigation_log_in)
        );

        Button signUpButton = root.findViewById(R.id.initial_sign_up_button);
        signUpButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_navigation_initial_to_navigation_sign_up)
        );

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}