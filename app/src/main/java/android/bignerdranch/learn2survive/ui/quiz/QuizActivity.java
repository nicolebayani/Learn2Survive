package android.bignerdranch.learn2survive.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Question;
import android.bignerdranch.learn2survive.domain.model.Quiz;
import android.bignerdranch.learn2survive.domain.model.QuizAttempt;
import android.bignerdranch.learn2survive.domain.repository.QuizRepository;
import android.bignerdranch.learn2survive.data.remote.QuizRepositoryImpl;
import android.bignerdranch.learn2survive.ui.quiz.adapters.QuestionPagerAdapter;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_QUIZ_ID = "quiz_id";
    public static final String EXTRA_LESSON_ID = "lesson_id";

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private TextView timerTextView;
    private TextView questionCounterTextView;
    private ProgressBar progressBar;
    private Button submitButton;
    private Button skipButton;

    private QuizRepository quizRepository;
    private Quiz quiz;
    private List<Question> questions;
    private List<Question> shuffledQuestions;
    private int currentQuestionIndex = 0;
    private Map<String, Integer> userAnswers;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int skippedAnswers = 0;
    private int totalPoints = 0;

    private CountDownTimer quizTimer;
    private CountDownTimer questionTimer;
    private long quizTimeRemaining;
    private long questionTimeRemaining;

    private Date startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String quizId = getIntent().getStringExtra(EXTRA_QUIZ_ID);
        String lessonId = getIntent().getStringExtra(EXTRA_LESSON_ID);

        quizRepository = new QuizRepositoryImpl();
        userAnswers = new HashMap<>();
        questions = new ArrayList<>();
        shuffledQuestions = new ArrayList<>();
        startTime = new Date();

        initViews();
        loadQuizData(quizId, lessonId);
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        timerTextView = findViewById(R.id.timerTextView);
        questionCounterTextView = findViewById(R.id.questionCounterTextView);
        progressBar = findViewById(R.id.progressBar);
        submitButton = findViewById(R.id.submitButton);
        skipButton = findViewById(R.id.skipButton);

        submitButton.setOnClickListener(v -> submitAnswer());
        skipButton.setOnClickListener(v -> skipQuestion());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentQuestionIndex = position;
                updateQuestionCounter();
                if (quiz != null && quiz.isTimed()) {
                    startQuestionTimer(questions.get(position).getTimeLimitSeconds());
                }
            }
        });
    }

    private void loadQuizData(String quizId, String lessonId) {
        progressBar.setVisibility(View.VISIBLE);

        if (quizId != null) {
            quizRepository.getQuiz(quizId, new QuizRepository.QuizCallback() {
                @Override
                public void onSuccess(Quiz quiz) {
                    QuizActivity.this.quiz = quiz;
                    loadQuestions(quizId);
                }

                @Override
                public void onFailure(Exception e) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else if (lessonId != null) {
            quizRepository.getQuizByLessonId(lessonId, new QuizRepository.QuizCallback() {
                @Override
                public void onSuccess(Quiz quiz) {
                    QuizActivity.this.quiz = quiz;
                    loadQuestions(quiz.getId());
                }

                @Override
                public void onFailure(Exception e) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void loadQuestions(String quizId) {
        quizRepository.getQuestionsForQuiz(quizId, new QuizRepository.QuestionsCallback() {
            @Override
            public void onSuccess(List<Question> loadedQuestions) {
                questions = loadedQuestions;
                if (quiz.isRandomized()) {
                    shuffledQuestions = shuffleQuestions(questions);
                } else {
                    shuffledQuestions = new ArrayList<>(questions);
                }
                setupViewPager();
                if (quiz.isTimed()) {
                    startQuizTimer(quiz.getTimeLimitSeconds());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private List<Question> shuffleQuestions(List<Question> original) {
        List<Question> shuffled = new ArrayList<>(original);
        Random random = new Random();
        for (int i = shuffled.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Question temp = shuffled.get(i);
            shuffled.set(i, shuffled.get(j));
            shuffled.set(j, temp);
        }
        return shuffled;
    }

    private void setupViewPager() {
        QuestionPagerAdapter adapter = new QuestionPagerAdapter(this, shuffledQuestions);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(String.valueOf(position + 1));
        }).attach();

        updateQuestionCounter();
    }

    private void startQuizTimer(int totalSeconds) {
        quizTimeRemaining = totalSeconds * 1000L;
        quizTimer = new CountDownTimer(quizTimeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                quizTimeRemaining = millisUntilFinished;
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                finishQuiz();
            }
        }.start();
    }

    private void startQuestionTimer(int seconds) {
        if (questionTimer != null) {
            questionTimer.cancel();
        }
        questionTimeRemaining = seconds * 1000L;
        questionTimer = new CountDownTimer(questionTimeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                questionTimeRemaining = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                skipQuestion();
            }
        }.start();
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void updateQuestionCounter() {
        questionCounterTextView.setText(
            String.format("Question %d of %d", currentQuestionIndex + 1, shuffledQuestions.size())
        );
    }

    public void onAnswerSelected(int answerIndex) {
        Question currentQuestion = shuffledQuestions.get(currentQuestionIndex);
        userAnswers.put(currentQuestion.getId(), answerIndex);
    }

    private void submitAnswer() {
        Question currentQuestion = shuffledQuestions.get(currentQuestionIndex);
        Integer answerIndex = userAnswers.get(currentQuestion.getId());

        if (answerIndex != null) {
            if (answerIndex == currentQuestion.getCorrectAnswerIndex()) {
                correctAnswers++;
                totalPoints += currentQuestion.getPoints();
                showCorrectAnimation();
            } else {
                wrongAnswers++;
                showWrongAnimation();
                if (quiz.isShowExplanation()) {
                    showExplanation(currentQuestion.getExplanation());
                }
            }
        } else {
            skippedAnswers++;
        }

        if (currentQuestionIndex < shuffledQuestions.size() - 1) {
            viewPager.setCurrentItem(currentQuestionIndex + 1);
        } else {
            finishQuiz();
        }
    }

    private void skipQuestion() {
        skippedAnswers++;
        if (currentQuestionIndex < shuffledQuestions.size() - 1) {
            viewPager.setCurrentItem(currentQuestionIndex + 1);
        } else {
            finishQuiz();
        }
    }

    private void showCorrectAnimation() {
        LottieAnimationView animation = new LottieAnimationView(this);
        animation.setAnimation("correct_answer.json");
        animation.playAnimation();
    }

    private void showWrongAnimation() {
        LottieAnimationView animation = new LottieAnimationView(this);
        animation.setAnimation("wrong_answer.json");
        animation.playAnimation();
    }

    private void showExplanation(String explanation) {
        if (explanation != null && !explanation.isEmpty()) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Explanation");
            builder.setMessage(explanation);
            builder.setPositiveButton("Continue", (dialog, which) -> {
                if (currentQuestionIndex < shuffledQuestions.size() - 1) {
                    viewPager.setCurrentItem(currentQuestionIndex + 1);
                } else {
                    finishQuiz();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }

    private void finishQuiz() {
        if (quizTimer != null) {
            quizTimer.cancel();
        }
        if (questionTimer != null) {
            questionTimer.cancel();
        }

        Date endTime = new Date();
        long timeTaken = (endTime.getTime() - startTime.getTime()) / 1000;

        int score = (int) ((double) correctAnswers / shuffledQuestions.size() * 100);
        boolean isPassed = score >= quiz.getPassingScore();

        QuizAttempt attempt = new QuizAttempt(
            null,
            getCurrentUserId(),
            quiz.getId(),
            quiz.getLessonId(),
            startTime,
            endTime,
            (int) timeTaken,
            getQuestionIds(),
            userAnswers,
            correctAnswers,
            wrongAnswers,
            skippedAnswers,
            score,
            totalPoints,
            true,
            isPassed
        );

        quizRepository.saveQuizAttempt(attempt, new QuizRepository.SaveCallback() {
            @Override
            public void onSuccess(String documentId) {
                navigateToResults(score, (int) timeTaken, isPassed);
            }

            @Override
            public void onFailure(Exception e) {
                navigateToResults(score, (int) timeTaken, isPassed);
            }
        });
    }

    private List<String> getQuestionIds() {
        List<String> ids = new ArrayList<>();
        for (Question q : shuffledQuestions) {
            ids.add(q.getId());
        }
        return ids;
    }

    private String getCurrentUserId() {
        return "user_id";
    }

    private void navigateToResults(int score, int timeTaken, boolean isPassed) {
        Intent intent = new Intent(this, QuizResultsActivity.class);
        intent.putExtra(QuizResultsActivity.EXTRA_SCORE, score);
        intent.putExtra(QuizResultsActivity.EXTRA_TIME_TAKEN, timeTaken);
        intent.putExtra(QuizResultsActivity.EXTRA_CORRECT_ANSWERS, correctAnswers);
        intent.putExtra(QuizResultsActivity.EXTRA_TOTAL_QUESTIONS, shuffledQuestions.size());
        intent.putExtra(QuizResultsActivity.EXTRA_IS_PASSED, isPassed);
        intent.putExtra(QuizResultsActivity.EXTRA_QUIZ_TITLE, quiz.getTitle());
        intent.putExtra(QuizResultsActivity.EXTRA_QUIZ_ID, quiz.getId());
        intent.putExtra(QuizResultsActivity.EXTRA_LESSON_ID, quiz.getLessonId());
        intent.putExtra(QuizResultsActivity.EXTRA_XP_REWARD, quiz.getXpReward());
        intent.putExtra(QuizResultsActivity.EXTRA_COIN_REWARD, quiz.getCoinReward());
        startActivity(intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (quizTimer != null) {
            quizTimer.cancel();
        }
        if (questionTimer != null) {
            questionTimer.cancel();
        }
    }
}
