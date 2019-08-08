package com.app.aihealthapp.ui.activity.home;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.adapter.HomeShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAreaAdapter;
import com.app.aihealthapp.ui.bean.HomeShopAreaBean;
import com.app.aihealthapp.ui.bean.HomeShopBean;
import com.app.aihealthapp.view.MyGridView;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.bean.CRPHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPMovementHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPStepInfo;
import com.crrepa.ble.conn.listener.CRPBloodOxygenChangeListener;
import com.crrepa.ble.conn.listener.CRPBloodPressureChangeListener;
import com.crrepa.ble.conn.listener.CRPHeartRateChangeListener;
import com.crrepa.ble.conn.listener.CRPStepChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * @Name：aihealthapp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/22 22:57
 * 修改人：Chen
 * 修改时间：2019/7/22 22:57
 */
public class HomeFragment extends BaseFragment implements CRPStepChangeListener,CRPBloodPressureChangeListener , CRPBloodOxygenChangeListener {

    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.rt_bind_device)
    RelativeLayout rt_bind_device;

    @BindView(R.id.ll_device_info)
    LinearLayout ll_device_info;
    @BindView(R.id.btn_add_wristband)
    Button btn_add_wristband;

    @BindView(R.id.ll_syncStep)
    LinearLayout ll_syncStep;
    @BindView(R.id.tv_step)
    TextView tvStep;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_calorie)
    TextView tvCalorie;

    @BindView(R.id.ll_blood_pressure)
    LinearLayout ll_blood_pressure;
    @BindView(R.id.tv_blood_pressure)
    TextView tv_blood_pressure;

    @BindView(R.id.ll_heart_rate)
    LinearLayout ll_heart_rate;
    @BindView(R.id.tv_heart_rate)
    TextView tv_heart_rate;

    @BindView(R.id.ll_blood_oxygen)
    LinearLayout ll_blood_oxygen;
    @BindView(R.id.tv_blood_oxygen)
    TextView tv_blood_oxygen;

    @BindView(R.id.btn_ask)
    Button btn_ask;
    @BindView(R.id.recycler_shop_area)
    RecyclerView recycler_shop_area;
    @BindView(R.id.gridview_shop)
    MyGridView gridview_shop;
    List<HomeShopAreaBean> datas  = new ArrayList<>();
    List<HomeShopBean> items = new ArrayList<>();
    private HomeShopAreaAdapter mHomeShopAreaAdapter;
    private HomeShopAdapter mHomeShopAdapter;
    private CRPBleClient mCRPBleClient;
    private CRPBleDevice mBleDevice;
    private CRPBleConnection mBleConnection;

    private String mac_address;


    public static HomeFragment getInstance(String title) {
        HomeFragment hf = new HomeFragment();
        hf.mTitle = title;
        return hf;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragement_home;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        tv_title_bar.setText("首页");
        configRecycleView(recycler_shop_area,new LinearLayoutManager(getContext()));

        mHomeShopAreaAdapter = new HomeShopAreaAdapter(datas);
        recycler_shop_area.setAdapter(mHomeShopAreaAdapter);

        for (int i = 0;i<4;i++){
            HomeShopBean mHomeShopBean = new HomeShopBean();
            items.add(mHomeShopBean);
        }
        mHomeShopAdapter = new HomeShopAdapter(getActivity(),items);
        gridview_shop.setAdapter(mHomeShopAdapter);
        mCRPBleClient = AppContext.getBleClient(AppContext.getContext());
        //判断设备mac地址是否为空
        if (SharedPreferenceHelper.getMacAddress(AppContext.getContext())!=null){
            mac_address = SharedPreferenceHelper.getMacAddress(AppContext.getContext());
            mBleDevice = mCRPBleClient.getBleDevice(mac_address);
            if (mBleDevice != null && mBleDevice.isConnected()) {
                mBleConnection = mBleDevice.connect();
                rt_bind_device.setVisibility(View.GONE);
                ll_device_info.setVisibility(View.VISIBLE);
                mBleConnection.setStepChangeListener(this);
                mBleConnection.setBloodPressureChangeListener(this);
                mBleConnection.setHeartRateChangeListener(mHeartRateChangListener);
                mBleConnection.setBloodOxygenChangeListener(this);

            }else {
                rt_bind_device.setVisibility(View.VISIBLE);
                ll_device_info.setVisibility(View.GONE);
            }
        }else {
            rt_bind_device.setVisibility(View.GONE);
            ll_device_info.setVisibility(View.GONE);

        }
    }

    @Override
    public void loadingData() {

        for (int i = 0;i<2;i++){
            HomeShopAreaBean mHomeShopAreaBean = new HomeShopAreaBean();
            datas.add(mHomeShopAreaBean);
        }
        mHomeShopAreaAdapter.notifyDataSetChanged();

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_add_wristband,R.id.ll_syncStep,R.id.ll_blood_pressure,R.id.ll_heart_rate,R.id.ll_blood_oxygen,R.id.btn_ask})
    public void onClick(View v){
        if (v==btn_add_wristband){
            if (!mCRPBleClient.isBluetoothEnable()){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBtIntent);
                return;
            }else {
                startActivity(new Intent(getContext(),BindDeviceActivity.class));
            }
//            startActivity(new Intent(getActivity(), LoginActivity.class));
        }else if (v==btn_ask){
            startActivity(new Intent(getActivity(),HealthAskActivity.class));
        }else if (v==ll_syncStep){
            //运动记步
            mBleConnection.syncStep();
        }else if (v==ll_blood_pressure){

            startActivity(new Intent(mActivity,MeasureActivity.class).putExtra("title","血压测量"));
            //血压测量
//            mBleConnection.startMeasureBloodPressure();
//            hud.show("正在测量，请稍后");

        }else if (v==ll_heart_rate){
            //心率测量
            mBleConnection.startMeasureOnceHeartRate();
            hud.show("正在测量，请稍后");

        }else if (v==ll_blood_oxygen){
            //血压测量
            mBleConnection.startMeasureBloodOxygen();
            hud.show("正在测量，请稍后");
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (event.getCode()== EventCode.Code.CONNECTED_SUCCESS){
            ToastyHelper.toastyNormal(getActivity(),"绑定成功");
            String address = event.getEventMessage();
            mBleDevice = mCRPBleClient.getBleDevice(address);
            mBleConnection = mBleDevice.connect();
            if (mBleDevice != null && mBleDevice.isConnected()) {
                rt_bind_device.setVisibility(View.GONE);
                ll_device_info.setVisibility(View.VISIBLE);
                mBleConnection.setStepChangeListener(this);
                mBleConnection.setBloodPressureChangeListener(this);
                mBleConnection.setHeartRateChangeListener(mHeartRateChangListener);
                mBleConnection.setBloodOxygenChangeListener(this);

            }
        }
    }

    @Override
    public void onStepChange(final CRPStepInfo crpStepInfo) {

        //运动
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStep.setText(crpStepInfo.getSteps()+"");
                tvDistance.setText(crpStepInfo.getDistance()+"");
                tvCalorie.setText(crpStepInfo.getCalories()+"");
            }
        });

    }

    @Override
    public void onPastStepChange(int i, CRPStepInfo crpStepInfo) {
        Log.d(TAG, "onPastStepChange: " + crpStepInfo.getSteps());
    }

    @Override
    public void onBloodPressureChange(final int i, final int i1) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastyHelper.toastyNormal(getActivity(),"测量成功");
                hud.dismiss();
                //血压
                tv_blood_pressure.setText(i+"/"+i1);
            }
        });

    }

    /*心率监听*/
    CRPHeartRateChangeListener mHeartRateChangListener = new CRPHeartRateChangeListener() {
        @Override
        public void onMeasuring(int rate) {
            Log.d(TAG, "onMeasuring: " + rate);


        }

        @Override
        public void onOnceMeasureComplete(final int rate) {
            Log.d(TAG, "onOnceMeasureComplete: " + rate);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastyHelper.toastyNormal(getActivity(),"测量成功");
                    hud.dismiss();
                    //心率
                    tv_heart_rate.setText(rate+"");
                }
            });
        }

        @Override
        public void onMeasureComplete(CRPHeartRateInfo info) {
            if (info != null && info.getMeasureData() != null) {
                for (Integer integer : info.getMeasureData()) {
                    Log.d(TAG, "onMeasureComplete: " + integer);
                }
            }
        }

        @Override
        public void on24HourMeasureResult(CRPHeartRateInfo info) {
            List<Integer> data = info.getMeasureData();
            Log.d(TAG, "on24HourMeasureResult: " + data.size());
        }

        @Override
        public void onMovementMeasureResult(List<CRPMovementHeartRateInfo> list) {
            for (CRPMovementHeartRateInfo info : list) {
                if (info != null) {
                    Log.d(TAG, "onMovementMeasureResult: " + info.getStartTime());
                }
            }
        }

    };

    @Override
    public void onBloodOxygenChange(final int i) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //血氧
                tv_blood_oxygen.setText(i+"");
                hud.dismiss();
                ToastyHelper.toastyNormal(getActivity(),"测量成功");
            }
        });

    }
}
