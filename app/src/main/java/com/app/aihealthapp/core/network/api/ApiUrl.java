package com.app.aihealthapp.core.network.api;

/**
 * Created by Administrator on 2017-11-02.
 * 对外接口url链接
 */

public final class ApiUrl {

    // dev = 0 测试 dev=1 生产
    public static final int dev = 0;
    /*通联域名*/
    public static final String HOST =dev==0?"http://test1.allinpaygx.com/":"http://voice.xtyj.allinpaygx.com/";
    public static final String HOST_CODE = dev==0? "http://xtest.allinpaygx.com/":"http://xtyj.allinpaygx.com/";//发送验证码域名
    public static final String Secret = "838d8edc55b24ab5b803a642183daa10";
    public static class VoiceApi{
        /**
         * 商户信息查询Marine、:
         * http://test1.allinpaygx.com
         */
        public static final String MerchantInfo = HOST +"msg/voice/getMerchant";
        /**
         * 商户设备接入
         */
        public static final String Connect = HOST +"msg/voice/connect";

        /**
         * 发送消息
         */
        public static final String VoiceSend = HOST +"msg/voice/send";

        /**
         * 订单列表
         */
        public static final String OrderList = HOST +"msg/voice/getOrders";

        /**
         * 解除绑定
         */
        public static final String UnBind = HOST + "msg/voice/unbind";

        /**
        * 获取版本信息
        */
        public static final String GetVersionInfo = HOST +"msg/voice/getVersionInfo";

        /**
         * 发送验证码
         */
        public static final String SendVerificationCode = HOST_CODE + "wx/cash/external/sendMsg";

    }
}
