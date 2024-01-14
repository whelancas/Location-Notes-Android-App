package com.example.mdpcw2.ui.notebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mdpcw2.MainActivity;
import com.example.mdpcw2.R;
import com.example.mdpcw2.database.tables.Exercises;
import com.example.mdpcw2.service.MyLocationListener;

import java.util.List;

public class ExercisesArrayAdapter extends ArrayAdapter<Exercises> {

    MyLocationListener locationListener = new MyLocationListener();

    public ExercisesArrayAdapter(@NonNull Context context, @NonNull List<Exercises> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Exercises exercises = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notebook_list_item, parent, false);
        }

        // Populate TextViews with field values
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);
        TextView textViewStartTime = convertView.findViewById(R.id.textViewStartTime);
        TextView textViewStartAddress = convertView.findViewById(R.id.textViewStartAddress);
        TextView textViewEndTime = convertView.findViewById(R.id.textViewEndTime);
        TextView textViewEndAddress = convertView.findViewById(R.id.textViewEndAddress);
        TextView textViewDistance = convertView.findViewById(R.id.textViewDistance);

        // Set values to TextViews
        if (exercises != null) {
            String startAddress = String.valueOf(locationListener.getAddress(getContext(), Double.parseDouble(exercises.getStartLatitude()), Double.parseDouble(exercises.getStartLongitude())));
            String endAddress = String.valueOf(locationListener.getAddress(getContext(), Double.parseDouble(exercises.getEndLatitude()), Double.parseDouble(exercises.getEndLongitude())));
            @SuppressLint("DefaultLocale") double distance = Double.parseDouble(String.format("%.2f", locationListener.getDistance(Double.parseDouble(exercises.getStartLatitude()),
                    Double.parseDouble(exercises.getStartLongitude()), Double.parseDouble(exercises.getEndLatitude()),
                    Double.parseDouble(exercises.getEndLongitude()))));

            textViewDate.setText("Date: " + exercises.getDate());
            textViewStartTime.setText("Start Time: " + exercises.getStartTime());
            textViewStartAddress.setText("Start Location: " + startAddress);
            textViewEndTime.setText("End Time: " + exercises.getEndTime());
            textViewEndAddress.setText("End Location: " + endAddress);
            textViewDistance.setText("Distance: " + distance + "m");
        }

        return convertView;
    }
}
