package com.example.mdpcw2.service;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.example.mdpcw2.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    public void setLastKnownLocation(Location location) {
        lastKnownLocation = location;
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

    public double getDistance(double startLat, double startLong, double endLat, double endLong) {
        Location startPoint = new Location("Start");
        startPoint.setLatitude(startLat);
        startPoint.setLongitude(startLong);

        Location endPoint = new Location("End");
        endPoint.setLatitude(endLat);
        endPoint.setLongitude(endLong);

        return startPoint.distanceTo(endPoint);
    }

    public StringBuilder getAddress(Context context, double LATITUDE, double LONGITUDE) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        StringBuilder strAddress = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                strAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                Log.w("Address", "getCompleteStringAddress: " + strAddress);
            } else {
                Log.w("Address", "No Address Returned");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Address", "Cannot Get Address");
        }
        return strAddress;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latLong = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.isEmpty()) {
                Log.d("Address", "Address is null");
                return null;
            }

            Address location = address.get(0);
            latLong = new LatLng(location.getLatitude(), location.getLongitude() );
            Log.d("Address", "Address: " + address + " gives lat/long: " + latLong);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return latLong;
    }

}