package com.risenb.ykj.network;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Vone (codeofshield@gmail.com) on 2016/9/17.
 */
public interface RetrofitUploadApi {

    //单文件上传
    @Multipart
    @POST("upload/formupload")//"image\";filename=\"ymdb.png"
    Observable<HttpResult<RetrofitUploadBean>> formUpload(@Part() MultipartBody.Part requestBody);

    //单文件上传
    @Multipart
    @POST("upload/formuploads")
    Observable<HttpResult<RetrofitUploadBean>> formUploads(@Part() List<MultipartBody.Part> parts);
}
