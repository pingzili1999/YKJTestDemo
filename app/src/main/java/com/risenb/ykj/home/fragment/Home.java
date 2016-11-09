package com.risenb.ykj.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.risenb.ykj.HomeAdater;
import com.risenb.ykj.MyAdapter;
import com.risenb.ykj.R;
import com.risenb.ykj.WeatherDailyModel;
import com.risenb.ykj.base.BaseFragment;
import com.risenb.ykj.home.adpater.TestAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * bottle_li
 * 2016年10月18日09:16:02
 * 测试用首页
 */
public class Home extends BaseFragment {

    RecyclerView testRlv;
    private TestAdapter mTestAdapter;
    private int mDistanceY;
    private LinearLayout mLayout;
    LinearLayoutManager layoutManager;
    private int type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.fragment_home, null);
        testRlv = (RecyclerView)viewContent.findViewById(R.id.testRlv);
        mLayout = (LinearLayout) viewContent.findViewById(R.id.homeTitle);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(i<4){
                list.add(i+1);
            }else{
                list.add(3);
            }
        }
        layoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        testRlv.setLayoutManager(layoutManager);
        mTestAdapter = new TestAdapter(getContext());
        testRlv.setAdapter(mTestAdapter);
        mTestAdapter.freshData(list);

        return viewContent;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showTitle("测试");
        layoutManager.scrollToPosition(0);
//        layoutManager.scrollToPositionWithOffset(5,0);
        testRlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //查找第一个可见的item的position
                int firstItemPosition =layoutManager.findFirstVisibleItemPosition();
                if(firstItemPosition==0){
                    //滑动的距离
                    mDistanceY += dy;
                    //toolbar的高度
                    View view1 = testRlv.getChildAt(0);
                    TextView mTextView = (TextView) view1.findViewById(R.id.tvTest);
                    int toolbarHeight = view1.getHeight();

                    //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                    if (mDistanceY < toolbarHeight) {
                        float scale = (float) mDistanceY / toolbarHeight;
                        float alpha = scale * 255;
                        mLayout.setBackgroundColor(Color.argb((int) alpha, 0, 0, 0));

                        float scale1 = scale;
                        float alpha1 = 255-scale1*255;
                         mTextView.setBackgroundColor(Color.argb((int) alpha1, 255, 255, 255));
                        if(alpha>150&&dy>0&&type!=1){
                            mDistanceY=toolbarHeight;
                            mLayout.setBackgroundColor(Color.argb(255,0,0,0));
                                mTextView.setBackgroundColor(Color.argb(0, 255, 255, 255));
                            layoutManager.scrollToPositionWithOffset(1,30);
                        }
                    } else {
                        //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                        //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                        //将标题栏的颜色设置为完全不透明状态
                        if(testRlv.canScrollVertically(-1)) {
                            mLayout.setBackgroundColor(Color.argb(0, 0, 0, 0));
                            mDistanceY = dy;
                        }else {
                            mLayout.setBackgroundColor(Color.argb(255, 0, 0, 0));
                            mTextView.setBackgroundColor(Color.argb(0, 255, 255, 255));
                        }
                    }
                }else{
                    mLayout.setBackgroundColor(Color.argb(255,0,0,0));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                type=newState;
            }
        });


    }




}
