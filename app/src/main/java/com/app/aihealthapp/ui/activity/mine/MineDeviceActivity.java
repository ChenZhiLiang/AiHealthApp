package com.app.aihealthapp.ui.activity.mine;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.CircleDialogHelper;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.PermissionHelper;
import com.app.aihealthapp.core.helper.PreferenceHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.permission.Permission;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.activity.home.BindDeviceActivity;
import com.app.aihealthapp.ui.bean.DeviceInfoBean;
import com.app.aihealthapp.ui.mvvm.view.MineDeviceView;
import com.app.aihealthapp.ui.mvvm.viewmode.MineDeviceViewMode;
import com.app.aihealthapp.util.utils;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.listener.CRPCameraOperationListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.lang.reflect.Method;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：我的健康手环
 * @Author：Chen
 * @Date：2019/7/26 21:53
 * 修改人：Chen
 * 修改时间：2019/7/26 21:53
 */
public class MineDeviceActivity extends BaseActivity implements MineDeviceView{

//    @BindView(R.id.rt_bind_device)
//    RelativeLayout rt_bind_device;
    @BindView(R.id.tv_device_name)
    TextView tv_device_name;
    @BindView(R.id.btn_bind)
    Button btn_bind;
    @BindView(R.id.check_open_phone)
    ImageView check_open_phone;
    @BindView(R.id.check_open_sms)
    ImageView check_open_sms;
    @BindView(R.id.check_open_qq)
    ImageView check_open_qq;
    @BindView(R.id.check_open_wx)
    ImageView check_open_wx;
    @BindView(R.id.rt_open_photo)
    RelativeLayout rt_open_photo;
//    @BindView(R.id.check_open_photo)
//    ImageView check_open_photo;
    private MineDeviceViewMode mMineDeviceViewMode;
    private int id;

    private CRPBleClient mCRPBleClient;
//    private CRPBleDevice mBleDevice;
//    private CRPBleConnection mBleConnection;

