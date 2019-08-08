package com.app.aihealthapp.core.helper;

import android.content.Context;
import android.util.Base64;

import com.app.aihealthapp.confing.AppConfig;
import com.app.aihealthapp.ui.bean.UserInfoBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/9.
 * 获取 SharedPreferences 的数据.
 */

public class SharedPreferenceHelper {

    /**
     * 设置用户信息 UserInfo
     *
     * @param context
     * @param user
     */
    public static void setUserInfo(Context context, UserInfoBean user) {
        if (user instanceof Serializable) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(user);//把对象写到流里
                String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                PreferenceHelper.write(context, AppConfig.TOKEN_FILE_NAME, AppConfig.USER_INFO, temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public static UserInfoBean getUserInfo(Context context) {
        String user = PreferenceHelper.readString(context,AppConfig.TOKEN_FILE_NAME,AppConfig.USER_INFO);
        if (user!=null){
            ByteArrayInputStream bais =  new ByteArrayInputStream(Base64.decode(user.getBytes(), Base64.DEFAULT));
            UserInfoBean user_info = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                user_info = (UserInfoBean) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return user_info;
        }else {
            return null;
        }
    }

    /**
     * 设置用户 Token
     *
     * @param context 上下文
     * @param token   token
     */
    public static void setUserToken(Context context, String token) {
        PreferenceHelper.write(context, AppConfig.TOKEN_FILE_NAME, AppConfig.TOKEN_NAME, token);
    }


    /**
     * 获取用户 Token
     *
     * @param context 上下文
     * @return token
     */
    public static String getUserToken(Context context) {
        return PreferenceHelper.readString(context, AppConfig.TOKEN_FILE_NAME, AppConfig.TOKEN_NAME);
    }

    /**
     * 设置设备mac地址
     *
     * @param context 上下文
     * @param address   设备mac地址
     */
    public static void setMacAddress(Context context, String address) {
        PreferenceHelper.write(context, AppConfig.TOKEN_FILE_NAME, AppConfig.MAC_ADDRESS, address);
    }


    /**
     * 获取用户 Token
     *
     * @param context 上下文
     * @return token
     */
    public static String getMacAddress(Context context) {
        return PreferenceHelper.readString(context, AppConfig.TOKEN_FILE_NAME, AppConfig.MAC_ADDRESS);
    }

    /**
     * 清除 Token
     *
     * @param context 上下文
     */
    public static void clearShared(Context context) {
        //清除登录信息
        PreferenceHelper.remove(context, AppConfig.TOKEN_FILE_NAME, AppConfig.TOKEN_NAME);
        PreferenceHelper.remove(context, AppConfig.TOKEN_FILE_NAME, AppConfig.USER_INFO);

    }

}
