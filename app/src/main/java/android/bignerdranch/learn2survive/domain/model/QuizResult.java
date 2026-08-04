package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;

public class QuizResult {
    private String id;
    private String userId;
    private String quizId;
    private String lessonId;
    private String quizTitle;
    private Date completedAt;
    private int score;
    private int totalScore;
    private double accuracy;
    private int timeTakenSeconds;
    private int stars;
    private int xpEarned;
    private int coinsEarned;
    private boolean isPassed;
    private int correctAnswers;
    private int totalQuestions;

    public QuizResult() {
    }

    public QuizResult(String id, String userId, String quizId, String lessonId,
                      String quizTitle, Date completedAt, int score, int totalScore,
                      double accuracy, int timeTakenSeconds, int stars,
                      int xpEarned, int coinsEarned, boolean isPassed,
                      int correctAnswers, int totalQuestions) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.lessonId = lessonId;
        this.quizTitle = quizTitle;
        this.completedAt = completedAt;
        this.score = score;
        this.totalScore = totalScore;
        this.accuracy = accuracy;
        this.timeTakenSeconds = timeTakenSeconds;
        this.stars = stars;
        this.xpEarned = xpEarned;
        this.coinsEarned = coinsEarned;
        this.isPassed = isPassed;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(int timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
