package com.example.mdpcw2;

import android.Manifest;
import android.app.ActivityManager;
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
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class LocationService extends Service {
    private static final long MIN_TIME_BETWEEN_UPDATES = 5;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    private static final String CHANNEL_ID = "LocationService Channel";
    private static final CharSequence CHANNEL_NAME = "Location Updates";
    private LocationManager locationManager;
    private final IBinder binder = new LocalBinder();
    MyLocationListener locationListener = new MyLocationListener();

    public MyLocationListener getLocationListener() {
        return locationListener;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "onStartCommand called");

        // Create a notification for the foreground service
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(CHANNEL_ID)
                .setContentText(CHANNEL_NAME)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();
        Log.d("Service", "Create notification");

        // Start the service as a foreground service
        startForeground(1, notification);
        Log.d("Service", "Start foreground service");

        // Location management from Coursework Specification
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BETWEEN_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        locationListener);
                Log.d("Service", "Service requested location");
            } catch(SecurityException e) {
                Log.d("Service", e.toString());
            }
        }

        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = (String) CHANNEL_NAME;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Log.d("Service", "Create notification channel");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the foreground service
        stopForeground(true);

        // Stop location updates when the service is destroyed
        if (locationManager != null) {
            locationManager.removeUpdates((LocationListener) this);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopForeground(STOP_FOREGROUND_DETACH);
        stopSelf();
        System.exit(0);
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        // Check if a service is running
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }

        return false;
    }
}


