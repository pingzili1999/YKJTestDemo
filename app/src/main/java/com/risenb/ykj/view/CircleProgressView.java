package com.risenb.ykj.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2016/11/7.
 */

public class CircleProgressView extends View {
    private static final String TAG = "CircleProgressBar";

    private int mMaxProgress = 300;
    //实际百分比进度
    private int mProgress = 30;
    //动画位置百分比进度
    private int mCurPercent;
    private final int mCircleLineStrokeWidth = 5;

    private final int mTxtStrokeWidth = 3;

    // 画圆所在的距形区域
    private RectF mRectF;
    private Paint mPaint;
    private Paint unitPaint;
    private Paint linePaint;
    private int textColor= Color.WHITE;
    private int lineColor= Color.argb(255,178,34,34);
    private Context mContext;
    private String mTxtTitle="PM2.5";
    private String mTxtUnit="μg/m³";
    private int unitColor = Color.WHITE;
    private String mTxtAirQuality="严重污染";


    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
        unitPaint = new Paint();
        linePaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.getWidth()-10;
        int height = this.getHeight()-10;

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.rgb(0xe9, 0xe9, 0xe9));
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        linePaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.STROKE);
      //  mPaint.setFakeBoldText(true);
        // 位置
        mRectF.left = mCircleLineStrokeWidth / 2+10; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2+10; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth / 2-10; // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth / 2-10; // 右下角y

        // 绘制进度条背景
        canvas.drawArc(mRectF, 90, 360, false, linePaint);
       // mPaint.setColor(Color.GREEN);
      //  canvas.drawArc(mRectF, 90, ((float) mCurPercent / mMaxProgress) * 360, false, mPaint);

        //绘制圆环
        linePaint.setStrokeWidth(8f);
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        int percent;
        if(mCurPercent>=mMaxProgress)
            percent=mMaxProgress-(100-mProgress/10);
        else
            percent=mCurPercent;
        path.addArc(mRectF, 90, ((float) percent / mMaxProgress) * 360);
        canvas.drawPath(path,linePaint);
        //绘制小圆
        PathMeasure measure = new PathMeasure(path, false);
        float[] coords = new float[] {0f, 0f};
        measure.getPosTan(measure.getLength(), coords, null);
//        Log.e(TAG,"(x,y)=("+coords[0]+","+coords[1]+")");
        linePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(coords[0],coords[1],10,linePaint);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2f);
        canvas.drawCircle(coords[0],coords[1],15.5f,linePaint);
        // 绘制进度文案显示
        mPaint.setStrokeWidth(mTxtStrokeWidth);
        mPaint.setColor(textColor);
        String text= mCurPercent+"";
        int textHeight = height /3;

        mPaint.setTextSize(textHeight);
        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 3, mPaint);

        if (!TextUtils.isEmpty(mTxtTitle)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtTitle;
            textHeight = height / 14;
            mPaint.setTextSize(textHeight);

            int textWidthTitle = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidthTitle / 2, height / 4 + textHeight /2, mPaint);
            textHeight = height / 16;
            unitPaint.setColor(unitColor);
            unitPaint.setTextSize(textHeight);
            unitPaint.setStrokeWidth(2);
            unitPaint.setAntiAlias(true);
            unitPaint.setAlpha(100);
            canvas.drawText(mTxtUnit, width / 2 + textWidth/2 , height/2 + height/10, unitPaint);
        }

        if (!TextUtils.isEmpty(mTxtAirQuality)) {
            //TODO 通过判断来确定显示的图标颜色及显示的文字描述：如空气优，空气污染
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtAirQuality;
            textHeight = height / 14;
            mPaint.setTextSize(textHeight);
            int textWidthUnit = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);

            RandomGenerator random = new RandomGenerator(mContext);
            Bitmap bitmap = random.getBitmap(3);
            canvas.drawText(text, width / 2 - textWidthUnit / 2 + bitmap.getWidth()/2, 3 * height / 4 + textHeight / 2, mPaint);

            canvas.drawBitmap(bitmap,(float)width / 2 - textWidthUnit/2- bitmap.getWidth()/2 ,(float) 3 * height / 4 - height/20 ,mPaint);
        }
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }
    public void setTextColor(int textColor){
        this.textColor=textColor;
    }
    public void setUnitColor(int unitColor){
        this.unitColor=unitColor;
    }
    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }
    public void setTextUnit(String textUnit){
        this.mTxtUnit=textUnit;
    }

    public void setProgress(int progress) {
//        this.mProgress = progress;
        if(progress>=300) {

        }else if(progress>=201){
            mTxtAirQuality="重度污染";
            lineColor= Color.argb(255,160,32,240);
        }else if(progress>=151){
            mTxtAirQuality="中度污染";
            lineColor= Color.RED;
        }else if(progress>=101){
            mTxtAirQuality="轻度污染";
            lineColor= Color.argb(255,255,97,0);
        }else if(progress>51){
            mTxtAirQuality="空气良";
            lineColor= Color.YELLOW;
        }else{
            mTxtAirQuality="空气优";
            lineColor= Color.GREEN;
        }
        setCurPercent(progress);
    }


    //内部设置百分比 用于动画效果
    private void setCurPercent(int percent) {

        mProgress = percent;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =1;i<=mProgress;i++){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //0 255 0 #00FF00 =Color.GREEN;
                    //255 0 0 #FF0000 =Color.RED;
                    if(i>mMaxProgress-(100-mProgress/10)){
                        mCurPercent = mProgress;
                    }else{
                        mCurPercent = i;
                    }
                    CircleProgressView.this.postInvalidate();
                }
            }

        }).start();

    }
}
