package com.example.mdpcw2.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mStartLocation = new MutableLiveData<>();
    private final MutableLiveData<String> mEndLocation = new MutableLiveData<>();

    public String getStartLocationText() {
        return "Start Location: " + mStartLocation;
    }

    public LiveData<String> getStartLocation() {
        return mStartLocation;
    }

    public void setStartLocation(String location) {
        mStartLocation.setValue(location);
    }

    public String getEndLocationText() {
        return "End Location: " + mEndLocation;
    }

    public LiveData<String> getEndLocation() {
        return mEndLocation;
    }

    public void setEndLocation(String location) {
        mStartLocation.setValue(location);
    }

}

