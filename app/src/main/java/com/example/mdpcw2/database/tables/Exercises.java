package com.example.mdpcw2.database.tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Exercises {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int exerciseID;
    @NonNull
    public String date;
    @NonNull
    public String startTime;
    @NonNull
    public String endTime;
    @NonNull
    public String startLatitude;
    @NonNull
    public String startLongitude;
    @NonNull
    public String endLatitude;
    @NonNull
    public String endLongitude;

    public Exercises() {
    }

    public Exercises(@NonNull LocalDate date, @NonNull LocalTime startTime, double startLatitude,
                     double startLongitude, @NonNull LocalTime endTime, double endLatitude, double endLongitude) {
        this.date = String.valueOf(date);
        this.startTime = String.valueOf(startTime);
        this.startLatitude = String.valueOf(startLatitude);
        this.startLongitude = String.valueOf(startLongitude);
        this.endTime = String.valueOf(endTime);
        this.endLatitude = String.valueOf(endLatitude);
        this.endLongitude = String.valueOf(endLongitude);
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }
}

