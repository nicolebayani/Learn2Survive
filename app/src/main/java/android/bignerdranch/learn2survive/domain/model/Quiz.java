package android.bignerdranch.learn2survive.domain.model;

import java.util.List;

public class Quiz {
    private String id;
    private String lessonId;
    private String title;
    private String description;
    private List<String> questionIds;
    private int totalQuestions;
    private int timeLimitSeconds;
    private int passingScore;
    private int xpReward;
    private int coinReward;
    private boolean isRandomized;
    private boolean isTimed;
    private boolean showExplanation;

    public Quiz() {
    }

    public Quiz(String id, String lessonId, String title, String description,
                List<String> questionIds, int totalQuestions, int timeLimitSeconds,
                int passingScore, int xpReward, int coinReward,
                boolean isRandomized, boolean isTimed, boolean showExplanation) {
        this.id = id;
        this.lessonId = lessonId;
        this.title = title;
        this.description = description;
        this.questionIds = questionIds;
        this.totalQuestions = totalQuestions;
        this.timeLimitSeconds = timeLimitSeconds;
        this.passingScore = passingScore;
        this.xpReward = xpReward;
        this.coinReward = coinReward;
        this.isRandomized = isRandomized;
        this.isTimed = isTimed;
        this.showExplanation = showExplanation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public void setTimeLimitSeconds(int timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public int getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(int passingScore) {
        this.passingScore = passingScore;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public int getCoinReward() {
        return coinReward;
    }

    public void setCoinReward(int coinReward) {
        this.coinReward = coinReward;
    }

    public boolean isRandomized() {
        return isRandomized;
    }

    public void setRandomized(boolean randomized) {
        isRandomized = randomized;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }

    public boolean isShowExplanation() {
        return showExplanation;
    }

    public void setShowExplanation(boolean showExplanation) {
        this.showExplanation = showExplanation;
    }
}
