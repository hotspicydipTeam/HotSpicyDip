package com.scnu.hotspicydip.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.scnu.hotspicydip.R;


public class RoundProgressBar extends ProgressBar {

    private Paint mPaint;
    private int roundColor;
    private int roundProgressColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private int index = 0;
    private boolean isDisplay;
    private int height;
    private boolean isShowIndex;

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

        // 获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, 0XFFD5D5D5);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, 0XFF0FA8AE);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 3);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, 0XFF0FA8AE);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 12);
        isDisplay = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, false);
        isShowIndex = mTypedArray.getBoolean(R.styleable.RoundProgressBar_indexDisplayable, true);

        mTypedArray.recycle();

    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Cap.ROUND);

        // 画圆
        int center = getWidth() / 2;
        int radius = (int) (center - roundWidth / 2);
        mPaint.setColor(roundColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(roundWidth);
        canvas.drawCircle(center, center, radius, mPaint);

        // 画进度条
        mPaint.setStrokeWidth(roundWidth);
        mPaint.setColor(roundProgressColor);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        mPaint.setStyle(Paint.Style.STROKE);
        if (index != 0) {
            canvas.drawArc(oval, -90, index * 1.0f / 6000 * 360, false, mPaint); // 根据进度画圆弧
        }

        // 画Index
        mPaint.setStrokeWidth(0);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        if (index >= 0) {
            if (isDisplay) {
                float base = (getWidth() - (mPaint.descent() + mPaint.ascent())) / 2.0f;
                int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20,
                        getResources().getDisplayMetrics());
                canvas.drawText(index + "", (getWidth() - mPaint.measureText(index + "")) / 2.0f,
                        (getWidth() - (mPaint.descent() + mPaint.ascent())) / 2.0f - marginTop, mPaint);
                mPaint.setTextSize(
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
                mPaint.setColor(0XFF666666);
                int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 40,
                        getResources().getDisplayMetrics());
                canvas.drawText("钓鱼指数", (getWidth() - mPaint.measureText("钓鱼指数")) / 2.0f, base + margin, mPaint);
                return;
            }
            if (isShowIndex) {
                canvas.drawText(index / 100 + "", (getWidth() - mPaint.measureText(index / 100 + "")) / 2.0f,
                        (getWidth() - (mPaint.descent() + mPaint.ascent())) / 2.0f, mPaint);
            }
        }

    }

    public synchronized int getIndex() {
        return index;
    }

    public synchronized void setIndex(int index) {
        this.index = index;
        postInvalidate();
    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

}
