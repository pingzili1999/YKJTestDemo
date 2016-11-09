package com.risenb.ykj.home.adpater.viewholder;

import android.view.View;

import com.risenb.ykj.R;
import com.risenb.ykj.base.BaseViewHolder;
import com.risenb.ykj.view.CircleProgressView;

/**
 * Created by Administrator on 2016/11/9.
 */

public class TestViewHolder1 extends BaseViewHolder<Integer>{
    CircleProgressView cpv;
    public TestViewHolder1(View itemView) {
        super(itemView);
        cpv = (CircleProgressView) itemView.findViewById(R.id.circleProgressbar);
    }
    @Override
    public void bindData(Integer data) {
        cpv.setProgress(168);
    }
}
