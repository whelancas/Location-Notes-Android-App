package com.example.mdpcw2.database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mdpcw2.database.tables.Exercises;

import java.util.List;

@Dao
public interface ExercisesDao {
    @Query("SELECT * FROM exercises")
    List<Exercises> getAll();

    @Insert
    void insert(Exercises exercises);

    @Update
    void update(Exercises exercises);

    @Delete
    void delete(Exercises exercises);
}
