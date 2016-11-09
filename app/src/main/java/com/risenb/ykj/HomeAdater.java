package com.risenb.ykj;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risenb.ykj.view.WeatherLineView;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class HomeAdater extends RecyclerView.Adapter<HomeAdater.WeatherDataViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeatherDailyModel> mDatas;
    private int mLowestTem;
    private int mHighestTem;

    public HomeAdater(Context context, List<WeatherDailyModel> datats, int lowtem, int hightem) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        mLowestTem = lowtem;
        mHighestTem = hightem;
    }

    @Override
    public WeatherDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        WeatherDataViewHolder viewHolder = new WeatherDataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherDataViewHolder holder, int position) {
        // 最低温度设置为15，最高温度设置为30
        Resources resources = mContext.getResources();
        WeatherDailyModel weatherModel = mDatas.get(position);
        holder.dayText.setText(weatherModel.getText_day()+"\n"+weatherModel.getText_day());
        int iconday = resources.getIdentifier("wth_code_" + weatherModel.getCode_day(), "drawable", mContext.getPackageName());
        if (iconday == 0) {
            holder.dayIcon.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.dayIcon.setImageResource(iconday);
        }
        holder.weatherLineView.setLowHighestData(mLowestTem, mHighestTem);
        int iconight = resources.getIdentifier("wth_code_" + weatherModel.getCode_day(), "drawable", mContext.getPackageName());
        if (iconight == 0) {
            holder.nighticon.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.nighticon.setImageResource(iconight);
        }
        holder.nightText.setText(weatherModel.getText_night()+"\n"+weatherModel.getText_day());
        int low[] = new int[5];
        int high[] = new int[5];
        low[1] = weatherModel.getLow();
        high[1] = weatherModel.getHigh();
      //  low[3]=1;high[3]=1;low[4]=1;high[4]=1;
        if (position <= 0) {
            low[3] = 1;
            high[3] = 1;
        } else {
            WeatherDailyModel weatherModelLeft = mDatas.get(position - 1);
            low[0] = (weatherModelLeft.getLow() + weatherModel.getLow()) / 2;
            high[0] = (weatherModelLeft.getHigh() + weatherModel.getHigh()) / 2;
        }
        if (position >= mDatas.size() - 1) {
            low[4] = 1;
            high[4] = 1;
        } else {
            WeatherDailyModel weatherModelRight = mDatas.get(position + 1);
            low[2] = (weatherModel.getLow() + weatherModelRight.getLow()) / 2;
            high[2] = (weatherModel.getHigh() + weatherModelRight.getHigh()) / 2;
        }
        Log.i("low","low[0]"+low[0]+"\tlow[1]"+low[1]+"\tlow[2]"+low[2]);
        holder.weatherLineView.setLowHighData(low, high);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class WeatherDataViewHolder extends RecyclerView.ViewHolder {

        TextView dayText;
        ImageView dayIcon;
        WeatherLineView weatherLineView;
        ImageView nighticon;
        TextView nightText;

        public WeatherDataViewHolder(View itemView) {
            super(itemView);
            dayText = (TextView) itemView.findViewById(R.id.id_day_text_tv);
            dayIcon = (ImageView) itemView.findViewById(R.id.id_day_icon_iv);
            weatherLineView = (WeatherLineView) itemView.findViewById(R.id.wea_line);
            nighticon = (ImageView) itemView.findViewById(R.id.id_night_icon_iv);
            nightText = (TextView) itemView.findViewById(R.id.id_night_text_tv);
        }
    }
}
