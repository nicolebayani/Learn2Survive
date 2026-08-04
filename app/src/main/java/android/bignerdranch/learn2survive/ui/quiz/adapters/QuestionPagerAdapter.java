package android.bignerdranch.learn2survive.ui.quiz.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import android.bignerdranch.learn2survive.domain.model.Question;
import android.bignerdranch.learn2survive.domain.model.QuestionType;
import android.bignerdranch.learn2survive.ui.quiz.fragments.MultipleChoiceFragment;
import android.bignerdranch.learn2survive.ui.quiz.fragments.TrueFalseFragment;
import android.bignerdranch.learn2survive.ui.quiz.fragments.ImageQuestionFragment;
import android.bignerdranch.learn2survive.ui.quiz.fragments.ScenarioQuestionFragment;

public class QuestionPagerAdapter extends FragmentStateAdapter {
    private List<Question> questions;

    public QuestionPagerAdapter(FragmentActivity fragmentActivity, List<Question> questions) {
        super(fragmentActivity);
        this.questions = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Question question = questions.get(position);
        QuestionType type = question.getType();

        switch (type) {
            case MULTIPLE_CHOICE:
                return MultipleChoiceFragment.newInstance(question);
            case TRUE_FALSE:
                return TrueFalseFragment.newInstance(question);
            case IMAGE_QUESTION:
                return ImageQuestionFragment.newInstance(question);
            case SCENARIO_QUESTION:
                return ScenarioQuestionFragment.newInstance(question);
            default:
                return MultipleChoiceFragment.newInstance(question);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
