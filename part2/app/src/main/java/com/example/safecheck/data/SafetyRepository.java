package com.example.safecheck.data;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {

    private SafetyDao dao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public SafetyRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        dao = db.safetyDao();
    }

    public void insert(SafetyCheck check, List<Defect> defects) {
        executor.execute(() -> {

            long id = dao.insertCheck(check);

            if (defects != null) {
                for (Defect d : defects) {
                    d.checkId = (int) id;
                }
                dao.insertDefects(defects);
            }
        });
    }

    public void insertDefect(Defect defect) {
        executor.execute(() ->
                dao.insertDefects(java.util.Collections.singletonList(defect))
        );
    }

    public List<SafetyCheck> getAllChecks() {
        return dao.getAllChecks();
    }

    public List<Defect> getDefects(int checkId) {
        return dao.getDefects(checkId);
    }

    public SafetyCheck getCheck(int id) {
        return dao.getCheck(id);
    }

    public void deleteCheck(SafetyCheck check) {
        executor.execute(() -> dao.deleteCheck(check));
    }
}