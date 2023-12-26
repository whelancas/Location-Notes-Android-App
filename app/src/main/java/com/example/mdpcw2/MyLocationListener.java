package com.example.mdpcw2;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {
    private Location lastKnownLocation;
    @Override
    public void onLocationChanged(Location location) {
        // Handle location updates here
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        lastKnownLocation = location;

        Log.d("Location", latitude + " " + longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Handle status changes if needed

        Log.d("Location", "onStatusChanged: " + provider + " " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Handle provider enabled

        Log.d("Location", "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Handle provider disabled

        Log.d("Location", "onProviderDisabled: " + provider);

    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }
}