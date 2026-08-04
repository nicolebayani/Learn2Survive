package android.bignerdranch.learn2survive.data.remote;

import android.bignerdranch.learn2survive.domain.model.DisasterScenario;
import android.bignerdranch.learn2survive.domain.model.DisasterSimulationType;
import android.bignerdranch.learn2survive.domain.model.ScenarioNode;
import android.bignerdranch.learn2survive.domain.model.SimulationAttempt;
import android.bignerdranch.learn2survive.domain.model.SimulationResult;
import android.bignerdranch.learn2survive.domain.repository.SimulationRepository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class SimulationRepositoryImpl implements SimulationRepository {
    private FirebaseFirestore db;
    private CollectionReference scenariosCollection;
    private CollectionReference nodesCollection;
    private CollectionReference attemptsCollection;
    private CollectionReference resultsCollection;

    public SimulationRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        scenariosCollection = db.collection("disaster_scenarios");
        nodesCollection = db.collection("scenario_nodes");
        attemptsCollection = db.collection("simulation_attempts");
        resultsCollection = db.collection("simulation_results");
    }

    @Override
    public void getScenario(String scenarioId, ScenarioCallback callback) {
        scenariosCollection.document(scenarioId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    DisasterScenario scenario = documentSnapshot.toObject(DisasterScenario.class);
                    if (scenario != null) {
                        scenario.setId(documentSnapshot.getId());
                        callback.onSuccess(scenario);
                    } else {
                        callback.onFailure(new Exception("Scenario not found"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getScenarioNode(String nodeId, NodeCallback callback) {
        nodesCollection.document(nodeId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    ScenarioNode node = documentSnapshot.toObject(ScenarioNode.class);
                    if (node != null) {
                        node.setId(documentSnapshot.getId());
                        callback.onSuccess(node);
                    } else {
                        callback.onFailure(new Exception("Node not found"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getAllScenarios(ScenariosCallback callback) {
        scenariosCollection.get()
                .addOnSuccessListener(querySnapshot -> {
                    List<DisasterScenario> scenarios = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot) {
                        DisasterScenario scenario = doc.toObject(DisasterScenario.class);
                        if (scenario != null) {
                            scenario.setId(doc.getId());
                            scenarios.add(scenario);
                        }
                    }
                    callback.onSuccess(scenarios);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void saveSimulationAttempt(SimulationAttempt attempt, SaveCallback callback) {
        DocumentReference docRef = attemptsCollection.document();
        attempt.setId(docRef.getId());
        docRef.set(attempt)
                .addOnSuccessListener(aVoid -> callback.onSuccess(docRef.getId()))
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void saveSimulationResult(SimulationResult result, SaveCallback callback) {
        DocumentReference docRef = resultsCollection.document();
        result.setId(docRef.getId());
        docRef.set(result)
                .addOnSuccessListener(aVoid -> callback.onSuccess(docRef.getId()))
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getUserSimulationResults(String userId, ResultsCallback callback) {
        resultsCollection.whereEqualTo("userId", userId)
                .orderBy("completedAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<SimulationResult> results = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot) {
                        SimulationResult result = doc.toObject(SimulationResult.class);
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
    public void getScenariosByType(DisasterSimulationType type, ScenariosCallback callback) {
        scenariosCollection.whereEqualTo("type", type)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<DisasterScenario> scenarios = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot) {
                        DisasterScenario scenario = doc.toObject(DisasterScenario.class);
                        if (scenario != null) {
                            scenario.setId(doc.getId());
                            scenarios.add(scenario);
                        }
                    }
                    callback.onSuccess(scenarios);
                })
                .addOnFailureListener(callback::onFailure);
    }
}
