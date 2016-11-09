package com.risenb.ykj.network;


import com.risenb.ykj.utlis.CommonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vone (codeofshield@gmail.com) on 2016/9/17.
 */
public class RetrofitSample {

    public void uploadSingleFileSample(String name) {
        //要上传的File
        File rawFile = new File(name);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", rawFile.getName(), RequestBody.create(MediaType.parse("image/*"), rawFile));
        RetrofitInstance.Instance().create(RetrofitUploadApi.class)
                .formUpload(filePart)
                .subscribeOn(Schedulers.io())//意思是耗时线程在io中进行
                .observeOn(AndroidSchedulers.mainThread())//意思是界面更新在主线程中
                .subscribe(new ApiSubscriber<RetrofitUploadBean>() {
                    @Override
                    public void onSuccess(RetrofitUploadBean t) {
                        CommonUtil.showToast(t.getFileName());
                    }

                    @Override
                    public void onFail(String msg) {
                        CommonUtil.showToast(msg);
                    }
                });
    }


    public void uploadMutiFileSample() {
        //要上传的File列表
        List<File> files = new ArrayList<>();

        List<MultipartBody.Part> parts = new ArrayList<>();
        for (File file : files) {
            parts.add(MultipartBody.Part.createFormData("file" + files.indexOf(file), file.getName(), RequestBody.create(MediaType.parse("image/*"), file)));
        }


        RetrofitInstance.Instance().create(RetrofitUploadApi.class)
                .formUploads(parts)
                .subscribeOn(Schedulers.io())//意思是耗时线程在io中进行
                .observeOn(AndroidSchedulers.mainThread())//意思是界面更新在主线程中
                .subscribe(new ApiSubscriber<RetrofitUploadBean>() {
                    @Override
                    public void onSuccess(RetrofitUploadBean t) {
                        CommonUtil.showToast(t.getFileName());
                    }

                    @Override
                    public void onFail(String msg) {
                        CommonUtil.showToast(msg);
                    }
                });
    }
}
