package com.fitstir.fitstirapp.ui.health.diary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiaryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<DiaryData> diaryData = new MutableLiveData<>();

    public DiaryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diary fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



    public void setDiaryData(DiaryData diaryData) {
        this.diaryData.setValue(diaryData);
    }
    public MutableLiveData<DiaryData> getDiaryData() {
        return diaryData;
    }
}
