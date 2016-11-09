package com.risenb.ykj.home.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.risenb.ykj.R;
import com.risenb.ykj.base.BaseRecycleAdapter;
import com.risenb.ykj.base.BaseViewHolder;
import com.risenb.ykj.home.adpater.viewholder.TestViewHolder1;
import com.risenb.ykj.home.adpater.viewholder.TestViewHolder2;
import com.risenb.ykj.home.adpater.viewholder.TestViewHolder3;
import com.risenb.ykj.home.adpater.viewholder.TestViewHolder4;

/**
 * Created by Administrator on 2016/11/9.
 */

public class TestAdapter extends BaseRecycleAdapter<Integer> {
    //建立枚举 2个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2,
        ITEM3,
        ITEM4
    }

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==ITEM_TYPE.ITEM1.ordinal()){
            view = LayoutInflater.from(getContext()).inflate(R.layout.home_item1,null,false);
            return new TestViewHolder1(view);
        }else if(viewType==ITEM_TYPE.ITEM2.ordinal()){
            view = LayoutInflater.from(getContext()).inflate(R.layout.home_item2,null,false);
            return new TestViewHolder2(view);
        }else if(viewType==ITEM_TYPE.ITEM3.ordinal()){
            view = LayoutInflater.from(getContext()).inflate(R.layout.home_item3,null,false);
            return new TestViewHolder3(view);
        }else{
            view = LayoutInflater.from(getContext()).inflate(R.layout.home_item4,null,false);
            return new TestViewHolder4(view);
        }
    }
    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        //activityStatus (integer, optional):活动状态 1=进行中；2=倒计时；3=已结束 ,
        if (getItem(position)==1)
            return ITEM_TYPE.ITEM1.ordinal();
        else if (getItem(position) == 2)
            return ITEM_TYPE.ITEM2.ordinal();
        else if(getItem(position)==3)
            return ITEM_TYPE.ITEM3.ordinal();
        else
            return ITEM_TYPE.ITEM4.ordinal();
    }
}
