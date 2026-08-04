package android.bignerdranch.learn2survive.ui.learning.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.data.remote.LessonRepositoryImpl;
import android.bignerdranch.learn2survive.domain.model.Lesson;
import android.bignerdranch.learn2survive.domain.model.UserProgress;
import android.bignerdranch.learn2survive.ui.learning.LessonDetailActivity;
import android.bignerdranch.learn2survive.ui.learning.adapters.LessonCardAdapter;

public class BookmarkedLessonsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyView;
    private LessonCardAdapter adapter;
    private LessonRepositoryImpl repository;
    private List<Lesson> bookmarkedLessons;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarked_lessons, container, false);
        
        initViews(view);
        setupRecyclerView();
        loadBookmarkedLessons();
        
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyView = view.findViewById(R.id.emptyView);
        repository = new LessonRepositoryImpl();
        bookmarkedLessons = new ArrayList<>();
        userId = FirebaseAuth.getInstance().getCurrentUser() != null ? 
                FirebaseAuth.getInstance().getCurrentUser().getUid() : "";
    }

    private void setupRecyclerView() {
        adapter = new LessonCardAdapter(bookmarkedLessons, this::onLessonClicked);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    private void loadBookmarkedLessons() {
        if (userId.isEmpty()) {
            showEmptyState();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        
        repository.getAllUserProgress(userId).addOnSuccessListener(querySnapshot -> {
            bookmarkedLessons.clear();
            
            if (querySnapshot.isEmpty()) {
                showEmptyState();
                progressBar.setVisibility(View.GONE);
                return;
            }

            List<String> bookmarkedLessonIds = new ArrayList<>();
            for (var doc : querySnapshot.getDocuments()) {
                UserProgress progress = doc.toObject(UserProgress.class);
                if (progress != null && progress.isBookmarked()) {
                    bookmarkedLessonIds.add(progress.getLessonId());
                }
            }

            if (bookmarkedLessonIds.isEmpty()) {
                showEmptyState();
                progressBar.setVisibility(View.GONE);
                return;
            }

            loadLessonsByIds(bookmarkedLessonIds);
        }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(recyclerView, "Failed to load bookmarked lessons", Snackbar.LENGTH_LONG).show();
        });
    }

    private void loadLessonsByIds(List<String> lessonIds) {
        for (String lessonId : lessonIds) {
            repository.getLessonById(lessonId).addOnSuccessListener(docSnapshot -> {
                if (docSnapshot.exists()) {
                    Lesson lesson = docSnapshot.toObject(Lesson.class);
                    if (lesson != null) {
                        lesson.setId(docSnapshot.getId());
                        bookmarkedLessons.add(lesson);
                        adapter.notifyDataSetChanged();
                    }
                }
                progressBar.setVisibility(View.GONE);
                
                if (bookmarkedLessons.isEmpty()) {
                    showEmptyState();
                }
            });
        }
    }

    private void showEmptyState() {
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void onLessonClicked(Lesson lesson) {
        Intent intent = new Intent(getContext(), LessonDetailActivity.class);
        intent.putExtra(LessonDetailActivity.EXTRA_LESSON_ID, lesson.getId());
        intent.putExtra(LessonDetailActivity.EXTRA_LESSON_TITLE, lesson.getTitle());
        intent.putExtra(LessonDetailActivity.EXTRA_LESSON_DESCRIPTION, lesson.getDescription());
        intent.putExtra(LessonDetailActivity.EXTRA_LOTTIE_ANIMATION, lesson.getMainLottieAnimation());
        startActivity(intent);
    }
}
