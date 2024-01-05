package com.example.mdpcw2.database.tables;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

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

    public Exercises(String date, String startTime, String startLatitude, String startLongitude, String endTime, String endLatitude, String endLongitude) {
        this.date = date;
        this.startTime = startTime;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endTime = endTime;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }
}

