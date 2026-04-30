package com.example.safecheck;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safecheck.data.Defect;
import com.example.safecheck.data.SafetyCheck;
import com.example.safecheck.data.SafetyRepository;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private SafetyRepository repo;
    private int checkId;
    private String vehicleReg;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_detail);

        TextView details = findViewById(R.id.details);
        Button emailBtn = findViewById(R.id.emailBtn);
        Button addDefectBtn = findViewById(R.id.addDefectBtn);

        repo = new SafetyRepository(this);

        checkId = getIntent().getIntExtra("id", -1);
        vehicleReg = getIntent().getStringExtra("vehicle");

        // load on every resume so new defects show up
        loadDefects(details);

        addDefectBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, AddDefectActivity.class);
            i.putExtra("checkId", checkId);
            startActivity(i);
        });

        emailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Safety Defect Report: " + vehicleReg);
            intent.putExtra(Intent.EXTRA_TEXT, details.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView details = findViewById(R.id.details);
        if (details != null) loadDefects(details);
    }

    private void loadDefects(TextView details) {
        new Thread(() -> {
            SafetyCheck check = repo.getCheck(checkId);
            List<Defect> defects = repo.getDefects(checkId);

            StringBuilder text = new StringBuilder();


            if (check != null) {
                text.append("Date: ").append(check.date).append("\n")
                        .append("Vehicle: ").append(check.vehicleRegistration).append("\n")
                        .append("Driver: ").append(check.driverName).append("\n")
                        .append("Status: ").append(check.overallStatus).append("\n\n")
                        .append("Defects:\n");
            }


            if (defects == null || defects.isEmpty()) {
                text.append("No defects found.");
            } else {
                for (Defect d : defects) {
                    text.append("- ").append(d.description)
                            .append(" (").append(d.severity).append(")\n");
                }
            }

            runOnUiThread(() -> details.setText(text.toString()));
        }).start();
    }
}