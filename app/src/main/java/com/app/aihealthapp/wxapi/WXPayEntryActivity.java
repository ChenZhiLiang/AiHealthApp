package com.app.aihealthapp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.aihealthapp.R;
import com.app.aihealthapp.confing.AppConfig;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * author：chenzl
 * Create time: 2018/1/18 0018 14:20
 * describe:
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_text);

        api = WXAPIFactory.createWXAPI(this, AppConfig.WEIXIN_APP_ID);
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
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            if (resp.errCode==0){
                Intent intent =new Intent();
                intent.setAction("action.pay.success");
                sendBroadcast(intent);
            }else if (resp.errCode==-1){
                ToastyHelper.toastyNormal(this,"支付失败");
            }else if (resp.errCode==-2){
                ToastyHelper.toastyNormal(this,"取消支付");
            }else {
                ToastyHelper.toastyNormal(this,"未知错误");
            }
            finish();
        }
    }
}
