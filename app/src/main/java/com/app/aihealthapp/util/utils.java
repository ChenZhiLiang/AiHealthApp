package com.app.aihealthapp.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import com.app.aihealthapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
     * 将Bitmap对象读到字节数组中
     *
     * @param bitmap
     * @return
     */
    public static byte[] bmpToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();
        return datas;
    }
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

    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


    /**解析日期，获取年月日星期*/
    public static String parseDateToYearMonthDayWeek(Date date){

        //获取默认选中的日期的年月日星期的值，并赋值
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期

        String yearStr = calendar.get(Calendar.YEAR)+"";//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        String monthStr = month < 10 ? "0" + month : month + "";
        int day = calendar.get(Calendar.DATE);//获取日
        String dayStr = day < 10 ? "0" + day : day + "";
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        /*星期日:Calendar.SUNDAY=1
         *星期一:Calendar.MONDAY=2
         *星期二:Calendar.TUESDAY=3
         *星期三:Calendar.WEDNESDAY=4
         *星期四:Calendar.THURSDAY=5
         *星期五:Calendar.FRIDAY=6
         *星期六:Calendar.SATURDAY=7 */
        switch (week) {
            case 1:
                weekStr = "周日";
                break;
            case 2:
                weekStr = "周一";
                break;
            case 3:
                weekStr = "周二";
                break;
            case 4:
                weekStr = "周三";
                break;
            case 5:
                weekStr = "周四";
                break;
            case 6:
                weekStr = "周五";
                break;
            case 7:
                weekStr = "周六";
                break;
            default:
                break;
        }
        return yearStr + "年" + monthStr + "月" + dayStr + "日" + ", " + weekStr;
    }

    /**
     * 从asset目录下读取城市json文件转化为String类型
     *
     * @Title: InitData
     * @param
     * @return void
     * @throws @date
     *             [2015年8月21日 上午9:40:00]
     */
    public static String InitAssetsData(Activity mActivity,String fileName) {
        StringBuffer sb = new StringBuffer();
        AssetManager mAssetManager = mActivity.getAssets();
        try {
            InputStream is = mAssetManager.open(fileName);
            byte[] data = new byte[is.available()];
            int len = -1;
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     *  @author
     *  @time
     *  @describe  分转小时 除以
     */
    public static String MinuteToHours(int TotalTime){
        String hours;
        if (TotalTime<60){
            hours = "00";
            return hours;
        }else {
            int temp_hours = TotalTime/60;
            if (temp_hours<10){
                hours = "0"+temp_hours;
                return hours;
            }else {
                hours = TotalTime+"";
                return hours;
            }
        }
    }

    /**
     *  @author
     *  @time    分小时 求于
     *  @describe
     */
    public static String MinuteInHours(int TotalTime){
        String minute;
        if (TotalTime<10){
            minute = "0"+TotalTime;
            return minute;
        }else if (10<=TotalTime&&TotalTime<60){
            minute = TotalTime+"";
            return minute;
        }else {
            int temp_minute = (int)(TotalTime%60);
            if (temp_minute<10){
                minute = "0"+temp_minute;
                return minute;
            }else {
                minute = temp_minute+"";
                return minute;
            }
        }
    }

    public static Spannable SleepTime(Context context,String str){
        Spannable span = new SpannableString(str);
        span.setSpan(new AbsoluteSizeSpan(40), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sleep_time)), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(40), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sleep_time)), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    /**
     * 方法描述：判断某一应用是否正在运行
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

}
