package com.example.learningassistanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    TextView tvScore, tvSummaryFeedback, tvResultMessage1, tvResultMessage2;
    TextView tvExplainPrompt, tvExplainResponse;
    Button btnContinue, btnExplainAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvScore = findViewById(R.id.tvScore);
        tvSummaryFeedback = findViewById(R.id.tvSummaryFeedback);
        tvResultMessage1 = findViewById(R.id.tvResultMessage1);
        tvResultMessage2 = findViewById(R.id.tvResultMessage2);
        btnContinue = findViewById(R.id.btnContinue);

        tvExplainPrompt = findViewById(R.id.tvExplainPrompt);
        tvExplainResponse = findViewById(R.id.tvExplainResponse);

        btnExplainAnswers = findViewById(R.id.btnExplainAnswers);
        btnContinue = findViewById(R.id.btnContinue);

        String question1 = getIntent().getStringExtra("question1");
        String question2 = getIntent().getStringExtra("question2");
        String selectedAnswer1 = getIntent().getStringExtra("selectedAnswer1");
        String selectedAnswer2 = getIntent().getStringExtra("selectedAnswer2");
        String correctAnswer1 = getIntent().getStringExtra("correctAnswer1");
        String correctAnswer2 = getIntent().getStringExtra("correctAnswer2");
        int score = getIntent().getIntExtra("score", 0);

        tvScore.setText("Score: " + score + "/2");

        if (selectedAnswer1 != null && selectedAnswer1.equals(correctAnswer1)) {
            tvResultMessage1.setText(question1 + "\n\nCorrect! Well done.");
        } else {
            tvResultMessage1.setText(
                    question1 + "\n\nYour answer: " + selectedAnswer1 +
                            "\nCorrect answer: " + correctAnswer1
            );
        }

        if (selectedAnswer2 != null && selectedAnswer2.equals(correctAnswer2)) {
            tvResultMessage2.setText(question2 + "\n\nCorrect! Well done.");
        } else {
            tvResultMessage2.setText(
                    question2 + "\n\nYour answer: " + selectedAnswer2 +
                            "\nCorrect answer: " + correctAnswer2
            );
        }

        if (score == 2) {
            tvSummaryFeedback.setText("Excellent work! You answered both questions correctly.");
        } else if (score == 1) {
            tvSummaryFeedback.setText("Good effort. You got 1 out of 2 correct and have one area to review.");
        } else {
            tvSummaryFeedback.setText("Keep practicing. Review the correct answers below and try again.");
        }

        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        // Generate ai feedback
        btnExplainAnswers.setOnClickListener(v -> {

            String prompt =
                    "Explain why the student's answers were correct or incorrect in a supportive and simple way.";

            String explanation = "";

            if (score == 2) {
                explanation =
                        "Excellent work. Both of your answers were correct. " +
                                "This shows a strong understanding of the topic.";

            } else if (score == 1) {
                explanation =
                        "Good effort. You answered one question correctly. " +
                                "Review the incorrect answer and compare it with the correct concept.";

            } else {
                explanation =
                        "Keep practicing. Both answers were incorrect, but mistakes help learning. " +
                                "Read the correct answers carefully and try again.";
            }

            tvExplainPrompt.setText(prompt);
            tvExplainResponse.setText("Generating explanation...");
            btnExplainAnswers.setEnabled(false);

            String finalExplanation = explanation;

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                tvExplainResponse.setText(finalExplanation);
                btnExplainAnswers.setEnabled(true);
            }, 1500);
        });
    }
}