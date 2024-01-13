package com.example.mdpcw2.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> startLocation = new MutableLiveData<>();
    private final MutableLiveData<String> endLocation = new MutableLiveData<>();
    private final MutableLiveData<String> distanceTravelled = new MutableLiveData<>();
    private final MutableLiveData<String> startLatitude = new MutableLiveData<>();
    private final MutableLiveData<String> startLongitude = new MutableLiveData<>();
    private final MutableLiveData<String> endLatitude = new MutableLiveData<>();
    private final MutableLiveData<String> endLongitude = new MutableLiveData<>();

    public LiveData<String> getStartLocation() {
        return startLocation;
    }

    public LiveData<String> getEndLocation() {
        return endLocation;
    }

    public LiveData<String> getDistanceTravelled() {
        return distanceTravelled;
    }

    public MutableLiveData<String> getStartLatitude() {
        return startLatitude;
    }

    public MutableLiveData<String> getStartLongitude() {
        return startLongitude;
    }

    public MutableLiveData<String> getEndLatitude() {
        return endLatitude;
    }

    public MutableLiveData<String> getEndLongitude() {
        return endLongitude;
    }

    public void setStartLocation(String location) {
        startLocation.setValue(location);
    }

    public void setEndLocation(String location) {
        endLocation.setValue(location);
    }

    public void setDistanceTravelled(String distance) {
        distanceTravelled.setValue(distance);
    }

    public void setStartLatitude(String latitude) {
        startLatitude.setValue(latitude);
    }

    public void setStartLongitude(String longitude) {
        startLongitude.setValue(longitude);
    }

    public void setEndLatitude(String latitude) {
        endLatitude.setValue(latitude);
    }

    public void setEndLongitude(String longitude) {
        endLongitude.setValue(longitude);
    }

}

