package com.fitstir.fitstirapp.ui.workouts.exercises;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewWorkoutBinding;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunnerData;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewWorkoutFragment extends Fragment implements RvInterface {

    private FragmentViewWorkoutBinding binding;
    private TextView exercise, body, targets, equipments, directions, saveName, saveBody, saveTarget, saveEquipment;
    private EditText saveDuration;
    private ImageView image, gifURL;
    private Date currentDate;
    private ImageButton favorite_workout;
    private SharedPreferences sharedPreferences;
    private Boolean isFavoriteButtonOn = false;
    private RvInterface rvInterface;
    private SimpleDateFormat formatter;
    private int favoriteButtonColorOn, favoriteButtonColorOff;
    private CardView dialog_CompletedWorkout;
    private Button tutorial_BTN, complete_BTN, completedSave_BTN, dialogCancel_BTN;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentViewWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //get current users details/info
        workoutsViewModel.getUserData();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        isFavoriteButtonOn = sharedPreferences.getBoolean("is_favorite", false);
        favoriteButtonColorOff = R.drawable.baseline_favorite_24_white;
        favoriteButtonColorOn = R.drawable.baseline_favorite_purple;

        complete_BTN = root.findViewById(R.id.completeWorkout_BTN);
        body = root.findViewById(R.id.tv_BodyPart);
        exercise = root.findViewById(R.id.view_ExerciseName);
        targets = root.findViewById(R.id.tv_Target);
        equipments = root.findViewById(R.id.tv_Equipment);
        directions = root.findViewById(R.id.tv_Directions);
        image = root.findViewById(R.id.view_Gif);
        favorite_workout = root.findViewById(R.id.favorite_workOut);
        tutorial_BTN = root.findViewById(R.id.tutorial_BTN);
        completedSave_BTN = root.findViewById(R.id.saveWorkout_BTN);
        dialogCancel_BTN = root.findViewById(R.id.cancelWorkout_BTN);
        saveBody = root.findViewById(R.id.exerciseGroup_Dialog);
        saveEquipment = root.findViewById(R.id.exerciseEquipment_Dialog);
        saveName = root.findViewById(R.id.exerciseName_Dialog);
        saveTarget = root.findViewById(R.id.exerciseTarget_Dialog);
        saveDuration = root.findViewById(R.id.timeInput_Dialog);
        dialog_CompletedWorkout = root.findViewById(R.id.completed_Workouts_Dialog);
        currentDate = new Date();
        formatter = new SimpleDateFormat(Constants.DATE_FORMAT);

        body.setText(workoutsViewModel.getBodyPart().getValue().trim());
        exercise.setText(workoutsViewModel.getExercise().getValue().trim());
        equipments.setText(workoutsViewModel.getEquipment().getValue().trim());
        directions.setText(workoutsViewModel.getDirections().getValue().trim());
        targets.setText(workoutsViewModel.getTarget().getValue().trim());
        rvInterface = this;

        Glide.with(requireActivity())
                .load(workoutsViewModel.getGifURL().getValue())
                .into(image);

        complete_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setVisibility(View.INVISIBLE);
                dialog_CompletedWorkout.setVisibility(View.VISIBLE);

                saveBody.setText(workoutsViewModel.getBodyPart().getValue().trim());
                saveName.setText(workoutsViewModel.getExercise().getValue().trim());
                saveEquipment.setText(workoutsViewModel.getEquipment().getValue().trim());
                saveTarget.setText(workoutsViewModel.getTarget().getValue().trim());


            }
        });
        completedSave_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = formatter.format(currentDate);
                String exerciseName = workoutsViewModel.getExercise().getValue().toString().trim();
                String exerciseGroup = workoutsViewModel.getBodyPart().getValue().toString().trim();
                String exerciseEquipment = workoutsViewModel.getEquipment().getValue().toString().trim();
                String exerciseTargetArea = workoutsViewModel.getTarget().getValue().toString().trim();
                String workoutLength = saveDuration.getText().toString();
                String filePath = exerciseName +" "+date;



                if(workoutLength != null && !workoutLength.isEmpty()){
                    //get info enter from user and parse
                    //get METValue from firebase database
                    //get users current weight from profile
                    //add MetValue and users input into method to calculate total calories burned

                    int usersWeight = workoutsViewModel.getCurrentUser_Weight().getValue().intValue();
                    double metValue = workoutsViewModel.getCalBurned().getValue();
                    double workoutDuration = Integer.parseInt(workoutLength);

                    double totalCaloriesBurned = workoutsViewModel.calculateBurnedCaloriesFromExercise(usersWeight,workoutDuration,metValue);

                    WorkoutApi data = new WorkoutApi(exerciseGroup, exerciseTargetArea,exerciseName,exerciseEquipment,totalCaloriesBurned, date);

                    FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference runRef = FirebaseDatabase.getInstance()
                            .getReference("CompletedWorkout")
                            .child(authUser.getUid())
                            .child(filePath);

                    runRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(requireActivity(), "Workout Completed and Saved", Toast.LENGTH_LONG).show();
                            dialog_CompletedWorkout.setVisibility(View.INVISIBLE);
                            image.setVisibility(View.VISIBLE);
                        }
                    });
                }else{
                    Toast.makeText(requireActivity(),"Must enter duration of workout", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialogCancel_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_CompletedWorkout.setVisibility(View.INVISIBLE);
                image.setVisibility(View.VISIBLE);
            }
        });
        favorite_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle favorite state

                isFavoriteButtonOn = !isFavoriteButtonOn;
                updateFavorite();

                // Save button state
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("is_favorite", isFavoriteButtonOn);
                editor.apply();

                onItemClick(workoutsViewModel.getFavoriteItemPosition().getValue());

            }
        });
        tutorial_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_viewWorkoutFragment_to_tutorialFragment);
            }
        });

        return root;
    }

    private void updateFavorite() {
        if(isFavoriteButtonOn){
            if(favorite_workout != null){
                favorite_workout.setImageResource(R.drawable.baseline_favorite_purple);
            }

        }
        else{
            if(favorite_workout != null){
                favorite_workout.setImageResource(R.drawable.baseline_favorite_24_white);
            }

        }
    }
    private void toggleFavorite(View view){
        favorite_workout.callOnClick();
    }
    @Override
    public void onItemClick(int position) {
        WorkoutsViewModel view = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);
        ArrayList<WorkoutApi> favoriteList = new ArrayList<>();
        WorkoutApi data = view.getWorkOuts().getValue().get(position);

        String pathFolder = "FavoriteWorkout";
        String filePathName = data.getExercise().toString();

        if(isFavoriteButtonOn) {

            view.saveFavoriteWorkout(data, requireActivity(),pathFolder,filePathName);
            favoriteList.add(data);

        }
        else {

            favoriteList.remove(data);

            //delete item from firebase
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("FavoriteWorkout")
                    .child(user.getUid())
                    .child(data.getExercise());
            dataRef.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(requireActivity(), "Favorite unsaved", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}