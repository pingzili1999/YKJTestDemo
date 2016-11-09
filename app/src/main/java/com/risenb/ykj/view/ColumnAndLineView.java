package com.risenb.ykj.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.risenb.ykj.R;

/**
 * bottle_li
 * Created by Administrator on 2016/10/10.
 * 通过RecyclerView展示线性天气的 item用的view (一条线和圆点，底部柱状图)
 */


public class ColumnAndLineView extends View {

    /**
     * 默认最小宽度50dp
     */
    private static final int defaultMinWidth = 100;
    /**
     * 默认最小高度80dp
     */
    private static final int defaultMinHeight = 80;
    /**
     * 字体最小默认16dp
     */
    private int columnTextSize = 13;
    private int leftTextSize = 16;
    /**
     * 文字颜色
     */
    private int textColor = Color.BLACK;

    private String unit = "";
    /**
     * A线的颜色,B线的颜色
     */
    private int lineColor = Color.YELLOW;
    private int lineColorB = Color.GREEN;
    private int dotColor=Color.WHITE;
    /**
     * 线的宽度
     */
    private int lineWidth = 1;
    /**
     * 圆点的宽度
     */
    private int dotRadius = 5;
    /**
     * 画文字的画笔
     */
    private TextPaint textPaint;
    private TextPaint columnTextPaint;
    /**
     * 文字的FontMetrics
     */
    private Paint.FontMetrics textFontMetrics;
    /**
     * 画点的画笔
     */
    private Paint dotPaint;
    /**
     * 文字和点的间距
     */
    private int mTextDotDistance = 5;
    private int columnMinDataDistance=20;//矩形和最小数据之间的间隙
    /**
     * 画线的画笔
     */
    private Paint mLinePaintA;
    private Paint mLinePaintB;
    /**
     * 所有数据中最小数据
     */
    private int minData;
    /**
     * 所有数据中最大的数据
     */
    private int maxData;
    private int maxColumnData;
    /**
     * 分别代表最左边的，中间的，右边的三个数值
     */
    private int lineData[];
    /**
     * 代表柱图的数值
     */
    private int columnData[];
    private String columnText;//柱子下面文字
    //三段线的文字
    private String text0="",text1="",text2="";
    public ColumnAndLineView(Context context) {
        this(context,null);
    }
    public ColumnAndLineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public ColumnAndLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 获取自定义属性并赋初始值
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColumnAndLineView,
                defStyleAttr, 0);
        columnTextSize = (int) a.getDimension(R.styleable.ColumnAndLineView_columnTextSize,
                dp2px(context, columnTextSize));
        textColor = a.getColor(R.styleable.ColumnAndLineView_columnTextColor, Color.parseColor("#333333"));
        lineColor = a.getColor(R.styleable.ColumnAndLineView_lineColor,Color.parseColor("#fee55b"));
        mTextDotDistance = a.getColor(R.styleable.ColumnAndLineView_cTextDotDistance,mTextDotDistance);
        leftTextSize = (int)a.getDimension(R.styleable.ColumnAndLineView_leftTextSize,dp2px(context, leftTextSize));
        dotColor = a.getColor(R.styleable.ColumnAndLineView_dotColor,Color.parseColor("#666666"));
        lineWidth = (int) a.getDimension(R.styleable.ColumnAndLineView_lineWidth,
                dp2px(context, lineWidth));
       dotRadius = (int) a.getDimension(R.styleable.ColumnAndLineView_dotRadius,
                dp2px(context, dotRadius));
        unit = a.getString(R.styleable.ColumnAndLineView_unitText);
        a.recycle();
    }

    /**
     * 设置画笔
     */
    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(leftTextSize);
        textPaint.setColor(textColor);
        textFontMetrics = textPaint.getFontMetrics();
        columnTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        columnTextPaint.setTextSize(columnTextSize);
        columnTextPaint.setColor(textColor);
        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setColor(dotColor);
        mLinePaintA = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaintB = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaintA.setStyle(Paint.Style.STROKE);
        mLinePaintB.setStyle(Paint.Style.STROKE);
        mLinePaintA.setStrokeWidth(lineWidth);
        mLinePaintB.setStrokeWidth(lineWidth);
        mLinePaintA.setColor(lineColor);
        mLinePaintB.setColor(lineColorB);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = getSize(widthMode, widthSize, 0);
        int height = getSize(heightMode, heightSize, 1);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//canvas.save();

        // 文本的高度
        int textHeight = (int) (textFontMetrics.bottom - textFontMetrics.top);
        // 一个基本的高度，由于最下面的时候，有文字和圆点和文字的宽度需要留空间
        int baseHeight = getHeight() - textHeight - mTextDotDistance -columnMinDataDistance+5;

        if (lineData == null) {
            //线最左侧的文字
            canvas.drawText(text0,0,baseHeight-cacHeight(maxData)-mTextDotDistance,textPaint);
            canvas.drawText(text1,0,baseHeight-cacHeight(minData)-mTextDotDistance,textPaint);
            canvas.drawText(text2,0,baseHeight+columnMinDataDistance-mTextDotDistance,textPaint);
            //第一条线
            canvas.drawLine(0,baseHeight-cacHeight(maxData), getWidth(),baseHeight-cacHeight(maxData),mLinePaintB);
            //第二条线
            canvas.drawLine(0,baseHeight-cacHeight(minData), getWidth(),baseHeight-cacHeight(minData),mLinePaintB);
            //最下面的线
            canvas.drawLine(0,baseHeight+columnMinDataDistance, getWidth(),baseHeight+columnMinDataDistance,mLinePaintB);
            return;
        }
        canvas.drawColor(Color.TRANSPARENT);

        // 折线相关
        int calMiddle = baseHeight - cacHeight(lineData[1]);
        canvas.drawCircle(getWidth() / 2, calMiddle, dotRadius, dotPaint);
        // 画数值文字

