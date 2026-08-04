package android.bignerdranch.learn2survive.domain.model;

import java.util.List;

public class Question {
    private String id;
    private String quizId;
    private String questionText;
    private QuestionType type;
    private String imageUrl;
    private String scenarioText;
    private List<String> options;
    private int correctAnswerIndex;
    private String explanation;
    private int timeLimitSeconds;
    private int points;
    private int order;

    public Question() {
    }

    public Question(String id, String quizId, String questionText, QuestionType type,
                    String imageUrl, String scenarioText, List<String> options,
                    int correctAnswerIndex, String explanation, int timeLimitSeconds,
                    int points, int order) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.type = type;
        this.imageUrl = imageUrl;
        this.scenarioText = scenarioText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.explanation = explanation;
        this.timeLimitSeconds = timeLimitSeconds;
        this.points = points;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScenarioText() {
        return scenarioText;
    }

    public void setScenarioText(String scenarioText) {
        this.scenarioText = scenarioText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public void setTimeLimitSeconds(int timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
