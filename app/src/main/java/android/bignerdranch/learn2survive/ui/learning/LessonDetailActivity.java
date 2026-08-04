package android.bignerdranch.learn2survive.ui.learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Lesson;
import android.bignerdranch.learn2survive.domain.model.UserProgress;
import android.bignerdranch.learn2survive.ui.learning.adapters.LessonPagerAdapter;
import android.bignerdranch.learn2survive.ui.custom.LessonProgressView;

public class LessonDetailActivity extends AppCompatActivity {
    public static final String EXTRA_LESSON_ID = "lesson_id";
    public static final String EXTRA_LESSON_TITLE = "lesson_title";
    public static final String EXTRA_LESSON_DESCRIPTION = "lesson_description";
    public static final String EXTRA_LOTTIE_ANIMATION = "lottie_animation";

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private LessonProgressView progressView;
    private TextView progressTextView;
    private ImageView bookmarkButton;
    private LottieAnimationView mainAnimation;
    private ProgressBar progressBar;
    private Button quizButton;

    private Lesson lesson;
    private UserProgress userProgress;
    private String lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);

        lessonId = getIntent().getStringExtra(EXTRA_LESSON_ID);
        String title = getIntent().getStringExtra(EXTRA_LESSON_TITLE);
        String description = getIntent().getStringExtra(EXTRA_LESSON_DESCRIPTION);
        String lottieAnimation = getIntent().getStringExtra(EXTRA_LOTTIE_ANIMATION);

        initViews();
        setupToolbar(title, description);
        loadLessonData();
        setupViewPager();
        setupBookmark();
        setupQuizButton();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        progressView = findViewById(R.id.progressView);
        progressTextView = findViewById(R.id.progressTextView);
        bookmarkButton = findViewById(R.id.bookmarkButton);
        mainAnimation = findViewById(R.id.mainAnimation);
        progressBar = findViewById(R.id.progressBar);
        quizButton = findViewById(R.id.quizButton);
    }

    private void setupToolbar(String title, String description) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setSubtitle(description);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadLessonData() {
        progressBar.setVisibility(View.VISIBLE);
        
        if (mainAnimation != null) {
            String lottieUrl = getIntent().getStringExtra(EXTRA_LOTTIE_ANIMATION);
            if (lottieUrl != null && !lottieUrl.isEmpty()) {
                mainAnimation.setAnimationFromUrl(lottieUrl);
                mainAnimation.playAnimation();
            }
        }
        
        progressBar.setVisibility(View.GONE);
    }

    private void setupViewPager() {
        LessonPagerAdapter adapter = new LessonPagerAdapter(this);
        viewPager.setAdapter(adapter);

        String[] tabTitles = {
            "Introduction",
            "Before",
            "During",
            "After",
            "Emergency Kit",
            "Evacuation",
            "Safety"
        };

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTitles[position]);
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateProgress(position);
            }
        });
    }

    private void updateProgress(int currentPosition) {
        int totalSections = 7;
        float progress = (float) (currentPosition + 1) / totalSections;
        progressView.setProgress(progress);
        progressTextView.setText((currentPosition + 1) + "/" + totalSections);
    }

    private void setupBookmark() {
        bookmarkButton.setOnClickListener(v -> {
            boolean isBookmarked = !isLessonBookmarked();
            bookmarkButton.setImageResource(isBookmarked ? 
                R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_outline);
            saveBookmarkState(isBookmarked);
        });
    }

    private boolean isLessonBookmarked() {
        return userProgress != null && userProgress.isBookmarked();
    }

    private void saveBookmarkState(boolean bookmarked) {
        // Save to Firestore
    }

    private void setupQuizButton() {
        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, android.bignerdranch.learn2survive.ui.quiz.QuizActivity.class);
            intent.putExtra(android.bignerdranch.learn2survive.ui.quiz.QuizActivity.EXTRA_LESSON_ID, lessonId);
            startActivity(intent);
        });
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
