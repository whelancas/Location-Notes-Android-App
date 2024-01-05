package com.example.mdpcw2.database.DOAs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.mdpcw2.database.tables.Exercises;
import com.example.mdpcw2.database.tables.Reminders;

@Dao
public interface RemindersDao {
    @Insert
    void insert(Reminders reminders);

    @Update
    void update(Reminders reminders);

    @Delete
    void delete(Reminders reminders);
}
