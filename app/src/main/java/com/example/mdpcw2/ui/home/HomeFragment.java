package com.example.mdpcw2.ui.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mdpcw2.LocationService;
import com.example.mdpcw2.MyLocationListener;
import com.example.mdpcw2.R;
import com.example.mdpcw2.databinding.FragmentHomeBinding;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    boolean mBound = false;
    private LocationService mService;
    private MyLocationListener locationListener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startExerciseButton = view.findViewById(R.id.homeStartExerciseButton);
        Button stopExerciseButton = view.findViewById(R.id.homeStopExerciseButton);

        startExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartExerciseClick(v);
            }
        });

        stopExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopExerciseClick(v);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            mService = binder.getService();
            locationListener = mService.getLocationListener();
            mBound = true;
            Log.d("mdpcw2", "Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.d("mdpcw2", "Service disconnected");
        }
    };


    public void onStartExerciseClick(View v) {
        Log.d("mdpcw2", "Start Button");
        if(!mBound) {
            Intent serviceIntent = new Intent(requireActivity(), LocationService.class);
            requireActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }

        if (LocationService.isServiceRunning(requireActivity(), LocationService.class)) {
            Log.d("mdpcw2", "Service running");
        } else {
            Log.d("mdpcw2", "Service not running");
        }
    }

    public void onStopExerciseClick(View v) {
        Log.d("mdpcw2", "Stop Button");
        if(mBound) {

            requireActivity().unbindService(serviceConnection);
            mBound = false;
        }

        if (LocationService.isServiceRunning(requireActivity(), LocationService.class)) {
            Log.d("mdpcw2", "Service running");

            if (locationListener != null) {
                Location lastKnownLocation = locationListener.getLastKnownLocation();
                if (lastKnownLocation != null) {
                    double latitude = lastKnownLocation.getLatitude();
                    double longitude = lastKnownLocation.getLongitude();
                    Log.d("mdpcw2", "Last known location: " + latitude + " " + longitude);
                }
            }
        } else {
            Log.d("mdpcw2", "Service not running");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBound) {
            requireActivity().unbindService(serviceConnection);
            mBound = false;
        }
    }
}