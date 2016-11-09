package com.risenb.ykj.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risenb.ykj.R;
import com.risenb.ykj.utlis.CommonUtil;


/**
 * Created by Fengyi on 2016/7/13.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    public void setNoTitleBar() {
        if(getSupportActionBar()!=null)
        getSupportActionBar().hide();
    }

    private boolean isTitleShow = false;

    /**
     * 显示标题
     */
    public BaseAppCompatActivity showTitle(String text) {
        if(findViewById(R.id.titleBar)==null) return this;
        findViewById(R.id.titleBar).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText(text);
        isTitleShow = true;
        return this;
    }

    /**
     * 显示返回
     */
    public BaseAppCompatActivity withBack() {
        if (!isTitleShow) return this;
        RelativeLayout back = (RelativeLayout) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        return this;
    }

    /**
     * 显示右边文字
     */
    public BaseAppCompatActivity withRightText(String text, View.OnClickListener onClickListener) {
        if (!isTitleShow) return this;
        TextView rightText = (TextView) findViewById(R.id.rightText);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 显示右边按钮1
     */
    public BaseAppCompatActivity withRightBtn1(int image, View.OnClickListener onClickListener) {
        if (!isTitleShow) return this;
        ImageView rightBtn1 = (ImageView) findViewById(R.id.rightIcon1);
        rightBtn1.setVisibility(View.VISIBLE);
        rightBtn1.setImageResource(image);
        rightBtn1.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 显示右边按钮2
     */
    public BaseAppCompatActivity withRightBtn2(int image, View.OnClickListener onClickListener) {
        if (!isTitleShow) return this;
        ImageView rightBtn2 = (ImageView) findViewById(R.id.rightIcon2);
        rightBtn2.setVisibility(View.VISIBLE);
        rightBtn2.setImageResource(image);
        rightBtn2.setOnClickListener(onClickListener);
        return this;
    }


    protected void makeText(final String content) {
        CommonUtil.showToast(content);
    }
}
