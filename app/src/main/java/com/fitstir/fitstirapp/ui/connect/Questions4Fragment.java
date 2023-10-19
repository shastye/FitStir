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
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentQuestion4Binding;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.Constants;
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
    private EditText fullName,confirmPassword,setEmail,setPassword;
    private RadioButton male, female;
    private EditText height_ft, height_in,weight,goal_weight;
    private ImageButton dob;
    private TextView age, userName, userAge, userWeight, userWeightGoal,
            featureName, calorieAmount, caloriesConsume_TV, caloriesBurn_TV, days;
    private FragmentQuestion4Binding binding;
    private View dialog;
    private Button ok_Dialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ConnectViewModel connectViewModel = new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        binding = FragmentQuestion4Binding.inflate(inflater, container, false);
        View root = binding.getRoot();
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
        userName = root.findViewById(R.id.name_workout_dialog);
        userAge = root.findViewById(R.id.age_workout_dialog);
        userWeight = root.findViewById(R.id.weight_workout_dialog);
        userWeightGoal = root.findViewById(R.id.goalWeight_workout_dialog);
        featureName = root.findViewById(R.id.featureName_workout_dialog);
        calorieAmount = root.findViewById(R.id.calories_workout_dialog);
        caloriesConsume_TV = root.findViewById(R.id.calories_statement_Consume);
        caloriesBurn_TV = root.findViewById(R.id.calories_statement_Burn);
        days = root.findViewById(R.id.days_workout_dialog);
        dialog = root.findViewById(R.id.workout_plan);
        ok_Dialog = root.findViewById(R.id.ok_workout_dialog);

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

        ConnectViewModel connectViewModel = new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        String val_In = height_in.getText().toString();
        String fName = fullName.getText().toString();
        String email = setEmail.getText().toString();
        String pass = setPassword.getText().toString();
        String confirm_pass = confirmPassword.getText().toString();
        String val_Ft = height_ft.getText().toString();
        String val_age = age.getText().toString();
        String val_GWt = goal_weight.getText().toString();
        String val_Wt = weight.getText().toString();

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
                            if(!val_Wt.isEmpty())
                            {
                                if(!val_GWt.isEmpty())
                                {
                                    if(!val_Ft.isEmpty())
                                    {
                                        if(!val_In.isEmpty())
                                        {
                                          if(!val_age.isEmpty())
                                          {
                                              int finalAge = Integer.parseInt(val_age);

                                              if(finalAge >= 14)
                                              {
                                                  int finalFt = Integer.parseInt(val_Ft);
                                                  int finalIn = Integer.parseInt(val_In);
                                                  int finalWt = Integer.parseInt(val_Wt);
                                                  int finalGWt = Integer.parseInt(val_GWt);

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
                                                                      String age = String.valueOf(finalAge);
                                                                      String weight = String.valueOf(finalWt);
                                                                      String goalWeight = String.valueOf(finalGWt);
                                                                      String height = String.valueOf(finalFt);

                                                                      //set viewModel for suggested workout plan data sharing
                                                                      connectViewModel.setUserName(fName);
                                                                      connectViewModel.setIsFemale(false);
                                                                      connectViewModel.setAge(age);
                                                                      connectViewModel.setWeight(weight);
                                                                      connectViewModel.setGoalWeight(goalWeight);
                                                                      connectViewModel.setUserHeight(height);

                                                                      if(uid != null)
                                                                      {
                                                                          dbRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                              @Override
                                                                              public void onComplete(@NonNull Task<Void> task) {
                                                                                  Methods.addGoalToFirebase(GoalTypes.WEIGHT_CHANGE, user.getGoal_weight());
                                                                                  Methods.addDataToGoal(GoalTypes.WEIGHT_CHANGE, Calendar.getInstance().getTime(), user.get_Weight());

                                                                                  Toast.makeText(getActivity(), "Sign In Complete", Toast.LENGTH_SHORT).show();
                                                                                  Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_connect)
                                                                                          .navigate(R.id.action_questions4Fragment_to_workoutPlanFragment);
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
                                                                      user.addWeightData(finalWt);

                                                                      String age = String.valueOf(finalAge);
                                                                      String weight = String.valueOf(finalWt);
                                                                      String goalWeight = String.valueOf(finalGWt);
                                                                      String height = String.valueOf(finalFt);

                                                                      //set viewModel for suggested workout plan data sharing
                                                                      connectViewModel.setUserName(fName);
                                                                      connectViewModel.setIsFemale(true);
                                                                      connectViewModel.setAge(age);
                                                                      connectViewModel.setWeight(weight);
                                                                      connectViewModel.setGoalWeight(goalWeight);
                                                                      connectViewModel.setUserHeight(height);

                                                                      if(uid != null) {
                                                                          dbRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                              @Override
                                                                              public void onComplete(@NonNull Task<Void> task) {
                                                                                  Methods.addGoalToFirebase(GoalTypes.WEIGHT_CHANGE, user.getGoal_weight());
                                                                                  Methods.addDataToGoal(GoalTypes.WEIGHT_CHANGE, Calendar.getInstance().getTime(), user.get_Weight());

                                                                                  Toast.makeText(getActivity(), "Sign In Complete", Toast.LENGTH_SHORT).show();
                                                                                  Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_connect)
                                                                                          .navigate(R.id.action_questions4Fragment_to_workoutPlanFragment);
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
                                                  age.setError("User age requires Parental Consent", warning);
                                              }
                                          }
                                          else
                                          {
                                              age.setError("Must enter age", warning);
                                          }
                                        }
                                        else
                                        {
                                            height_in.setError("Must enter inches for accurate results", warning);
                                        }
                                    }
                                    else
                                    {
                                        height_ft.setError("Must enter height for accurate results", warning);
                                    }
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
