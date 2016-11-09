package com.risenb.ykj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.risenb.ykj.view.ColumnAndLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.WeatherDataViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeatherDailyModel> mDatas=new ArrayList<>();
    private int minData;
    private int maxData;
    private int maxColumnData;

    public MyAdapter(Context context, List<WeatherDailyModel> mDatas, int maxData, int minData,int maxColumnData) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas.addAll(mDatas);
        this.minData = minData;
        this.maxData = maxData;
        Log.i("adapter","max="+maxData);
        Log.i("adapter","min="+minData);
        this.maxColumnData = maxColumnData;
    }

    @Override
    public WeatherDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item1, parent, false);
        WeatherDataViewHolder viewHolder = new WeatherDataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherDataViewHolder holder, int position) {
        // 最低温度设置为15，最高温度设置为30
        WeatherDailyModel weatherModel = mDatas.get(position);
        holder.weatherLineView.setMinAndMaxData(minData, maxData,maxColumnData);
        holder.weatherLineView.setTextLineFront(minData+"°",maxData+"°","风力");
        if(weatherModel.isStart()){
            holder.weatherLineView.setLineAndColumnData(null, null,null);
        }else{
            int low[] = new int[5];
            int high[] = new int[2];
            low[1] = weatherModel.getLow();
            high[0] = weatherModel.getHigh();
            //  low[3]=1;high[3]=1;low[4]=1;high[4]=1;
            if (position <= 1) {
                low[3] = 1;
            } else {
                WeatherDailyModel weatherModelLeft = mDatas.get(position - 1);
                low[0] = (weatherModelLeft.getLow() + weatherModel.getLow()) / 2;
            }
            if (position >= mDatas.size() - 1) {
                low[4] = 1;
            } else {
                WeatherDailyModel weatherModelRight = mDatas.get(position + 1);
                low[2] = (weatherModel.getLow() + weatherModelRight.getLow()) / 2;
            }
            holder.weatherLineView.setLineAndColumnData(low, high,weatherModel.getText_day());
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class WeatherDataViewHolder extends RecyclerView.ViewHolder {

        ColumnAndLineView weatherLineView;


        public WeatherDataViewHolder(View itemView) {
            super(itemView);
            weatherLineView = (ColumnAndLineView) itemView.findViewById(R.id.wea_line);
        }
    }
}
