package com.example.mdpcw2.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mdpcw2.database.DOAs.ExercisesDao;
import com.example.mdpcw2.database.DOAs.NotesDoa;
import com.example.mdpcw2.database.DOAs.RemindersDao;
import com.example.mdpcw2.database.tables.Exercises;
import com.example.mdpcw2.database.tables.Notes;
import com.example.mdpcw2.database.tables.Reminders;

@Database(entities = {Exercises.class, Notes.class, Reminders.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String NAME = "AppDatabase";

    public abstract ExercisesDao exercisesDao();

    public abstract NotesDoa notesDao();

    public abstract RemindersDao remindersDao();

}
