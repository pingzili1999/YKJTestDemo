package com.risenb.ykj.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vone (codeofshield@gmail.com) on 2016/8/7.
 */
public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        if (RetrofitInstance.isDebug)
            Log.d("CommonInterceptor", String.format("响应请求 %s %.1fms", response.request().url(), (t2 - t1) / 1e6d));
        return response;
    }
}
