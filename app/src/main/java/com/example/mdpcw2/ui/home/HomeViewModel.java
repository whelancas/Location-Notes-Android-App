package com.example.mdpcw2.ui.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mdpcw2.LocationService;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mStartLocationText = new MutableLiveData<>();

    public LiveData<String> getStartLocationText() {
        return mStartLocationText;
    }

    public void setStartLocation(String location) {
        mStartLocationText.setValue(location);
    }

}

