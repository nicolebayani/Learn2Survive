package android.bignerdranch.learn2survive.ui.learning;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.ui.learning.fragments.LessonsListFragment;
import android.bignerdranch.learn2survive.ui.learning.fragments.BookmarkedLessonsFragment;

public class LearningActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        initViews();
        setupBottomNavigation();
        
        if (savedInstanceState == null) {
            loadFragment(new LessonsListFragment());
        }
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fabBookmark = findViewById(R.id.fabBookmark);
        
        fabBookmark.setOnClickListener(v -> {
            loadFragment(new BookmarkedLessonsFragment());
        });
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();
                
                if (itemId == R.id.navigation_lessons) {
                    fragment = new LessonsListFragment();
                } else if (itemId == R.id.navigation_progress) {
                    fragment = new ProgressFragment();
                }
                
                if (fragment != null) {
                    loadFragment(fragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
