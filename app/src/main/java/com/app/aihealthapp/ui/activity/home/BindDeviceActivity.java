package com.app.aihealthapp.ui.activity.home;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.uitrarefresh.UTRefreshLayout;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrDefaultHandler;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrFrameLayout;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.adapter.DeviceListAdapter;
import com.app.aihealthapp.ui.mvvm.view.BindDeviceView;
import com.app.aihealthapp.ui.mvvm.viewmode.BindDeviceViewMode;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;
import com.crrepa.ble.scan.bean.CRPScanDevice;
import com.crrepa.ble.scan.callback.CRPScanCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：添加绑定设备
 * @Author：Chen
 * @Date：2019/7/27 17:26
 * 修改人：Chen
 * 修改时间：2019/7/27 17:26
 */
public class BindDeviceActivity extends BaseActivity implements CRPScanCallback, BindDeviceView {

    @BindView(R.id.refresh_layout)
    UTRefreshLayout refresh_layout;
    @BindView(R.id.recycler_device)
    RecyclerView recycler_device;

    @BindView(R.id.tv_search)
    TextView tv_search;
    private DeviceListAdapter mDeviceListAdapter;
    private List<CRPBleDevice> devicelList  = new ArrayList<>();

    private CRPBleClient mCRPBleClient;
//    private CRPBleConnection mBleConnection;


    private BindDeviceViewMode mBindDeviceViewMode;
    boolean isRefresh = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_device;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("添加设备");
    }

    @Override
    public void initView() {
        mCRPBleClient = AppContext.getBleClient();
        mBindDeviceViewMode = new BindDeviceViewMode(this);
        refresh_layout.disableWhenHorizontalMove(true);
        refresh_layout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                if (devicelList.size()>0){
                    devicelList.clear();
                }
                tv_search.setText("搜索中...");
                mCRPBleClient.scanDevice(BindDeviceActivity.this,10000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                ViewGroup viewGroup = (ViewGroup) content;
                if (viewGroup instanceof ScrollView) {
                    return viewGroup.getScrollY() == 0;
                } else {
                    return true;
                }
            }
        });

        recycler_device.setLayoutManager(new LinearLayoutManager(this));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycler_device.setHasFixedSize(true);
        recycler_device.setItemAnimator(new DefaultItemAnimator());

        mDeviceListAdapter = new DeviceListAdapter(devicelList);
        recycler_device.setAdapter(mDeviceListAdapter);
        mDeviceListAdapter.setOnItemClickListener(new BaseXRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, final Object data, int position) {
//                final CRPBleDevice bleClient = (CRPBleDevice)data;
                AppContext.mBleDevice = (CRPBleDevice)data;
                mCRPBleClient.cancelScan();
                AppContext.mBleConnection = AppContext.mBleDevice.connect();
                AppContext.mBleConnection.setConnectionStateListener(new CRPBleConnectionStateListener() {
                    @Override
                    public void onConnectionStateChange(int newState) {
                        switch (newState) {
                            case CRPBleConnectionStateListener.STATE_CONNECTED://连接成功
                                AppContext.mBleConnection.syncTime();
                                AppContext.mBleConnection.findDevice();
                                mBindDeviceViewMode.BindDevice(AppContext.mBleDevice.getMacAddress());
                                mCRPBleClient.cancelScan();
                                break;
                            case CRPBleConnectionStateListener.STATE_CONNECTING://正在连接
                                hud.show("正在连接...");
                                break;
                            case CRPBleConnectionStateListener.STATE_DISCONNECTED://连接断开
                                hud.dismiss();
                                showLoadFailMsg("连接失败");
                                break;
                            case CRPBleConnectionStateListener.STATE_DISCONNECTING://正在连接断开
                                hud.dismiss();
                                showLoadFailMsg("连接失败");
                                break;
                        }
                    }
                });

            }
        });

    }

    @Override
    public void initData() {
        tv_search.setText("搜索中...");
        // 获取本地蓝牙适配器
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter!=null){
            // 获取已经配对的设备
            Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
            for(BluetoothDevice device : bondedDevices ){
                unpairDevice(device);
            }
        }
        mCRPBleClient.scanDevice(this,10000);

    }
    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.e("unpairDevice", e.getMessage());
        }
    }
    @Override
    public void onScanning(CRPScanDevice crpScanDevice) {

        CRPBleDevice bleDevice = mCRPBleClient.getBleDevice(crpScanDevice.getDevice().getAddress());
        if (!TextUtils.isEmpty(bleDevice.getName())&&bleDevice.getName().equals("Qs-05")){
            devicelList.add(bleDevice);
            mDeviceListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onScanComplete(List<CRPScanDevice> list) {

        tv_search.setText("搜索完成");
        refresh_layout.refreshComplete();
    }

    @Override
    public void BindDeviceReuslt(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            showLoadFailMsg("绑定成功");
            //发送广播通知 已经绑定成功设备
            Intent intent =new Intent();
            intent.setAction("action.bind_device_success");
            sendBroadcast(intent);
//            EventBusHelper.sendEvent(new Event(EventCode.Code.BIND_DEVICE));
            finish();
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
