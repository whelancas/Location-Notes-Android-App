package com.example.mdpcw2.ui.home;

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
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mdpcw2.service.LocationService;
import com.example.mdpcw2.service.MyLocationListener;
import com.example.mdpcw2.R;
import com.example.mdpcw2.databinding.FragmentHomeBinding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;
    boolean mBound = false;
    private MyLocationListener locationListener;
    TextView startLocation, endLocation, distanceTravelled;
    Location lastKnownLocation;
    double latitude, startLatitude, endLatitude, longitude, startLongitude, endLongitude;
    String address;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this)).get(HomeViewModel.class);

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
        distanceTravelled = (TextView) view.findViewById(R.id.homeDistanceTravelledTodayTextView);
        Button newNoteButton = view.findViewById(R.id.homeAddNoteButton);

        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeAddNoteClick(v);
            }
        });

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
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate date = LocalDate.now();
                LocalTime startTime = LocalTime.now();
                Log.d("DateTime", date + " " + startTime);
            }

            String startLocationText = "Start Location: " + address;
            homeViewModel.setStartLocation(address);
            startLocation.setText(startLocationText);

            startLatitude = latitude;
            startLongitude = longitude;

            homeViewModel.setStartLatitude(String.valueOf(startLatitude));
            homeViewModel.setStartLongitude(String.valueOf(startLongitude));

            String endLocationText = "End Location: ";
            endLocation.setText(endLocationText);
            distanceTravelled.setText("Distance Travelled: ");
        } else {
            Log.d("Address", "Address null onStartButton");
        }
    }

    public void onStopExerciseClick(View v) {
        Log.d("Button", "Home Stop Button");
        lastLocation();

        if (address != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalTime endTime = LocalTime.now();
                Log.d("DateTime", String.valueOf(endTime));
            }

            String endLocationText = "End Location: " + address;
            homeViewModel.setEndLocation(address);
            endLocation.setText(endLocationText);

            endLatitude = latitude;
            endLongitude = longitude;

            homeViewModel.setEndLatitude(String.valueOf(endLatitude));
            homeViewModel.setEndLongitude(String.valueOf(endLongitude));

            Location startPoint = new Location("Start");
            startPoint.setLatitude(startLatitude);
            startPoint.setLongitude(startLongitude);

            Location endPoint = new Location("End");
            endPoint.setLatitude(endLatitude);
            endPoint.setLongitude(endLongitude);

            double distance = startPoint.distanceTo(endPoint);
            homeViewModel.setDistanceTravelled(String.valueOf(distance));
            distanceTravelled.setText("Distance Travelled: " + (int) distance + " metres");
        } else {
            Log.d("Address", "Address null onStartButton");
        }
    }

    public void onHomeAddNoteClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_new_note);
    }

    public StringBuilder getAddress(double LATITUDE, double LONGITUDE) {
        Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
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

    public void lastLocation() {
        if (locationListener != null) {
            lastKnownLocation = locationListener.getLastKnownLocation();
            if (lastKnownLocation != null) {
                Log.d("Location", "lastLocation: "+ lastKnownLocation);
                latitude = lastKnownLocation.getLatitude();
                longitude = lastKnownLocation.getLongitude();
                address = String.valueOf(getAddress(latitude, longitude));
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