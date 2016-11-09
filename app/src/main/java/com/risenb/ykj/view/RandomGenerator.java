package com.risenb.ykj.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.risenb.ykj.R;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/4.
 */

public class RandomGenerator {
    private final Random RANDOM = new Random();
    private Bitmap[] mBitmaps=new Bitmap[9];
    private Context mContext;
    public RandomGenerator(Context context){
        this.mContext=context;
        initBitmapArr();
    }
    // 区间随机
    public float getRandom(float lower, float upper) {
        float min = Math.min(lower, upper);
        float max = Math.max(lower, upper);
        return getRandom(max - min) + min;
    }
    //随机雨滴图片大小
    public Bitmap getBitmap(int number){
//        int number= new Random().nextInt(3);
        return mBitmaps[number];
    }
    // 上界随机
    public float getRandom(float upper) {
        return RANDOM.nextFloat() * upper;
    }

    // 上界随机
    public int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }
    private void initBitmapArr(){

//        mBitmaps[2] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rain2);
   //     mBitmaps[2] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rain3);
        mBitmaps[3] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.leaf);
//        mBitmaps[2] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.rain5);
////        mBitmaps[7] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rainbig);
////        mBitmaps[0] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rainmoves);
//        mBitmaps[1] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rainsmall);
//      //  mBitmaps[0] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rainmed);
//        mBitmaps[0]= BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rain1);


    }
    /**
     * 获得一个给定范围的随机整数
     * 可以负数到正数
     *
     * @param smallistNum
     * @param BiggestNum
     * @return
     */
    public int getRandomNum(int smallistNum, int BiggestNum) {
        Random random = new Random();
        return (Math.abs(random.nextInt()) % (BiggestNum - smallistNum + 1)) + smallistNum;
    }

    //随机产生划线的起始点坐标和结束点坐标
    public int[] getLine(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        int temp = getRandomWidth(width);
        for (int i = 0; i < 4; i += 4) {
            tempCheckNum[i] = temp;
            tempCheckNum[i + 1] = (int) (Math.random() * height / 8);
            tempCheckNum[i + 2] = temp;
            tempCheckNum[i + 3] = (int) (Math.random() * height / 4);
        }
        return tempCheckNum;
    }

    public int getRandomWidth(int width) {
        return (int) (Math.random() * width);
    }
}