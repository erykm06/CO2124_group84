package com.example.safecheck.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// child table where the table is deleted when parent table SafetyCheck is deleted
@Entity(foreignKeys = @ForeignKey(
        entity = SafetyCheck.class,
        parentColumns = "checkId",
        childColumns = "checkId",
        onDelete = ForeignKey.CASCADE
))
public class Defect {

    @PrimaryKey(autoGenerate = true)
    public int defectId;

    public int checkId;
    public String description;
    public String severity;
}
