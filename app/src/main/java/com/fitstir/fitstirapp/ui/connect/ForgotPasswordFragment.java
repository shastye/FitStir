package com.fitstir.fitstirapp.ui.connect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentForgotPasswordBinding;
import com.fitstir.fitstirapp.databinding.FragmentLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private FirebaseAuth auth;
    private Button submitButton;
    private Button cancelButton;
    private EditText resetPass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();



        // Addition Text Here
        resetPass = root.findViewById(R.id.forgot_password);
        submitButton = root.findViewById(R.id.submit_password_forgot_button);
        
        submitButton.setOnClickListener(v->{

            String email = resetPass.getText().toString().trim();
            auth = FirebaseAuth.getInstance();

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(),"Email Sent", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(v).navigate(R.id.action_forgotPasswordFragment_to_navigation_log_in2);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"Email Send Failed.. Try again!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        cancelButton = root.findViewById(R.id.cancel_password_forgot_button);
        cancelButton.setOnClickListener(v-> {
            //cancelled reset request
            Navigation.findNavController(v).navigate(R.id.action_forgotPasswordFragment_to_navigation_log_in2);
        });
        return root ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}