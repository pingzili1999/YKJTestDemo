package com.risenb.ykj.network;


import android.content.Intent;


import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Fengyi on 2016/8/9.
 */
public abstract class ApiSubscriber<E> extends Subscriber<HttpResult> {


    public abstract void onSuccess(E t);

    public abstract void onFail(String msg);


    @Override
    public void onStart() {

    }

    @Override
    public void onCompleted() {

    }

//    public void onSessionExpire() {
//        DiskUtils.deleteUserInfo();
//        SPUtils.remove(CommonUtil.mApplicationContent,"yes");
//        ActivityManager.currentActivity().startActivity(new Intent(ActivityManager.currentActivity(), LoginActivity.class));
//    }
//
//
//
//    public void bindingProcessing(){
//        ActivityManager.currentActivity().startActivity(new Intent(ActivityManager.currentActivity(), BoundPhoneNumberActivity.class));
//    }
//    public void noAddressManagement(){
//        ActivityManager.currentActivity().startActivity(new Intent(ActivityManager.currentActivity(), AddressManagementActivity.class));
//    }

    @Override
    public void onError(Throwable e) {
        if (RetrofitInstance.isDebug) {
            e.printStackTrace();
        }
        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
           // onFail(exception.message());
            onFail("网络连接失败，请查看网络设置");
        } else {
            if (RetrofitInstance.isDebug) {
                onFail(e.getMessage());
                e.printStackTrace();
            } else
                onFail("网络连接失败，请查看网络设置");
        }
    }

    @Override
    public void onNext(HttpResult httpResult) {
        if (0 == httpResult.getCode()) {
            if (httpResult.getData() != null)
                onSuccess((E) httpResult.getData());
            else
                onSuccess(null);
        }else if (2 == httpResult.getCode()) {
//            onSessionExpire();
        } else if(3==httpResult.getCode()){
//            bindingProcessing();
        }else if(4==httpResult.getCode()){
//            noAddressManagement();
        }else{
            onFail(httpResult.getMsg());
        }
    }
}
