package com.app.aihealthapp.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.alipay.sdk.app.PayTask;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.activity.home.HealthAskActivity;
import com.app.aihealthapp.ui.bean.PaymentBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.util.Map;

/**
 * author：chenzl
 * Create time: 2018/12/29 0029 15:30
 * describe: 支付工具类
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class PayUtils {

    private Activity mActivity;
    private int doctor_id;
    public PayUtils(Activity mActivity){
        this.mActivity = mActivity;
    }

    /**
     * 微信支付
     * @param mPaymentBean 微信支付参数集合
     */
    public void WXPay(PaymentBean mPaymentBean){
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(mActivity, mPaymentBean.getAppid(), true);
        mWxApi.registerApp(mPaymentBean.getAppid());
        /**
         * 请求app服务器得到的回调结果
         */
        if (mWxApi != null) {
            PayReq req = new PayReq();
            req.appId =  mPaymentBean.getAppid();// 微信开放平台审核通过的应用APPID
            req.partnerId = mPaymentBean.getPartnerid();// 微信支付分配的商户号
            req.prepayId = mPaymentBean.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
            req.nonceStr = mPaymentBean.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
            req.timeStamp = mPaymentBean.getTimestamp();// 时间戳，app服务器小哥给出
            req.packageValue = mPaymentBean.getPackageX();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
            req.sign = mPaymentBean.getSign();// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
            mWxApi.sendReq(req);
        }else {
            Log.e("PayUtils","IWXAPI未初始化");
        }
    }

    /**
     * 支付宝支付
     */
    public void Alipay(final String alipay_sdk,int doctor_id){
        this.doctor_id = doctor_id;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(alipay_sdk, false);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            if (payResult.getResultStatus().equals("9000")) {//支付成功
                Intent intent =new Intent();
                intent.setAction("action.pay.success");
                mActivity.sendBroadcast(intent);
            } else if (payResult.getResultStatus().equals("6001")) {//取消支付
                ToastyHelper.customCenterToast(mActivity,"取消支付");
            } else {//支付失败
                ToastyHelper.customCenterToast(mActivity,"支付失败");
            }
        }
    };
}
