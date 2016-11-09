package com.risenb.ykj.my.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.risenb.ykj.MyAdapter;
import com.risenb.ykj.R;
import com.risenb.ykj.WeatherDailyModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/10.
 */
public class My extends Fragment {
    RecyclerView rvHome;
    private List<WeatherDailyModel> mWeatherModels=new ArrayList<>();
    private MyAdapter mWeaDataAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.fragment_home, null);


        return viewContent;
    }


}
