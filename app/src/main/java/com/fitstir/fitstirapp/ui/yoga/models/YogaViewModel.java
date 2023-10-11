package com.fitstir.fitstirapp.ui.yoga.models;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.CustomsAdapter;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.FavoriteAdapter;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.PoseAdapter;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.YogaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YogaViewModel extends ViewModel {

	private final MutableLiveData<String> mText;
	private final MutableLiveData<Integer> favoriteItemPosition = new MutableLiveData<>();
	private final MutableLiveData<ArrayList<PoseModel>> yoga = new MutableLiveData<>();
	private final MutableLiveData<PoseModel> likedFavorites = new MutableLiveData<>(new PoseModel());
	private final MutableLiveData<Integer> cat_Id = new MutableLiveData<>(0);
	private final MutableLiveData<String> english_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> sanskrit_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> translated_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> pose_Description = new MutableLiveData<>(" ");
	private final MutableLiveData<String> pose_Benefits = new MutableLiveData<>(" ");
	private final MutableLiveData<String> levels = new MutableLiveData<>();
	private final MutableLiveData<String> url_PNG = new MutableLiveData<>(" ");
	private final MutableLiveData<String> url_Vid = new MutableLiveData<>(" ");
	private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
	private MutableLiveData<String> type = new MutableLiveData<>();



	public MutableLiveData<Integer> getFavoriteItemPosition() {return favoriteItemPosition;}
	public MutableLiveData<ArrayList<PoseModel>> getYoga() {return yoga;}
	public MutableLiveData<Integer> getCat_Id() {return cat_Id;}
	public MutableLiveData<Boolean> getIsLoadingLiveData() {return isLoading;}
	public MutableLiveData<String> getmText() {return mText;}
	public MutableLiveData<String> getEnglish_Name() {return english_Name;}
	public MutableLiveData<String> getSanskrit_Name() {return sanskrit_Name;}
	public MutableLiveData<String> getTranslated_Name() {return translated_Name;}
	public MutableLiveData<String> getPose_Description() {return pose_Description;}
	public MutableLiveData<String> getPose_Benefits() {return pose_Benefits;}
	public MutableLiveData<String> getLevels() {return levels;}
	public MutableLiveData<String> getUrl_PNG() {return url_PNG;}
	public MutableLiveData<String> getVideo() {return url_Vid;}
	public MutableLiveData<String> getType() {return type;}
	public void getClickedItem(int position, ArrayList<PoseModel> list){

		String englishName = list.get(position).getEnglish_name();
		String sanskritName = list.get(position).getSanskrit_name();
		String difficulty = list.get(position).getDifficulty_level();
		String translatedName = list.get(position).getTranslation_name();
		String poseDescription = list.get(position).getPose_description();
		String poseBenefits = list.get(position).getPose_benefits();
		String urlPNG = list.get(position).getUrl_png();
		String urlVid = list.get(position).getUrl_Vid();
		String pose_Type = list.get(position).getPose_Type();

		setEnglish_Name(englishName);
		setSanskrit_Name(sanskritName);
		setLevels(difficulty);
		setTranslated_Name(translatedName);
		setPose_Description(poseDescription);
		setPose_Benefits(poseBenefits);
		setUrl_PNG(urlPNG);
		setUrl_Vid(urlVid);
		setType(pose_Type);
	}
	public MutableLiveData<PoseModel> getLikedFavorites() {return likedFavorites;}

	public YogaViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue(" ");
	}

	public void setFavoriteItemPosition(int pos){favoriteItemPosition.setValue(pos);}
	public void setYoga(ArrayList<PoseModel> allPoses){yoga.setValue(allPoses);}
	public void setLikedFavorites(PoseModel yogaModel){likedFavorites.setValue(yogaModel);}
	public void setType(String types) {type.setValue(types);}
	public void setCat_Id(Integer id){cat_Id.setValue(id);}
	public void setEnglish_Name(String englishName){english_Name.setValue(englishName);}
	public void setSanskrit_Name(String sanskritName){sanskrit_Name.setValue(sanskritName);}
	public void setTranslated_Name(String translatedName){translated_Name.setValue(translatedName);}
	public void setPose_Description(String poseDescription){pose_Description.setValue(poseDescription);}
	public void setPose_Benefits(String benefits){pose_Benefits.setValue(benefits);}
	public void setUrl_PNG(String urlPng){url_PNG.setValue(urlPng);}
	public void setUrl_Vid(String urlVid){url_Vid.setValue(urlVid);}
	public void setLevels(String difficulty_Levels){levels.setValue(difficulty_Levels);}



	public void saveYogaData(PoseModel pose, Context context, String path, String childTitle){

		FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
		DatabaseReference dataRef = FirebaseDatabase.getInstance()
				.getReference(path)
				.child(authUser.getUid())
				.child(childTitle.trim());
		dataRef.setValue(pose).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {

				Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show();
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {


				int maxAttempts = 4;

				for (int i = 0; i < maxAttempts;){
					if(i >= 0){
						i++;
						Toast.makeText(context, "Saved Failed...Please try again", Toast.LENGTH_LONG).show();


					} else if ( i > 3 && i != 0 ) {
						i++;
						Toast.makeText(context, "Check title for errors", Toast.LENGTH_LONG).show();

					}
					else{
						i++;
						Toast.makeText(context, "Sorry for the issues...Restart the application", Toast.LENGTH_LONG).show();
					}
				}


			}
		});
	}
	public void fetchYogaData(ArrayList<PoseModel> arrayList, String string, YogaAdapter adapter){
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection(string).get()
				.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
					@Override
					public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
						if(!queryDocumentSnapshots.isEmpty()){
							List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
							for(DocumentSnapshot d : list){
								PoseModel bodyApi = d.toObject(PoseModel.class);
								arrayList.add(bodyApi);
								//setYoga(bodyApi);
							}
							adapter.notifyDataSetChanged();
						}else{
							//
						}
					}
				}).addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Log.e("error while loading",e.toString());
					}
				});
	}
	public void fetchCustomData(ArrayList<PoseModel> arrayList, String string, CustomsAdapter adapter){
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection(string).get()
				.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
					@Override
					public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
						if(!queryDocumentSnapshots.isEmpty()){
							List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
							for(DocumentSnapshot d : list){
								PoseModel bodyApi = d.toObject(PoseModel.class);
								arrayList.add(bodyApi);
								//setYoga(bodyApi);
							}
							adapter.notifyDataSetChanged();
						}else{
							Log.d("Document Snapshot", "onSuccess: Empty Document");
						}
					}
				}).addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Log.e("error while loading",e.toString());
					}
				});
	}
	public void fetchFavorites(ArrayList<PoseModel> data, FavoriteAdapter adapter){

		FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
		FirebaseDatabase db = FirebaseDatabase.getInstance();
		DatabaseReference dbRef = db.getReference("FavoriteItemYoga")
				.child(authUser.getUid());
		dbRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if(!snapshot.exists()){
					//Toast.makeText(context, "No Runs Completed Yet..", Toast.LENGTH_LONG).show();
				}
				else{
					for(DataSnapshot dataSnapshot : snapshot.getChildren())
					{
						PoseModel faveData =  dataSnapshot.getValue(PoseModel.class);
						data.add(faveData);
						setYoga(data);
					}
					adapter.notifyDataSetChanged();
				}
			}
			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
	public ArrayList<PoseModel> fetchAllData(ArrayList<PoseModel> data){
		//TODO: fetch all yoga levels data and return array list

		return null;
	}
}
