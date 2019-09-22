package com.app.aihealthapp.ui.activity.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/9/20 21:39
 * 修改人：Chen
 * 修改时间：2019/9/20 21:39
 */
public class ComeWxMessage {
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    public static final String SEND_WX_BROADCAST="SEND_WX_BROADCAST";
    public static final String QQ="com.tencent.mobileqq";
    public static final String WX="com.tencent.mm";
    public static final String MMS="com.android.mms";
    public static final String CALL="com.android.incallui";
    private MyMessage myMessage;
    private Context context;

    public ComeWxMessage(MyMessage myMessage, Context context) {
        this.myMessage = myMessage;
        this.context = context;
        registBroadCast();
    }

    private BroadcastReceiver b=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            String pachageName=bundle.getString("packageName");
            switch (pachageName){
                case WX:
                    myMessage.comeWxMessage();
                    break;
                case QQ:
                    myMessage.comeQQmessage();
                    break;
                case MMS:
                    myMessage.comeShortMessage();
                    break;
                case CALL:
                    myMessage.comePhone();
                    break;
            }
        }
    };
    private void registBroadCast() {
        IntentFilter filter=new IntentFilter(SEND_WX_BROADCAST);
        context.registerReceiver(b,filter);
    }
    public void unRegistBroadcast(){
        context.unregisterReceiver(b);
    }

    public void openSetting(){
        if (!isEnabled()) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "已开启服务权限", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isEnabled() {
        String pkgName = context.getPackageName();
        final String flat = Settings.Secure.getString(context.getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
