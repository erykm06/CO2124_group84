package com.example.safecheck;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

 private RecyclerView rv;
 private SafetyAdapter adapter;
 private SafetyViewModel vm;

 @Override
 protected void onCreate(Bundle b) {
  super.onCreate(b);
  setContentView(R.layout.activity_main);

  rv = findViewById(R.id.recyclerView);
  rv.setLayoutManager(new LinearLayoutManager(this));

  adapter = new SafetyAdapter(new ArrayList<>(), this);
  rv.setAdapter(adapter);

  Button addBtn = findViewById(R.id.addBtn);
  addBtn.setOnClickListener(v -> {
   Intent intent = new Intent(MainActivity.this, AddCheckActivity.class);
   startActivity(intent);
  });

  vm = new ViewModelProvider(this).get(SafetyViewModel.class);

  vm.list.observe(this, list -> {
   if (list != null) {
    adapter.set(list);
   }
  });

  vm.load();
 }

 @Override
 protected void onResume() {
  super.onResume();
  if (vm != null) {
   vm.load();
  }
 }
}