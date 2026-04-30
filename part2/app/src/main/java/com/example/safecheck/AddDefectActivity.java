package com.example.safecheck;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.safecheck.data.Defect;

public class AddDefectActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_add_defect);

        int checkId = getIntent().getIntExtra("checkId", -1);

        EditText description = findViewById(R.id.defectDescription);
        RadioButton severityHigh = findViewById(R.id.severityHigh);
        Button save = findViewById(R.id.saveDefectBtn);

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);

        save.setOnClickListener(v -> {
            if (description.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter a defect description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (checkId == -1) {
                Toast.makeText(this, "Invalid check ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Defect d = new Defect();
            d.checkId = checkId;
            d.description = description.getText().toString();
            d.severity = severityHigh.isChecked() ? "High" : "Low";

            viewModel.insertDefect(d);
            Toast.makeText(this, "Defect saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}