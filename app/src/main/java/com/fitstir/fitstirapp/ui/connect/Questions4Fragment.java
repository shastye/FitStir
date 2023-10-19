package com.fitstir.fitstirapp.ui.connect;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentQuestion4Binding;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Questions4Fragment extends Fragment {
    private DatabaseReference dbRef;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private EditText fullName;
    private EditText setEmail;
    private EditText setPassword;
    private EditText confirmPassword;
    private RadioButton male;
    private RadioButton female;
    private EditText height_ft;
    private EditText height_in;
    private EditText weight;
    private EditText goal_weight;
    private ImageButton dob;
    private TextView age;
    private FragmentQuestion4Binding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentQuestion4Binding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();


        //firebase integration
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Users");
        auth = FirebaseAuth.getInstance();

        //additional code here
        fullName = root.findViewById(R.id.sign_up_full_name);
        setEmail = root.findViewById(R.id.set_email);
        setPassword = root.findViewById(R.id.set_password);
        confirmPassword = root.findViewById(R.id.confirm_password);
        height_ft = root.findViewById(R.id.et_height_ft);
        height_in = root.findViewById(R.id.et_height_in);
        weight = root.findViewById(R.id.et_weight);
        goal_weight = root.findViewById(R.id.et_goal_weight);
        dob = root.findViewById(R.id.ib_calendar);
        age = root.findViewById(R.id.age_view);
        male = root.findViewById(R.id.button_male);
        female = root.findViewById(R.id.button_female);


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int _year = c.get(Calendar.YEAR);
                int _month = c.get(Calendar.MONTH);
                int _day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog date_dialog = new DatePickerDialog(v.getContext(), datePickerListener, _year, _month, _day);
                date_dialog.getDatePicker().setMaxDate(new Date().getTime());
                date_dialog.show();
            }
        });

        Button submit = root.findViewById(R.id.button_submit);
        submit.setOnClickListener(v->{
            createUser();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String format = new SimpleDateFormat("dd MM YYYY").format(c.getTime());
            age.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
        }
    };
    int calculateAge(long date){
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if(today.get(Calendar.DAY_OF_MONTH) > birth.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }
    public void createUser() {
        String fName = fullName.getText().toString();
        String email = setEmail.getText().toString();
        String pass = setPassword.getText().toString();
        String confirm_pass = confirmPassword.getText().toString();
        String val_Ft = height_ft.getText().toString();
        int finalFt = Integer.parseInt(val_Ft);
        String val_In = height_in.getText().toString();
        int finalIn = Integer.parseInt(val_In);
        String val_Wt = weight.getText().toString();
        int finalWt = Integer.parseInt(val_Wt);
        String val_GWt = goal_weight.getText().toString();
        int finalGWt = Integer.parseInt(val_GWt);
        String val_age = age.getText().toString();
        int finalAge = Integer.parseInt(val_age);

        final Drawable warning = AppCompatResources.getDrawable(requireContext(),R.drawable.baseline_warning_amber_24);
        warning.setBounds(0,0, warning.getIntrinsicWidth(),warning.getIntrinsicHeight());

        if(!fName.isEmpty())
        {
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    if(!pass.isEmpty() && pass.length() >= 6)
                    {
                        if(pass.matches(confirm_pass))
                        {
                            if(finalWt > 55)
                            {
                                if(finalGWt > 55)
                                {
                                    auth.createUserWithEmailAndPassword(email, pass)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    Boolean radioBtnState = male.isChecked();
                                                    Boolean radioBtnState_female = female.isChecked();

                                                    if(radioBtnState == true)
                                                    {
                                                        final String uid = auth.getCurrentUser().getUid();
                                                        UserProfileData user = new UserProfileData(fName,email,pass,"male", finalFt, finalIn, finalWt, finalGWt, finalAge);
                                                        if(uid != null)
                                                        {
                                                            dbRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Goal goal = new Goal(GoalTypes.WEIGHT_CHANGE, user.getGoal_weight());
                                                                    goal.addData(Calendar.getInstance().getTime(), user.get_Weight());
                                                                    Methods.addGoalToFirebase(goal);

                                                                    Toast.makeText(getActivity(), "Sign In Complete", Toast.LENGTH_SHORT).show();
                                                                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                                                                    Objects.requireNonNull(getActivity()).startActivity(myIntent);
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getActivity(), "Sign Up Failed...Please Try Again!!!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                    else if(radioBtnState_female == true)
                                                    {
                                                        final String uid = auth.getCurrentUser().getUid();
                                                        UserProfileData user = new UserProfileData(fName,email,pass,"female", finalFt, finalIn, finalWt, finalGWt, finalAge);
                                                        //user.addWeightData(finalWt);

                                                        if(uid != null) {
                                                            dbRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Goal goal = new Goal(GoalTypes.WEIGHT_CHANGE, user.getGoal_weight());
                                                                    goal.addData(Calendar.getInstance().getTime(), user.get_Weight());
                                                                    Methods.addGoalToFirebase(goal);

                                                                    Toast.makeText(getActivity(), "Sign In Complete", Toast.LENGTH_SHORT).show();
                                                                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                                                                    Objects.requireNonNull(getActivity()).startActivity(myIntent);
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getActivity(), "Sign Up Failed...Please Try Again!!!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            });
                                }
                                else
                                {
                                    weight.setError("Error reading weight or equals 0...please enter another number", warning);
                                }
                            }
                            else
                            {
                                goal_weight.setError("Error reading weight or equals 0...please enter another number", warning);
                            }
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
            fullName.setError("Field can not be left empty", warning);
        }
    }
}
