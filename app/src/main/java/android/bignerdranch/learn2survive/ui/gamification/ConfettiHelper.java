package android.bignerdranch.learn2survive.ui.gamification;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfettiHelper extends View {
    private List<ConfettiParticle> particles;
    private Paint paint;
    private Random random;
    private boolean isAnimating;

    public ConfettiHelper(Context context) {
        super(context);
        init();
    }

    public ConfettiHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConfettiHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        particles = new ArrayList<>();
        paint = new Paint();
        random = new Random();
        isAnimating = false;
    }

    public void startConfetti() {
        particles.clear();
        
        // Create confetti particles
        int particleCount = 100;
        for (int i = 0; i < particleCount; i++) {
            particles.add(new ConfettiParticle(
                getWidth() / 2f,
                getHeight() / 2f,
                randomColor(),
                randomSize(),
                randomVelocity(),
                randomAngle()
            ));
        }
        
        isAnimating = true;
        invalidate();
    }

    public void stopConfetti() {
        isAnimating = false;
        particles.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        if (!isAnimating || particles.isEmpty()) {
            return;
        }
        
        for (ConfettiParticle particle : particles) {
            // Update particle position
            particle.x += particle.vx;
            particle.y += particle.vy;
            particle.vy += 0.5f; // Gravity
            particle.rotation += particle.rotationSpeed;
            
            // Bounce off walls
            if (particle.x < 0 || particle.x > getWidth()) {
                particle.vx *= -0.8f;
            }
            if (particle.y > getHeight()) {
                particle.y = getHeight();
                particle.vy *= -0.6f;
            }
            
            // Draw particle
            canvas.save();
            canvas.translate(particle.x, particle.y);
            canvas.rotate(particle.rotation);
            
            paint.setColor(particle.color);
            canvas.drawRect(
                -particle.size / 2f,
                -particle.size / 2f,
                particle.size / 2f,
                particle.size / 2f,
                paint
            );
            
            canvas.restore();
        }
        
        // Remove particles that are out of bounds
        particles.removeIf(p -> p.y > getHeight() + 100);
        
        // Continue animation if particles remain
        if (!particles.isEmpty()) {
            postInvalidateDelayed(16); // ~60 FPS
        } else {
            isAnimating = false;
        }
    }

    private int randomColor() {
        int[] colors = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.MAGENTA, Color.CYAN, Color.parseColor("#FF9800"),
            Color.parseColor("#9C27B0"), Color.parseColor("#00BCD4")
        };
        return colors[random.nextInt(colors.length)];
    }

    private float randomSize() {
        return 10 + random.nextFloat() * 20;
    }

    private float randomVelocity() {
        return -10 + random.nextFloat() * 20;
    }

    private float randomAngle() {
        return random.nextFloat() * 360;
    }

    private static class ConfettiParticle {
        float x, y;
        float vx, vy;
        float size;
        int color;
        float rotation;
        float rotationSpeed;

        ConfettiParticle(float x, float y, int color, float size, float velocity, float angle) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.size = size;
            this.vx = (float) (velocity * Math.cos(Math.toRadians(angle)));
            this.vy = (float) (velocity * Math.sin(Math.toRadians(angle)));
            this.rotation = angle;
            this.rotationSpeed = -5 + (float) (Math.random() * 10);
        }
    }
}