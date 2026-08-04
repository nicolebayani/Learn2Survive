package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class QuizAttempt {
    private String id;
    private String userId;
    private String quizId;
    private String lessonId;
    private Date startTime;
    private Date endTime;
    private int timeTakenSeconds;
    private List<String> questionIds;
    private Map<String, Integer> userAnswers;
    private int correctAnswers;
    private int wrongAnswers;
    private int skippedAnswers;
    private int score;
    private int totalPoints;
    private boolean isCompleted;
    private boolean isPassed;

    public QuizAttempt() {
    }

    public QuizAttempt(String id, String userId, String quizId, String lessonId,
                       Date startTime, Date endTime, int timeTakenSeconds,
                       List<String> questionIds, Map<String, Integer> userAnswers,
                       int correctAnswers, int wrongAnswers, int skippedAnswers,
                       int score, int totalPoints, boolean isCompleted, boolean isPassed) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.lessonId = lessonId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeTakenSeconds = timeTakenSeconds;
        this.questionIds = questionIds;
        this.userAnswers = userAnswers;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.skippedAnswers = skippedAnswers;
        this.score = score;
        this.totalPoints = totalPoints;
        this.isCompleted = isCompleted;
        this.isPassed = isPassed;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(int timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public Map<String, Integer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Map<String, Integer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getSkippedAnswers() {
        return skippedAnswers;
    }

    public void setSkippedAnswers(int skippedAnswers) {
        this.skippedAnswers = skippedAnswers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }
}
