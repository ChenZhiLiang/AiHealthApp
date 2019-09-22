package com.app.aihealthapp.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 16:26
 * 修改人：Chen
 * 修改时间：2019/8/18 16:26
 */
public class utils {
    /**
     * @return
     */
    public static String getPresentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static boolean isEnabledNotification(Context context) {
        String pkgName = context.getPackageName();
        final String flat = Settings.Secure.getString(context.getContentResolver(),"enabled_notification_listeners");
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

    public static void toggleNotificationListenerService(Context context) {

        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context,com.app.aihealthapp.ui.activity.service.NotifyService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//
        pm.setComponentEnabledSetting(
                new ComponentName(context,com.app.aihealthapp.ui.activity.service.NotifyService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }
}
