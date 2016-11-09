package com.risenb.ykj.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.risenb.ykj.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    private TextView ts,textTs;
    private String tiShi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_error);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        ts = (TextView) findViewById(R.id.tv_TS);
        textTs = (TextView) findViewById(R.id.ts_tv_s);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onPayFinish, errCode = " + resp.errCode + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
             if(String.valueOf(resp.errCode).equals("0")){
                tiShi = "支付成功";
            }else{
                tiShi = "支付失败";
             }
        }
        countDownTimer.start();

    }

    private CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
        @Override
        public void onTick(long l) {
            ts.setText(tiShi+"("+l/1000+"s后跳转)");
        }

        @Override
        public void onFinish() {
            finish();
        }
    };
}