package com.example.learningassistanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningassistanceapp.ollama.OllamaApiService;
import com.example.learningassistanceapp.ollama.OllamaRequest;
import com.example.learningassistanceapp.ollama.OllamaResponse;
import com.example.learningassistanceapp.ollama.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuizTitle, tvQuestion1, tvQuestion2, tvQuizDescription;
    TextView tvPromptText, tvHintResponse;
    RadioGroup radioGroupQuestion1, radioGroupQuestion2;

    RadioButton rbQ1Option1, rbQ1Option2, rbQ1Option3;
    RadioButton rbQ2Option1, rbQ2Option2, rbQ2Option3;

    Button btnSubmitQuiz, btnGenerateHint;


    String correctAnswer1 = "";
    String correctAnswer2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuizTitle = findViewById(R.id.tvQuizTitle);
        tvQuizDescription = findViewById(R.id.tvQuizDescription);
        tvQuestion1 = findViewById(R.id.tvQuestion1);
        tvQuestion2 = findViewById(R.id.tvQuestion2);
        tvPromptText = findViewById(R.id.tvPromptText);
        tvHintResponse = findViewById(R.id.tvHintResponse);

        radioGroupQuestion1 = findViewById(R.id.radioGroupQuestion1);
        radioGroupQuestion2 = findViewById(R.id.radioGroupQuestion2);

        rbQ1Option1 = findViewById(R.id.rbQ1Option1);
        rbQ1Option2 = findViewById(R.id.rbQ1Option2);
        rbQ1Option3 = findViewById(R.id.rbQ1Option3);

        rbQ2Option1 = findViewById(R.id.rbQ2Option1);
        rbQ2Option2 = findViewById(R.id.rbQ2Option2);
        rbQ2Option3 = findViewById(R.id.rbQ2Option3);

        btnSubmitQuiz = findViewById(R.id.btnSubmitQuiz);
        btnGenerateHint = findViewById(R.id.btnGenerateHint);

        SharedPreferences prefs = getSharedPreferences("LearningAppPrefs", MODE_PRIVATE);
        String interests = prefs.getString("interests", "");

        if (interests.contains("Algorithms")) {
            tvQuizTitle.setText("Algorithms Task");
            tvQuizDescription.setText("This short task checks your understanding of algorithm basics.");

            tvQuestion1.setText("Question 1: What is an algorithm?");
            rbQ1Option1.setText("A step-by-step procedure to solve a problem");
            rbQ1Option2.setText("A computer monitor");
            rbQ1Option3.setText("A phone charger");
            correctAnswer1 = "A step-by-step procedure to solve a problem";

            tvQuestion2.setText("Question 2: What is recursion?");
            rbQ2Option1.setText("A loop that never ends");
            rbQ2Option2.setText("A method that calls itself");
            rbQ2Option3.setText("A type of keyboard");
            correctAnswer2 = "A method that calls itself";

        } else if (interests.contains("Databases")) {
            tvQuizTitle.setText("Databases Task");
            tvQuizDescription.setText("This short task checks your understanding of databases and SQL.");

            tvQuestion1.setText("Question 1: What is a database?");
            rbQ1Option1.setText("A collection of organised data");
            rbQ1Option2.setText("A keyboard shortcut");
            rbQ1Option3.setText("A type of speaker");
            correctAnswer1 = "A collection of organised data";

            tvQuestion2.setText("Question 2: What is SQL used for?");
            rbQ2Option1.setText("Styling websites");
            rbQ2Option2.setText("Managing and querying data");
            rbQ2Option3.setText("Charging a laptop");
            correctAnswer2 = "Managing and querying data";

        } else if (interests.contains("Testing")) {
            tvQuizTitle.setText("Testing Task");
            tvQuizDescription.setText("This short task checks your understanding of software testing concepts.");

            tvQuestion1.setText("Question 1: What is software testing?");
            rbQ1Option1.setText("Checking software for errors");
            rbQ1Option2.setText("Drawing app icons");
            rbQ1Option3.setText("Buying a domain name");
            correctAnswer1 = "Checking software for errors";

            tvQuestion2.setText("Question 2: What is a unit test?");
            rbQ2Option1.setText("Testing one small part of the program");
            rbQ2Option2.setText("Testing the internet speed");
            rbQ2Option3.setText("Deleting old code");
            correctAnswer2 = "Testing one small part of the program";

        } else {
            tvQuizTitle.setText("General Learning Task");
            tvQuizDescription.setText("This short task checks your learning habits and study understanding.");

            tvQuestion1.setText("Question 1: What helps students improve most?");
            rbQ1Option1.setText("Practice and feedback");
            rbQ1Option2.setText("Ignoring mistakes");
            rbQ1Option3.setText("Only guessing");
            correctAnswer1 = "Practice and feedback";

            tvQuestion2.setText("Question 2: What is a good study habit?");
            rbQ2Option1.setText("Leaving all work until the last minute");
            rbQ2Option2.setText("Studying regularly in smaller sessions");
            rbQ2Option3.setText("Never reviewing notes");
            correctAnswer2 = "Studying regularly in smaller sessions";
        }

        // Generates hints for questions
        btnGenerateHint.setOnClickListener(v -> {

            String currentTaskTitle = tvQuizTitle.getText().toString();
            String prompt = "";

            if (currentTaskTitle.contains("Algorithms")) {
                prompt = "Give the student one short helpful hint for an algorithms quiz without revealing the answer.";
            } else if (currentTaskTitle.contains("Databases")) {
                prompt = "Give the student one short helpful hint for a databases quiz without revealing the answer.";
            } else if (currentTaskTitle.contains("Testing")) {
                prompt = "Give the student one short helpful hint for a software testing quiz without revealing the answer.";
            } else if (currentTaskTitle.contains("Web Development")) {
                prompt = "Give the student one short helpful hint for a web development quiz without revealing the answer.";
            } else {
                prompt = "Give the student one short helpful hint for a general learning quiz without revealing the answer.";
            }

            tvPromptText.setText(prompt);
            tvHintResponse.setText("Loading hint...");
            btnGenerateHint.setEnabled(false);

            OllamaApiService apiService =
                    RetrofitClient.getClient().create(OllamaApiService.class);

            OllamaRequest request =
                    new OllamaRequest("llama3.1:8b", prompt, false);

            apiService.generateResponse(request).enqueue(new Callback<OllamaResponse>() {

                @Override
                public void onResponse(Call<OllamaResponse> call, Response<OllamaResponse> response) {

                    btnGenerateHint.setEnabled(true);

                    if (response.isSuccessful()
                            && response.body() != null
                            && response.body().getResponse() != null) {

                        tvHintResponse.setText(response.body().getResponse().trim());

                    } else {
                        tvHintResponse.setText("Failed to generate hint.");
                    }
                }

                @Override
                public void onFailure(Call<OllamaResponse> call, Throwable t) {

                    btnGenerateHint.setEnabled(true);
                    tvHintResponse.setText("Connection failed: " + t.getMessage());
                }
            });
        });

        btnSubmitQuiz.setOnClickListener(v -> {
            int selectedId1 = radioGroupQuestion1.getCheckedRadioButtonId();
            int selectedId2 = radioGroupQuestion2.getCheckedRadioButtonId();

            if (selectedId1 == -1 || selectedId2 == -1) {
                Toast.makeText(QuizActivity.this, "Please answer both questions", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedButton1 = findViewById(selectedId1);
            RadioButton selectedButton2 = findViewById(selectedId2);

            String selectedAnswer1 = selectedButton1.getText().toString();
            String selectedAnswer2 = selectedButton2.getText().toString();

            int score = 0;

            if (selectedAnswer1.equals(correctAnswer1)) {
                score++;
            }

            if (selectedAnswer2.equals(correctAnswer2)) {
                score++;
            }

            Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
            intent.putExtra("question1", tvQuestion1.getText().toString());
            intent.putExtra("question2", tvQuestion2.getText().toString());
            intent.putExtra("selectedAnswer1", selectedAnswer1);
            intent.putExtra("selectedAnswer2", selectedAnswer2);
            intent.putExtra("correctAnswer1", correctAnswer1);
            intent.putExtra("correctAnswer2", correctAnswer2);
            intent.putExtra("score", score);
            startActivity(intent);
        });
    }
}