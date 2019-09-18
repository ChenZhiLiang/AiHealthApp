package com.app.aihealthapp.ui.activity.mine;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.CircleDialogHelper;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.activity.home.BindDeviceActivity;
import com.app.aihealthapp.ui.bean.DeviceInfoBean;
import com.app.aihealthapp.ui.mvvm.view.MineDeviceView;
import com.app.aihealthapp.ui.mvvm.viewmode.MineDeviceViewMode;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;

import java.lang.reflect.Method;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：我的健康设备
 * @Author：Chen
 * @Date：2019/7/26 21:53
 * 修改人：Chen
 * 修改时间：2019/7/26 21:53
 */
public class MineDeviceActivity extends BaseActivity implements MineDeviceView, OnCheckedChangeListener {

//    @BindView(R.id.rt_bind_device)
//    RelativeLayout rt_bind_device;
    @BindView(R.id.tv_device_name)
    TextView tv_device_name;
    @BindView(R.id.btn_bind)
    Button btn_bind;
    @BindView(R.id.check_open_phone)
    CheckBox check_open_phone;
    @BindView(R.id.check_open_sms)
    CheckBox check_open_sms;
    @BindView(R.id.check_open_qq)
    CheckBox check_open_qq;
    @BindView(R.id.check_open_wx)
    CheckBox check_open_wx;
    @BindView(R.id.check_open_photo)
    CheckBox check_open_photo;
//    @BindView(R.id.btn_unbind)
//    Button btn_unbind;

    private MineDeviceViewMode mMineDeviceViewMode;
    private int id;

    private CRPBleClient mCRPBleClient;
    private CRPBleDevice mBleDevice;
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
        setTitle("我的健康设备");
    }

    @Override
    public void initView() {
        mMineDeviceViewMode = new MineDeviceViewMode(this);
        mCRPBleClient = AppContext.getBleClient();

        check_open_phone.setOnCheckedChangeListener(this);
        check_open_sms.setOnCheckedChangeListener(this);
        check_open_qq.setOnCheckedChangeListener(this);
        check_open_wx.setOnCheckedChangeListener(this);
        check_open_photo.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        mMineDeviceViewMode.getMineDeviceInfo(true);
    }

    @OnClick({R.id.btn_bind})
    public void onClick(View v){
        if (v==btn_bind){
            if (btn_bind.getText().equals("绑定设备")){
                if (!mCRPBleClient.isBluetoothEnable()){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                    return;
                }else {
                    startActivity(new Intent(this, BindDeviceActivity.class));
                }
            }else {
                CircleDialogHelper.ShowDialog(this,"温馨提示","确定解除绑定该设备(Qs-05)?","确定","取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBleDevice = mCRPBleClient.getBleDevice(mDeviceInfoBean.getDevice_no());
                        if (mBleDevice.isConnected()){
                            mBleDevice.disconnect();
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

//                rt_bind_device.setVisibility(View.VISIBLE);
//                btn_unbind.setVisibility(View.GONE);
            }else {
                 mDeviceInfoBean = GsonHelper.GsonToBean(data,DeviceInfoBean.class);
                tv_device_name.setText("已绑定设备(Qs-05)");
                btn_bind.setText("解除绑定");
//                rt_bind_device.setVisibility(View.GONE);
//                btn_unbind.setVisibility(View.VISIBLE);

                id = mDeviceInfoBean.getId();

                if (mDeviceInfoBean.getIs_open_phone()==0){
                    check_open_phone.setChecked(false);
                }else {
                    check_open_phone.setChecked(true);
                }

                if (mDeviceInfoBean.getIs_open_sms()==0){
                    check_open_sms.setChecked(false);
                }else {
                    check_open_sms.setChecked(true);
                }

                if (mDeviceInfoBean.getIs_open_qq()==0){
                    check_open_qq.setChecked(false);
                }else {
                    check_open_qq.setChecked(true);
                }
                if (mDeviceInfoBean.getIs_open_wechat()==0){
                    check_open_wx.setChecked(false);
                }else {
                    check_open_wx.setChecked(true);
                }
                if (mDeviceInfoBean.getIs_open_photo()==0){
                    check_open_photo.setChecked(false);
                }else {
                    check_open_photo.setChecked(true);
                }
            }

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (event.getCode()== EventCode.Code.BIND_DEVICE){
            mMineDeviceViewMode.getMineDeviceInfo(false);
        }
    }
    @Override
    public void UpdateDeviceResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
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
            mMineDeviceViewMode.getMineDeviceInfo(false);
            showLoadFailMsg("解绑成功");

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));

        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.check_open_phone:
                if (isChecked){
                    if (mDeviceInfoBean.getIs_open_phone()==0){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),1,-1,-1,
                                -1,-1);
                    }

                }else {
                    if (mDeviceInfoBean.getIs_open_phone()==1){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),0,-1,-1,
                                -1,-1);
                    }

                }
                break;
            case R.id.check_open_sms:
                if (isChecked){
                    if (mDeviceInfoBean.getIs_open_sms()==0){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,1,-1,
                                -1,-1);
                    }

                }else {
                    if (mDeviceInfoBean.getIs_open_sms()==1){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,0,-1,
                                -1,-1);
                    }

                }

                break;
            case R.id.check_open_qq:

                if (isChecked){
                    if (mDeviceInfoBean.getIs_open_qq()==0){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,-1,
                                1,-1);
                    }

                }else {
                    if (mDeviceInfoBean.getIs_open_qq()==1){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,-1,
                                0,-1);
                    }

                }
                break;
            case R.id.check_open_wx:
                if (isChecked){
                    if (mDeviceInfoBean.getIs_open_wechat()==0){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,1,
                                -1,-1);
                    }

                }else {
                    if (mDeviceInfoBean.getIs_open_wechat()==1){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,0,
                                -1,-1);
                    }

                }
                break;
            case R.id.check_open_photo:
                if (isChecked){
                    if (mDeviceInfoBean.getIs_open_photo()==0){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,-1,
                                -1,1);
                    }

                }else {
                    if (mDeviceInfoBean.getIs_open_photo()==1){
                        mMineDeviceViewMode.UpdateDevice(mDeviceInfoBean.getId(),-1,-1,-1,
                                -1,0);
                    }
                }
                break;
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
