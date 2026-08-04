package android.bignerdranch.learn2survive.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.bignerdranch.learn2survive.R;

public class LessonProgressView extends View {
    private Paint backgroundPaint;
    private Paint progressPaint;
    private float progress = 0f;
    private int strokeWidth = 8;

    public LessonProgressView(Context context) {
        super(context);
        init();
    }

    public LessonProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LessonProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.gray_200));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setProgress(float progress) {
        this.progress = Math.max(0f, Math.min(1f, progress));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2f - strokeWidth;
        float centerX = width / 2f;
        float centerY = height / 2f;

        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);

        float sweepAngle = 360f * progress;
        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                -90f, sweepAngle, false, progressPaint);
    }
}