//        String text = String.valueOf(lineData[1]) + unit ;
//        int baseX = (int) (canvas.getWidth() / 2 - textPaint.measureText(text) / 2);
//        // mTextFontMetrics.top为负的
//        // 需要加上文字高度和文字与圆点之间的空隙
//        int baseY = (int) (calMiddle - textFontMetrics.top) + mTextDotDistance;
//        canvas.drawText(text, baseX, baseY, textPaint);

//        Path p = new Path();
//        p.moveTo(0, calLeft);
//        p.quadTo(getWidth() / 2, calMiddle,);
//        canvas.drawPath(p,mLinePaintB);

        //第一条线
        canvas.drawLine(0,baseHeight-cacHeight(maxData), getWidth(),baseHeight-cacHeight(maxData),mLinePaintB);
        //第二条线
        canvas.drawLine(0,baseHeight-cacHeight(minData), getWidth(),baseHeight-cacHeight(minData),mLinePaintB);
       //最下面的线
        canvas.drawLine(0,baseHeight+columnMinDataDistance, getWidth(),baseHeight+columnMinDataDistance,mLinePaintB);

        if (lineData[3] == 0) {
            // 数据左侧
            int calLeft = baseHeight - cacHeight(lineData[0]);
            canvas.drawLine(0, calLeft, getWidth() / 2, calMiddle, mLinePaintA);
        }

        if (lineData[4] == 0) {
            // 数据右侧
            int calRight = baseHeight - cacHeight(lineData[2]);
            canvas.drawLine(getWidth() / 2, calMiddle, getWidth(), calRight, mLinePaintA);
        }


        //柱 下文字
        int baseX2 = (int) (canvas.getWidth() / 2 - columnTextPaint.measureText(columnText) / 2);//画板的一半减去文字总长度的一般
        int baseY2 = (baseHeight+columnMinDataDistance+textHeight);
        canvas.drawText(columnText, baseX2, baseY2, columnTextPaint);


            mLinePaintB.setStyle(Paint.Style.FILL);
          //  canvas.translate(0,baseHeight+columnMinDataDistance);
            int j = baseHeight+columnMinDataDistance;
            canvas.drawRect(2,j-getColumnHeight(baseHeight-cacHeight(minData),j,columnData[0]),getWidth()-2,j,mLinePaintB);
         //canvas.restore();

    }


    /**
     * 设置最大最小数据
     *
     * @param minData
     * @param maxData
     */
    public void setMinAndMaxData(int minData, int maxData,int maxColumnData) {
        this.minData = minData;
        this.maxData = maxData;
        this.maxColumnData =maxColumnData;
        invalidate();
    }
    /**
     *
     */
    public void setTextLineFront(String text0,String text1,String text2){

        if(text0!=null)
            this.text0=text0;
        if(text1!=null)
            this.text1=text1;
        if(text2!=null)
            this.text2 = text2;
        invalidate();
    }
    /**
     * 设置当前view中的三个数据点，中间的数据就是要显示的数据，
     * 第一个数据是与左侧相邻数据的平均数，第二个数据是与右侧相邻数据的平均数
     * @param lineData
     * @param columnData 柱图数据
     */
    public void setLineAndColumnData(int lineData[],int columnData[],String columnText) {
        this.lineData=lineData;
        this.columnData=columnData;
        this.columnText = columnText;
        invalidate();
    }

    /**
     * @param mode Mode
     * @param size Size
     * @param type 0表示宽度，1表示高度
     * @return 宽度或者高度
     */
    private int getSize(int mode, int size, int type) {
        // 默认
        int result;
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            if (type == 0) {
                // 最小不能低于最小的宽度
                result = dp2px(getContext(), defaultMinWidth) + getPaddingLeft() + getPaddingRight();
            } else {
                // 最小不能小于最小的宽度加上一些数据
                int textHeight = (int) (textFontMetrics.bottom - textFontMetrics.top);
                // 加上2个文字的高度
                result = dp2px(getContext(), defaultMinHeight) + textHeight +
                        // 需要加上两个文字和圆点的间距
                        getPaddingTop() + getPaddingBottom() + mTextDotDistance+columnMinDataDistance;
            }
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
    private int cacHeight(int tem) {
        // 最大，最小之差
        int temDistance = maxData - minData - maxColumnData;
        int textHeight = (int) (textFontMetrics.bottom - textFontMetrics.top);
        // view的最高和最低之差，需要减去文字高度和文字与圆点之间的空隙
        int viewDistance = getHeight() - 2 * textHeight - 2 * mTextDotDistance;
        // 今天的温度和最低温度之间的差别
        int currTemDistance = tem - minData - maxColumnData;
        return currTemDistance * viewDistance / temDistance;
    }
    private int getColumnHeight(int minDataHeight,int column0,int tem) {
        // 最大，最小之差
        int temDistance = column0-minDataHeight-columnMinDataDistance;
        return temDistance/maxColumnData*tem;
    }

}
