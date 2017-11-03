package com.fengniao.news.widget;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

//贝塞尔曲线
public class BesselView extends View {

    final float BASE = 200.0f;

    private int screenW, screenH;
    private int width, height;

    private Paint mPaint, pointPaint, linePaint;

    private PointF startP, endP, controlP;

    private android.graphics.Path path;

    private ValueAnimator mValueAnimator;


    public BesselView(Context context) {
        this(context, null);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        screenH = dm.heightPixels;
        screenW = dm.widthPixels;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8.0f);
        //设置画笔模式，有填充、描边、填充加描边三种模式
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(Color.CYAN);
        pointPaint.setStrokeWidth(20.0f);
        pointPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(2.0f);
        linePaint.setAntiAlias(true);

        startP = new PointF(BASE, 0);
        endP = new PointF(BASE, 0);
        controlP = new PointF(0, -BASE);
        path = new android.graphics.Path();

        //通过属性动画变动贝塞尔曲线辅助点
        mValueAnimator = ValueAnimator.ofFloat(-BASE, BASE);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setDuration(3000);
        mValueAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            controlP.x = value;
            controlP.y = value;
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = Math.min(screenH, screenW);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = Math.min(screenH, screenW);
        }
        setMeasuredDimension(widthSize, heightSize);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        startP.x = left + BASE;
        startP.y = bottom / 2;
        endP.x = right - BASE;
        endP.y = bottom / 2;
        controlP.x = right / 2;
        controlP.y = bottom / 2 - BASE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();

//        canvas.translate(width / 2, height / 2);
        canvas.drawColor(Color.BLACK);
        canvas.drawPoint(startP.x, startP.y, pointPaint);
        canvas.drawPoint(endP.x, endP.y, pointPaint);

        canvas.drawLine(startP.x, startP.y, controlP.x, controlP.y, linePaint);
        canvas.drawLine(endP.x, endP.y, controlP.x, controlP.y, linePaint);

        path.moveTo(startP.x, startP.y);
        //二阶贝塞尔曲线
        path.quadTo(controlP.x, controlP.y, endP.x, endP.y);
        canvas.drawPath(path, mPaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlP.x = event.getX();
        controlP.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.cancel();
    }
}
