package com.example.mdpcw2.database.tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reminders {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int reminderID;
    @NonNull
    public String latitude;
    @NonNull
    public String longitude;
    @NonNull
    public String reminder;

    public Reminders() {}

    public Reminders(String latitude, String longitude, String reminder) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.reminder = reminder;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getReminder() {
        return reminder;
    }
}
