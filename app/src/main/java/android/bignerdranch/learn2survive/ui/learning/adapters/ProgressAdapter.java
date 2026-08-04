package android.bignerdranch.learn2survive.ui.learning.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.UserProgress;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {
    private List<UserProgress> progressList;

    public ProgressAdapter(List<UserProgress> progressList) {
        this.progressList = progressList;
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_progress, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        UserProgress progress = progressList.get(position);
        holder.bind(progress);
    }

    @Override
    public int getItemCount() {
        return progressList.size();
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private TextView lessonTitleTextView;
        private ProgressBar progressBar;
        private TextView progressTextView;
        private TextView statusTextView;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonTitleTextView = itemView.findViewById(R.id.lessonTitleTextView);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressTextView = itemView.findViewById(R.id.progressTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }

        public void bind(UserProgress progress) {
            lessonTitleTextView.setText("Lesson: " + progress.getLessonId());
            
            int progressPercent = (int) progress.getProgressPercentage();
            progressBar.setProgress(progressPercent);
            progressTextView.setText(progressPercent + "%");
            
            if (progress.isCompleted()) {
                statusTextView.setText("Completed");
                statusTextView.setTextColor(itemView.getContext().getResources().getColor(R.color.success));
            } else {
                statusTextView.setText("In Progress");
                statusTextView.setTextColor(itemView.getContext().getResources().getColor(R.color.warning));
            }
        }
    }
}
