package com.fitstir.fitstirapp.ui.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentQuestionBinding;

import java.util.Objects;

public class QuestionFragment extends Fragment {

    private FragmentQuestionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button signUpButton = root.findViewById(R.id.button_sign_up_question);
        signUpButton.setOnClickListener(v -> {
            // TODO: Do the questionnaire

            // If completed
            Intent myIntent = new Intent(getActivity(), MainActivity.class);
            Objects.requireNonNull(getActivity()).startActivity(myIntent);
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