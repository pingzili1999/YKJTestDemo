package com.risenb.ykj.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risenb.ykj.R;
import com.risenb.ykj.utlis.CommonUtil;


/**
 * Created by Fengyi on 2016/7/13.
 */
public class BaseFragment extends Fragment {


    private boolean isTitleShow = false;

    /**
     * 显示标题
     */
    public BaseFragment showTitle(String text) {
        if(getView().findViewById(R.id.titleBar)==null) return this;
        getView().findViewById(R.id.titleBar).setVisibility(View.VISIBLE);
        TextView title = (TextView) getView().findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText(text);
        isTitleShow = true;
        return this;
    }

    public BaseFragment setBackground(int color){
        if(!isTitleShow)return  this;
        getView().findViewById(R.id.titleBar).setBackgroundColor(color);
        return this;
    }
    /**
     * 显示返回
     */
    public BaseFragment withBack() {
        if (!isTitleShow) return this;
        RelativeLayout back = (RelativeLayout) getView().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return this;
    }

    /**
     * 显示右边文字
     */
    public BaseFragment withRightText(String text, View.OnClickListener onClickListener) {
        if (!isTitleShow) return this;
        TextView rightText = (TextView) getView().findViewById(R.id.rightText);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 显示右边按钮1
     */
    public BaseFragment withRightBtn1(int image, View.OnClickListener onClickListener) {
        if (!isTitleShow) return this;
        ImageView rightBtn1 = (ImageView) getView().findViewById(R.id.rightIcon1);
        rightBtn1.setVisibility(View.VISIBLE);
        rightBtn1.setImageResource(image);
        rightBtn1.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 显示右边按钮2
     */
    public BaseFragment withRightBtn2(int image, View.OnClickListener onClickListener) {
        if (!isTitleShow) return this;
        ImageView rightBtn2 = (ImageView) getView().findViewById(R.id.rightIcon2);
        rightBtn2.setVisibility(View.VISIBLE);
        rightBtn2.setImageResource(image);
        rightBtn2.setOnClickListener(onClickListener);
        return this;
    }


    protected void makeText(final String content) {
        CommonUtil.showToast(content);
    }
}
