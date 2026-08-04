package android.bignerdranch.learn2survive.ui.learning.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.data.remote.LessonRepositoryImpl;
import android.bignerdranch.learn2survive.domain.model.Lesson;
import android.bignerdranch.learn2survive.domain.model.UserProgress;
import android.bignerdranch.learn2survive.ui.learning.adapters.ProgressAdapter;

public class ProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView overallProgressText;
    private ProgressAdapter adapter;
    private LessonRepositoryImpl repository;
    private List<UserProgress> progressList;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        
        initViews(view);
        setupRecyclerView();
        loadProgress();
        
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        overallProgressText = view.findViewById(R.id.overallProgressText);
        repository = new LessonRepositoryImpl();
        progressList = new ArrayList<>();
        userId = FirebaseAuth.getInstance().getCurrentUser() != null ? 
                FirebaseAuth.getInstance().getCurrentUser().getUid() : "";
    }

    private void setupRecyclerView() {
        adapter = new ProgressAdapter(progressList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void loadProgress() {
        if (userId.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        
        repository.getAllUserProgress(userId).addOnSuccessListener(querySnapshot -> {
            progressList.clear();
            int totalCompleted = 0;
            int totalLessons = 0;

            for (var doc : querySnapshot.getDocuments()) {
                UserProgress progress = doc.toObject(UserProgress.class);
                if (progress != null) {
                    progressList.add(progress);
                    totalLessons++;
                    if (progress.isCompleted()) {
                        totalCompleted++;
                    }
                }
            }

            adapter.notifyDataSetChanged();
            updateOverallProgress(totalCompleted, totalLessons);
            progressBar.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(recyclerView, "Failed to load progress", Snackbar.LENGTH_LONG).show();
        });
    }

    private void updateOverallProgress(int completed, int total) {
        if (total == 0) {
            overallProgressText.setText("0% Complete");
            return;
        }
        
        int percentage = (int) ((double) completed / total * 100);
        overallProgressText.setText(percentage + "% Complete");
    }
}
