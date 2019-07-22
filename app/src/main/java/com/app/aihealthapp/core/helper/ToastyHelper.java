package com.app.aihealthapp.core.helper;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aihealthapp.R;
import com.app.aihealthapp.view.toasty.Toasty;

/**
 * Created by Administrator on 2017-11-06.
 * 二次封装Toasty
 */

public class ToastyHelper {

    /**
     * 显示错误信息的Toast:
     * @param activity
     * @param msg
     */
     public static void toastyError(Activity activity, String msg){
         Toasty.error(activity, msg, Toast.LENGTH_SHORT, true).show();
     }

    /**
     * 显示成功的Toast::
     * @param activity
     * @param msg
     */
    public static void toastySuccess(Activity activity,String msg){
        Toasty.success(activity, msg, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示info的Toast::
     * @param activity
     * @param msg
     */
    public static void toastyInfo(Activity activity,String msg){
        Toasty.info(activity, msg, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示警告的:
     * @param activity
     * @param msg
     */
    public static void toastyWarning(Activity activity,String msg){
        Toasty.warning(activity, msg, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示常规的:
     * @param activity
     * @param msg
     */
    public static void toastyNormal(Activity activity,String msg){
        Toasty.normal(activity, msg).show();
    }


    /**
     * 自定义居中弹窗 半透明阴影
     * @param activity
     * @param msg
     */
   /* public static void customCenterToast(Activity activity,String msg){
        View view = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.toast_custom, null);
        LinearLayout toast_linear = view.findViewById(R.id.toast_linear);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UnitUtil.dip2px(activity,100),UnitUtil.dip2px(activity,100));
        toast_linear.setLayoutParams(layoutParams);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(msg);
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }*/
}
