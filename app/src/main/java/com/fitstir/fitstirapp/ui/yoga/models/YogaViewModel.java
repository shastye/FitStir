package com.fitstir.fitstirapp.ui.yoga.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.security.PrivilegedAction;

public class YogaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> cat_Name = new MutableLiveData<>(" ");
    private final MutableLiveData<String> english_Name = new MutableLiveData<>(" ");
    private final MutableLiveData<String> adapted_Name = new MutableLiveData<>(" ");
    private final MutableLiveData<String> sanskrit_Name = new MutableLiveData<>(" ");
    private final MutableLiveData<String> translated_Name = new MutableLiveData<>(" ");
    private final MutableLiveData<String> poses = new MutableLiveData<>(" ");
    private final MutableLiveData<String> pose_Description = new MutableLiveData<>(" ");
    private final MutableLiveData<String> pose_Benefits = new MutableLiveData<>(" ");
    private final MutableLiveData<String> levels = new MutableLiveData<>(" ");
    private final MutableLiveData<String> url_SVG = new MutableLiveData<>(" ");
    private final MutableLiveData<String> url_PNG = new MutableLiveData<>(" ");
    private final MutableLiveData<String> url_ALT = new MutableLiveData<>(" ");
    private final MutableLiveData<Integer> cat_Id = new MutableLiveData<>(0);


    public MutableLiveData<String> getmText() {return mText;}
    public MutableLiveData<String> getCat_Name() {return cat_Name;}
    public MutableLiveData<String> getEnglish_Name() {return english_Name;}
    public MutableLiveData<String> getAdapted_Name() {return adapted_Name;}
    public MutableLiveData<String> getSanskrit_Name() {return sanskrit_Name;}
    public MutableLiveData<String> getTranslated_Name() {return translated_Name;}
    public MutableLiveData<String> getPoses() {return poses;}
    public MutableLiveData<String> getPose_Description() {return pose_Description;}
    public MutableLiveData<String> getPose_Benefits() {return pose_Benefits;}
    public MutableLiveData<String> getLevels() {return levels;}
    public MutableLiveData<String> getUrl_SVG() {return url_SVG;}
    public MutableLiveData<String> getUrl_PNG() {return url_PNG;}
    public MutableLiveData<String> getUrl_ALT() {return url_ALT;}
    public MutableLiveData<Integer> getCat_Id() {return cat_Id;}

    public YogaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(" ");
    }

    public void setCat_Name(String catName){cat_Name.setValue(catName);}
    public void setEnglish_Name(String englishName){english_Name.setValue(englishName);}
    public void setAdapted_Name(String adaptedName){adapted_Name.setValue(adaptedName);}
    public void setSanskrit_Name(String sanskritName){sanskrit_Name.setValue(sanskritName);}
    public void setTranslated_Name(String translatedName){translated_Name.setValue(translatedName);}
    public void setPose_Description(String poseDescription){pose_Description.setValue(poseDescription);}
    public void setPose_Benefits(String benefits){pose_Benefits.setValue(benefits);}
    public void setUrl_PNG(String urlPng){url_PNG.setValue(urlPng);}
    public void setUrl_SVG(String urlSvg){url_SVG.setValue(urlSvg);}
    public void setUrl_ALT(String urlAlt){url_ALT.setValue(urlAlt);}
    public void setLevels(String _levels){levels.setValue(_levels);}
    public void setPoses(String pose){poses.setValue(pose);}
    public void setCat_Id(Integer id){cat_Id.setValue(id);}





}
