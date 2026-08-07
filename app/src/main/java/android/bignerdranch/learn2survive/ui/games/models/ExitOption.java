package android.bignerdranch.learn2survive.ui.games.models;

public class ExitOption {
    private String action;
    private String explanation;
    private boolean correct;
    private int points;

    public ExitOption(String action, String explanation, boolean correct, int points) {
        this.action = action;
        this.explanation = explanation;
        this.correct = correct;
        this.points = points;
    }

    public String getAction() {
        return action;
    }

    public String getExplanation() {
        return explanation;
    }

    public boolean isCorrect() {
        return correct;
    }

    public int getPoints() {
        return points;
    }
}