package com.app.aihealthapp.ui;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Vibrator;
import android.support.multidex.MultiDex;

import com.app.aihealthapp.confing.AppConfig;
import com.app.aihealthapp.core.base.BaseApplication;
import com.crrepa.ble.CRPBleClient;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017-11-02.
 */

public class AppContext extends BaseApplication {

    public Vibrator mVibrator;
    private static AppContext mInstance;

    public static IWXAPI wxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        mBleClient = CRPBleClient.create(this);

        // 推荐分享 调用微信 用到
        wxapi = WXAPIFactory.createWXAPI(this,AppConfig.WEIXIN_APP_ID,true);
        //将应用的app_id 注册到微信
        wxapi.registerApp(AppConfig.WEIXIN_APP_ID);
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private CRPBleClient mBleClient;

    public static CRPBleClient getBleClient(Context context) {
        AppContext application = (AppContext) context.getApplicationContext();
        return application.mBleClient;
    }


    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static synchronized AppContext getInstance() {
        return mInstance;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //非默认值
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        //非默认值
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
