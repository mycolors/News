package com.fengniao.news.module.test;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    public static final float RADIUS = 50F;

    private Point currentPoint;

    private Paint mPaint;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }


    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }


    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        animator.addUpdateListener(animation -> {
            currentPoint = (Point) animation.getAnimatedValue();
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(5000);
        animatorSet.play(animator).with(objectAnimator);
        animatorSet.start();
    }

}
