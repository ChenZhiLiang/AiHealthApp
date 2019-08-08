package com.app.aihealthapp.core.helper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.view.circledialog.CircleDialog;
import com.app.aihealthapp.view.circledialog.callback.ConfigButton;
import com.app.aihealthapp.view.circledialog.callback.ConfigDialog;
import com.app.aihealthapp.view.circledialog.params.ButtonParams;
import com.app.aihealthapp.view.circledialog.params.DialogParams;

/**
 * Created by Administrator on 2017-04-13.
 * CircleDialog 圆角对话框、进度条、列表框、输入框
 */

public class CircleDialogTools {


    /**
     * 选择列表
     * @param activity
     * @param title
     * @param items
     * @param listener
     */
    public static void circleDialogList(AppCompatActivity activity, String title, Object items, AdapterView.OnItemClickListener listener){
        new CircleDialog.Builder(activity)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setTitle(title)
                .setTitleColor(activity.getResources().getColor(R.color.default_text_color))
                .setItems(items, listener)
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        //取消按钮字体颜色
                        params.textColor = Color.RED;
                    }
                })
                .show();
    }

    /**
     * 底部弹窗
     * @param activity
     * @param items
     * @param listener
     */
    public static void ShowPopupBottomDialog(AppCompatActivity activity, Object items, AdapterView.OnItemClickListener listener){
        new CircleDialog.Builder(activity)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setItems(items, listener)
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        //取消按钮字体颜色
                        params.textColor = Color.RED;
                    }
                })
                .show();
    }

    /**
     * 标题提示dialog
     * @param activity
     * @param title
     * @param str
     * @param positive
     * @param negative
     * @param listener
     * @param listener1
     */
    public static void ShowDialog(AppCompatActivity activity, String title, String str, String positive, String negative, View.OnClickListener listener, View.OnClickListener listener1){
        new CircleDialog.Builder(activity)
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setTitle(title)
                .setText(str)
                .setPositive(positive,listener)
                .setNegative(negative, listener1)
                .show();
    }
   /**
     * 无标题提示dialog
     * @param activity
     * @param str
     * @param listener
     */
    public static void ShowDialoghint(AppCompatActivity activity, String str, View.OnClickListener listener, View.OnClickListener listener1){
        new CircleDialog.Builder(activity)
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setText(str)
                .setPositive(activity.getString(R.string.str_sure),listener)
                .setNegative(activity.getString(R.string.cancel), listener1)
                .show();
    }
}
