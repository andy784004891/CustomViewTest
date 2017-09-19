package com.andy.circleview1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 唐卓 on 2017/8/1
 * 圆环
 */

public class CircleView extends View {
    private Paint mPaintCurrent;//圆弧画笔
    private float mPaintWidth;//画笔宽度
    private int mPaintColor = Color.RED;//画笔颜色
    private int location;//从哪个位置开始
    private float startAngle;//开始角度

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        mPaintCurrent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWidth = array.getDimension(R.styleable.CircleProgressView_progress_paint_width, 8);
        mPaintColor = array.getColor(R.styleable.CircleProgressView_progress_paint_color, mPaintColor);
        location = array.getInt(R.styleable.CircleProgressView_progress_location, 2);
        array.recycle(); //一定记得释放

//        location = array.getInt(R.styleable.CircleProgressView_progress_location, 2);
//        mPaintWidth = array.getDimension(R.styleable.CircleProgressView_progress_paint_width, dip2px(context, 4));//默认4dp
//        mPaintColor = array.getColor(R.styleable.CircleProgressView_progress_paint_color, mPaintColor);
//        array.recycle();
        init();
    }

    private void init() {
        mPaintCurrent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCurrent.setColor(mPaintColor);  //圆环
        mPaintCurrent.setStyle(Paint.Style.STROKE);
        mPaintCurrent.setStrokeWidth(mPaintWidth);
//        canvas.drawCircle(200, 200, 100, mPaint);
        mPaintCurrent.setStrokeCap(Paint.Cap.ROUND);  //圆形的线帽
        if (location == 1) {
            startAngle = -180;
        } else if (location == 2) {//默认从上侧开始
            startAngle = -90;
        } else if (location == 3) {
            startAngle = 0;
        } else if (location == 4) {
            startAngle = 90;
        }

        txtPaint.setTextSize(50);
        txtPaint.setColor(Color.GREEN);
    }
    private Paint txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;  //取最小值
        setMeasuredDimension(size, size);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(mPaintWidth / 2, mPaintWidth / 2, getWidth() - mPaintWidth / 2, getHeight() - mPaintWidth / 2);
        float sweepAngle = 360 * mCurrent / 100;
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mPaintCurrent);
        canvas.drawText(mCurrent+"", getWidth()/2, getHeight()/2, txtPaint);
    }

    private int mCurrent;
    private Handler mHandler = new Handler();
    private int time = 100;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mHandler.postDelayed(this, time);
                mCurrent++;
                if (mCurrent == 100) {
                    mCurrent = -100;
                }
                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    public void startDraw(){
        mHandler.postDelayed(mRunnable, time);
    }
}
