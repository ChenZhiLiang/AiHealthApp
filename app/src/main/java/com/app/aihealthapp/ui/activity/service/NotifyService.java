package com.app.aihealthapp.ui.activity.service;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/9/20 21:36
 * 修改人：Chen
 * 修改时间：2019/9/20 21:36
 */
public class NotifyService extends NotificationListenerService {
    public static final String SEND_WX_BROADCAST="SEND_WX_BROADCAST";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        Log.e("AAA", "=2==onNotificationPosted   ID :"
                + sbn.getId() + "\t"
                + sbn.getNotification().tickerText + "\t"
                + sbn.getPackageName());
        String packageName=sbn.getPackageName();
        String tickerText = sbn.getNotification().tickerText.toString();
        Intent intent=new Intent();
        intent.setAction(SEND_WX_BROADCAST);
        Bundle bundle=new Bundle();
        bundle.putString("packageName",packageName);
        bundle.putString("tickerText",tickerText);
        intent.putExtras(bundle);
        this.sendBroadcast(intent);

    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.e("AAA","服务启动");

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap) {
        Log.e("AAA", "=4==onNotificationRemoved   ID :"
                + sbn.getId() + "\t"
                + sbn.getNotification().tickerText
                + "\t" + sbn.getPackageName());
    }
}
