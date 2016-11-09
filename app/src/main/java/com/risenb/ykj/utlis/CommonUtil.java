package com.risenb.ykj.utlis;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.risenb.ykj.R;
import com.risenb.ykj.network.RetrofitInstance;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {


    public static Context mApplicationContent;
    /**
     * 初始化获取全局上下文
     */
    public static void initialize(Context applicationContext) {
        mApplicationContent = applicationContext;
    }

    /**
     * UniversalImageLoader display config
     */
    public static DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)//加载中
            .showImageOnFail(R.mipmap.ic_launcher)//加载终止
            .showImageForEmptyUri(R.mipmap.ic_launcher)//加载失败
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    /**
     * 判断应用是否处于后台状态
     */
    public static boolean isBackground() {
        ActivityManager am = (ActivityManager) mApplicationContent.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mApplicationContent.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制文本到剪贴板
     */
    public static void copyToClipboard(String text) {
        if (Build.VERSION.SDK_INT >= 11) {
            ClipboardManager cbm = (ClipboardManager) mApplicationContent.getSystemService(Activity.CLIPBOARD_SERVICE);
            cbm.setPrimaryClip(ClipData.newPlainText(mApplicationContent.getPackageName(), text));
        } else {
            android.text.ClipboardManager cbm = (android.text.ClipboardManager) mApplicationContent.getSystemService(Activity.CLIPBOARD_SERVICE);
            cbm.setText(text);
        }
    }


    /**
     * 经纬度测距
     */
    public static double distance(double jingdu1, double weidu1, double jingdu2, double weidu2) {
        double a, b, R;
        R = 6378137; // 地球半径
        weidu1 = weidu1 * Math.PI / 180.0;
        weidu2 = weidu2 * Math.PI / 180.0;
        a = weidu1 - weidu2;
        b = (jingdu1 - jingdu2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(weidu1)
                * Math.cos(weidu2) * sb2 * sb2));
        return d;
    }

    /**
     * 是否有网络
     */
    public static boolean isNetWorkAvilable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mApplicationContent.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 取APP版本号
     */
    public static int getAppVersionCode() {
        try {
            PackageManager mPackageManager = mApplicationContent.getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(mApplicationContent.getPackageName(), 0);
            return _info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 取APP版本名
     */
    public static String getAppVersionName() {
        try {
            PackageManager mPackageManager = mApplicationContent.getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(mApplicationContent.getPackageName(), 0);
            return _info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


    /**
     * Md5 Encrypt
     */
    public static String MD5(byte[] data) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(data);
        byte[] m = md5.digest();
        return Base64.encodeToString(m, Base64.DEFAULT);
    }


    /**
     * 获取Uri
     */
    public static Uri getUriFromRes(int id) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + mApplicationContent.getResources().getResourcePackageName(id) + "/"
                + mApplicationContent.getResources().getResourceTypeName(id) + "/"
                + mApplicationContent.getResources().getResourceEntryName(id));
    }

    //获得屏幕的宽
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    public static String changeMoney(double money) {
        DecimalFormat decimalFormat = new DecimalFormat("￥###0.00");
        String changeMoney = decimalFormat.format(money);
        return changeMoney;
    }

    /** 之前显示的内容 */
    private static String oldMsg ;
    /** Toast对象 */
    private static Toast toast = null ;
    /** 第一次时间 */
    private static long oneTime = 0 ;
    /** 第二次时间 */
    private static long twoTime = 0 ;
    public  static ProgressDialog mProgressDialog;

    /**
     * int 类型转换成字符串类型
     * @param agr
     * @return
     */
    public static String stringForInt(Integer agr){
        return agr+"";
    }

    /**
     * 显示一个对话框 并可设置能否取消及显示文字(显示后一定要dismiss)
     */
    public static void showDialog(Context activity,String showText,boolean cancelable){
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);//圆形
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setMessage(showText);
        mProgressDialog.show();
    }
    public static void dismiss(){
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }
    /**
     * 验证字符串是否是网址
     */
    public static String isHttpUrl(String url){

        if(url.startsWith("http:")){
            return url;
        }else{
            return Connect(RetrofitInstance.imageUrl,url);
        }

    }

    /**
     * 显示Toast（短时间多次执行不会多次弹出）
     * @param message
     */
    public static void showToast(String message){
        if(toast == null){
            toast = Toast.makeText(mApplicationContent, message, Toast.LENGTH_SHORT);
            toast.show() ;
            oneTime = System.currentTimeMillis() ;
        }else{
            twoTime = System.currentTimeMillis() ;
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show() ;
                }
            }else{
                oldMsg = message ;
                toast.setText(message) ;
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }
    public static void showLongToast(String message){
        if(toast == null){
            toast = Toast.makeText(mApplicationContent, message, Toast.LENGTH_LONG);
            toast.show() ;
            oneTime = System.currentTimeMillis() ;
        }else{
            twoTime = System.currentTimeMillis() ;
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_LONG){
                    toast.show() ;
                }
            }else{
                oldMsg = message ;
                toast.setText(message) ;
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }
    /**
     * 验证手机格式
     */
    public static String isMobileNO(String mobiles){
        if(TextUtils.isEmpty(mobiles)||mobiles.equals(" ")){
            return "null";
        }else {
            Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            if(m.matches())
                return "ok";
            else
                return "no";
        }
    }
    /**
     * 判断号码是否为手机号
     */
    public static boolean isMobilePhoneNum(String phoneNum) {
        Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }
    /**
     * 输入键盘是否显示
     */
    public static boolean isOpenInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager)context .getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }
    /**
     * 关闭输入键盘
     */
    public static void closeInputMethod(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) mApplicationContent.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 验证密码格式
     */

    public static String PassWordIsOK(String passWord){
        if(TextUtils.isEmpty(passWord)||passWord.equals(" ")){
            return "null";
        }
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{6,16}");
        Matcher matcher = pattern.matcher(passWord);
        System.out.println(matcher.matches()+"---");
        return matcher.matches()==true?"ok":"no";
    }

    /**
     * MD5加密密码
     */

    public static String md5(String password) {
        byte[] bytes = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());  //更新摘要
            bytes = digest.digest(); //再通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置。
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            /**
             * 0xFF默认是整形，一个byte跟0xFF相与会先将那个byte转化成整形运算
             */
            if ((b & 0xFF) < 0x10) {  //如果为1位 前面补个0
                builder.append("0");
            }

            builder.append(Integer.toHexString(b & 0xFF));
        }

        return builder.toString();
    }
    /**
     * 连接字符串
     */
    public static String Connect(String s,String s1){
        StringBuffer result = new StringBuffer();
        result.append(s);
        result.append(s1);
        return result.toString();
    }


}
