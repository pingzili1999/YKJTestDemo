package com.risenb.ykj.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fengyi on 2016/8/5.
 */
public class RetrofitInstance {

//    private static String baseUrl = "http://192.168.1.29/ymdb/index.php/Service/";
    private static String baseUrl = "http://www.yscbuy.com/index.php/Service/";
    public static String imageUrl = "http://www.yscbuy.com/index.php/Service/";
    private static Retrofit retrofit;
    public static boolean isDebug =true;

    private static Gson gson;

    public static Gson GsonInstance()
    {
        if(gson==null)
        {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();

        }
        return gson;
    }

    public static Retrofit Instance() {

        if (retrofit == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)
                    .addInterceptor(new CommonInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS);

            if (isDebug) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClient.addInterceptor(httpLoggingInterceptor);
            }


            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(GsonInstance()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
