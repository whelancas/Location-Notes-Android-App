package com.example.mdpcw2.ui.reminders;

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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.example.mdpcw2.R;
import com.example.mdpcw2.database.AppDatabase;
import com.example.mdpcw2.database.DAOs.RemindersDao;
import com.example.mdpcw2.database.tables.Reminders;
import com.example.mdpcw2.databinding.FragmentRemindersBinding;
import com.example.mdpcw2.service.LocationService;
import com.example.mdpcw2.service.MyLocationListener;

import java.util.List;
import java.util.concurrent.Executors;

public class RemindersFragment extends Fragment {

    AppDatabase db;
    RemindersDao remindersDao;
    MyLocationListener locationListener;
    public static Location lastKnownLocation;
    Boolean mBound = false;

    private FragmentRemindersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RemindersViewModel remindersViewModel =
                new ViewModelProvider(this).get(RemindersViewModel.class);

        binding = FragmentRemindersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Room.databaseBuilder(requireActivity(),
                AppDatabase.class, "AppDatabase").build();
        remindersDao = db.remindersDao();

        if(!mBound){
            Intent serviceIntent = new Intent(requireActivity(), LocationService.class);
            requireActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            Log.d("Service", "Service connected in Reminders (onCreateView)");
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
            Log.d("Service", "Service connected in Reminders (onServiceConnected)");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.d("Service", "Service disconnected in Reminders");
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Service", "onViewCreated");

        Button newReminderButton = view.findViewById(R.id.remindersAddReminderButton);

        if(mBound) {
            lastKnownLocation = locationListener.getLastKnownLocation();
            Log.d("Reminders", String.valueOf(lastKnownLocation));
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Reminders> remindersList = remindersDao.getAll();

            // Update UI on the main thread
            requireActivity().runOnUiThread(() -> {
                if(!remindersList.isEmpty() && remindersList != null) {
                    ListView listView = view.findViewById(R.id.remidersListView);
                    RemindersArrayAdapter arrayAdapter = new RemindersArrayAdapter(
                            requireActivity(),
                            remindersList);

                    listView.setAdapter(arrayAdapter);
                }
            });
        });

        newReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReminderAddReminderClick(v);
            }
        });
    }

    public void onReminderAddReminderClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_navigation_reminders_to_newReminderFragment);
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