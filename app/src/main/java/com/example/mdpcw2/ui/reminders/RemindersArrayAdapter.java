package com.example.mdpcw2.ui.reminders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mdpcw2.R;
import com.example.mdpcw2.database.tables.Exercises;
import com.example.mdpcw2.database.tables.Reminders;
import com.example.mdpcw2.service.MyLocationListener;

import java.util.List;

public class RemindersArrayAdapter extends ArrayAdapter<Reminders> {

    MyLocationListener locationListener = new MyLocationListener();

    public RemindersArrayAdapter(@NonNull Context context, @NonNull List<Reminders> objects) {
        super(context, 0, objects);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Reminders reminders = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reminders_list_item, parent, false);
        }

        // Populate TextViews with field values
        TextView textViewReminder = convertView.findViewById(R.id.textViewReminder);
        TextView textViewAddress = convertView.findViewById(R.id.textViewAddress);
        TextView textViewDistance = convertView.findViewById(R.id.textViewDistance);


        // Set values to TextViews
        if (reminders != null) {

            String address = String.valueOf(locationListener.getAddress(getContext(), Double.parseDouble(reminders.getLatitude()), Double.parseDouble(reminders.getLongitude())));
            double distance = 0;

            if(RemindersFragment.lastKnownLocation != null) {
                distance = Double.parseDouble(String.format("%.2f", locationListener.getDistance(Double.parseDouble(reminders.getLatitude()),
                        Double.parseDouble(reminders.getLongitude()), Double.parseDouble(String.valueOf(RemindersFragment.lastKnownLocation.getLatitude())),
                        Double.parseDouble(String.valueOf(RemindersFragment.lastKnownLocation.getLongitude())))));
            }

            textViewReminder.setText("Reminder: " + reminders.getReminder());
            textViewAddress.setText("Location: " + address);
            textViewDistance.setText("Distance: " + distance + "m");
        }

        return convertView;
    }
}
