package com.fitstir.fitstirapp.ui.yoga.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.YogaAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YogaViewModel extends ViewModel {

	private final MutableLiveData<String> mText;
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


	public YogaViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue(" ");
	}


	public void setCat_Id(Integer id){cat_Id.setValue(id);}
	public void setEnglish_Name(String englishName){english_Name.setValue(englishName);}
	public void setSanskrit_Name(String sanskritName){sanskrit_Name.setValue(sanskritName);}
	public void setTranslated_Name(String translatedName){translated_Name.setValue(translatedName);}
	public void setPose_Description(String poseDescription){pose_Description.setValue(poseDescription);}
	public void setPose_Benefits(String benefits){pose_Benefits.setValue(benefits);}
	public void setUrl_PNG(String urlPng){url_PNG.setValue(urlPng);}
	public void setUrl_Vid(String urlVid){url_Vid.setValue(urlVid);}
	public void setLevels(String difficulty_Levels){levels.setValue(difficulty_Levels);}
	public void getClickedItem(int position, ArrayList<PoseModel> list){

		String englishName = list.get(position).getEnglish_name();
		String sanskritName = list.get(position).getSanskrit_name();
		String translatedName = list.get(position).getTranslation_name();
		String poseDescription = list.get(position).getPose_description();
		String poseBenefits = list.get(position).getPose_benefits();
		String urlPNG = list.get(position).getUrl_png();
		String urlVid = list.get(position).getUrl_Vid();


		setEnglish_Name(englishName);
		setSanskrit_Name(sanskritName);
		setTranslated_Name(translatedName);
		setPose_Description(poseDescription);
		setPose_Benefits(poseBenefits);
		setUrl_PNG(urlPNG);
		setUrl_Vid(urlVid);

	}



}
