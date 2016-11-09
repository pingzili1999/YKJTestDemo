package com.risenb.ykj.utlis.citytool;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.risenb.ykj.network.RetrofitInstance;
import com.risenb.ykj.utlis.CommonUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/9/14.
 */
public class CityUtil {

    private static String rawData;
    private static List<ItemBean> allBeans;
    private static List<ItemBean> singleBeans;

    public static String getRawData() {
        if (rawData == null) {
            try {
                InputStream is;
                is = CommonUtil.mApplicationContent.getAssets().open("citys.txt");
                rawData = readTextFromSdcard(is);
            } catch (Exception e) {
                rawData = null;
            }
        }
        return rawData;
    }

    public static List<ItemBean> getAllBean() {
        if (allBeans == null && getRawData() != null) {
            allBeans = RetrofitInstance.GsonInstance().fromJson(getRawData(), new TypeToken<List<ItemBean>>() {
            }.getType());
        }
        Log.e("readTextFromSdcard", "读取结束" + new Date().toLocaleString());
        return allBeans;
    }

    public static List<ItemBean> singleAllBean() {
        if (singleBeans == null) {
            singleBeans = new ArrayList<>();
            for (ItemBean a : getAllBean()) {
                singleBeans.add(a);
                if (a.getChildren() != null) {
                    for (ItemBean b : a.getChildren()) {
                        singleBeans.add(b);
                        if (b.getChildren() != null) {
                            for (ItemBean c : b.getChildren()) {
                                singleBeans.add(c);
                            }
                        }
                    }
                }
            }
        }
        return singleBeans;
    }

    public static List<ItemBean> getProvinces() {
        final List<ItemBean> result = new ArrayList<>();
        Observable.from(getAllBean())
                .subscribe(new Action1<ItemBean>() {
                    @Override
                    public void call(ItemBean itemBean) {
                        ItemBean temp = itemBean.clone();
                        temp.setChildren(null);
                        result.add(temp);
                    }
                });
        return result;
    }

    public static List<ItemBean> getCities(long provinceRealValue) {
        final List<ItemBean> result = new ArrayList<>();
        for (ItemBean a : getAllBean()) {
            if (a.getRealValue() == provinceRealValue) {
                for (ItemBean b : a.getChildren()) {
                    ItemBean temp = b.clone();
                    temp.setChildren(null);
                    result.add(temp);
                }
            }
        }
        return result;
    }

    public static List<ItemBean> getRegions(long provinceRealValue, long cityValue) {
        final List<ItemBean> result = new ArrayList<>();
        for (ItemBean a : getAllBean()) {
            if (a.getRealValue() == provinceRealValue) {
                for (ItemBean b : a.getChildren()) {
                    if (b.getRealValue() == cityValue) {
                        for (ItemBean c : b.getChildren()) {
                            if ("市辖区".equals(c.getShowValue()) || "县".equals(c.getShowValue()))
                                continue;
                            ItemBean temp = c.clone();
                            temp.setChildren(null);
                            result.add(temp);
                        }
                    }
                }
            }
        }
        return result;
    }

    private static String readTextFromSdcard(InputStream is) throws Exception {
        Log.e("readTextFromSdcard", "读取开始" + new Date().toLocaleString());
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

}
