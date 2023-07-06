package com.fitstir.fitstirapp.ui.connect;

import static android.content.Intent.getIntent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentQuestion4Binding;
import com.fitstir.fitstirapp.databinding.FragmentQuestions2Binding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Questions4Fragment extends Fragment {


    private ToggleButton male;
    private  ToggleButton female;
    private EditText height_ft;
    private EditText height_in;
    private EditText weight;
    private EditText goal_weight;
    private ImageButton dob;
    private TextView age;
    private FragmentQuestion4Binding binding;
    private DatabaseReference dataRef;
    private FirebaseDatabase database;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentQuestion4Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        //additional code here


        height_ft = root.findViewById(R.id.et_height_ft);
        height_in = root.findViewById(R.id.et_height_in);
        weight = root.findViewById(R.id.et_weight);
        dob = root.findViewById(R.id.ib_calendar);
        age = root.findViewById(R.id.age_view);
        male = root.findViewById(R.id.male);
        female = root.findViewById(R.id.female);

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
            updateUserData();
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

        if(today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }

    public void updateUserData()
    {
        dataRef = FirebaseDatabase.getInstance().getReference("users");
        //dataRef.addChildEventListener(new ValueEventListener(){...});
    }
}
