package com.risenb.ykj.base;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.risenb.ykj.utlis.ActivityManager;
import com.risenb.ykj.utlis.CommonUtil;
import com.risenb.ykj.utlis.DiskUtils;
import com.umeng.socialize.PlatformConfig;

import im.fir.sdk.FIR;


/**
 * Created by Vone (codeofshield@gmail.com) on 2016/8/10.
 */
public class BaseApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
        ActiveAndroid.setLoggingEnabled(true);
        CommonUtil.initialize(getApplicationContext());
        DiskUtils.getContext(getApplicationContext());
        initImageLoader(getApplicationContext());
        registerActivityLifecycleCallbacks(ActivityManager.getActivityLifecycleCallbacks());
        umengInit();
    }


    /**
     * UniversalImageLoader config
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }

    public void umengInit() {
        //PlatformConfig.setWeixin("wx9d32eb964f894f26", "ed20803624875fa3d54bd381a866a6ed");
        PlatformConfig.setWeixin("wx9d32eb964f894f26", "4b74357c2d799cefa98263061e964c08");
       // PlatformConfig.setQQZone("1105614465", "wUb3JOPggxnGFmmF");
        PlatformConfig.setQQZone("1105728508", "huh9C3IAmA9xvNVz");
    }

}
