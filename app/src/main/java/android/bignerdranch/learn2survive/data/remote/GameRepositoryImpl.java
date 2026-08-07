package android.bignerdranch.learn2survive.data.remote;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.bignerdranch.learn2survive.domain.model.GameScore;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.domain.repository.GameRepository;

public class GameRepositoryImpl implements GameRepository {
    private FirebaseFirestore db;
    private static final String SCORES_COLLECTION = "game_scores";

    public GameRepositoryImpl() {
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void saveGameScore(GameScore score, GameScoreCallback callback) {
        db.collection(SCORES_COLLECTION)
                .add(score)
                .addOnSuccessListener(documentReference -> {
                    score.setId(documentReference.getId());
                    callback.onSuccess(documentReference.getId());
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getHighScores(GameType gameType, int limit, HighScoresCallback callback) {
        db.collection(SCORES_COLLECTION)
                .whereEqualTo("gameType", gameType.toString())
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<GameScore> scores = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        GameScore score = document.toObject(GameScore.class);
                        score.setId(document.getId());
                        scores.add(score);
                    }
                    callback.onSuccess(scores);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getUserHighScores(String userId, GameType gameType, int limit, HighScoresCallback callback) {
        db.collection(SCORES_COLLECTION)
                .whereEqualTo("userId", userId)
                .whereEqualTo("gameType", gameType.toString())
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<GameScore> scores = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        GameScore score = document.toObject(GameScore.class);
                        score.setId(document.getId());
                        scores.add(score);
                    }
                    callback.onSuccess(scores);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getUserBestScore(String userId, GameType gameType, BestScoreCallback callback) {
        db.collection(SCORES_COLLECTION)
                .whereEqualTo("userId", userId)
                .whereEqualTo("gameType", gameType.toString())
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        GameScore score = queryDocumentSnapshots.getDocuments().get(0).toObject(GameScore.class);
                        score.setId(queryDocumentSnapshots.getDocuments().get(0).getId());
                        callback.onSuccess(score);
                    } else {
                        callback.onSuccess(null);
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }
}