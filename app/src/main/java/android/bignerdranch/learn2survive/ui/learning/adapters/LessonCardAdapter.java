package android.bignerdranch.learn2survive.ui.learning.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.function.Consumer;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Lesson;

public class LessonCardAdapter extends RecyclerView.Adapter<LessonCardAdapter.LessonViewHolder> {
    private List<Lesson> lessons;
    private Consumer<Lesson> onLessonClicked;

    public LessonCardAdapter(List<Lesson> lessons, Consumer<Lesson> onLessonClicked) {
        this.lessons = lessons;
        this.onLessonClicked = onLessonClicked;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson_card, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.bind(lesson, onLessonClicked);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailImageView;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView durationTextView;
        private ProgressBar progressBar;
        private View cardView;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            progressBar = itemView.findViewById(R.id.progressBar);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bind(Lesson lesson, Consumer<Lesson> onLessonClicked) {
            titleTextView.setText(lesson.getTitle());
            descriptionTextView.setText(lesson.getDescription());
            durationTextView.setText(lesson.getEstimatedDurationMinutes() + " min");
            
            if (lesson.getThumbnailUrl() != null && !lesson.getThumbnailUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(lesson.getThumbnailUrl())
                        .into(thumbnailImageView);
            }

            cardView.setOnClickListener(v -> onLessonClicked.accept(lesson));
        }
    }
}