    private DeviceInfoBean mDeviceInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_device;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("我的健康手环");
    }

    @Override
    public void initView() {
        mMineDeviceViewMode = new MineDeviceViewMode(this);
        mCRPBleClient = AppContext.getBleClient();

        /*监听广播 消息*/
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("action.bind_device_success");
        registerReceiver(mNotificationReceiver, intentFilter);

    }
    private BroadcastReceiver mNotificationReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.bind_device_success")){
                mMineDeviceViewMode.getMineDeviceInfo(false);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotificationReceiver);

    }

    @Override
    public void initData() {
        mMineDeviceViewMode.getMineDeviceInfo(true);
    }

    @OnClick({R.id.btn_bind,R.id.check_open_phone,R.id.check_open_sms,R.id.check_open_qq,R.id.check_open_wx,R.id.rt_open_photo})
    public void onClick(View v){

        if (v==btn_bind){
            if (btn_bind.getText().equals("绑定设备")){
                if (!mCRPBleClient.isBluetoothEnable()){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                    return;
                }else {
                    if (utils.isLocServiceEnable(this)){
                        startActivity(new Intent(this, BindDeviceActivity.class));
                    }else {
                        CircleDialogHelper.ShowDialog(this, "温馨提示", "扫描附近蓝牙设备需要开启定位服务", "开启", "取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =  new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        },null);
                    }
                }
            }else {
                CircleDialogHelper.ShowDialog(this,"温馨提示","确定解除绑定该设备(Qs-05)?","确定","取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        mBleDevice = mCRPBleClient.getBleDevice(mDeviceInfoBean.getDevice_no());
                        if (AppContext.mBleDevice.isConnected()){
                            AppContext.mBleDevice.disconnect();
                        }
                        // 获取本地蓝牙适配器
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if(mBluetoothAdapter!=null){
                            // 获取已经配对的设备
                            Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
                            for(BluetoothDevice device : bondedDevices ){
                                //取消设备的配对
                                unpairDevice(device);
                            }
                        }

                        mMineDeviceViewMode.UnBind(id);

                    }
                }, null);
            }
        }else {
            if (btn_bind.getText().equals("绑定设备")){
                ToastyHelper.toastyNormal(this,"请先绑定设备");
            }else {
                switch (v.getId()){
                    case R.id.check_open_phone:
                        if (mDeviceInfoBean.getIs_open_phone()==0){
                            if (!utils.isEnabledNotification(AppContext.getContext())){
                                isShowNotification();
                            }else {
                                mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),1,-1,-1,
                                        -1,-1);
                            }

                        }else {
                            mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),0,-1,-1,
                                    -1,-1);
                        }
                        break;
                    case R.id.check_open_sms:
                        if (mDeviceInfoBean.getIs_open_sms()==0){
                            if (!utils.isEnabledNotification(AppContext.getContext())){
                                isShowNotification();
                            }else {
                                mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,1,-1,
                                        -1,-1);
                            }

                        }else {
                            mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,0,-1,
                                    -1,-1);
                        }
                        break;
                    case R.id.check_open_qq:

                        if (mDeviceInfoBean.getIs_open_qq()==0){
                            if (!utils.isEnabledNotification(AppContext.getContext())){
                                isShowNotification();
                            }else {
                                mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,-1,
                                        1,-1);
                            }

                        }else {
                            mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,-1,
                                    0,-1);
                        }
                        break;
                    case R.id.check_open_wx:
                        if (mDeviceInfoBean.getIs_open_wechat()==0){
                            if (!utils.isEnabledNotification(AppContext.getContext())){
                                isShowNotification();
                            }else {
                                mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,1,
                                        -1,-1);
                            }

                        }else {
                            mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,0,
                                    -1,-1);
                        }
                        break;
                    case R.id.rt_open_photo:

                        if (new PermissionHelper().RequestPermisson(this, Permission.CAMERA)){
                            startActivity(new Intent(this,TakePhotoActivity.class));
                        }
                        break;
                }
            }
        }


    }

    //反射来调用BluetoothDevice.removeBond取消设备的配对
    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.e("unpairDevice", e.getMessage());
        }
    }
    @Override
    public void MineDeviceInfo(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            if (data.equals("{}")){
                btn_bind.setText("绑定设备");
                tv_device_name.setText("您还未绑定设备");
                check_open_phone.setBackgroundResource(R.mipmap.car_off);
                check_open_sms.setBackgroundResource(R.mipmap.car_off);
                check_open_qq.setBackgroundResource(R.mipmap.car_off);
                check_open_wx.setBackgroundResource(R.mipmap.car_off);
//                check_open_photo.setBackgroundResource(R.mipmap.car_off);

            }else {
                mDeviceInfoBean = GsonHelper.GsonToBean(data,DeviceInfoBean.class);
                /*保存设备信息在本地*/
                SharedPreferenceHelper.setDeviceInfo(this,mDeviceInfoBean);
//                AppContext.mBleDevice = mCRPBleClient.getBleDevice(mDeviceInfoBean.getDevice_no());
//                mBleConnection = mBleDevice.connect();
                if (TextUtils.isEmpty(mDeviceInfoBean.getDevice_no())){
                    btn_bind.setText("绑定设备");
                    tv_device_name.setText("您还未绑定设备");
                    check_open_phone.setBackgroundResource(R.mipmap.car_off);
                    check_open_sms.setBackgroundResource(R.mipmap.car_off);
                    check_open_qq.setBackgroundResource(R.mipmap.car_off);
                    check_open_wx.setBackgroundResource(R.mipmap.car_off);
                    ToastyHelper.toastyNormal(MineDeviceActivity.this,"已绑定的设备不存在，请重新绑定！");
                }else {
                    tv_device_name.setText("已绑定设备(Qs-05)");
                    btn_bind.setText("解除绑定");
                    id = mDeviceInfoBean.getId();
                    if (mDeviceInfoBean.getIs_open_phone()==0){
                        check_open_phone.setBackgroundResource(R.mipmap.car_off);
                    }else {
                        check_open_phone.setBackgroundResource(R.mipmap.car_on);
                    }
                    if (mDeviceInfoBean.getIs_open_sms()==0){
                        check_open_sms.setBackgroundResource(R.mipmap.car_off);
                    }else {
                        check_open_sms.setBackgroundResource(R.mipmap.car_on);
                    }
                    if (mDeviceInfoBean.getIs_open_qq()==0){
                        check_open_qq.setBackgroundResource(R.mipmap.car_off);

                    }else {
                        check_open_qq.setBackgroundResource(R.mipmap.car_on);
                    }
                    if (mDeviceInfoBean.getIs_open_wechat()==0){
                        check_open_wx.setBackgroundResource(R.mipmap.car_off);

                    }else {
                        check_open_wx.setBackgroundResource(R.mipmap.car_on);
                    }
                }


//                if (mDeviceInfoBean.getIs_open_photo()==0){
//                    check_open_photo.setBackgroundResource(R.mipmap.car_off);
//
//                }else {
//                    check_open_photo.setBackgroundResource(R.mipmap.car_on);
//                }
            }

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }


    private void isShowNotification(){
        CircleDialogHelper.ShowDialog(this,"温馨提示","需要开启通知读取权限才能正常使用消息推送","开启","取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            }
        }, null);
    }

    @Override
    public void UpdateDeviceResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            utils.toggleNotificationListenerService(this);
            //刷新
            mMineDeviceViewMode.getMineDeviceInfo(false);

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));

        }
    }

    @Override
    public void UnBindResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            EventBusHelper.sendEvent(new Event(EventCode.Code.UN_BIND_DEVICE));
//            //清除本地设备信息
            SharedPreferenceHelper.clearDevice(this);
            mMineDeviceViewMode.getMineDeviceInfo(false);
            showLoadFailMsg("解绑成功");

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));

        }
    }


    @Override
    public void showProgress() {

        hud.show();
    }

    @Override
    public void hideProgress() {

        hud.dismiss();
    }

    @Override
    public void showLoadFailMsg(String err) {
        ToastyHelper.toastyNormal(this,err);
    }

}
