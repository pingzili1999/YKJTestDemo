package com.risenb.ykj.regulation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.risenb.ykj.R;
import com.risenb.ykj.base.BaseFragment;

/**
 * Created by Administrator on 2016/10/10.
 */
public class Regulation extends BaseFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.fragment_regulation, null);

        return viewContent;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showTitle("测试").withBack().setBackground(R.color.cardview_dark_background);

    }
}
