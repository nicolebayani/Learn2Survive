package android.bignerdranch.learn2survive.ui.onboarding;

public class OnboardingItem {
    private int titleRes;
    private int descriptionRes;
    private String lottieAnimationName;

    public OnboardingItem(int titleRes, int descriptionRes, String lottieAnimationName) {
        this.titleRes = titleRes;
        this.descriptionRes = descriptionRes;
        this.lottieAnimationName = lottieAnimationName;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getDescriptionRes() {
        return descriptionRes;
    }

    public String getLottieAnimationName() {
        return lottieAnimationName;
    }
}
