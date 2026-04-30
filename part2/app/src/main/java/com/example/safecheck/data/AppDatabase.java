package com.example.safecheck.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {SafetyCheck.class, Defect.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SafetyDao safetyDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "safecheck_db"
            ).build();
        }
        return INSTANCE;
    }
}
