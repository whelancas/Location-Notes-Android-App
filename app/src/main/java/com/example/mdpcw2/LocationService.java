package com.example.mdpcw2;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class LocationService extends Service implements LocationListener {
    private static final long MIN_TIME_BETWEEN_UPDATES = 5;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    private static final String CHANNEL_ID = "LocationService Channel";
    private static final CharSequence CHANNEL_NAME = "Location Updates";
    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Set up the LocationManager and request location updates here
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BETWEEN_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    this
            );
        }

        // Create a notification for the foreground service
        Notification notification = buildNotification();

        // Start the service as a foreground service
        startForeground(1, notification);

        return START_STICKY;
    }

    private Notification buildNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class); // Replace with your activity
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("GeoTracker")
                .setContentText("Tracking your location.")
                .setContentIntent(pendingIntent)
                .build();
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the foreground service
        stopForeground(true);

        // Stop location updates when the service is destroyed
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
}
