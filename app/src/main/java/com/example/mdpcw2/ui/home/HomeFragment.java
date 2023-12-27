package com.example.mdpcw2.ui.home;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
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

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;
    boolean mBound = false;
    private MyLocationListener locationListener;
    TextView startLocation;
    TextView endLocation;
    Location lastKnownLocation;
    double latitude;
    double longitude;
    String address;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(!mBound){
            Intent serviceIntent = new Intent(requireActivity(), LocationService.class);
            requireActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startExerciseButton = view.findViewById(R.id.homeStartExerciseButton);
        Button stopExerciseButton = view.findViewById(R.id.homeStopExerciseButton);
        startLocation = (TextView) view.findViewById(R.id.homeStartLocationTextView);
        endLocation = (TextView) view.findViewById(R.id.homeEndLocationTextView);

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
            LocationService mService = binder.getService();
            locationListener = mService.getLocationListener();
            mBound = true;
            Log.d("Service", "Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.d("Service", "Service disconnected");
        }
    };


    public void onStartExerciseClick(View v) {
        Log.d("Button", "Home Start Button");
        lastLocation();

        if (address != null) {
            String startLocationText = "Start Location: " + address;
            homeViewModel.setStartLocation(address);
            startLocation.setText(startLocationText);
        } else {
            Log.d("Address", "Address null onStartButton");
        }
    }

    public void onStopExerciseClick(View v) {
        Log.d("Button", "Home Stop Button");
        lastLocation();

        if (address != null) {
            String endLocationText = "End Location: " + address;
            homeViewModel.setEndLocation(address);
            endLocation.setText(endLocationText);
        } else {
            Log.d("Address", "Address null onStartButton");
        }
    }

    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Address", "getCompleteStringAddress: "+strReturnedAddress.toString());
            } else {
                Log.w("Address", "No Address Returned");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Address", "Cannot Get Address");
        }
        return strAdd;
    }

    public void lastLocation() {
        if (locationListener != null) {
            lastKnownLocation = locationListener.getLastKnownLocation();
            if (lastKnownLocation != null) {
                Log.d("Location", "lastLocation: "+String.valueOf(lastKnownLocation));
                latitude = lastKnownLocation.getLatitude();
                longitude = lastKnownLocation.getLongitude();
                address = getCompleteAddressString(latitude, longitude);
            }
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