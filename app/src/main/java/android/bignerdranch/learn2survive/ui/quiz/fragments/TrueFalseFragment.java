package android.bignerdranch.learn2survive.ui.quiz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Question;

public class TrueFalseFragment extends Fragment {
    private static final String ARG_QUESTION = "question";

    private Question question;
    private TextView questionTextView;
    private Button trueButton;
    private Button falseButton;
    private int selectedAnswerIndex = -1;

    public static TrueFalseFragment newInstance(Question question) {
        TrueFalseFragment fragment = new TrueFalseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_true_false, container, false);

        questionTextView = view.findViewById(R.id.questionTextView);
        trueButton = view.findViewById(R.id.trueButton);
        falseButton = view.findViewById(R.id.falseButton);

        setupQuestion();
        setupOptionListeners();

        return view;
    }

    private void setupQuestion() {
        if (question != null) {
            questionTextView.setText(question.getQuestionText());
        }
    }

    private void setupOptionListeners() {
        trueButton.setOnClickListener(v -> {
            selectedAnswerIndex = 0;
            highlightSelectedOption(0);
            notifyAnswerSelected(0);
        });

        falseButton.setOnClickListener(v -> {
            selectedAnswerIndex = 1;
            highlightSelectedOption(1);
            notifyAnswerSelected(1);
        });
    }

    private void highlightSelectedOption(int index) {
        if (index == 0) {
            trueButton.setBackgroundResource(R.drawable.selected_option_background);
            falseButton.setBackgroundResource(R.drawable.option_background);
        } else {
            falseButton.setBackgroundResource(R.drawable.selected_option_background);
            trueButton.setBackgroundResource(R.drawable.option_background);
        }
    }

    private void notifyAnswerSelected(int index) {
        if (getActivity() instanceof MultipleChoiceFragment.AnswerListener) {
            ((MultipleChoiceFragment.AnswerListener) getActivity()).onAnswerSelected(index);
        }
    }
}
