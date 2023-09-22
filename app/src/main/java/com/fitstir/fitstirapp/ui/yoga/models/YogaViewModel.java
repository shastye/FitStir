package com.fitstir.fitstirapp.ui.yoga.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.runtracker.utilites.RunnerData;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.fitstir.fitstirapp.ui.yoga.IYogaApi;
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

	private IYogaApi apiInterface;
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final MutableLiveData<String> mText;
	private final MutableLiveData<String> category_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> english_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> adapted_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> sanskrit_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> translated_Name = new MutableLiveData<>(" ");
	private final MutableLiveData<String> poses = new MutableLiveData<>(" ");
	private final MutableLiveData<String> pose_Description = new MutableLiveData<>(" ");
	private final MutableLiveData<String> pose_Benefits = new MutableLiveData<>(" ");
	private final MutableLiveData<List<PoseModel>> levels = new MutableLiveData<>();
	private final MutableLiveData<String> url_SVG = new MutableLiveData<>(" ");
	private final MutableLiveData<String> url_PNG = new MutableLiveData<>(" ");
	private final MutableLiveData<String> url_ALT = new MutableLiveData<>(" ");
	private final MutableLiveData<Integer> cat_Id = new MutableLiveData<>(0);
	private final MutableLiveData<List<CategoryModel>> responseCat = new MutableLiveData<>();
	private final MutableLiveData<List<PoseModel>> responsePose = new MutableLiveData<>();
	private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
	private MutableLiveData<String> error = new MutableLiveData<>();




	public MutableLiveData<Boolean> getIsLoadingLiveData() {return isLoading;}
	public MutableLiveData<String> getErrorLiveData() {return error;}
	public LiveData<List<CategoryModel>> getResponseCat() {return responseCat;}
	public LiveData<List<PoseModel>> getResponsePose() {return responsePose;}
	public MutableLiveData<String> getmText() {return mText;}
	public MutableLiveData<String> getCat_Name() {return category_Name;}
	public MutableLiveData<String> getEnglish_Name() {return english_Name;}
	public MutableLiveData<String> getAdapted_Name() {return adapted_Name;}
	public MutableLiveData<String> getSanskrit_Name() {return sanskrit_Name;}
	public MutableLiveData<String> getTranslated_Name() {return translated_Name;}
	public MutableLiveData<String> getPoses() {return poses;}
	public MutableLiveData<String> getPose_Description() {return pose_Description;}
	public MutableLiveData<String> getPose_Benefits() {return pose_Benefits;}
	public MutableLiveData<List<PoseModel>> getLevels() {return levels;}
	public MutableLiveData<String> getUrl_SVG() {return url_SVG;}
	public MutableLiveData<String> getUrl_PNG() {return url_PNG;}
	public MutableLiveData<String> getUrl_ALT() {return url_ALT;}
	public MutableLiveData<Integer> getCat_Id() {return cat_Id;}

	public YogaViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue(" ");
		apiInterface = RetrofitClientObject.getInstance().create(IYogaApi.class);
	}

	public void setCat_Name(String catName){category_Name.setValue(catName);}
	public void setEnglish_Name(String englishName){english_Name.setValue(englishName);}
	public void setAdapted_Name(String adaptedName){adapted_Name.setValue(adaptedName);}
	public void setSanskrit_Name(String sanskritName){sanskrit_Name.setValue(sanskritName);}
	public void setTranslated_Name(String translatedName){translated_Name.setValue(translatedName);}
	public void setPose_Description(String poseDescription){pose_Description.setValue(poseDescription);}
	public void setPose_Benefits(String benefits){pose_Benefits.setValue(benefits);}
	public void setUrl_PNG(String urlPng){url_PNG.setValue(urlPng);}
	public void setUrl_SVG(String urlSvg){url_SVG.setValue(urlSvg);}
	public void setUrl_ALT(String urlAlt){url_ALT.setValue(urlAlt);}
	public void setLevels(List<PoseModel> difficulty_Levels){levels.setValue(difficulty_Levels);}
	public void setPoses(String pose){poses.setValue(pose);}
	public void setCat_Id(Integer id){cat_Id.setValue(id);}
	public void setPoseResponse(List<PoseModel> poseResponse){this.responsePose.setValue(poseResponse);}
	public void setCatResponse(List<CategoryModel> catResponse){this.responseCat.setValue(catResponse);}

	public void fetchApiData( YogaAdapter adapter){
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try{
					Response<List<PoseModel>> posesResponse = apiInterface.getPoses().execute();
						if (posesResponse.isSuccessful()) {
							final List<PoseModel> poses = posesResponse.body();
							responsePose.postValue(poses);
							adapter.notifyDataSetChanged();

					} else {
						// Handle error for categories request
					}
				}catch(IOException e){
					e.printStackTrace();
					Log.e("Error", e.getMessage());
				}
				apiInterface.getPoses().enqueue(new Callback<List<PoseModel>>() {
					@Override
					public void onResponse(Call<List<PoseModel>> call, Response<List<PoseModel>> response) {
						if (response.isSuccessful()) {
							final List<PoseModel> pose = response.body();
							responsePose.postValue(pose);

						}else{
							//handle error
						}
					}
					@Override
					public void onFailure(Call<List<PoseModel>> call, Throwable t) {
						//handle this error
					}
				});
			}
		});
	}

	public void getClickedItem(int position, ArrayList<PoseModel> list){

		String englishName = list.get(position).getEnglish_name();
		String adaptedName = list.get(position).getSanskrit_name_adapted();
		String sanskritName = list.get(position).getSanskrit_name();
		String translatedName = list.get(position).getTranslation_name();
		String poseDescription = list.get(position).getPose_description();
		String poseBenefits = list.get(position).getPose_benefits();
		String urlPNG = list.get(position).getUrl_png();
		String urlSVG = list.get(position).getUrl_svg();
		String urlALT = list.get(position).getUrl_svg_alt();

		setEnglish_Name(englishName);
		setAdapted_Name(adaptedName);
		setSanskrit_Name(sanskritName);
		setTranslated_Name(translatedName);
		setPose_Description(poseDescription);
		setPose_Benefits(poseBenefits);
		setUrl_PNG(urlPNG);
		setUrl_SVG(urlSVG);
		setUrl_ALT(urlALT);
	}

	@Override
	public void onCleared(){
		super.onCleared();
		executorService.shutdown();
	}

}
