package com.app.aihealthapp.core.network.api;

/**
 * Created by Administrator on 2017-11-02.
 * 对外接口url链接
 */

public final class ApiUrl {

    // dev = 0 测试 dev=1 生产
    public static final int dev = 0;
    public static final String HOST =dev==0?"http://aijiankang.cacpo.com/":"http://aijiankang.cacpo.com/";
    public static class HomeApi{
        //首页
        public static final String Home = HOST +"api/index/dashboard";
    }

    public static class UserApi{
        public static final String Verify = HOST +"api/verify";
        public static final String Register = HOST +"api/register";
        public static final String Login = HOST +"api/login";
        public static final String UserInfo = HOST +"api/user/dashboard";
        public static final String Authentication = HOST +"api/user/realname";
    }

    public static class DeviceApi{

        public static final String DeviceInfo = HOST +"api/device/dashboard";
        public static final String BindDevice = HOST +"api/device/bind";

    }
}
