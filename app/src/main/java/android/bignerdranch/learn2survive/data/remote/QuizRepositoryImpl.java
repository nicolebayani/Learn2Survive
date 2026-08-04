package android.bignerdranch.learn2survive.data.remote;

import android.bignerdranch.learn2survive.domain.model.Question;
import android.bignerdranch.learn2survive.domain.model.Quiz;
import android.bignerdranch.learn2survive.domain.model.QuizAttempt;
import android.bignerdranch.learn2survive.domain.model.QuizResult;
import android.bignerdranch.learn2survive.domain.repository.QuizRepository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizRepositoryImpl implements QuizRepository {
    private FirebaseFirestore db;
    private CollectionReference quizzesCollection;
    private CollectionReference questionsCollection;
    private CollectionReference attemptsCollection;
    private CollectionReference resultsCollection;

    public QuizRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        quizzesCollection = db.collection("quizzes");
        questionsCollection = db.collection("questions");
        attemptsCollection = db.collection("quiz_attempts");
        resultsCollection = db.collection("quiz_results");
    }

    @Override
    public void getQuiz(String quizId, QuizCallback callback) {
        quizzesCollection.document(quizId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Quiz quiz = documentSnapshot.toObject(Quiz.class);
                    if (quiz != null) {
                        quiz.setId(documentSnapshot.getId());
                        callback.onSuccess(quiz);
                    } else {
                        callback.onFailure(new Exception("Quiz not found"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getQuestionsForQuiz(String quizId, QuestionsCallback callback) {
        questionsCollection.whereEqualTo("quizId", quizId)
                .orderBy("order")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Question> questions = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot) {
                        Question question = doc.toObject(Question.class);
                        if (question != null) {
                            question.setId(doc.getId());
                            questions.add(question);
                        }
                    }
                    callback.onSuccess(questions);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void saveQuizAttempt(QuizAttempt attempt, SaveCallback callback) {
        DocumentReference docRef = attemptsCollection.document();
        attempt.setId(docRef.getId());
        docRef.set(attempt)
                .addOnSuccessListener(aVoid -> callback.onSuccess(docRef.getId()))
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void saveQuizResult(QuizResult result, SaveCallback callback) {
        DocumentReference docRef = resultsCollection.document();
        result.setId(docRef.getId());
        docRef.set(result)
                .addOnSuccessListener(aVoid -> callback.onSuccess(docRef.getId()))
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getUserQuizResults(String userId, QuizResultsCallback callback) {
        resultsCollection.whereEqualTo("userId", userId)
                .orderBy("completedAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<QuizResult> results = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot) {
                        QuizResult result = doc.toObject(QuizResult.class);
                        if (result != null) {
                            result.setId(doc.getId());
                            results.add(result);
                        }
                    }
                    callback.onSuccess(results);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getQuizByLessonId(String lessonId, QuizCallback callback) {
        quizzesCollection.whereEqualTo("lessonId", lessonId)
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
                        Quiz quiz = doc.toObject(Quiz.class);
                        if (quiz != null) {
                            quiz.setId(doc.getId());
                            callback.onSuccess(quiz);
                        } else {
                            callback.onFailure(new Exception("Quiz not found"));
                        }
                    } else {
                        callback.onFailure(new Exception("Quiz not found"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }
}
