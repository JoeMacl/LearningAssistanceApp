package com.example.learningassistanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView tvHello, tvInterests, tvRecommendedTopic, tvTaskTitle, tvTaskDescription, tvProgressMessage;
    Button btnStartTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvHello = findViewById(R.id.tvHello);
        tvInterests = findViewById(R.id.tvInterests);
        tvRecommendedTopic = findViewById(R.id.tvRecommendedTopic);
        tvTaskTitle = findViewById(R.id.tvTaskTitle);
        tvTaskDescription = findViewById(R.id.tvTaskDescription);
        tvProgressMessage = findViewById(R.id.tvProgressMessage);
        btnStartTask = findViewById(R.id.btnStartTask);

        SharedPreferences prefs = getSharedPreferences("LearningAppPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", "Student");
        String interests = prefs.getString("interests", "No interests selected");

        tvHello.setText("Hello, " + username);
        tvInterests.setText("Your interests: " + interests);

        if (interests.contains("Algorithms")) {
            tvRecommendedTopic.setText("Recommended Topic: Algorithms");
            tvTaskTitle.setText("Generated Task 1");
            tvTaskDescription.setText("Practice algorithm basics and recursion with two short questions.");
            tvProgressMessage.setText("Progress: 1 recommended task ready today");

        } else if (interests.contains("Databases")) {
            tvRecommendedTopic.setText("Recommended Topic: Databases");
            tvTaskTitle.setText("Generated Task 1");
            tvTaskDescription.setText("Review database fundamentals and basic SQL concepts.");
            tvProgressMessage.setText("Progress: 1 recommended task ready today");

        } else if (interests.contains("Testing")) {
            tvRecommendedTopic.setText("Recommended Topic: Testing");
            tvTaskTitle.setText("Generated Task 1");
            tvTaskDescription.setText("Strengthen your understanding of testing and unit tests.");
            tvProgressMessage.setText("Progress: 1 recommended task ready today");

        } else if (interests.contains("Web Development")) {
            tvRecommendedTopic.setText("Recommended Topic: Web Development");
            tvTaskTitle.setText("Generated Task 1");
            tvTaskDescription.setText("Revise core web concepts and front-end basics.");
            tvProgressMessage.setText("Progress: 1 recommended task ready today");

        } else {
            tvRecommendedTopic.setText("Recommended Topic: General Study Skills");
            tvTaskTitle.setText("Generated Task 1");
            tvTaskDescription.setText("Complete a short practice activity to improve learning habits.");
            tvProgressMessage.setText("Progress: 1 recommended task ready today");
        }

        btnStartTask.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
            startActivity(intent);
        });
    }
}