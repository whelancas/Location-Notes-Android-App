package com.example.mdpcw2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mdpcw2.database.DAOs.ExercisesDao;
import com.example.mdpcw2.database.DAOs.NotesDao;
import com.example.mdpcw2.database.DAOs.RemindersDao;
import com.example.mdpcw2.database.tables.Exercises;
import com.example.mdpcw2.database.tables.Notes;
import com.example.mdpcw2.database.tables.Reminders;

@Database(entities = {Exercises.class, Notes.class, Reminders.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String NAME = "AppDatabase";

    public abstract ExercisesDao exercisesDao();

    public abstract NotesDao notesDao();

    public abstract RemindersDao remindersDao();

}
