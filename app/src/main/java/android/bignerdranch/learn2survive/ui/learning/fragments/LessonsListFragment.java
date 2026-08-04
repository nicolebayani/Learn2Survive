package android.bignerdranch.learn2survive.ui.learning.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import android.bignerdranch.learn2survive.domain.model.DisasterType;
import android.bignerdranch.learn2survive.domain.model.Lesson;
import android.bignerdranch.learn2survive.ui.learning.LessonDetailActivity;
import android.bignerdranch.learn2survive.ui.learning.adapters.LessonCardAdapter;

public class LessonsListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LessonCardAdapter adapter;
    private LessonRepositoryImpl repository;
    private List<Lesson> lessons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons_list, container, false);
        
        initViews(view);
        setupRecyclerView();
        loadLessons();
        
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        repository = new LessonRepositoryImpl();
        lessons = new ArrayList<>();
    }

    private void setupRecyclerView() {
        adapter = new LessonCardAdapter(lessons, this::onLessonClicked);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    private void loadLessons() {
        progressBar.setVisibility(View.VISIBLE);
        
        repository.getAllLessons().addOnSuccessListener(querySnapshot -> {
            lessons.clear();
            for (var doc : querySnapshot.getDocuments()) {
                Lesson lesson = doc.toObject(Lesson.class);
                if (lesson != null) {
                    lesson.setId(doc.getId());
                    lessons.add(lesson);
                }
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(recyclerView, "Failed to load lessons", Snackbar.LENGTH_LONG).show();
        });
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
