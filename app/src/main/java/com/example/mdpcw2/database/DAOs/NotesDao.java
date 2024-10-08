package com.example.mdpcw2.database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mdpcw2.database.tables.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void insert(Notes notes);

    @Update
    void update(Notes notes);

    @Delete
    void delete(Notes notes);

    @Query("SELECT * FROM Notes WHERE exerciseID = :exerciseID")
    List<Notes> getNotesListByExerciseID(int exerciseID);
}
