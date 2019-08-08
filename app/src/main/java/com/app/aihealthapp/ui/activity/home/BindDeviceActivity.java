package com.app.aihealthapp.ui.activity.home;

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
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.uitrarefresh.UTRefreshLayout;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrDefaultHandler;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrFrameLayout;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.adapter.DeviceListAdapter;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;
import com.crrepa.ble.scan.bean.CRPScanDevice;
import com.crrepa.ble.scan.callback.CRPScanCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：添加绑定设备
 * @Author：Chen
 * @Date：2019/7/27 17:26
 * 修改人：Chen
 * 修改时间：2019/7/27 17:26
 */
public class BindDeviceActivity extends BaseActivity implements CRPScanCallback{

    @BindView(R.id.refresh_layout)
    UTRefreshLayout refresh_layout;
    @BindView(R.id.recycler_device)
    RecyclerView recycler_device;

    @BindView(R.id.tv_search)
    TextView tv_search;
    private DeviceListAdapter mDeviceListAdapter;
    private List<CRPBleDevice> devicelList  = new ArrayList<>();

    private CRPBleClient mCRPBleClient;
    private CRPBleConnection mBleConnection;


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
                final CRPBleDevice bleClient = (CRPBleDevice)data;
                mBleConnection = bleClient.connect();
                mBleConnection.setConnectionStateListener(new CRPBleConnectionStateListener() {
                    @Override
                    public void onConnectionStateChange(int newState) {
                        switch (newState) {
                            case CRPBleConnectionStateListener.STATE_CONNECTED://连接成功
                                mBleConnection.syncTime();
                                mBleConnection.findDevice();
                                SharedPreferenceHelper.setMacAddress(AppContext.getContext(),bleClient.getMacAddress());//连接成功 保存mac address
                                EventBusHelper.sendEvent(new Event(EventCode.Code.CONNECTED_SUCCESS,bleClient.getMacAddress()));
                                hud.dismiss();
                                finish();
                                break;
                            case CRPBleConnectionStateListener.STATE_CONNECTING://正在连接
                                hud.show("正在连接");
                                break;
                            case CRPBleConnectionStateListener.STATE_DISCONNECTED://连接断开
                                break;
                            case CRPBleConnectionStateListener.STATE_DISCONNECTING://正在连接断开
                                break;
                        }
                    }
                });

            }
        });
        mCRPBleClient = AppContext.getBleClient(AppContext.getContext());

    }

    @Override
    public void initData() {
//        if (!isRefresh){
//            hud.show("正在搜索设备.....");
//        }
        tv_search.setText("搜索中...");
        mCRPBleClient.scanDevice(this,10000);

    }

    @Override
    public void onScanning(CRPScanDevice crpScanDevice) {

        CRPBleDevice bleDevice = mCRPBleClient.getBleDevice(crpScanDevice.getDevice().getAddress());
        if (!TextUtils.isEmpty(bleDevice.getName())){
            devicelList.add(bleDevice);
            mDeviceListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onScanComplete(List<CRPScanDevice> list) {
        tv_search.setText("搜索完成");
        refresh_layout.refreshComplete();
//        hud.dismiss();
    }
}
