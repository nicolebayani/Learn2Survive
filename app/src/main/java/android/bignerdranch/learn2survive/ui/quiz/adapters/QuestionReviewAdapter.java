package android.bignerdranch.learn2survive.ui.quiz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Question;

public class QuestionReviewAdapter extends RecyclerView.Adapter<QuestionReviewAdapter.ViewHolder> {
    private List<Question> questions;

    public QuestionReviewAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.bind(question, position + 1);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questionNumberTextView;
        private TextView questionTextView;
        private TextView correctAnswerTextView;
        private TextView explanationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNumberTextView = itemView.findViewById(R.id.questionNumberTextView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            correctAnswerTextView = itemView.findViewById(R.id.correctAnswerTextView);
            explanationTextView = itemView.findViewById(R.id.explanationTextView);
        }

        public void bind(Question question, int number) {
            questionNumberTextView.setText("Question " + number);
            questionTextView.setText(question.getQuestionText());
            
            if (question.getOptions() != null && question.getCorrectAnswerIndex() < question.getOptions().size()) {
                correctAnswerTextView.setText(
                    "Correct Answer: " + question.getOptions().get(question.getCorrectAnswerIndex())
                );
            }
            
            if (question.getExplanation() != null && !question.getExplanation().isEmpty()) {
                explanationTextView.setText("Explanation: " + question.getExplanation());
                explanationTextView.setVisibility(View.VISIBLE);
            } else {
                explanationTextView.setVisibility(View.GONE);
            }
        }
    }
}
