package com.app.aihealthapp.core.helper;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2017-04-13.
 * 弹出选择窗口
 */

public class CircleDialogHelper {

    public static void ShowBottomDialog(AppCompatActivity activity, Object list, AdapterView.OnItemClickListener listener){
        CircleDialogTools.ShowPopupBottomDialog(activity,list,listener);
    }

    public static void ShowDialog(AppCompatActivity activity, String title, String str, String positive, String negative, View.OnClickListener listener, View.OnClickListener listener1){
        CircleDialogTools.ShowDialog(activity,title,str,positive,negative,listener,listener1);
    }
    public static void ShowDialogHint(AppCompatActivity activity, String str, View.OnClickListener listener, View.OnClickListener listener1){
        CircleDialogTools.ShowDialoghint(activity,str,listener,listener1);
    }
}
