package com.example.safecheck.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// parent table where one safety check has many defects
@Entity
public class SafetyCheck {
    @PrimaryKey(autoGenerate = true)
    public int checkId;

    public String date;
    public String vehicleRegistration;
    public String driverName;
    public String overallStatus;
}