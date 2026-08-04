package android.bignerdranch.learn2survive.ui.quiz;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Question;
import android.bignerdranch.learn2survive.domain.model.Quiz;
import android.bignerdranch.learn2survive.domain.repository.QuizRepository;
import android.bignerdranch.learn2survive.data.remote.QuizRepositoryImpl;
import android.bignerdranch.learn2survive.ui.quiz.adapters.QuestionReviewAdapter;

public class QuizReviewActivity extends AppCompatActivity {
    public static final String EXTRA_QUIZ_ID = "quiz_id";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button closeButton;

    private QuizRepository quizRepository;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_review);

        String quizId = getIntent().getStringExtra(EXTRA_QUIZ_ID);

        quizRepository = new QuizRepositoryImpl();

        initViews();
        loadQuizData(quizId);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        closeButton = findViewById(R.id.closeButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        closeButton.setOnClickListener(v -> finish());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Question Review");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadQuizData(String quizId) {
        progressBar.setVisibility(View.VISIBLE);

        quizRepository.getQuestionsForQuiz(quizId, new QuizRepository.QuestionsCallback() {
            @Override
            public void onSuccess(List<Question> loadedQuestions) {
                questions = loadedQuestions;
                setupAdapter();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupAdapter() {
        QuestionReviewAdapter adapter = new QuestionReviewAdapter(questions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
