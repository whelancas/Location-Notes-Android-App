package com.example.mdpcw2;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        // Handle location updates here
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        // Do something with the latitude and longitude
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Handle status changes if needed
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Handle provider enabled
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Handle provider disabled
    }
}