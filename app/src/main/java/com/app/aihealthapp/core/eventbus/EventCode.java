package com.app.aihealthapp.core.eventbus;

/**
 * author：chenzl
 * Create time: 2017/11/16 0016 09:14
 * describe:
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public final class EventCode {
    public static final class Code {
        public static final int QR_LOGIN = 0x111111;//扫码登录
        public static final int LOOk_CHANG = 0x222222;
        public static final int CONNECTED_SUCCESS = 0x33333;
        public static final int LOGIN_SUCCESS = 0x444444;//登录成功
        public static final int LOGOUT = 0x000000;
        public static final int AUTHENTICATION_SUCCESS = 0x555555;//实名认证成功
        public static final int MEASURE_SUCCESS = 0x666666;//测量成功
        public static final int BIND_DEVICE = 0x777777;//绑定设备成功
        public static final int WEIXIN_AOUTH = 0x666668;//微信授权
        public static final int WEIXIN_PAY = 0x666660;//微信支付
        public static final int WEIXIN_SHARE_SUCCESS = 0x666661;//微信分享成功
    }
}
