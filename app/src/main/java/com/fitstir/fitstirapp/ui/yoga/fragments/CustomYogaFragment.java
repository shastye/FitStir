package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.widget.Toast;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentCustomYogaBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.CustomInterface;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.CustomsAdapter;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.CustomsAdapterView;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.PoseAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CustomYogaFragment extends Fragment implements CustomInterface {

    private EditText routineName;
    private Button save, cancel;
    private ImageButton addTo, addTo_RV, subtract_RV;
    private RecyclerView customList, customWindowView, expandableCardView_RV;
    private CustomInterface rvI;
    private View dialog;
    private CardView listCardView, windowCardView, openCardView, closedCardView;
    private FragmentCustomYogaBinding binding;
    private ArrayList<PoseModel> yogaPoseList,newCustomList, retrievedRoutineList, tempList ;
    private CustomsAdapter adapter;
    private CustomsAdapterView adapterWindow;
    private PoseAdapter routineAdapter;
    private PoseModel model;
    private ImageView open, close;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call  yoga view model
        YogaViewModel yogaView = new ViewModelProvider(this).get(YogaViewModel.class);


        // Inflate the layout for this fragment
        binding = FragmentCustomYogaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        routineName = root.findViewById(R.id.routine_Title_ET);
        save = root.findViewById(R.id.save_Routine);
        cancel = root.findViewById(R.id.cancel_Routine);
        addTo = root.findViewById(R.id.add_To_Custom);
        customList = root.findViewById(R.id.custom_RV);
        customWindowView = root.findViewById(R.id.justAdded_RV);
        listCardView = root.findViewById(R.id.addPoses_CV);
        windowCardView = root.findViewById(R.id.justAdded_CV);
        addTo_RV = root.findViewById(R.id.add_To_BTN);
        subtract_RV = root.findViewById(R.id.subtract_from_BTN);
        dialog = root.findViewById(R.id.custom_empty);
        openCardView = root.findViewById(R.id.custon_cardView_opened);
        closedCardView = root.findViewById(R.id.custon_cardView_closed);
        expandableCardView_RV = root.findViewById(R.id.opened_cardview_RV);
        open = root.findViewById(R.id.open_cardview);
        close = root.findViewById(R.id.close_cardview);

        retrievedRoutineList = new ArrayList<>();
        yogaPoseList = new ArrayList<>();
        newCustomList = new ArrayList<>();
        tempList = new ArrayList<>();
        model = new PoseModel();
        rvI = this;

        listCardView.setVisibility(View.INVISIBLE);
        windowCardView.setVisibility(View.INVISIBLE);
        dialog.setVisibility(View.INVISIBLE);
        openCardView.setVisibility(View.INVISIBLE);
        closedCardView.setVisibility(View.INVISIBLE);


        // Get data before option selection from each category
        // to make a full list of everything to chose from for customs fragment
        adapter = new CustomsAdapter(rvI, requireActivity(), yogaPoseList);
        yogaView.fetchYogaData(yogaPoseList, Constants.YOGA_ID.BEGINNER_YOGA,adapter);

        adapterWindow = new CustomsAdapterView(rvI, requireActivity(), newCustomList, yogaView);


        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("CustomRoutines")
                .child(authUser.getUid());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    ArrayList<PoseModel> data = new ArrayList<>();

                    for(DataSnapshot routineSnapshot : snapshot.getChildren())
                    {
                        String rName = routineSnapshot.getKey();
                        model.setRoutineName(rName);

                        Iterable<DataSnapshot> poseSnapshots = routineSnapshot.getChildren();
                        ArrayList<PoseModel> poseList = new ArrayList<>();

                      for(DataSnapshot poseSnapshot : poseSnapshots){
                          PoseModel retrievedData =  poseSnapshot.getValue(PoseModel.class);
                          if(retrievedData != null){
                              poseList.add(retrievedData);
                          }

                      }
                      data.addAll(poseList);
                        if(!data.isEmpty()){
                            closedCardView.setVisibility(View.VISIBLE);
                        }

                    }
                    expandableCardView_RV.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    expandableCardView_RV.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                    routineAdapter = new PoseAdapter( data, requireActivity(), rvI);
                    expandableCardView_RV.setAdapter(routineAdapter);

                    routineAdapter.notifyDataSetChanged();

                    open.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            closedCardView.setVisibility(View.INVISIBLE);
                            openCardView.setVisibility(View.VISIBLE);
                        }
                    });
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            model.setIsClosedCardView(true);
                            closedCardView.setVisibility(View.VISIBLE);
                            openCardView.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                else{
                    dialog.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < newCustomList.size(); i++){
                    newCustomList.remove(i);
                    windowCardView.setVisibility(View.INVISIBLE);
                }
            }
        });

        return root;
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

                newCustomList.remove(newCustomList.get(position));

                // reset recyclerview and check if array is at end to close dialog recyclerview
                customWindowView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                customWindowView.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                customWindowView.setAdapter(adapterWindow);

                if(newCustomList.size() == 0){
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
                String childPath = routineName.getText().toString();
                PoseModel poseModel = new PoseModel();
                poseModel.setRoutineName(childPath);

                Map<String, Object> convertedObject = new HashMap<>();

                int index = 0;
                for(PoseModel item : newCustomList){
                    String key = item.getEnglish_name();
                    convertedObject.put(key, item);
                    index++;
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


    }
}