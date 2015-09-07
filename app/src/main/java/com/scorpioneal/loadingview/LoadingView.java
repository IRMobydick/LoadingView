package com.scorpioneal.loadingview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by ScorpioNeal on 15/8/19.
 */
public class LoadingView extends View {

    private static final String TAG = LoadingView.class.getSimpleName();

    private float mViewWidth;
    private float mViewHeight;
    private float mViewLength; //正方形边长

    private float mCenterX, mCenterY;

    private float mRadius;

    private static final float PADDING = 10;

    private Paint mLoadPaint;

    private float mEndAngle = 150;
    private float mSweapAngle = 120;
    private float mMaxSweapAngle = 120;

    private float mStrokeWidth = 3;
    private int mPaintColor = Color.WHITE;

    private Path mPath;

    private static final int START_ANIM_TIME = 2000;
    private static final int END_ANIM_TIME = 1000;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLoadPaint = new Paint();
        mLoadPaint.setAntiAlias(true);
        mLoadPaint.setStrokeJoin(Paint.Join.ROUND);
        mLoadPaint.setStrokeWidth(mStrokeWidth);
        mLoadPaint.setColor(mPaintColor);
        mLoadPaint.setStyle(Paint.Style.STROKE);


        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "mPaint.getColor " + mLoadPaint.getColor());
        mPath.reset();
        RectF oval = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        mPath.addArc(oval, mEndAngle - mSweapAngle, mSweapAngle);
        canvas.drawPath(mPath, mLoadPaint);
        mPath.addArc(oval, mEndAngle - mSweapAngle + 180, mSweapAngle);
        canvas.drawPath(mPath, mLoadPaint);
    }

    private AnimatorSet sets = new AnimatorSet();

    public void start() {

        ValueAnimator animator = new ValueAnimator().ofFloat(0f, 180f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mSweapAngle = (mMaxSweapAngle - 5) * animFunc(value);

                postInvalidate();
            }
        });
        animator.setDuration(START_ANIM_TIME);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());


        ValueAnimator endAngleAnimator = new ValueAnimator().ofFloat(150f, 930f);
        endAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEndAngle = (float) animation.getAnimatedValue();
                Log.d(TAG, "mEndAngle " + mEndAngle);
                postInvalidate();
            }
        });
        endAngleAnimator.setDuration(START_ANIM_TIME);
        endAngleAnimator.setRepeatMode(ValueAnimator.RESTART);
        endAngleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        endAngleAnimator.setInterpolator(new LinearInterpolator());

        sets.playTogether(animator, endAngleAnimator);
        sets.start();

    }

    public void end() {
        sets.end();
    }


    public void demoStartLoading() {

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0.0f, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0.0f, 1);
        scaleXAnimator.setDuration(START_ANIM_TIME);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(START_ANIM_TIME);
        scaleYAnimator.setInterpolator(new LinearInterpolator());

        ValueAnimator animator = new ValueAnimator().ofFloat(0f, 180f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mSweapAngle = (mMaxSweapAngle - 5) * animFunc(value);

                postInvalidate();
            }
        });
        animator.setDuration(START_ANIM_TIME);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());


        ValueAnimator endAngleAnimator = new ValueAnimator().ofFloat(150f, 930f);
        endAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEndAngle = (float) animation.getAnimatedValue();
                Log.d(TAG, "mEndAngle " + mEndAngle);
                postInvalidate();
            }
        });
        endAngleAnimator.setDuration(START_ANIM_TIME);
        endAngleAnimator.setRepeatMode(ValueAnimator.RESTART);
        endAngleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        endAngleAnimator.setInterpolator(new LinearInterpolator());


        sets.playTogether(scaleXAnimator, scaleYAnimator, animator, endAngleAnimator);
        sets.playTogether(animator, endAngleAnimator);
        sets.start();

    }

    public void demoEndLoading() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0);
        scaleXAnimator.setDuration(END_ANIM_TIME);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(END_ANIM_TIME);
        scaleYAnimator.setInterpolator(new LinearInterpolator());
        AnimatorSet sets = new AnimatorSet();
        sets.playTogether(scaleXAnimator, scaleYAnimator);
        sets.start();
    }

    /**
     * 转动变化的规则通过函数来控制
     */
    private float animFunc(float value) {
        return (float) Math.cos(value / 180 * Math.PI);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        mViewLength = Math.min(mViewHeight, mViewWidth);
        mCenterX = mViewWidth / 2;
        mCenterY = mViewHeight / 2;
        mRadius = (mViewLength - 2 * PADDING) / 2;
    }

    /**
     * 放大缩小
     *
     * @param x float 0－1
     */
    public void scaleView(float x) {
        float f = scaleFunction(x);
        setScaleX(f);
        setScaleY(f);
//        postInvalidate();
    }

    /**
     * 放大缩小的变化函数，可以自己更改
     *
     * @param x
     * @return
     */
    private float scaleFunction(float x) {
        if (x >= 0 && x < 0.4f) {
            return 2.5f * x;
        } else if (x >= 0.4f && x <= 1) {
            return 1;
        }
        return 1;
    }

    /**
     * 旋转
     *
     * @param x float 0 - 1
     */
    public void roundView(float x) {
        mSweapAngle = (mMaxSweapAngle - 5) * (float) Math.cos(x * Math.PI);
        mEndAngle = 150 + (930 - 150) * x;
        postInvalidate();
    }

    /**
     * 暂定是线性的
     *
     * @param x
     * @return
     */
    private float roundFunction(float x) {
        return x;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        mLoadPaint.setStrokeWidth(mStrokeWidth);
        invalidate();
    }

    public int getPaintColor() {
        return mPaintColor;
    }

    public void setPaintColor(int mPaintColor) {
        this.mPaintColor = mPaintColor;
        mLoadPaint.setColor(mPaintColor);
        invalidate();
    }
}
