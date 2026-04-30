package com.example.safecheck.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface SafetyDao {

    @Insert
    long insertCheck(SafetyCheck check);

    @Insert
    void insertDefects(List<Defect> defects);

    @Query("SELECT * FROM SafetyCheck WHERE checkId = :id")
    SafetyCheck getCheck(int id);

    @Query("SELECT * FROM SafetyCheck")
    List<SafetyCheck> getAllChecks();

    @Query("SELECT * FROM Defect WHERE checkId = :id")
    List<Defect> getDefects(int id);

    @Delete
    void deleteCheck(SafetyCheck check);
}
