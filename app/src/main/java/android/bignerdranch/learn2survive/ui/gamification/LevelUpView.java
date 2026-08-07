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
import android.bignerdranch.learn2survive.domain.model.PlayerLevel;

public class LevelUpView extends LinearLayout {
    private TextView levelNumber;
    private TextView levelTitle;
    private TextView rewardText;
    private LottieAnimationView levelUpAnimation;
    private LottieAnimationView confettiAnimation;
    private View backgroundView;

    public LevelUpView(Context context) {
        super(context);
        init(context);
    }

    public LevelUpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LevelUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_level_up, this, true);
        
        levelNumber = findViewById(R.id.levelNumber);
        levelTitle = findViewById(R.id.levelTitle);
        rewardText = findViewById(R.id.rewardText);
        levelUpAnimation = findViewById(R.id.levelUpAnimation);
        confettiAnimation = findViewById(R.id.confettiAnimation);
        backgroundView = findViewById(R.id.backgroundView);
        
        setVisibility(GONE);
    }

    public void showLevelUp(PlayerLevel newLevel, OnDismissListener listener) {
        if (newLevel == null) return;
        
        // Set content
        levelNumber.setText(String.valueOf(newLevel.getLevel()));
        levelTitle.setText(newLevel.getTitle());
        rewardText.setText(String.format("Level Up! +%d Coins", newLevel.getCoinsReward()));
        
        // Show the view
        setVisibility(VISIBLE);
        
        // Play animations
        playLevelUpAnimation(listener);
    }

    private void playLevelUpAnimation(OnDismissListener listener) {
        // Reset animations
        backgroundView.setAlpha(0f);
        backgroundView.setScaleX(0.3f);
        backgroundView.setScaleY(0.3f);
        levelNumber.setAlpha(0f);
        levelNumber.setScaleX(0f);
        levelNumber.setScaleY(0f);
        
        // Level up animation
        levelUpAnimation.setAnimation(R.raw.level_up);
        levelUpAnimation.playAnimation();
        
        // Confetti animation
        confettiAnimation.setAnimation(R.raw.confetti);
        confettiAnimation.playAnimation();
        
        // Background scale animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(backgroundView, "scaleX", 0.3f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(backgroundView, "scaleY", 0.3f, 1.2f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(backgroundView, "alpha", 0f, 1f);
        
        AnimatorSet backgroundAnim = new AnimatorSet();
        backgroundAnim.playTogether(scaleX, scaleY, alpha);
        backgroundAnim.setDuration(500);
        
        // Level number bounce animation
        ObjectAnimator numberScaleX = ObjectAnimator.ofFloat(levelNumber, "scaleX", 0f, 1.5f, 1f);
        ObjectAnimator numberScaleY = ObjectAnimator.ofFloat(levelNumber, "scaleY", 0f, 1.5f, 1f);
        ObjectAnimator numberAlpha = ObjectAnimator.ofFloat(levelNumber, "alpha", 0f, 1f);
        
        AnimatorSet numberAnim = new AnimatorSet();
        numberAnim.playTogether(numberScaleX, numberScaleY, numberAlpha);
        numberAnim.setDuration(600);
        numberAnim.setStartDelay(300);
        
        // Sequential animations
        AnimatorSet fullAnimation = new AnimatorSet();
        fullAnimation.playSequentially(backgroundAnim, numberAnim);
        
        fullAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                // Auto dismiss after 4 seconds
                postDelayed(() -> {
                    dismissAnimation(listener);
                }, 4000);
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
        alpha.setDuration(400);
        
        alpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
                levelUpAnimation.cancelAnimation();
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