package android.bignerdranch.learn2survive.ui.custom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.bignerdranch.learn2survive.R;

public class StatCard extends CardView {
    private ImageView iconView;
    private TextView titleView;
    private TextView valueView;
    private LinearLayout container;

    public StatCard(Context context) {
        super(context);
        init(context, null);
    }

    public StatCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StatCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_stat_card, this, true);
        
        iconView = findViewById(R.id.statIcon);
        titleView = findViewById(R.id.statTitle);
        valueView = findViewById(R.id.statValue);
        container = findViewById(R.id.statContainer);
        
        setCardElevation(8f);
        setRadius(16f);
        
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.StatCard,
                    0, 0
            );
            
            try {
                String title = a.getString(R.styleable.StatCard_title);
                String value = a.getString(R.styleable.StatCard_value);
                int iconRes = a.getResourceId(R.styleable.StatCard_icon, 0);
                int cardColor = a.getColor(R.styleable.StatCard_cardColor,
                        ContextCompat.getColor(context, R.color.surface));
                
                if (title != null) setTitle(title);
                if (value != null) setValue(value);
                if (iconRes != 0) setIcon(iconRes);
                setCardColor(cardColor);
            } finally {
                a.recycle();
            }
        }
        
        // Enable click animation
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

    public void setValue(String value) {
        valueView.setText(value);
    }

    public void setIcon(int iconRes) {
        iconView.setImageResource(iconRes);
    }

    public void setCardColor(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(16f);
        container.setBackground(drawable);
    }

    public void animateValue(int startValue, int endValue) {
        ValueAnimator animator = ValueAnimator.ofInt(startValue, endValue);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            valueView.setText(String.valueOf(animation.getAnimatedValue()));
        });
        animator.start();
    }
}
