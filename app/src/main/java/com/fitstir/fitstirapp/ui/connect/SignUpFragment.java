package com.fitstir.fitstirapp.ui.connect;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private DatabaseReference dataRef;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private EditText firstName;
    private EditText lastName;
    private EditText setEmail;
    private EditText setPassword;
    private EditText confirmPassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("Users");
        auth = FirebaseAuth.getInstance();
        firstName = root.findViewById(R.id.sign_up_first_name);
        lastName = root.findViewById(R.id.sign_up_last_name);
        setEmail = root.findViewById(R.id.set_email);
        setPassword = root.findViewById(R.id.set_password);
        confirmPassword = root.findViewById(R.id.confirm_password);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button signUpButton = root.findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(v -> {
            signUp();
        });
        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void signUp(){

        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String email = setEmail.getText().toString();
        String pass = setPassword.getText().toString();
        String confirm_pass = confirmPassword.getText().toString();

        final Drawable warning = AppCompatResources.getDrawable(requireContext(),R.drawable.baseline_warning_amber_24);
        warning.setBounds(0,0, warning.getIntrinsicWidth(),warning.getIntrinsicHeight());

        if(!fName.isEmpty())
        {
            if (!lName.isEmpty())
            {
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    if(!pass.isEmpty() && pass.length() >= 6)
                    {
                        if(pass.matches(confirm_pass))
                        {
                            //TODO: finish creating new user
                            auth.createUserWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            final String uid = auth.getCurrentUser().getUid();
                                            UserProfileData user = new UserProfileData(fName, lName,email,pass);
                                            if(uid != null)
                                            {
                                                dataRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                      Navigation.findNavController(getView()).navigate(R.id.action_navigation_sign_up_to_navigation_question);
                                                    }
                                                });
                                            }


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), "Sign Up Failed...Please Try Again!!!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else
                        {
                            confirmPassword.setError("Passwords do not match", warning);
                        }
                    }
                    else if (pass.isEmpty())
                    {
                        setPassword.setError("Password can not be empty",warning);
                    }
                    else
                    {
                        setPassword.setError("Password must be at least 6 characters", warning);
                    }
                }
                else if (email.isEmpty())
                {
                    setEmail.setError("Email can not be empty...Please Try Again!!", warning);
                }
                else
                {
                    setEmail.setError("Please enter valid Email!!", warning);
                }
            }
            else
            {
                lastName.setError("Field can not be left empty", warning);
            }
        }
        else
        {
            firstName.setError("Field can not be left empty", warning);
        }
    }
}