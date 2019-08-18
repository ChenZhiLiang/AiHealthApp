package com.app.aihealthapp.ui.activity.home;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.bgabanner.BGABanner;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.activity.mine.LoginActivity;
import com.app.aihealthapp.ui.adapter.HealthManageAdapter;
import com.app.aihealthapp.ui.adapter.HealthShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAreaAdapter;
import com.app.aihealthapp.ui.bean.AdvListBean;
import com.app.aihealthapp.ui.bean.ArticleCateListBean;
import com.app.aihealthapp.ui.bean.DeviceInfoBean;
import com.app.aihealthapp.ui.bean.HomeBean;
import com.app.aihealthapp.ui.mvvm.view.HomeView;
import com.app.aihealthapp.ui.mvvm.viewmode.HomeViewMode;
import com.app.aihealthapp.view.MyGridView;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.bean.CRPStepInfo;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;
import com.crrepa.ble.conn.listener.CRPStepChangeListener;

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
public class HomeFragment extends BaseFragment implements HomeView,BGABanner.Adapter<ImageView,AdvListBean >,
        BGABanner.Delegate<ImageView, AdvListBean>,CRPStepChangeListener{

    @BindView(R.id.banner_home_adv)
    BGABanner banner_home_adv;
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
    TextView tv_step;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_calorie)
    TextView tv_calorie;

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
    @BindView(R.id.rt_health_data)
    RelativeLayout rt_health_data;

    @BindView(R.id.btn_ask)
    Button btn_ask;
    @BindView(R.id.btn_inquiry)
    Button btn_inquiry;
    @BindView(R.id.grid_health_manage)
    MyGridView grid_health_manage;
    @BindView(R.id.grid_shop_manage)
    MyGridView grid_shop_manage;
    @BindView(R.id.recycler_shop_area)
    RecyclerView recycler_shop_area;
    @BindView(R.id.gridview_shop)
    MyGridView gridview_shop;


    private CRPBleClient mCRPBleClient;
    private CRPBleDevice mBleDevice;
    private CRPBleConnection mBleConnection;

    private HomeViewMode mHomeViewMode;
    private HealthManageAdapter mHealthManageAdapter;
    private HealthShopAdapter mHealthShopAdapter;
    private HomeShopAreaAdapter mHomeShopAreaAdapter;
    private HomeShopAdapter mHomeShopAdapter;

    private DeviceInfoBean mDeviceInfoBean;
    private boolean ClickStep = false;//判断是否点击运动记步

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
        banner_home_adv.setAdapter(this);
        banner_home_adv.setDelegate(this);
        mHomeViewMode = new HomeViewMode(this);
        configRecycleView(recycler_shop_area,new LinearLayoutManager(getContext()));
        mCRPBleClient = AppContext.getBleClient(AppContext.getContext());
    }



    @Override
    public void loadingData() {
        mHomeViewMode.getHomeDatas(true);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_add_wristband,R.id.ll_syncStep,R.id.ll_blood_pressure,R.id.ll_heart_rate,R.id.ll_blood_oxygen,R.id.btn_ask,R.id.btn_inquiry})
    public void onClick(View v){
        if (v==btn_add_wristband){
            if (isLogin()){
                if (!mCRPBleClient.isBluetoothEnable()){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                    return;
                }else {
                    startActivity(new Intent(getContext(),BindDeviceActivity.class));
                }
            }else {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }else if (v==btn_ask){
            startActivity(new Intent(getActivity(),HealthAskActivity.class));
        }else if (v==btn_inquiry){
            startActivity(new Intent(getActivity(),DoctorListActivity.class));
        }else if (v==ll_syncStep){
            ClickStep = true;
            //运动记步
            mBleConnection.syncStep();
        }else if (v==ll_blood_pressure){
            startActivity(new Intent(mActivity,MeasureActivity.class).putExtra("Device_no",mDeviceInfoBean.getDevice_no()).putExtra("type",0));
        }else if (v==ll_heart_rate){
            //心率测量
            startActivity(new Intent(mActivity,MeasureActivity.class).putExtra("Device_no",mDeviceInfoBean.getDevice_no()).putExtra("type",1));
        }else if (v==ll_blood_oxygen){
            //血压测量
            startActivity(new Intent(mActivity,MeasureActivity.class).putExtra("Device_no",mDeviceInfoBean.getDevice_no()).putExtra("type",2));
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
            mHomeViewMode.getHomeDatas(false);
        }else if (event.getCode()== EventCode.Code.LOGIN_SUCCESS){
            mHomeViewMode.getHomeDatas(false);
        }else if (event.getCode()== EventCode.Code.MEASURE_SUCCESS){
            mHomeViewMode.getHomeDatas(false);
        }else if (event.getCode()== EventCode.Code.LOGOUT){
            mHomeViewMode.getHomeDatas(false);

        }
    }

    @Override
    public void onStepChange(final CRPStepInfo crpStepInfo) {
        //运动
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeViewMode.runSteps(crpStepInfo.getSteps(),crpStepInfo.getDistance(),crpStepInfo.getCalories(),ClickStep);
                tv_step.setText(crpStepInfo.getSteps()+"");
                tv_distance.setText(crpStepInfo.getDistance()+"");
                tv_calorie.setText(crpStepInfo.getCalories()+"");
            }
        });
    }

    @Override
    public void onPastStepChange(int i, CRPStepInfo crpStepInfo) {
        Log.d(TAG, "onPastStepChange: " + crpStepInfo.getSteps());
    }

    @Override
    public void HomeDatasResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){

            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            final HomeBean  homeBean = GsonHelper.GsonToBean(data,HomeBean.class);
            if (homeBean.getIs_bind_bracelet()==0){
                rt_bind_device.setVisibility(View.VISIBLE);
                ll_device_info.setVisibility(View.GONE);
            }else {
                rt_bind_device.setVisibility(View.GONE);
                ll_device_info.setVisibility(View.VISIBLE);

                tv_step.setText(homeBean.getRun_steps().getSteps());
                tv_distance.setText(homeBean.getRun_steps().getDistance());
                tv_calorie.setText(homeBean.getRun_steps().getCalories());

                if (TextUtils.isEmpty(homeBean.getHealth_data().getBlood_pressure())){
                    tv_blood_pressure.setText("0/0");
                }else {
                    tv_blood_pressure.setText(homeBean.getHealth_data().getBlood_pressure());
                }
                if (TextUtils.isEmpty(homeBean.getHealth_data().getBlood_oxygen())){
                    tv_blood_oxygen.setText("0");
                }else {
                    tv_blood_oxygen.setText(homeBean.getHealth_data().getBlood_oxygen()+"%");
                }
                if (TextUtils.isEmpty(homeBean.getHealth_data().getHeart_rate())){
                    tv_heart_rate.setText("0");
                }else {
                    tv_heart_rate.setText(homeBean.getHealth_data().getHeart_rate());
                }
                if (homeBean.getHealth_report()==0){
                    rt_health_data.setVisibility(View.GONE);
                }else {
                    rt_health_data.setVisibility(View.VISIBLE);
                }

                mHomeViewMode.getMineDeviceInfo();
            }

            banner_home_adv.setData(homeBean.getAdv_list(), null);
            //健康管理gridview
            mHealthManageAdapter = new HealthManageAdapter(mActivity,homeBean.getArticle_cate_list());
            grid_health_manage.setAdapter(mHealthManageAdapter);
            grid_health_manage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ArticleCateListBean mArticleCateListBean = homeBean.getArticle_cate_list().get(position);
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.HeadLine+mArticleCateListBean.getId()));
                }
            });
            // 健康商圈gridview
            mHealthShopAdapter = new HealthShopAdapter(mActivity,homeBean.getGoods_cate_list());
            grid_shop_manage.setAdapter(mHealthShopAdapter);

            //健康商圈列表
            mHomeShopAreaAdapter = new HomeShopAreaAdapter(mActivity,homeBean.getShop_list());
            recycler_shop_area.setAdapter(mHomeShopAreaAdapter);

            //健康商城
            mHomeShopAdapter = new HomeShopAdapter(getActivity(),homeBean.getGoods_list());
            gridview_shop.setAdapter(mHomeShopAdapter);
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void MineDeviceInfo(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            if (TextUtils.isEmpty(data)){
                rt_bind_device.setVisibility(View.VISIBLE);
            }else {
                rt_bind_device.setVisibility(View.GONE);
                mDeviceInfoBean = GsonHelper.GsonToBean(data,DeviceInfoBean.class);
                mBleDevice = mCRPBleClient.getBleDevice(mDeviceInfoBean.getDevice_no());
                mBleConnection = mBleDevice.connect();
                mBleConnection.setConnectionStateListener(new CRPBleConnectionStateListener() {
                    @Override
                    public void onConnectionStateChange(int i) {
                        if (i==CRPBleConnectionStateListener.STATE_CONNECTED){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBleConnection.setStepChangeListener(HomeFragment.this);
                                }
                            });
                        }
                    }
                });
            }

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void runStepsResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        ClickStep = false;
        if (ret==0){
            Log.i("runSteps","上传步数成功");
        }else {
            Log.i("runSteps","上传步数失败");
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

        ToastyHelper.toastyNormal(mActivity,err);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, AdvListBean model, int position) {
        showLoadFailMsg("点击");

    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, AdvListBean model, int position) {
        GlideHelper.loadImageView(mActivity, model.getPic(), itemView);

    }
}
