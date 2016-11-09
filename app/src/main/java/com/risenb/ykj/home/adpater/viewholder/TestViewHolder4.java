package com.risenb.ykj.home.adpater.viewholder;

import android.view.View;

import com.risenb.ykj.R;
import com.risenb.ykj.base.BaseViewHolder;
import com.risenb.ykj.view.HourItem;
import com.risenb.ykj.view.IndexHorizontalScrollView;
import com.risenb.ykj.view.Today24HourView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class TestViewHolder4 extends BaseViewHolder<Integer>{
    private IndexHorizontalScrollView indexHorizontalScrollView;
    private Today24HourView today24HourView;
    List<HourItem> listItems;
    public TestViewHolder4(View itemView) {
        super(itemView);
        indexHorizontalScrollView = (IndexHorizontalScrollView)itemView.findViewById(R.id.indexHorizontalScrollView);
        today24HourView = (Today24HourView)itemView.findViewById(R.id.today24HourView);
    }

    @Override
    public void bindData(Integer data) {
        initHourItems();
        today24HourView.setHourItems(listItems);
        indexHorizontalScrollView.setToday24HourView(today24HourView);
    }
    private static final int TEMP[] = {22, 23, 23, 23, 23,
            22, 23, 23, 23, 22,
            21, 21, 22, 22, 23,
            23, 24, 24, 25, 25,
            25, 26, 25, 24};
    private static final int WINDY[] = {2, 2, 3, 3, 3,
            4, 4, 4, 3, 3,
            3, 4, 4, 4, 4,
            2, 2, 2, 3, 3,
            3, 5, 5, 5};
    private static final int WEATHER_RES[] ={R.mipmap.w0, R.mipmap.w1, R.mipmap.w3, -1, -1
            ,R.mipmap.w5, R.mipmap.w7, R.mipmap.w9, -1, -1
            ,-1, R.mipmap.w10, R.mipmap.w15, -1, -1
            ,-1, -1, -1, -1, -1
            ,R.mipmap.w18, -1, -1, R.mipmap.w19};


    private void initHourItems(){
        listItems = new ArrayList<>();
        for(int i=0; i<24; i++){
            String time;
            if(i<10){
                time = "0" + i + ":00";
            } else {
                time = i + ":00";
            }
            HourItem hourItem = new HourItem();
            hourItem.time = time;
            hourItem.windy = WINDY[i];
            hourItem.temperature = TEMP[i];
            hourItem.res = WEATHER_RES[i];
            listItems.add(hourItem);
        }
    }
}
