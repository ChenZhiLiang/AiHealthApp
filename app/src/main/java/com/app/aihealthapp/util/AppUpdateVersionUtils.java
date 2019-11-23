package com.app.aihealthapp.util;

import android.app.Activity;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.app.aihealthapp.R;
import com.app.aihealthapp.ui.bean.VersionInfoBean;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/11/20 22:05
 * 修改人：Chen
 * 修改时间：2019/11/20 22:05
 */
public class AppUpdateVersionUtils {
    private DownloadBuilder builder;

    public void UpdateVersion(final Activity mActivity, VersionInfoBean mVersionInfoBean){
        if (Integer.parseInt(mVersionInfoBean.getVersionCode()) > utils.getVersionCode(mActivity)) {
            UIData uiData = UIData.create();
            uiData.setTitle(mVersionInfoBean.getChangeLog());
            uiData.setDownloadUrl(mVersionInfoBean.getApkUrl());
            uiData.setContent(mVersionInfoBean.getUpdateTips());
            builder = AllenVersionChecker
                    .getInstance()
                    .downloadOnly(uiData);
            builder.setForceRedownload(true); //默认false 下载忽略本地缓存
            builder.setShowNotification(true); // 默认true 是否显示通知栏
            builder.setNotificationBuilder(createCustomNotification());
            if (mVersionInfoBean.isForceUpgrade()) {//强制更新，关闭界面
                builder.setShowDownloadingDialog(true);
                builder.setForceUpdateListener(new ForceUpdateListener() {
                    @Override
                    public void onShouldForceUpdate() {
                        mActivity.finish();
                    }
                });
            }
            //设置是否显示下载对话框
            builder.setShowDownloadingDialog(false);
            builder.executeMission(mActivity);
        }
    }

    /*自定义通知栏显示下载进度*/
    private NotificationBuilder createCustomNotification() {
        return NotificationBuilder.create()
                .setRingtone(true)
                .setIcon(R.mipmap.logo)
                .setTicker("custom_ticker")
                .setContentTitle("版本更新")
                .setContentText("%d%%");
    }
}
