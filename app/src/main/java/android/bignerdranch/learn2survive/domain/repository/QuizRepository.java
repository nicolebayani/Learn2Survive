package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.Question;
import android.bignerdranch.learn2survive.domain.model.Quiz;
import android.bignerdranch.learn2survive.domain.model.QuizAttempt;
import android.bignerdranch.learn2survive.domain.model.QuizResult;

import java.util.List;

public interface QuizRepository {
    void getQuiz(String quizId, QuizCallback callback);
    void getQuestionsForQuiz(String quizId, QuestionsCallback callback);
    void saveQuizAttempt(QuizAttempt attempt, SaveCallback callback);
    void saveQuizResult(QuizResult result, SaveCallback callback);
    void getUserQuizResults(String userId, QuizResultsCallback callback);
    void getQuizByLessonId(String lessonId, QuizCallback callback);

    interface QuizCallback {
        void onSuccess(Quiz quiz);
        void onFailure(Exception e);
    }

    interface QuestionsCallback {
        void onSuccess(List<Question> questions);
        void onFailure(Exception e);
    }

    interface SaveCallback {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }

    interface QuizResultsCallback {
        void onSuccess(List<QuizResult> results);
        void onFailure(Exception e);
    }
}
