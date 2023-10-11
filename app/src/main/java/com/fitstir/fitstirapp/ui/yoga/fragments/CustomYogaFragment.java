package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentCustomYogaBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.CustomInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.CustomsAdapter;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.CustomsAdapterView;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.ICustoms;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.PoseAdapter;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.TitleAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomYogaFragment extends Fragment implements CustomInterface, ICustoms {

    //region Variables
    private EditText routineName;
    private Button save, cancel;
    private ImageButton addTo, addTo_RV, subtract_RV;
    private RecyclerView customList, customWindowView, expandableCardView_RV, main;
    private CustomInterface rvI;
    private ICustoms interface2;
    private View dialog;
    private CardView listCardView, windowCardView, openCardView, closedCardView;
    private FragmentCustomYogaBinding binding;
    private ArrayList<PoseModel> yogaPoseList,newCustomList, retrievedRoutineList, tempList, routineFolderList, poseList ;
    private CustomsAdapter adapter;
    private CustomsAdapterView adapterWindow;
    private PoseAdapter routineAdapter;
    private TitleAdapter folderAdapter;
    private PoseModel model;
    private ImageView open, close;
    private Map<String, List<PoseModel>> folderPoseMap;
    private NestedScrollView scrollView;
//endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call  yoga view model
        YogaViewModel yogaView = new ViewModelProvider(this).get(YogaViewModel.class);


        // Inflate the layout for this fragment
        binding = FragmentCustomYogaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //region initializations
        routineName = root.findViewById(R.id.routine_Title_ET);
        save = root.findViewById(R.id.save_Routine);
        cancel = root.findViewById(R.id.cancel_Routine);
        addTo = root.findViewById(R.id.add_To_Custom);
        listCardView = root.findViewById(R.id.addPoses_CV);
        windowCardView = root.findViewById(R.id.justAdded_CV);
        dialog = root.findViewById(R.id.custom_empty);
        openCardView = root.findViewById(R.id.custon_cardView_opened);
        closedCardView = root.findViewById(R.id.custon_cardView_closed);
        open = root.findViewById(R.id.open_cardview);
        close = root.findViewById(R.id.close_cardview);
        customList = root.findViewById(R.id.custom_RV);
        customWindowView = root.findViewById(R.id.justAdded_RV);
        main = root.findViewById(R.id.main_RV);
        expandableCardView_RV = root.findViewById(R.id.opened_cardview_RV);
        addTo_RV = root.findViewById(R.id.add_To_BTN);
        subtract_RV = root.findViewById(R.id.subtract_from_BTN);
        retrievedRoutineList = new ArrayList<>();
        yogaPoseList = new ArrayList<>();
        newCustomList = new ArrayList<>();
        tempList = new ArrayList<>();
        model = new PoseModel();
        routineFolderList = new ArrayList<>();
        rvI = this;
        interface2 = this;


        //initial view logic
        listCardView.setVisibility(View.INVISIBLE);
        windowCardView.setVisibility(View.INVISIBLE);
        dialog.setVisibility(View.INVISIBLE);
        //openCardView.setVisibility(View.INVISIBLE);
        //closedCardView.setVisibility(View.INVISIBLE);
//endregion

        // Get data before option selection from each category
        // to make a full list of everything to choose from for customs fragment
        adapter = new CustomsAdapter(rvI, requireActivity(), yogaPoseList);
        yogaView.fetchCustomData(yogaPoseList, Constants.YOGA_ID.BEGINNER_YOGA,adapter);
        adapterWindow = new CustomsAdapterView(rvI, requireActivity(), newCustomList, yogaView);

        getCustomRoutines();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < newCustomList.size(); i++){
                    newCustomList.remove(i);
                }
                windowCardView.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }

    public void getCustomRoutines(){

        //firebase integration and visibility settings
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("CustomRoutines")
                .child(authUser.getUid());

        folderPoseMap = new HashMap<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    //ArrayList<PoseModel> data = new ArrayList<>();

                    for(DataSnapshot routineSnapshot : snapshot.getChildren())
                    {
                        String routineName = routineSnapshot.getKey();
                        //model.setRoutineName(rName);
                        poseList = new ArrayList<>();
                        //Iterable<DataSnapshot> poseSnapshots = routineSnapshot.getChildren();
                        for(DataSnapshot poseSnapshot : routineSnapshot.getChildren()){
                            PoseModel retrievedData =  poseSnapshot.getValue(PoseModel.class);
                            if(retrievedData != null){
                                poseList.add(retrievedData);
                            }

                        }
                        folderPoseMap.put(routineName, poseList);
                        if(!folderPoseMap.isEmpty()){
                            closedCardView.setVisibility(View.VISIBLE);
                        }
                    }

                    //parent recyclerView
                    main.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    main.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                    folderAdapter = new TitleAdapter( new HashMap<>(), interface2, requireActivity());
                    folderAdapter.setFolderPoseMap(folderPoseMap);
                    main.setAdapter(folderAdapter);
                    folderAdapter.notifyDataSetChanged();

                    //child recyclerView
                    expandableCardView_RV.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    expandableCardView_RV.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                    routineAdapter = new PoseAdapter( poseList, requireActivity());
                    expandableCardView_RV.setAdapter(routineAdapter);
                    routineAdapter.notifyDataSetChanged();

                }
                else{
                    dialog.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database Error", error.getMessage());
                Log.d("Firebase Save to Database error", error.getDetails());
            }
        });

        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setVisibility(View.INVISIBLE);
                try{
                    if(!yogaPoseList.isEmpty()){
                        listCardView.setVisibility(View.VISIBLE);
                        customList.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        customList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                        customList.setAdapter(adapter);
                    }
                    else{
                        customList.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        customList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                        customList.setAdapter(adapter);
                    }
                }catch (NullPointerException e){
                    Log.e("Fetch Error", "Sorry error retrieving pose data");
                }
            }
        });
    }
    @Override
    public void onItemClick(int position, boolean isAddButtonClicked) {

        model = yogaPoseList.get(position);

        try{
            if(isAddButtonClicked){

                newCustomList.add(yogaPoseList.get(position));

                //Set visibility then set recycleView
                windowCardView.setVisibility(View.VISIBLE);
                customWindowView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                customWindowView.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                customWindowView.setAdapter(adapterWindow);
            }
            else{

                if(newCustomList.size() != 0){
                    newCustomList.remove(newCustomList.get(position));

                    // reset recyclerview and check if array is at end to close dialog recyclerview
                    customWindowView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    customWindowView.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                    customWindowView.setAdapter(adapterWindow);
                }

                else if(newCustomList.size() == 0){
                    windowCardView.setVisibility(View.INVISIBLE);
                }
            }

        }catch (NullPointerException e){
            Log.e("Load/Update", "Error: Load/Update..try again later");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = "CustomRoutines";
                String childPath = routineName.getText().toString().trim();

                PoseModel poseModel = new PoseModel();
                poseModel.setRoutineName(childPath);

                Map<String, Object> convertedObject = new HashMap<>();


                for(PoseModel item : newCustomList){

                    item.setRoutineName(childPath);
                    String key = "Pose: "+ item.getEnglish_name();
                    convertedObject.put(key, item);

                }
                // Make sure user adds title to store routine properly.
                if(!childPath.isEmpty() || childPath == "\n") {

                    FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference dataRef = FirebaseDatabase.getInstance()
                            .getReference(path)
                            .child(authUser.getUid())
                            .child(poseModel.getRoutineName());
                        dataRef.setValue(convertedObject, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                listCardView.setVisibility(View.INVISIBLE);
                                windowCardView.setVisibility(View.INVISIBLE);
                                Toast.makeText(requireActivity(), "Saved successfully", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                    Toast.makeText(requireActivity(),"Must enter Routine title to save", Toast.LENGTH_LONG).show();
                }
            }
        });
        getCustomRoutines();
    }

    @Override
    public void customOnItemClick(int position) {
        if(!folderPoseMap.isEmpty()){
            ArrayList<String> routineNames = new ArrayList<>(folderPoseMap.keySet());
            String clickedRoutine = routineNames.get(position);

            ArrayList<PoseModel> posesFromRoutine = (ArrayList<PoseModel>) folderPoseMap.get(clickedRoutine);

            routineAdapter.setPoseList(posesFromRoutine);
            model.setRoutineSize(posesFromRoutine.size());
            routineAdapter.notifyDataSetChanged();

        }
    }
}