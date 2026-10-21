package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.ScenarioAttempt;

import java.util.List;

public interface ScenarioAttemptRepository {
    void saveScenarioAttempt(ScenarioAttempt attempt, SaveCallback callback);
    void getScenarioAttempts(String userId, String scenarioId, LoadCallback callback);
    void getLatestAttempt(String userId, String scenarioId, LoadSingleCallback callback);
    void markRewardClaimed(String attemptId, SaveCallback callback);
    void getAttemptCount(String userId, String scenarioId, CountCallback callback);

    interface SaveCallback {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }

    interface LoadCallback {
        void onSuccess(List<ScenarioAttempt> attempts);
        void onFailure(Exception e);
    }

    interface LoadSingleCallback {
        void onSuccess(ScenarioAttempt attempt);
        void onFailure(Exception e);
    }

    interface CountCallback {
        void onSuccess(int count);
        void onFailure(Exception e);
    }
}
