package com.fitstir.fitstirapp.ui.health.diary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DiaryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<DiaryData> OGdiaryData = new MutableLiveData<>();
    private final MutableLiveData<DiaryData> newDiaryData = new MutableLiveData<>();
    private final MutableLiveData<String> previousFragment = new MutableLiveData<>("");

    public DiaryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diary fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



    public void setOGdiaryData(DiaryData OGdiaryData) {
        this.OGdiaryData.setValue(OGdiaryData);
    }
    public MutableLiveData<DiaryData> getOGdiaryData() {
        return OGdiaryData;
    }

    public void setNewDiaryData(DiaryData newDiaryData) {
        this.newDiaryData.setValue(newDiaryData);
    }
    public MutableLiveData<DiaryData> getNewDiaryData() {
        return newDiaryData;
    }

    public void setPreviousFragment(String previousFragment) {
        this.previousFragment.setValue(previousFragment);
    }
    public MutableLiveData<String> getPreviousFragment() {
        return previousFragment;
    }

    public static DiaryData getData() {
        Calendar cal = Calendar.getInstance();
        Date _0 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date _1 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date _2 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date _3 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date _4 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date _5 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date _6 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date _7 = cal.getTime();

        ArrayList<DiaryEntry> emotions = new ArrayList<DiaryEntry>() {{
            add(new DiaryEntry(_7, "\uD83E\uDD2A", "foobarfo"));
            add(new DiaryEntry(_6, "\uD83D\uDE33", "foobarf"));
            add(new DiaryEntry(_5, "\uD83E\uDD29", "foobar"));
            add(new DiaryEntry(_4, "\uD83D\uDE07", "fooba"));
            add(new DiaryEntry(_3, "\uD83D\uDE2D", "foob"));
            add(new DiaryEntry(_2, "\uD83E\uDD70", "foo"));
            add(new DiaryEntry(_1, "\uD83E\uDD73", "fo"));
            add(new DiaryEntry(_0, "\uD83E\uDD2C", "f"));
        }};

        ArrayList<Task> tasks = new ArrayList<Task>() {{
            Task _t1 = new Task("f");
            _t1.addDate(_6);
            _t1.addDate(_4);
            _t1.addDate(_3);
            _t1.addDate(_2);
            _t1.addDate(_0);
            Task _t2 = new Task("fo");
            _t2.addDate(_7);
            _t2.addDate(_6);
            _t2.addDate(_5);
            _t2.addDate(_3);
            _t2.addDate(_0);
            Task _t3 = new Task("foo");
            _t3.addDate(_5);
            _t3.addDate(_3);
            _t3.addDate(_2);
            _t3.addDate(_1);
            _t3.addDate(_0);
            Task _t4 = new Task("foob");
            _t4.addDate(_7);
            _t4.addDate(_6);
            _t4.addDate(_5);
            _t4.addDate(_4);
            _t4.addDate(_2);
            Task _t5 = new Task("fooba");
            _t5.addDate(_4);
            _t5.addDate(_1);
            Task _t6 = new Task("foobar");
            _t6.addDate(_7);
            Task _t7 = new Task("foobarf");
            _t7.addDate(_4);
            _t7.addDate(_0);
            Task _t8 = new Task("foobarfo");
            _t8.addDate(_2);
            _t8.addDate(_1);
            Task _t9 = new Task("foobarfoo");
            Task _t0 = new Task("foobarfoob");
            _t0.addDate(_1);

            add(_t1);
            add(_t2);
            add(_t3);
            add(_t4);
            add(_t5);
            add(_t6);
            add(_t7);
            add(_t8);
            add(_t9);
            add(_t0);
        }};

        DiaryData data = new DiaryData();
        data.setEmotions(emotions);
        data.setTasks(tasks);

        return data;
    }
}
