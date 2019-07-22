package com.app.aihealthapp.core.helper;

import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.bean.UserInfoBean;

/**
 * author：chenzl
 * Create time: 2017/12/22 0022 14:28
 * describe: 用户信息管理类
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class UserHelper {

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfoBean getUserInfo() {

        UserInfoBean User = SharedPreferenceHelper.getUserInfo(AppContext.getContext());
        if (User != null) {
            return User;
        }else {
            return null;
        }
    }

    /**
     * 获取 用户Token
     * @return
     */
    public static String getToken() {
        String token = SharedPreferenceHelper.getUserToken(AppContext.getContext());
        if (token != null) {
            return token;
        }
        else {
            return null;
        }
    }


    /**
     * 清除用户信息
     */
    public static void clearUser(){
        SharedPreferenceHelper.clearShared(AppContext.getContext());
    }



}
