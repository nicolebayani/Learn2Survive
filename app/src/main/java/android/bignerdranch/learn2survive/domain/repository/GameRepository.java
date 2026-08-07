package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.GameScore;
import android.bignerdranch.learn2survive.domain.model.GameType;

import java.util.List;

public interface GameRepository {
    void saveGameScore(GameScore score, GameScoreCallback callback);
    void getHighScores(GameType gameType, int limit, HighScoresCallback callback);
    void getUserHighScores(String userId, GameType gameType, int limit, HighScoresCallback callback);
    void getUserBestScore(String userId, GameType gameType, BestScoreCallback callback);
    
    interface GameScoreCallback {
        void onSuccess(String scoreId);
        void onFailure(Exception e);
    }
    
    interface HighScoresCallback {
        void onSuccess(List<GameScore> scores);
        void onFailure(Exception e);
    }
    
    interface BestScoreCallback {
        void onSuccess(GameScore score);
        void onFailure(Exception e);
    }
}