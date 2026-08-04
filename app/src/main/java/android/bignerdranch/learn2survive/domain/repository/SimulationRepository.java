package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.DisasterScenario;
import android.bignerdranch.learn2survive.domain.model.ScenarioNode;
import android.bignerdranch.learn2survive.domain.model.SimulationAttempt;
import android.bignerdranch.learn2survive.domain.model.SimulationResult;

import java.util.List;

public interface SimulationRepository {
    void getScenario(String scenarioId, ScenarioCallback callback);
    void getScenarioNode(String nodeId, NodeCallback callback);
    void getAllScenarios(ScenariosCallback callback);
    void saveSimulationAttempt(SimulationAttempt attempt, SaveCallback callback);
    void saveSimulationResult(SimulationResult result, SaveCallback callback);
    void getUserSimulationResults(String userId, ResultsCallback callback);
    void getScenariosByType(DisasterSimulationType type, ScenariosCallback callback);

    interface ScenarioCallback {
        void onSuccess(DisasterScenario scenario);
        void onFailure(Exception e);
    }

    interface NodeCallback {
        void onSuccess(ScenarioNode node);
        void onFailure(Exception e);
    }

    interface ScenariosCallback {
        void onSuccess(List<DisasterScenario> scenarios);
        void onFailure(Exception e);
    }

    interface SaveCallback {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }

    interface ResultsCallback {
        void onSuccess(List<SimulationResult> results);
        void onFailure(Exception e);
    }
}
