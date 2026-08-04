package android.bignerdranch.learn2survive.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.bignerdranch.learn2survive.R;

public class ExpandableCardView extends FrameLayout {
    private LinearLayout headerLayout;
    private LinearLayout contentLayout;
    private ImageView expandIcon;
    private TextView titleTextView;
    private boolean isExpanded = false;
    private OnExpandListener expandListener;

    public interface OnExpandListener {
        void onExpand(boolean expanded);
    }

    public ExpandableCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ExpandableCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpandableCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_expandable_card, this);
        
        headerLayout = findViewById(R.id.headerLayout);
        contentLayout = findViewById(R.id.contentLayout);
        expandIcon = findViewById(R.id.expandIcon);
        titleTextView = findViewById(R.id.titleTextView);

        headerLayout.setOnClickListener(v -> toggleExpand());
        
        contentLayout.setVisibility(GONE);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setContent(View contentView) {
        contentLayout.removeAllViews();
        contentLayout.addView(contentView);
    }

    public void toggleExpand() {
        isExpanded = !isExpanded;
        
        if (isExpanded) {
            contentLayout.setVisibility(VISIBLE);
            expandIcon.setRotation(180f);
        } else {
            contentLayout.setVisibility(GONE);
            expandIcon.setRotation(0f);
        }

        expandIcon.animate()
                .rotation(isExpanded ? 180f : 0f)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        if (expandListener != null) {
            expandListener.onExpand(isExpanded);
        }
    }

    public void setExpanded(boolean expanded) {
        if (isExpanded != expanded) {
            toggleExpand();
        }
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setOnExpandListener(OnExpandListener listener) {
        this.expandListener = listener;
    }
}
