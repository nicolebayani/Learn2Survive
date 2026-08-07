package android.bignerdranch.learn2survive.ui.gamification;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Achievement;

public class AchievementUnlockedView extends LinearLayout {
    private ImageView achievementIcon;
    private TextView achievementTitle;
    private TextView achievementDescription;
    private TextView rewardText;
    private LottieAnimationView confettiAnimation;
    private View backgroundView;

    public AchievementUnlockedView(Context context) {
        super(context);
        init(context);
    }

    public AchievementUnlockedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AchievementUnlockedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_achievement_unlocked, this, true);
        
        achievementIcon = findViewById(R.id.achievementIcon);
        achievementTitle = findViewById(R.id.achievementTitle);
        achievementDescription = findViewById(R.id.achievementDescription);
        rewardText = findViewById(R.id.rewardText);
        confettiAnimation = findViewById(R.id.confettiAnimation);
        backgroundView = findViewById(R.id.backgroundView);
        
        setVisibility(GONE);
    }

    public void showAchievement(Achievement achievement, OnDismissListener listener) {
        if (achievement == null) return;
        
        // Set content
        achievementTitle.setText(achievement.getTitle());
        achievementDescription.setText(achievement.getDescription());
        rewardText.setText(String.format("+%d XP  +%d Coins", achievement.getXpReward(), achievement.getCoinsReward()));
        
        // Set icon (in real app, load from resource ID)
        achievementIcon.setImageResource(android.R.drawable.ic_menu_star);
        
        // Show the view
        setVisibility(VISIBLE);
        
        // Play animations
        playAchievementAnimation(listener);
    }

    private void playAchievementAnimation(OnDismissListener listener) {
        // Reset animations
        backgroundView.setAlpha(0f);
        backgroundView.setScaleX(0.5f);
        backgroundView.setScaleY(0.5f);
        achievementIcon.setAlpha(0f);
        achievementIcon.setScaleX(0f);
        achievementIcon.setScaleY(0f);
        
        // Confetti animation
        confettiAnimation.setAnimation(R.raw.confetti);
        confettiAnimation.playAnimation();
        
        // Background slide-in animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(backgroundView, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(backgroundView, "scaleY", 0.5f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(backgroundView, "alpha", 0f, 1f);
        
        AnimatorSet backgroundAnim = new AnimatorSet();
        backgroundAnim.playTogether(scaleX, scaleY, alpha);
        backgroundAnim.setDuration(300);
        
        // Icon bounce animation
        ObjectAnimator iconScaleX = ObjectAnimator.ofFloat(achievementIcon, "scaleX", 0f, 1.2f, 1f);
        ObjectAnimator iconScaleY = ObjectAnimator.ofFloat(achievementIcon, "scaleY", 0f, 1.2f, 1f);
        ObjectAnimator iconAlpha = ObjectAnimator.ofFloat(achievementIcon, "alpha", 0f, 1f);
        
        AnimatorSet iconAnim = new AnimatorSet();
        iconAnim.playTogether(iconScaleX, iconScaleY, iconAlpha);
        iconAnim.setDuration(400);
        iconAnim.setStartDelay(200);
        
        // Sequential animations
        AnimatorSet fullAnimation = new AnimatorSet();
        fullAnimation.playSequentially(backgroundAnim, iconAnim);
        
        fullAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                // Auto dismiss after 3 seconds
                postDelayed(() -> {
                    dismissAnimation(listener);
                }, 3000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        
        fullAnimation.start();
    }

    private void dismissAnimation(OnDismissListener listener) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        alpha.setDuration(300);
        
        alpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
                confettiAnimation.cancelAnimation();
                if (listener != null) {
                    listener.onDismiss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        
        alpha.start();
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}