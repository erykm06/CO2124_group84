package com.example.safecheck;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.safecheck.data.SafetyCheck;
import com.example.safecheck.data.SafetyRepository;
import com.example.safecheck.data.Defect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SafetyViewModel extends AndroidViewModel {

    public MutableLiveData<List<SafetyCheck>> list = new MutableLiveData<>();

    private SafetyRepository repo;

    public SafetyViewModel(@NonNull Application application) {
        super(application);
        repo = new SafetyRepository(application);
    }

    public void load() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<SafetyCheck> data = repo.getAllChecks();
            list.postValue(data);
        });
    }

    public void insert(SafetyCheck check) {
        Executors.newSingleThreadExecutor().execute(() -> {
            repo.insert(check, new ArrayList<>());
            load(); // refresh list after inserting
        });
    }

    public void insertDefect(Defect defect) {
        Executors.newSingleThreadExecutor().execute(() -> repo.insertDefect(defect));
    }
}