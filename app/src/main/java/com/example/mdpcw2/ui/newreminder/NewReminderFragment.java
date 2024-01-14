package com.example.mdpcw2.ui.newreminder;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mdpcw2.R;
import com.example.mdpcw2.database.AppDatabase;
import com.example.mdpcw2.database.DAOs.ExercisesDao;
import com.example.mdpcw2.database.DAOs.RemindersDao;
import com.example.mdpcw2.database.tables.Exercises;
import com.example.mdpcw2.database.tables.Reminders;
import com.example.mdpcw2.databinding.FragmentNewReminderBinding;
import com.example.mdpcw2.service.LocationService;
import com.example.mdpcw2.service.MyLocationListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.Executors;

public class NewReminderFragment extends Fragment {

    private FragmentNewReminderBinding binding;
    AppDatabase db;
    RemindersDao remindersDao;
    MyLocationListener locationListener;
    EditText reminder, address;
    Boolean mBound = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewReminderViewModel NewReminderViewModel =
                new ViewModelProvider(this).get(NewReminderViewModel.class);

        binding = FragmentNewReminderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Room.databaseBuilder(requireActivity(), AppDatabase.class, "AppDatabase").build();
        remindersDao  = db.remindersDao();

        if(!mBound){
            Intent serviceIntent = new Intent(requireActivity(), LocationService.class);
            requireActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }

        return root;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button useCurrentLocationButton = view.findViewById(R.id.newReminderSetCurrentLocationButton);
        Button useCustomLocationButton = view.findViewById(R.id.setReminderCustomLocationButton);
        Button cancelButton = view.findViewById(R.id.newReminderCancelButton);
        reminder = view.findViewById(R.id.newReminderEditText);
        address = view.findViewById(R.id.newReminderAddressEditText);

        useCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useCurrentLocation(v);
            }
        });

        useCustomLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { useCustomLocation(v); }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Navigation.findNavController(v).navigate(R.id.action_navigation_new_reminder_to_navigation_reminders); }
        });
    }

    private void useCustomLocation(View v) {

        Executors.newSingleThreadExecutor().execute(() -> {
            LatLng latLong = locationListener.getLocationFromAddress(getContext(), String.valueOf(address));
            remindersDao.insert(new Reminders(String.valueOf(latLong.latitude), String.valueOf(latLong.longitude), reminder.getText().toString()));
        });

        Navigation.findNavController(v).navigate(R.id.action_navigation_new_reminder_to_navigation_reminders);
    }

    private void useCurrentLocation(View v) {

        Executors.newSingleThreadExecutor().execute(() -> {
            Location location = locationListener.getLastKnownLocation();
            remindersDao.insert(new Reminders(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), reminder.getText().toString()));
        });

        Navigation.findNavController(v).navigate(R.id.action_navigation_new_reminder_to_navigation_reminders);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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