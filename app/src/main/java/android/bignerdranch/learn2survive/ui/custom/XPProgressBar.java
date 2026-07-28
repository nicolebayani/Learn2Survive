package android.bignerdranch.learn2survive.ui.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import android.bignerdranch.learn2survive.R;

public class XPProgressBar extends View {
    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint textPaint;
    private RectF rectF;
    private float progress = 0f;
    private float animatedProgress = 0f;
    private int maxProgress = 100;
    private int backgroundColor;
    private int progressColor;
    private int textColor;
    private float cornerRadius = 16f;
    private float strokeWidth = 20f;
    private boolean showText = true;
    private ValueAnimator progressAnimator;

    public XPProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public XPProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public XPProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();
        
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.XPProgressBar,
                    0, 0
            );
            
            try {
                backgroundColor = a.getColor(R.styleable.XPProgressBar_backgroundColor,
                        ContextCompat.getColor(context, R.color.text_hint));
                progressColor = a.getColor(R.styleable.XPProgressBar_progressColor,
                        ContextCompat.getColor(context, R.color.primary));
                textColor = a.getColor(R.styleable.XPProgressBar_textColor,
                        ContextCompat.getColor(context, R.color.text_primary));
                cornerRadius = a.getDimension(R.styleable.XPProgressBar_cornerRadius, 16f);
                strokeWidth = a.getDimension(R.styleable.XPProgressBar_strokeWidth, 20f);
                showText = a.getBoolean(R.styleable.XPProgressBar_showText, true);
                maxProgress = a.getInteger(R.styleable.XPProgressBar_maxProgress, 100);
            } finally {
                a.recycle();
            }
        } else {
            backgroundColor = ContextCompat.getColor(context, R.color.text_hint);
            progressColor = ContextCompat.getColor(context, R.color.primary);
            textColor = ContextCompat.getColor(context, R.color.text_primary);
        }
        
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStrokeWidth(strokeWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(strokeWidth);
        textPaint.setColor(textColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        float width = getWidth();
        float height = getHeight();
        float padding = strokeWidth / 2;
        
        rectF.set(padding, padding, width - padding, height - padding);
        
        // Draw background
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, backgroundPaint);
        
        // Draw progress
        float sweepAngle = (animatedProgress / maxProgress) * 360f;
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint);
        
        // Draw text
        if (showText) {
            textPaint.setTextSize(strokeWidth * 0.8f);
            float centerY = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2;
            String text = (int) animatedProgress + "/" + maxProgress;
            canvas.drawText(text, width / 2, centerY, textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 300;
        int desiredHeight = (int) (strokeWidth * 3);
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        int width, height;
        
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }
        
        setMeasuredDimension(width, height);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        animateProgress();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    private void animateProgress() {
        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
        
        progressAnimator = ValueAnimator.ofFloat(animatedProgress, progress);
        progressAnimator.setDuration(1000);
        progressAnimator.addUpdateListener(animation -> {
            animatedProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        progressAnimator.start();
    }

    public void setProgressColor(int color) {
        this.progressColor = color;
        progressPaint.setColor(color);
        invalidate();
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        backgroundPaint.setColor(color);
        invalidate();
    }
}
