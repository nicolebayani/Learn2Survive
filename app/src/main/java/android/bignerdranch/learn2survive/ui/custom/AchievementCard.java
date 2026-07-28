package android.bignerdranch.learn2survive.ui.custom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.bignerdranch.learn2survive.R;

public class AchievementCard extends CardView {
    private ImageView iconView;
    private TextView titleView;
    private TextView descriptionView;
    private TextView dateView;
    private LinearLayout container;

    public AchievementCard(Context context) {
        super(context);
        init(context, null);
    }

    public AchievementCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AchievementCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_achievement_card, this, true);
        
        iconView = findViewById(R.id.achievementIcon);
        titleView = findViewById(R.id.achievementTitle);
        descriptionView = findViewById(R.id.achievementDescription);
        dateView = findViewById(R.id.achievementDate);
        container = findViewById(R.id.achievementContainer);
        
        setCardElevation(6f);
        setRadius(12f);
        
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.AchievementCard,
                    0, 0
            );
            
            try {
                String title = a.getString(R.styleable.AchievementCard_cardTitle);
                String description = a.getString(R.styleable.AchievementCard_description);
                String date = a.getString(R.styleable.AchievementCard_date);
                int iconRes = a.getResourceId(R.styleable.AchievementCard_cardIcon, 0);
                boolean unlocked = a.getBoolean(R.styleable.AchievementCard_unlocked, true);
                int cardColor = a.getColor(R.styleable.AchievementCard_cardBackgroundColor,
                        ContextCompat.getColor(context, R.color.surface));
                
                if (title != null) setTitle(title);
                if (description != null) setDescription(description);
                if (date != null) setDate(date);
                if (iconRes != 0) setIcon(iconRes);
                setUnlocked(unlocked);
                setCardColor(cardColor);
            } finally {
                a.recycle();
            }
        }
        
        setClickable(true);
        setFocusable(true);
    }

    @Override
    public boolean performClick() {
        animatePress();
        return super.performClick();
    }

    private void animatePress() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.95f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.95f);
        scaleX.setDuration(100);
        scaleY.setDuration(100);
        scaleX.start();
        scaleY.start();
        
        postDelayed(() -> {
            ObjectAnimator scaleBackX = ObjectAnimator.ofFloat(this, "scaleX", 0.95f, 1f);
            ObjectAnimator scaleBackY = ObjectAnimator.ofFloat(this, "scaleY", 0.95f, 1f);
            scaleBackX.setDuration(100);
            scaleBackY.setDuration(100);
            scaleBackX.start();
            scaleBackY.start();
        }, 100);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setDescription(String description) {
        descriptionView.setText(description);
    }

    public void setDate(String date) {
        dateView.setText(date);
    }

    public void setIcon(int iconRes) {
        iconView.setImageResource(iconRes);
    }

    public void setUnlocked(boolean unlocked) {
        float alpha = unlocked ? 1f : 0.5f;
        setAlpha(alpha);
        if (!unlocked) {
            iconView.setColorFilter(
                    ContextCompat.getColor(getContext(), R.color.text_hint));
        } else {
            iconView.clearColorFilter();
        }
    }

    public void setCardColor(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(12f);
        container.setBackground(drawable);
    }
}
