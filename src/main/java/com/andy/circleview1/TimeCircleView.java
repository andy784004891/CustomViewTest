package com.andy.circleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 唐卓 on 2017/9/19.
 * 一点资讯的splash进度条
 */

public class TimeCircleView extends View {

    public TimeCircleView(Context context) {
        this(context, null);
    }

    public TimeCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //可以通过set方法,也可以通过构造方法
    private int mTxtColor = Color.BLUE;
    private int mTxtSize = 50;
    /** 填写倒计时的秒数 s **/
    private int mTotalTime = 5;
    private float mStartAngle = 0f;
    private int mStrokeWidth = 10;
    private float mSweepAngle;
    private int mDrawCount;
    //默认0-360
    private boolean mSmall2Large = true;
    private long mDelayTime = 50;
    //默认顺时针
    private boolean mIsClockwise = true;
    //计算时间变化需要的值的值
    private int mValue = 20;
    private String mContent;


    private Paint mTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private void init() {
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆形的线帽
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(Color.BLUE);

        mTxtPaint.setColor(mTxtColor);
        mTxtPaint.setTextSize(mTxtSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mContent)){
            canvas.drawText(mTotalTime + "s", getWidth()/2 - mTxtSize/4, getHeight()/2 + mTxtSize/4, mTxtPaint);
        }else {
            mTxtPaint.setTextSize(30);
            canvas.drawText(mContent,getWidth()/2 - mTxtSize, getHeight()/2 + mTxtSize/4, mTxtPaint);
        }

        RectF rectF = new RectF(mStrokeWidth/2, mStrokeWidth/2, getWidth() - mStrokeWidth/2, getHeight() - mStrokeWidth/2);
        //分100次画完
        if (mSmall2Large){  // 0 --> 360
            mSweepAngle = 360f * mDrawCount / 100;
        }else { //360 -- 0
            mSweepAngle = 360f * mDrawCount/100 - 360f;
        }
        if (!mIsClockwise){
            mSweepAngle = -mSweepAngle;
        }
        canvas.drawArc(rectF, mStartAngle,  mSweepAngle, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;  //取最小值
        setMeasuredDimension(size, size);  //宽高一致为圆形
    }

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, mDelayTime);
            mDrawCount++;
            if (Math.abs(mDrawCount) > 100){
                return;
            }
            if (mDrawCount % mValue == 0){
                mTotalTime--;  //更新倒计时时间
            }
            invalidate();
        }
    };

    //开始执行画的动作
    public void startDraw() {
        mHandler.post(mRunnable);
    }

    public void setTotalTime(int time){
        if (time <= 0){
            return;
        }
        mTotalTime = time;
        mDelayTime = time * 10;  // =  time * 1000 / 100
        mValue = 100 / time;
    }

    public void setStartAngle(float angle){
        mStartAngle = angle;
    }

    /**
     * 决定从0到360 还是360到0,  都是顺时针
     * @param flag
     */
    public void setSmall2Large(boolean flag){
        mSmall2Large = flag;
    }

    /**
     * 决定顺时针画还是逆时针画
     * true 顺  false 逆
     */
    public void setClockwise(boolean flag){
        mIsClockwise = flag;
    }

    /**
     *设置圆环内部显示的内容
     */
    public void setCircleContent(String content){
        mContent = content;
    }

}
