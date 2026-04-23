package com.example.learningassistanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InterestActivity extends AppCompatActivity {

    CheckBox cbAlgorithms, cbDataStructures, cbWebDev, cbTesting, cbDatabases;
    Button btnNextInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        cbAlgorithms = findViewById(R.id.cbAlgorithms);
        cbDataStructures = findViewById(R.id.cbDataStructures);
        cbWebDev = findViewById(R.id.cbWebDev);
        cbTesting = findViewById(R.id.cbTesting);
        cbDatabases = findViewById(R.id.cbDatabases);
        btnNextInterest = findViewById(R.id.btnNextInterest);

        btnNextInterest.setOnClickListener(v -> {
            ArrayList<String> selectedInterests = new ArrayList<>();

            if (cbAlgorithms.isChecked()) selectedInterests.add("Algorithms");
            if (cbDataStructures.isChecked()) selectedInterests.add("Data Structures");
            if (cbWebDev.isChecked()) selectedInterests.add("Web Development");
            if (cbTesting.isChecked()) selectedInterests.add("Testing");
            if (cbDatabases.isChecked()) selectedInterests.add("Databases");

            if (selectedInterests.isEmpty()) {
                Toast.makeText(InterestActivity.this, "Please select at least one interest", Toast.LENGTH_SHORT).show();
                return;
            }

            String joinedInterests = String.join(", ", selectedInterests);

            SharedPreferences prefs = getSharedPreferences("LearningAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("interests", joinedInterests);
            editor.apply();

            Intent intent = new Intent(InterestActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
    }
}