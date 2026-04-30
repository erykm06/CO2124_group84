package com.example.safecheck;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.safecheck.data.SafetyCheck;

public class AddCheckActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_add);

        EditText vehicle = findViewById(R.id.vehicle);
        EditText driver = findViewById(R.id.driver);
        Button save = findViewById(R.id.saveBtn);

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);

        save.setOnClickListener(v -> {
            if (vehicle.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter vehicle details", Toast.LENGTH_SHORT).show();
                return;
            }

            SafetyCheck c = new SafetyCheck();
            c.vehicleRegistration = vehicle.getText().toString();
            c.driverName = driver.getText().toString();
            c.date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            c.overallStatus = "Pass";

            viewModel.insert(c);
            finish();
        });
    }
}