package com.app.aihealthapp.core.helper;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Button;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.permission.Action;
import com.app.aihealthapp.core.permission.AndPermission;
import com.app.aihealthapp.core.permission.Permission;
import com.app.aihealthapp.core.permission.PermissionSetting;
import com.app.aihealthapp.core.permission.Rationale;
import com.app.aihealthapp.core.permission.RequestExecutor;
import com.app.aihealthapp.core.permission.checker.StrictChecker;

import java.util.List;

/**
 * author：chenzl
 * Create time: 2018/1/30 0030 09:55
 * describe: 权限请求帮助类
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class PermissionHelper implements Rationale {

    private PermissionSetting mSetting;
    private boolean isGrant = false ;//是否同意授权

    public PermissionHelper(){

    }
    /*请求权限*/
    public  boolean RequestPermisson(final Context context,final String... permissions1){
        mSetting  = new PermissionSetting(context);
        AndPermission.with(context)
                .permission(permissions1)
                .rationale(this)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        StrictChecker checker = new StrictChecker();
                        // 判断是否授权成功，防止有些国产手机(比如oppo A57)不适配，成功后再判断一次 是否已经授权，没授权再重新授权
                        if (checker.hasPermission(context, permissions)) {
                            isGrant = true;
                            if (permissions1== Permission.Group.LOCATION){
//                                EventBusHelper.sendEvent(new Event(EventCode.Code.ACCESS_LOCATION));//请求定位权限成功，发送事件通知
                            }
                        } else {
                            mSetting.showSetting(permissions);
                        }
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // TODO what to do
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            isGrant = false;
                            mSetting.showSetting(permissions);
                        }
                    }
                }).start();
        return isGrant;
    }
    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_rationale, TextUtils.join("\n", permissionNames));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示")
                .setMessage(message)
                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        executor.execute();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                executor.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnPos.setTextColor(Color.BLACK);
        btnNeg.setTextColor(Color.BLACK);
    }
}
