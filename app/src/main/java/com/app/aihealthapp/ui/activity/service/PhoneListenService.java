package com.app.aihealthapp.ui.activity.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.bean.DeviceInfoBean;
import com.crrepa.ble.conn.listener.CRPPhoneOperationListener;
import com.crrepa.ble.conn.type.CRPBleMessageType;
import com.crrepa.ble.conn.type.CRPPhoneOperationType;

import java.lang.reflect.Method;

import static com.crrepa.ble.conn.type.CRPPhoneOperationType.REJECT_INCOMING;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/9/22 15:23
 * 修改人：Chen
 * 修改时间：2019/9/22 15:23
 */
public class PhoneListenService extends Service {
    private static final String tag = "PhoneListenService";

    // 电话管理者对象
    private TelephonyManager mTelephonyManager;
    // 电话状态监听者
    private MyPhoneStateListener myPhoneStateListener;
    // 动态监听去电的广播接收器
//    private InnerOutCallReceiver mInnerOutCallReceiver;

    @Override
    public void onCreate() {
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        myPhoneStateListener = new MyPhoneStateListener();
        mTelephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        // 动态注册广播接收器监听去电信息
//        mInnerOutCallReceiver = new InnerOutCallReceiver();
        // 手机拨打电话时会发送：android.intent.action.NEW_OUTGOING_CALL的广播
//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
//        registerReceiver(mInnerOutCallReceiver, intentFilter);
        super.onCreate();
    }

    /**
     * 动态注册广播接收器监听去电信息
     */
    class InnerOutCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取播出的去电号码
            String outPhone = getResultData();
            Log.i(tag, "outPhone:" + outPhone);
        }
    }

    /**
     * 自定义内部类对来电的电话状态进行监听
     */
    class MyPhoneStateListener extends PhoneStateListener {
        // 重写电话状态改变时触发的方法
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            DeviceInfoBean mDeviceInfoBean = SharedPreferenceHelper.getDeviceInfo(AppContext.getContext());//获取最新的设备信息
            if (mDeviceInfoBean!=null&&mDeviceInfoBean.getIs_open_phone()==1&AppContext.mBleDevice!=null&&AppContext.mBleDevice.isConnected()){
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.i(tag, "响铃:" + incomingNumber);
                        AppContext.mBleConnection.sendMessage(incomingNumber, CRPBleMessageType.MESSAGE_PHONE,0);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.i(tag, "接听");
                        AppContext.mBleConnection.sendCall0ffHook();
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.i(tag, "挂断");
                        AppContext.mBleConnection.sendCall0ffHook();
                        break;
                }
                AppContext.mBleConnection.setPhoneOperationListener(new CRPPhoneOperationListener() {
                    @Override
                    public void onOperationChange(int i) {
                        if (i== CRPPhoneOperationType.REJECT_INCOMING){//手环挂断电话
                            endcall();
                        }
                    }
                });

            }
        }
    }

    /**
     * 通过反射的方式挂断电话
     */
    public void endcall() {
        try {
            //获取到ServiceManager
            Class<?> clazz = Class.forName("android.os.ServiceManager");
            //获取到ServiceManager里面的方法
            Method method = clazz.getDeclaredMethod("getService", String.class);
            //通过反射的方法调用方法
            IBinder iBinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
            ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
            iTelephony.endCall();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // 取消来电的电话状态监听服务
        if (mTelephonyManager != null && myPhoneStateListener != null) {
            mTelephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        // 取消去电的广播监听
//        if (mInnerOutCallReceiver != null) {
//            unregisterReceiver(mInnerOutCallReceiver);
//        }
        super.onDestroy();
    }
}

