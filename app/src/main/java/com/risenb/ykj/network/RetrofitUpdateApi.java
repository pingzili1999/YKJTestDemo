package com.risenb.ykj.network;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Vone (codeofshield@gmail.com) on 2016/9/17.
 */
public interface RetrofitUpdateApi {

    //检查更新
    @POST("upload/update")
    Observable<HttpResult<RetrofitUpdateBean>> update();


}
