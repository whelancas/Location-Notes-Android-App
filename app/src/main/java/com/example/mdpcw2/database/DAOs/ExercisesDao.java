package com.example.mdpcw2.database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.mdpcw2.database.tables.Exercises;

@Dao
public interface ExercisesDao {
    @Insert
    void insert(Exercises exercises);

    @Update
    void update(Exercises exercises);

    @Delete
    void delete(Exercises exercises);
}
