package com.fitstir.fitstirapp.ui.connect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentLogInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class LogInFragment extends Fragment {

    private FragmentLogInBinding binding;
    private ImageButton ForgotButton;
    private FirebaseAuth auth;
    private EditText user_Email, user_Password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentLogInBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        auth = FirebaseAuth.getInstance();
        user_Email = root.findViewById(R.id.username_log_in);
        user_Password = root.findViewById(R.id.user_password);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button logInButton = root.findViewById(R.id.button_log_in);
        logInButton.setOnClickListener(v -> {

            //firebase authentication method
            validate();
        });

        ForgotButton = root.findViewById(R.id.button_forgot_pass);
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

    public void validate(){
    String email = user_Email.getText().toString();
    String password = user_Password.getText().toString();

    if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        if(!password.isEmpty() &&  password.length() >= 6){
            auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getActivity(), "LogIn Successfull", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(getActivity(), MainActivity.class);
                            Objects.requireNonNull(getActivity()).startActivity(myIntent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "LogIn Failed...Please Try Again!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else if(password.isEmpty())
        {
            user_Password.setError("Password can not be empty");
        }
        else
        {
            user_Password.setError("Password must be at least 6 characters");
        }

    }
    else if(email.isEmpty())
    {
        user_Email.setError("Email can not be empty...Please Try Again!!");
    }
    else
    {
        user_Email.setError("Please enter valid Email!!");
    }
    }
}