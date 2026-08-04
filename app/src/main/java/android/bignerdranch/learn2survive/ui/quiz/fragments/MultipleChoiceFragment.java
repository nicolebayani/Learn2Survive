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

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Question;

public class MultipleChoiceFragment extends Fragment {
    private static final String ARG_QUESTION = "question";

    private Question question;
    private TextView questionTextView;
    private Button[] optionButtons;
    private int selectedAnswerIndex = -1;

    public static MultipleChoiceFragment newInstance(Question question) {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);

        questionTextView = view.findViewById(R.id.questionTextView);
        optionButtons = new Button[4];
        optionButtons[0] = view.findViewById(R.id.option1Button);
        optionButtons[1] = view.findViewById(R.id.option2Button);
        optionButtons[2] = view.findViewById(R.id.option3Button);
        optionButtons[3] = view.findViewById(R.id.option4Button);

        setupQuestion();
        setupOptionListeners();

        return view;
    }

    private void setupQuestion() {
        if (question != null) {
            questionTextView.setText(question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size() && i < 4; i++) {
                optionButtons[i].setText(options.get(i));
                optionButtons[i].setVisibility(View.VISIBLE);
            }
            for (int i = options.size(); i < 4; i++) {
                optionButtons[i].setVisibility(View.GONE);
            }
        }
    }

    private void setupOptionListeners() {
        for (int i = 0; i < 4; i++) {
            final int index = i;
            optionButtons[i].setOnClickListener(v -> {
                selectedAnswerIndex = index;
                highlightSelectedOption(index);
                notifyAnswerSelected(index);
            });
        }
    }

    private void highlightSelectedOption(int index) {
        for (int i = 0; i < 4; i++) {
            if (i == index) {
                optionButtons[i].setBackgroundResource(R.drawable.selected_option_background);
            } else {
                optionButtons[i].setBackgroundResource(R.drawable.option_background);
            }
        }
    }

    private void notifyAnswerSelected(int index) {
        if (getActivity() instanceof AnswerListener) {
            ((AnswerListener) getActivity()).onAnswerSelected(index);
        }
    }

    public interface AnswerListener {
        void onAnswerSelected(int answerIndex);
    }
}
