package android.bignerdranch.learn2survive.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Date;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.QuizResult;
import android.bignerdranch.learn2survive.domain.repository.QuizRepository;
import android.bignerdranch.learn2survive.data.remote.QuizRepositoryImpl;

import com.google.firebase.firestore.FirebaseFirestore;

public class QuizResultsActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "score";
    public static final String EXTRA_TIME_TAKEN = "time_taken";
    public static final String EXTRA_CORRECT_ANSWERS = "correct_answers";
    public static final String EXTRA_TOTAL_QUESTIONS = "total_questions";
    public static final String EXTRA_IS_PASSED = "is_passed";
    public static final String EXTRA_QUIZ_TITLE = "quiz_title";
    public static final String EXTRA_QUIZ_ID = "quiz_id";
    public static final String EXTRA_LESSON_ID = "lesson_id";
    public static final String EXTRA_XP_REWARD = "xp_reward";
    public static final String EXTRA_COIN_REWARD = "coin_reward";

    private TextView scoreTextView;
    private TextView accuracyTextView;
    private TextView timeTextView;
    private TextView starsTextView;
    private TextView xpTextView;
    private TextView coinsTextView;
    private TextView statusTextView;
    private TextView correctAnswersTextView;
    private ImageView[] starImageViews;
    private LottieAnimationView resultAnimation;
    private ProgressBar progressBar;
    private Button reviewButton;
    private Button retryButton;
    private Button homeButton;

    private QuizRepository quizRepository;

    private int score;
    private int timeTaken;
    private int correctAnswers;
    private int totalQuestions;
    private boolean isPassed;
    private String quizTitle;
    private String quizId;
    private String lessonId;
    private int xpReward;
    private int coinReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        quizRepository = new QuizRepositoryImpl();

        extractIntentData();
        initViews();
        displayResults();
        saveQuizResult();
        updateUserProgress();
    }

    private void extractIntentData() {
        score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        timeTaken = getIntent().getIntExtra(EXTRA_TIME_TAKEN, 0);
        correctAnswers = getIntent().getIntExtra(EXTRA_CORRECT_ANSWERS, 0);
        totalQuestions = getIntent().getIntExtra(EXTRA_TOTAL_QUESTIONS, 0);
        isPassed = getIntent().getBooleanExtra(EXTRA_IS_PASSED, false);
        quizTitle = getIntent().getStringExtra(EXTRA_QUIZ_TITLE);
        quizId = getIntent().getStringExtra(EXTRA_QUIZ_ID);
        lessonId = getIntent().getStringExtra(EXTRA_LESSON_ID);
        xpReward = getIntent().getIntExtra(EXTRA_XP_REWARD, 0);
        coinReward = getIntent().getIntExtra(EXTRA_COIN_REWARD, 0);
    }

    private void initViews() {
        scoreTextView = findViewById(R.id.scoreTextView);
        accuracyTextView = findViewById(R.id.accuracyTextView);
        timeTextView = findViewById(R.id.timeTextView);
        starsTextView = findViewById(R.id.starsTextView);
        xpTextView = findViewById(R.id.xpTextView);
        coinsTextView = findViewById(R.id.coinsTextView);
        statusTextView = findViewById(R.id.statusTextView);
        correctAnswersTextView = findViewById(R.id.correctAnswersTextView);
        resultAnimation = findViewById(R.id.resultAnimation);
        progressBar = findViewById(R.id.progressBar);
        reviewButton = findViewById(R.id.reviewButton);
        retryButton = findViewById(R.id.retryButton);
        homeButton = findViewById(R.id.homeButton);

        starImageViews = new ImageView[3];
        starImageViews[0] = findViewById(R.id.star1);
        starImageViews[1] = findViewById(R.id.star2);
        starImageViews[2] = findViewById(R.id.star3);

        reviewButton.setOnClickListener(v -> reviewQuiz());
        retryButton.setOnClickListener(v -> retryQuiz());
        homeButton.setOnClickListener(v -> goHome());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quiz Results");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void displayResults() {
        double accuracy = totalQuestions > 0 ? 
            (double) correctAnswers / totalQuestions * 100 : 0;
        int stars = calculateStars(accuracy);

        scoreTextView.setText(score + "%");
        accuracyTextView.setText(String.format("%.1f%%", accuracy));
        timeTextView.setText(formatTime(timeTaken));
        starsTextView.setText(stars + "/3");
        xpTextView.setText("+" + xpReward);
        coinsTextView.setText("+" + coinReward);
        correctAnswersTextView.setText(correctAnswers + "/" + totalQuestions);

        statusTextView.setText(isPassed ? "PASSED" : "FAILED");
        statusTextView.setTextColor(isPassed ? 
            getColor(R.color.success) : getColor(R.color.error));

        displayStars(stars);

        if (isPassed) {
            resultAnimation.setAnimation("correct_answer.json");
        } else {
            resultAnimation.setAnimation("wrong_answer.json");
        }
        resultAnimation.playAnimation();

        progressBar.setProgress(score);
    }

    private int calculateStars(double accuracy) {
        if (accuracy >= 90) return 3;
        if (accuracy >= 70) return 2;
        if (accuracy >= 50) return 1;
        return 0;
    }

    private void displayStars(int stars) {
        for (int i = 0; i < 3; i++) {
            if (i < stars) {
                starImageViews[i].setImageResource(R.drawable.ic_star_filled);
            } else {
                starImageViews[i].setImageResource(R.drawable.ic_star_outline);
            }
        }
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }

    private void saveQuizResult() {
        double accuracy = totalQuestions > 0 ? 
            (double) correctAnswers / totalQuestions * 100 : 0;
        int stars = calculateStars(accuracy);

        QuizResult result = new QuizResult(
            null,
            getCurrentUserId(),
            quizId,
            lessonId,
            quizTitle,
            new Date(),
            score,
            100,
            accuracy,
            timeTaken,
            stars,
            isPassed ? xpReward : 0,
            isPassed ? coinReward : 0,
            isPassed,
            correctAnswers,
            totalQuestions
        );

        quizRepository.saveQuizResult(result, new QuizRepository.SaveCallback() {
            @Override
            public void onSuccess(String documentId) {
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    private void updateUserProgress() {
        if (isPassed) {
            updateUserXP(xpReward);
            updateUserCoins(coinReward);
            updateLessonProgress();
        }
    }

    private void updateUserXP(int xp) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = getCurrentUserId();
        
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long currentXP = documentSnapshot.getLong("xp");
                        if (currentXP == null) currentXP = 0L;
                        
                        db.collection("users").document(userId)
                                .update("xp", currentXP + xp)
                                .addOnSuccessListener(aVoid -> {
                                })
                                .addOnFailureListener(e -> {
                                });
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    private void updateUserCoins(int coins) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = getCurrentUserId();
        
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long currentCoins = documentSnapshot.getLong("coins");
                        if (currentCoins == null) currentCoins = 0L;
                        
                        db.collection("users").document(userId)
                                .update("coins", currentCoins + coins)
                                .addOnSuccessListener(aVoid -> {
                                })
                                .addOnFailureListener(e -> {
                                });
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    private void updateLessonProgress() {
        android.bignerdranch.learn2survive.domain.repository.LessonRepository lessonRepository =
                new android.bignerdranch.learn2survive.data.remote.LessonRepositoryImpl();
        
        lessonRepository.getUserProgress(getCurrentUserId(), lessonId,
                new android.bignerdranch.learn2survive.domain.repository.LessonRepository.UserProgressCallback() {
                    @Override
                    public void onSuccess(android.bignerdranch.learn2survive.domain.model.UserProgress progress) {
                        if (progress != null) {
                            progress.setCompleted(true);
                            progress.setCompletedAt(new java.util.Date());
                            lessonRepository.saveUserProgress(progress,
                                    new android.bignerdranch.learn2survive.domain.repository.LessonRepository.SaveCallback() {
                                        @Override
                                        public void onSuccess(String documentId) {
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                    }
                });
    }

    private String getCurrentUserId() {
        return "user_id";
    }

    private void reviewQuiz() {
        Intent intent = new Intent(this, QuizReviewActivity.class);
        intent.putExtra(QuizReviewActivity.EXTRA_QUIZ_ID, quizId);
        startActivity(intent);
    }

    private void retryQuiz() {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(QuizActivity.EXTRA_QUIZ_ID, quizId);
        startActivity(intent);
        finish();
    }

    private void goHome() {
        finish();
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
