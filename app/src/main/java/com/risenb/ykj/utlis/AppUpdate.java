package com.risenb.ykj.utlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;


import com.risenb.ykj.network.ApiSubscriber;
import com.risenb.ykj.network.RetrofitInstance;
import com.risenb.ykj.network.RetrofitUpdateApi;
import com.risenb.ykj.network.RetrofitUpdateBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vone (codeofshield@gmail.com) on 2016/10/4.
 */

public class AppUpdate {


    private static DownloadManager dm = (DownloadManager) CommonUtil.mApplicationContent.getSystemService(Context.DOWNLOAD_SERVICE);
    public static long DownloadID;

    public static void update(final boolean showToast, final Activity holder) {
        RetrofitInstance.Instance().create(RetrofitUpdateApi.class)
                .update()
                .subscribeOn(Schedulers.io())//意思是耗时线程在io中进行
                .observeOn(AndroidSchedulers.mainThread())//意思是界面更新在主线程中
                .subscribe(new ApiSubscriber<RetrofitUpdateBean>() {
                    @Override
                    public void onSuccess(final RetrofitUpdateBean t) {
                        if (CommonUtil.getAppVersionCode() < t.getVersionCode()) {
                            new AlertDialog.Builder(holder).setTitle("发现新版本 " + t.getVersionName())
                                    .setMessage(t.getChangeLog())
                                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (canDownload()) {
                                                dialog.dismiss();
                                                DownloadID = startDownload(t.getUrl(), "云端夺宝更新", t.getChangeLog());
                                            }
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        } else {
                            if (showToast) {
                                CommonUtil.showToast("当前为最新版本，无需更新");
                            }
                        }
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
    }


    public static long startDownload(String uri, String title, String description) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(uri));
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //req.setAllowedOverRoaming(false);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //第一种
        //file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
        //req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第二种
        //file:///storage/emulated/0/Download/update.apk
        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ymdb.apk");
        //第三种 自定义文件路径
        //req.setDestinationUri()
        req.setTitle(title);
        req.setDescription(description);
        req.setMimeType("application/vnd.android.package-archive");
        //加入下载队列
        return dm.enqueue(req);
    }


    public static boolean canDownload() {
        try {
            int state = CommonUtil.mApplicationContent.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
