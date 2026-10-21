package android.bignerdranch.learn2survive.data.repository;

import android.bignerdranch.learn2survive.domain.model.ScenarioAttempt;
import android.bignerdranch.learn2survive.domain.repository.ScenarioAttemptRepository;
import android.bignerdranch.learn2survive.Learn2SurviveApplication;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScenarioAttemptRepositoryImpl implements ScenarioAttemptRepository {
    private FirebaseFirestore firestore;
    private static final String SCENARIO_ATTEMPTS_COLLECTION = "scenario_attempts";

    public ScenarioAttemptRepositoryImpl() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    private boolean isNetworkAvailable() {
        return Learn2SurviveApplication.getInstance().isNetworkAvailable();
    }

    @Override
    public void saveScenarioAttempt(ScenarioAttempt attempt, SaveCallback callback) {
        if (!isNetworkAvailable()) {
            callback.onFailure(new Exception("No internet connection. Please check your network and try again."));
            return;
        }

        if (attempt.getUserId() == null || attempt.getUserId().isEmpty()) {
            callback.onFailure(new IllegalArgumentException("User ID cannot be null or empty"));
            return;
        }

        if (attempt.getScenarioId() == null || attempt.getScenarioId().isEmpty()) {
            callback.onFailure(new IllegalArgumentException("Scenario ID cannot be null or empty"));
            return;
        }

        CollectionReference attemptsRef = firestore.collection(SCENARIO_ATTEMPTS_COLLECTION);

        if (attempt.getId() == null || attempt.getId().isEmpty()) {
            attemptsRef.add(attempt)
                    .addOnSuccessListener(documentReference -> callback.onSuccess(documentReference.getId()))
                    .addOnFailureListener(callback::onFailure);
        } else {
            attemptsRef.document(attempt.getId())
                    .set(attempt)
                    .addOnSuccessListener(aVoid -> callback.onSuccess(attempt.getId()))
                    .addOnFailureListener(callback::onFailure);
        }
    }

    @Override
    public void getScenarioAttempts(String userId, String scenarioId, LoadCallback callback) {
        if (!isNetworkAvailable()) {
            callback.onFailure(new Exception("No internet connection. Please check your network and try again."));
            return;
        }

        if (userId == null || userId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("User ID cannot be null or empty"));
            return;
        }

        if (scenarioId == null || scenarioId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("Scenario ID cannot be null or empty"));
            return;
        }

        firestore.collection(SCENARIO_ATTEMPTS_COLLECTION)
                .whereEqualTo("userId", userId)
                .whereEqualTo("scenarioId", scenarioId)
                .orderBy("completedAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<ScenarioAttempt> attempts = new ArrayList<>();
                    for (com.google.firebase.firestore.DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        ScenarioAttempt attempt = doc.toObject(ScenarioAttempt.class);
                        if (attempt != null) {
                            attempt.setId(doc.getId());
                            attempts.add(attempt);
                        }
                    }
                    callback.onSuccess(attempts);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getLatestAttempt(String userId, String scenarioId, LoadSingleCallback callback) {
        if (!isNetworkAvailable()) {
            callback.onFailure(new Exception("No internet connection. Please check your network and try again."));
            return;
        }

        if (userId == null || userId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("User ID cannot be null or empty"));
            return;
        }

        if (scenarioId == null || scenarioId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("Scenario ID cannot be null or empty"));
            return;
        }

        firestore.collection(SCENARIO_ATTEMPTS_COLLECTION)
                .whereEqualTo("userId", userId)
                .whereEqualTo("scenarioId", scenarioId)
                .orderBy("completedAt", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        ScenarioAttempt attempt = querySnapshot.getDocuments().get(0).toObject(ScenarioAttempt.class);
                        if (attempt != null) {
                            attempt.setId(querySnapshot.getDocuments().get(0).getId());
                            callback.onSuccess(attempt);
                        } else {
                            callback.onFailure(new Exception("Failed to parse attempt data"));
                        }
                    } else {
                        callback.onFailure(new Exception("No attempts found"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void markRewardClaimed(String attemptId, SaveCallback callback) {
        if (!isNetworkAvailable()) {
            callback.onFailure(new Exception("No internet connection. Please check your network and try again."));
            return;
        }

        if (attemptId == null || attemptId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("Attempt ID cannot be null or empty"));
            return;
        }

        DocumentReference docRef = firestore.collection(SCENARIO_ATTEMPTS_COLLECTION).document(attemptId);
        docRef.update("wasRewardClaimed", true)
                .addOnSuccessListener(aVoid -> callback.onSuccess(attemptId))
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getAttemptCount(String userId, String scenarioId, CountCallback callback) {
        if (!isNetworkAvailable()) {
            callback.onFailure(new Exception("No internet connection. Please check your network and try again."));
            return;
        }

        if (userId == null || userId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("User ID cannot be null or empty"));
            return;
        }

        if (scenarioId == null || scenarioId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("Scenario ID cannot be null or empty"));
            return;
        }

        firestore.collection(SCENARIO_ATTEMPTS_COLLECTION)
                .whereEqualTo("userId", userId)
                .whereEqualTo("scenarioId", scenarioId)
                .get()
                .addOnSuccessListener(querySnapshot -> callback.onSuccess(querySnapshot.size()))
                .addOnFailureListener(callback::onFailure);
    }
}
