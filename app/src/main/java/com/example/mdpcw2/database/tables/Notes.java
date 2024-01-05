package com.example.mdpcw2.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Exercises.class, parentColumns = "exerciseID", childColumns = "exerciseID", onDelete = ForeignKey.CASCADE))
public class Notes {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int noteID;
    public String tags;
    @NonNull
    public String note;
    public int exerciseID;

    public Notes(String tags, String note) {
        this.tags = tags;
        this.note = note;
    }
}
