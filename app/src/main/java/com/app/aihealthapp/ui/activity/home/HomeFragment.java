package com.app.aihealthapp.ui.activity.home;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.aihealthapp.R;
import com.app.aihealthapp.confing.AppConfig;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.bgabanner.BGABanner;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.CircleDialogHelper;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.selectlibrary.CitySelect;
import com.app.aihealthapp.core.selectlibrary.Province;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.MainActivity;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.activity.mine.LoginActivity;
import com.app.aihealthapp.ui.adapter.GridviewAreaAdapter;
import com.app.aihealthapp.ui.adapter.HealthManageAdapter;
import com.app.aihealthapp.ui.adapter.HealthShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAreaAdapter;
import com.app.aihealthapp.ui.bean.AdvListBean;
import com.app.aihealthapp.ui.bean.ArticleCateListBean;
import com.app.aihealthapp.ui.bean.CountryCityBean;
import com.app.aihealthapp.ui.bean.DeviceInfoBean;
import com.app.aihealthapp.ui.bean.GoodsCateListBean;
import com.app.aihealthapp.ui.bean.GoodsListBean;
import com.app.aihealthapp.ui.bean.HomeBean;
import com.app.aihealthapp.ui.bean.ShopListBean;
import com.app.aihealthapp.ui.bean.VersionInfoBean;
import com.app.aihealthapp.ui.mvvm.view.HomeView;
import com.app.aihealthapp.ui.mvvm.viewmode.HomeViewMode;
import com.app.aihealthapp.util.AppUpdateVersionUtils;
import com.app.aihealthapp.util.utils;
import com.app.aihealthapp.view.MyGridView;
import com.app.aihealthapp.view.MyPopWindow;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.bean.CRPStepInfo;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;
import com.crrepa.ble.conn.listener.CRPStepChangeListener;
import com.crrepa.ble.conn.type.CRPBleMessageType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;
import static com.app.aihealthapp.ui.activity.service.ComeWxMessage.CALL;
import static com.app.aihealthapp.ui.activity.service.ComeWxMessage.MMS;
import static com.app.aihealthapp.ui.activity.service.ComeWxMessage.QQ;
import static com.app.aihealthapp.ui.activity.service.ComeWxMessage.WX;

/**
 * @Name：aihealthapp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/22 22:57
 * 修改人：Chen
 * 修改时间：2019/7/22 22:57
 */
public class HomeFragment extends BaseFragment implements HomeView, BGABanner.Adapter<ImageView, AdvListBean>,
        BGABanner.Delegate<ImageView, AdvListBean>, CRPStepChangeListener {

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.ll_location)
    LinearLayout ll_location;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.banner_home_adv)
    BGABanner banner_home_adv;
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.image_news)
    ImageView image_news;
    @BindView(R.id.image_red_dot)
    ImageView image_red_dot;
    @BindView(R.id.rt_bind_device)
    RelativeLayout rt_bind_device;

    @BindView(R.id.ll_device_info)
    LinearLayout ll_device_info;
    @BindView(R.id.btn_add_wristband)
    Button btn_add_wristband;


    @BindView(R.id.ll_sleep)
    LinearLayout ll_sleep;
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

    @BindView(R.id.btn_look_report)
    Button btn_look_report;
    @BindView(R.id.btn_ask)
    Button btn_ask;
    @BindView(R.id.btn_inquiry)
    Button btn_inquiry;
    @BindView(R.id.grid_health_manage)
    MyGridView grid_health_manage;
    @BindView(R.id.grid_shop_manage)
    MyGridView grid_shop_manage;
    @BindView(R.id.btn_shop_index)
    Button btn_shop_index;
    @BindView(R.id.recycler_shop_area)
    RecyclerView recycler_shop_area;
    @BindView(R.id.gridview_shop)
    MyGridView gridview_shop;
    @BindView(R.id.btn_health_shop)
    Button btn_health_shop;


    private CRPBleClient mCRPBleClient;

    private HomeViewMode mHomeViewMode;
    private HealthManageAdapter mHealthManageAdapter;
    private HealthShopAdapter mHealthShopAdapter;
    private HomeShopAreaAdapter mHomeShopAreaAdapter;
    private HomeShopAdapter mHomeShopAdapter;

    private DeviceInfoBean mDeviceInfoBean = null;
//    private boolean ClickStep = false;//判断是否点击运动记rt_health_data步
    private HomeBean homeBean;

    //声明AMapLocationClient类对象，定位发起端
//    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
//    public AMapLocationClientOption mLocationOption = null;

    private MyPopWindow window_city;
    private View popView;
    private View view_empty;
    TextView tvPresentCity;
    TextView tvCheckArea;
    GridView mGridView;
    private boolean isShowArea = false;
    private List<CountryCityBean> mCountryCityBean;
    private  List<CountryCityBean.CityListBean.AreaListBean> AreaList = new ArrayList<>();
    private GridviewAreaAdapter mGridviewAreaAdapter;

    private String city_id;
    private String area_id;
    private int uid;

//    private Dialog dialog;

    private AppUpdateVersionUtils mAppUpdateVersionUtils;
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
        ll_location.setVisibility(View.VISIBLE);
        tv_title_bar.setText("首页");
        image_news.setVisibility(View.VISIBLE);
        banner_home_adv.setAdapter(this);
        banner_home_adv.setDelegate(this);
        mHomeViewMode = new HomeViewMode(this);
        configRecycleView(recycler_shop_area, new LinearLayoutManager(getContext()));
        mCRPBleClient = AppContext.getBleClient();

        /*监听广播 消息*/
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("SEND_WX_BROADCAST");
        intentFilter.addAction("action.bind_device_success");
        intentFilter.addAction("action.check.location");
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        mActivity.registerReceiver(mNotificationReceiver, intentFilter);

        if (SharedPreferenceHelper.getDeviceInfo(mActivity)!=null){
            mDeviceInfoBean = SharedPreferenceHelper.getDeviceInfo(mActivity);
        }
        if (SharedPreferenceHelper.getUserInfo(mActivity)!=null){
            uid = SharedPreferenceHelper.getUserInfo(mActivity).getId();
        }else {
            uid=0;
        }
        //每次进来默认 没选择市区
        SharedPreferenceHelper.setSelect(mActivity,false);
//        initLocation();

    }

    private BroadcastReceiver mNotificationReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("SEND_WX_BROADCAST")){
                Bundle bundle=intent.getExtras();
                String pachageName=bundle.getString("packageName");
                String tickerText = bundle.getString("tickerText");
                switch (pachageName){
                    case WX:
                        mDeviceInfoBean = SharedPreferenceHelper.getDeviceInfo(mActivity);//获取最新的设备信息
                        if (mDeviceInfoBean!=null&&mDeviceInfoBean.getIs_open_wechat()==1){
                            if (AppContext.mBleDevice!=null&&AppContext.mBleDevice.isConnected()){
                                AppContext.mBleConnection.sendMessage(tickerText, CRPBleMessageType.MESSAGE_WECHAT,0);
                            }
                        }
                        break;
                    case QQ:
                        mDeviceInfoBean = SharedPreferenceHelper.getDeviceInfo(mActivity);//获取最新的设备信息
                        if (mDeviceInfoBean!=null&&mDeviceInfoBean.getIs_open_qq()==1){
                            if (AppContext.mBleDevice!=null&&AppContext.mBleDevice.isConnected()){
                                AppContext.mBleConnection.sendMessage(tickerText, CRPBleMessageType.MESSAGE_QQ,0);
                            }
                        }
                        break;
                    case MMS:
                        mDeviceInfoBean = SharedPreferenceHelper.getDeviceInfo(mActivity);//获取最新的设备信息
                        if (mDeviceInfoBean!=null&&mDeviceInfoBean.getIs_open_sms()==1){
                            if (AppContext.mBleDevice!=null&&AppContext.mBleDevice.isConnected()){
                                AppContext.mBleConnection.sendMessage(tickerText, CRPBleMessageType.MESSAGE_SMS,0);
                            }
                        }
                        break;
                    case CALL:

                        break;
                }
            }else if (action.equals("action.bind_device_success")){
                mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);
            }else if (action.equals("action.check.location")){

                tvPresentCity.setText("您正在看："+SharedPreferenceHelper.getCity(mActivity));
                area_id = SharedPreferenceHelper.getAreaId(mActivity);
                if (TextUtils.equals(area_id,AppConfig.AREA_ID_DEF)){
                    tv_location.setText(SharedPreferenceHelper.getCity(AppContext.getContext()));
                }else {
                    tv_location.setText(SharedPreferenceHelper.getArea(AppContext.getContext()));
                }
                //刷新 重载
                mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);

                for (int i = 0; i < mCountryCityBean.size(); i++) {
                    if (SharedPreferenceHelper.getProvince(AppContext.getContext()).equals(mCountryCityBean.get(i).getName())){
                        for(int j=0;j<mCountryCityBean.get(i).getCityList().size();j++){
                            if (SharedPreferenceHelper.getCity(AppContext.getContext()).equals(mCountryCityBean.get(i).getCityList().get(j).getName())){
                                AreaList.clear();
                                AreaList.addAll(mCountryCityBean.get(i).getCityList().get(j).getAreaList());
                                mGridviewAreaAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mNotificationReceiver);

    }

    @Override
    public void onResume() {
        super.onResume();
        mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);

    }



        /*if (new PermissionHelper().RequestPermisson(mActivity, Permission.Group.LOCATION)) {
            mLocationClient.startLocation();
        }else {
            if (!SharedPreferenceHelper.getSelect(mActivity)){
                SharedPreferenceHelper.setProvince(mActivity,AppConfig.PROVINCE_DEF);
                SharedPreferenceHelper.setCityId(mActivity,AppConfig.CITY_ID_DEF);
                SharedPreferenceHelper.setCity(mActivity,AppConfig.CITY_DEF);
                SharedPreferenceHelper.setAreaId(mActivity,AppConfig.AREA_ID_DEF);
                SharedPreferenceHelper.setArea(mActivity,AppConfig.AREA_DEF);
//                tv_location.setText(AppConfig.CITY_DEF);
                tv_location.setText("全国");
                city_id = SharedPreferenceHelper.getCityId(mActivity);
                area_id = SharedPreferenceHelper.getAreaId(mActivity);
                mHomeViewMode.getHomeDatas(true,SharedPreferenceHelper.getCityId(mActivity),"0",uid);
            }else {
                tv_location.setText(SharedPreferenceHelper.getArea(mActivity));
                city_id = SharedPreferenceHelper.getCityId(mActivity);
                area_id = SharedPreferenceHelper.getAreaId(mActivity);
                mHomeViewMode.getHomeDatas(true,city_id,area_id,uid);
            }
            city_id = SharedPreferenceHelper.getCityId(mActivity);
            area_id = SharedPreferenceHelper.getAreaId(mActivity);
            for (int i = 0; i < mCountryCityBean.size(); i++) {
                if (AppConfig.PROVINCE_DEF.equals(mCountryCityBean.get(i).getName())){
                    for(int j=0;j<mCountryCityBean.get(i).getCityList().size();j++){
                        if (AppConfig.CITY_DEF.equals(mCountryCityBean.get(i).getCityList().get(j).getName())){
                            AreaList.addAll(mCountryCityBean.get(i).getCityList().get(j).getAreaList());
                            mGridviewAreaAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }*/

    @Override
    public void initData() {
      /*  Dialog dialog =  new CitySelect(mActivity)
                .setMainColor(Color.RED)
                .listener(new CitySelect.OnSelectListener() {
                    @Override
                    public void onSelect(Province province, Province.City city,Province.Area area) {
//                        Log.e("399",province + "  " + city + "  " + area);
                        tvPresentCity.setText("您正在看："+city.name);
                        if (area!=null){
                            area_id = area.code;
                            tv_location.setText(area.name);
                            SharedPreferenceHelper.setArea(mActivity,area.name);
                        }else {
                            area_id = AppConfig.AREA_ID_DEF;
                            tv_location.setText(city.name);
                            SharedPreferenceHelper.setArea(mActivity,"");
                        }
                        SharedPreferenceHelper.setProvince(mActivity,province.name);
                        SharedPreferenceHelper.setCity(mActivity,city.name);
                        SharedPreferenceHelper.setCityId(mActivity,city.code);
                        SharedPreferenceHelper.setSelect(mActivity,true);
                        SharedPreferenceHelper.setAreaId(mActivity,area_id);
                        Intent intent =new Intent();
                        intent.setAction("action.check.location");
                        mActivity.sendBroadcast(intent);
                    }
                }).dialog();*/
        initCity();
        mHomeViewMode.GetVersionInfo();

    }


    /*初始化定位参数*/
  /*  private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
//        mLocationOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

    }*/

    /*
    * 初始化城市
    * */
    private void initCity(){

        mCountryCityBean = GsonHelper.GsonToList(utils.InitAssetsData(mActivity,"city.json"),CountryCityBean.class,"city");
        popView = getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
        view_empty = popView.findViewById(R.id.view_empty);
        tvPresentCity = popView.findViewById(R.id.tv_present_city);
        tvCheckArea = popView.findViewById(R.id.tv_check_area);
        mGridView = popView.findViewById(R.id.gridview_area);
        view_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window_city.dismiss();
            }
        });
        tvCheckArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window_city.dismiss();
                ((MainActivity) getActivity()).getDialog().show();
//                ShowCitySelect();
            }
        });
        mGridviewAreaAdapter = new GridviewAreaAdapter(mActivity,AreaList);
        mGridView.setAdapter(mGridviewAreaAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tv_location.setText(AreaList.get(position).getName());
                area_id = AreaList.get(position).getCode();
                SharedPreferenceHelper.setSelect(mActivity,true);
                SharedPreferenceHelper.setAreaId(mActivity,area_id);
                SharedPreferenceHelper.setArea(mActivity,AreaList.get(position).getName());
                window_city.dismiss();
                Intent intent =new Intent();
                intent.setAction("action.check.location");
                mActivity.sendBroadcast(intent);
            }
        });
        window_city = new MyPopWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void loadingData() {

        if (!SharedPreferenceHelper.getSelect(mActivity)){
            SharedPreferenceHelper.setProvince(mActivity,AppConfig.PROVINCE_DEF);
            SharedPreferenceHelper.setCityId(mActivity,AppConfig.CITY_ID_DEF);
            SharedPreferenceHelper.setCity(mActivity,AppConfig.CITY_DEF);
            SharedPreferenceHelper.setAreaId(mActivity,AppConfig.AREA_ID_DEF);
            SharedPreferenceHelper.setArea(mActivity,AppConfig.AREA_DEF);
            tv_location.setText(AppConfig.CITY_DEF);
        }else {
            tv_location.setText(SharedPreferenceHelper.getArea(mActivity));
        }
        city_id = SharedPreferenceHelper.getCityId(mActivity);
        area_id = SharedPreferenceHelper.getAreaId(mActivity);
        mHomeViewMode.getHomeDatas(true,city_id,area_id,uid);

//        for (int i = 0; i < mCountryCityBean.size(); i++) {
//            if (AppConfig.PROVINCE_DEF.equals(mCountryCityBean.get(i).getName())){
//                for(int j=0;j<mCountryCityBean.get(i).getCityList().size();j++){
//                    if (AppConfig.CITY_DEF.equals(mCountryCityBean.get(i).getCityList().get(j).getName())){
//                        AreaList.addAll(mCountryCityBean.get(i).getCityList().get(j).getAreaList());
//                        mGridviewAreaAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }
    }

    @OnClick({R.id.ll_location,R.id.btn_add_wristband, R.id.ll_sleep, R.id.ll_blood_pressure, R.id.ll_heart_rate, R.id.ll_blood_oxygen, R.id.btn_look_report, R.id.btn_ask,
            R.id.btn_inquiry,R.id.btn_shop_index,R.id.btn_health_shop,R.id.image_news})
    public void onClick(View v) {

        if (v==ll_location){
            /*Android N上Popwindow显示位置不正确问题*/
            if (Build.VERSION.SDK_INT >= 24) {
                Rect visibleFrame = new Rect();
                toolbar.getGlobalVisibleRect(visibleFrame);
                int height = toolbar.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                window_city.setHeight(height);
                window_city.showAsDropDown(toolbar, 0, 0);
            } else {
                window_city.showAsDropDown(toolbar, 0, 0);
            }
        }else if (v==image_news){
            if (isLogin()){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.msg_list));
            }else {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }else if (v == btn_add_wristband) {
            if (isLogin()) {
                if (!mCRPBleClient.isBluetoothEnable()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                    return;
                } else {
                    if (utils.isLocServiceEnable(mActivity)){
                        startActivity(new Intent(getContext(), BindDeviceActivity.class));
                    }else {

                        CircleDialogHelper.ShowDialog((AppCompatActivity) mActivity, "温馨提示", "扫描附近蓝牙设备需要开启定位服务", "开启", "取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =  new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        },null);
                    }
                }
            } else {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        } else if (v == btn_look_report) {
            //体检报告
            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MedicalReport));

        } else if (v == btn_ask) {
            startActivity(new Intent(getActivity(), HealthAskActivity.class).putExtra("kind_type",0));
        } else if (v == btn_inquiry) {
            startActivity(new Intent(getActivity(), DoctorListActivity.class).putExtra("cate_id",16).putExtra("kind_type",1));
        }  else if (v==btn_shop_index){
            boolean isSlect = SharedPreferenceHelper.getSelect(mActivity);
            String city_code = SharedPreferenceHelper.getCityId(AppContext.getContext());
            String area_code = SharedPreferenceHelper.getAreaId(AppContext.getContext());
            if(isLogin()){
                if (isSlect){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Index+"?uid="+ UserHelper.getUserInfo().getId()+"&city_code=" + city_code + "&area_code=" + area_code));
                }else {
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Index+"?uid="+ UserHelper.getUserInfo().getId()+"&city_code=" + city_code + "&area_code=0"));
                }
            }else {
                if (isSlect){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Index + "?city_code=" + city_code + "&area_code=" + area_code));
                }else {
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Index + "?city_code=" + city_code + "&area_code=0"));
                }
            }
//            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Index));
        }else if (v==btn_health_shop){
            boolean isSlect = SharedPreferenceHelper.getSelect(mActivity);
            String city_code = SharedPreferenceHelper.getCityId(AppContext.getContext());
            String area_code = SharedPreferenceHelper.getAreaId(AppContext.getContext());
            if(isLogin()){
                if (isSlect){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Self_Support+"?uid="+ UserHelper.getUserInfo().getId()+"&city_code=" + city_code + "&area_code=" + area_code));
                }else {
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Self_Support+"?uid="+ UserHelper.getUserInfo().getId()+"&city_code=" + city_code + "&area_code=0"));
                }
            }else {
                if (isSlect){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Self_Support + "?city_code=" + city_code + "&area_code=" + area_code));
                }else {
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Self_Support + "?city_code=" + city_code + "&area_code=0"));
                }
            }

        }else {
            if (homeBean.getIs_bind_bracelet()==1) {
                if (v == ll_sleep) {
                    startActivity(new Intent(mActivity, SleepActivity.class).putExtra("Device_no", mDeviceInfoBean.getDevice_no()));
                }  else if (v == ll_blood_oxygen) {
                    //血压测量
                    startActivity(new Intent(mActivity, MeasureActivity.class).putExtra("Device_no", mDeviceInfoBean.getDevice_no()).putExtra("type", 2));
                }else if (v == ll_heart_rate) {
                    //心率测量
                    startActivity(new Intent(mActivity, MeasureActivity.class).putExtra("Device_no", mDeviceInfoBean.getDevice_no()).putExtra("type", 1));
                }else if (v==ll_blood_pressure){

                    startActivity(new Intent(mActivity, MeasureActivity.class).putExtra("Device_no", mDeviceInfoBean.getDevice_no()).putExtra("type", 0));

                }
            }else {
                ToastyHelper.toastyNormal(mActivity, "未绑定设备，请绑定设备");
            }
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (SharedPreferenceHelper.getUserInfo(mActivity)!=null){
            uid = SharedPreferenceHelper.getUserInfo(mActivity).getId();
        }else {
            uid = 0;
        }
        if (event.getCode() == EventCode.Code.CONNECTED_SUCCESS) {
            mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);
        } else if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {
            tv_location.setText(AppConfig.CITY_DEF);
            city_id = SharedPreferenceHelper.getCityId(mActivity);
            area_id = SharedPreferenceHelper.getAreaId(mActivity);
            tvPresentCity.setText("您正在看："+AppConfig.CITY_DEF);
            AreaList.clear();
            mGridviewAreaAdapter.notifyDataSetChanged();
            mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);
        } else if (event.getCode() == EventCode.Code.MEASURE_SUCCESS) {
            mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);
        } else if (event.getCode() == EventCode.Code.LOGOUT) {
            mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);
        }else if(event.getCode()== EventCode.Code.UN_BIND_DEVICE){
            AppContext.mBleDevice.disconnect();
            mHomeViewMode.getHomeDatas(false,city_id,area_id,uid);
        }
    }

    @Override
    public void onStepChange(final CRPStepInfo crpStepInfo) {
        //运动
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeViewMode.runSteps(crpStepInfo.getSteps(), crpStepInfo.getDistance(), crpStepInfo.getCalories());
                tv_step.setText(crpStepInfo.getSteps() + "");
                tv_distance.setText(crpStepInfo.getDistance() + "");
                tv_calorie.setText(crpStepInfo.getCalories() + "");
            }
        });
    }

    @Override
    public void onPastStepChange(int i, CRPStepInfo crpStepInfo) {
        Log.d(TAG, "onPastStepChange: " + crpStepInfo.getSteps());
    }

    @Override
    public void HomeDatasResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(), "ret");
        if (ret == 0) {
            String data = GsonHelper.GsonToData(result.toString(), "data").toString();
            homeBean = GsonHelper.GsonToBean(data, HomeBean.class);
            if (homeBean.getMsg_count()>0){
                image_red_dot.setVisibility(View.VISIBLE);
            }else {
                image_red_dot.setVisibility(View.INVISIBLE);
            }
            if (homeBean.getIs_bind_bracelet() == 0) {//未绑定手环
                rt_bind_device.setVisibility(View.VISIBLE);
            } else {//绑定手环
                rt_bind_device.setVisibility(View.GONE);
                tv_step.setText(homeBean.getRun_steps().getSteps());
                tv_distance.setText(homeBean.getRun_steps().getDistance());
                tv_calorie.setText(homeBean.getRun_steps().getCalories());
                if (TextUtils.isEmpty(homeBean.getHealth_data().getBlood_pressure())) {
                    tv_blood_pressure.setText("0/0");
                } else {
                    tv_blood_pressure.setText(homeBean.getHealth_data().getBlood_pressure());
                }
                if (TextUtils.isEmpty(homeBean.getHealth_data().getBlood_oxygen())) {
                    tv_blood_oxygen.setText("0");
                } else {
                    tv_blood_oxygen.setText(homeBean.getHealth_data().getBlood_oxygen() + "%");
                }
                if (TextUtils.isEmpty(homeBean.getHealth_data().getHeart_rate())) {
                    tv_heart_rate.setText("0");
                } else {
                    tv_heart_rate.setText(homeBean.getHealth_data().getHeart_rate());
                }
                //获取设备信息
                mHomeViewMode.getMineDeviceInfo();
            }

            banner_home_adv.setData(homeBean.getAdv_list(), null);
            //健康管理gridview
            mHealthManageAdapter = new HealthManageAdapter(mActivity, homeBean.getArticle_cate_list());
            grid_health_manage.setAdapter(mHealthManageAdapter);
            grid_health_manage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch (position){
                        case 0://疑难杂症
                            startActivity(new Intent(getActivity(), DoctorListActivity.class).putExtra("cate_id",10).putExtra("kind_type",2));
                            break;
                        case 1://健康知识
                            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.headline_list+"?cate_id=11"));
                            break;
                        case 2://健康论坛
                            ArticleCateListBean mArticleCateListBean = homeBean.getArticle_cate_list().get(position);
                            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", mArticleCateListBean.getUrl()));
                            break;
                        case 3://养生保健
                            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.headline_list+"?cate_id=13"));
                            break;
                        case 4://运动健康
                            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.headline_list+"?cate_id=14"));
                            break;
                    }

                }
            });
            // 健康商圈gridview
            mHealthShopAdapter = new HealthShopAdapter(mActivity, homeBean.getGoods_cate_list());
            grid_shop_manage.setAdapter(mHealthShopAdapter);
            grid_shop_manage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GoodsCateListBean mGoodsCateListBean =  homeBean.getGoods_cate_list().get(position);
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", mGoodsCateListBean.getUrl()));

                }
            });
            //健康商圈列表
            mHomeShopAreaAdapter = new HomeShopAreaAdapter(mActivity, homeBean.getShop_list());
            recycler_shop_area.setAdapter(mHomeShopAreaAdapter);
            mHomeShopAreaAdapter.setOnItemClickListener(new BaseXRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, Object data, int position) {
                    ShopListBean mShopListBean =  homeBean.getShop_list().get(position);
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", mShopListBean.getUrl()));

                }
            });
            //健康商城
            mHomeShopAdapter = new HomeShopAdapter(getActivity(), homeBean.getGoods_list());
            gridview_shop.setAdapter(mHomeShopAdapter);
            gridview_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GoodsListBean mGoodsListBean = homeBean.getGoods_list().get(position);
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", mGoodsListBean.getUrl()));

                }
            });
        } else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(), "msg"));
        }
    }


    @Override
    public void MineDeviceInfo(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(), "ret");
        if (ret == 0) {
            String data = GsonHelper.GsonToData(result.toString(), "data").toString();
            if (data.equals("{}")) {
                rt_bind_device.setVisibility(View.VISIBLE);
            } else {
                rt_bind_device.setVisibility(View.GONE);
                mDeviceInfoBean = GsonHelper.GsonToBean(data, DeviceInfoBean.class);
                SharedPreferenceHelper.setDeviceInfo(mActivity,mDeviceInfoBean);
                if (TextUtils.isEmpty(mDeviceInfoBean.getDevice_no())){
                    rt_bind_device.setVisibility(View.VISIBLE);
                    ToastyHelper.toastyNormal(mActivity,"已绑定的设备不存在，请重新绑定！");
                }else {
                    AppContext.mBleDevice = mCRPBleClient.getBleDevice(mDeviceInfoBean.getDevice_no());
                    if (!AppContext.mBleDevice.isConnected()){
                        AppContext.mBleConnection = AppContext.mBleDevice.connect();
                        AppContext.mBleConnection.setConnectionStateListener(new CRPBleConnectionStateListener() {
                            @Override
                            public void onConnectionStateChange(int newState) {
                                switch (newState) {
                                    case CRPBleConnectionStateListener.STATE_CONNECTED://连接成功
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AppContext.mBleConnection.setStepChangeListener(HomeFragment.this);
                                            }
                                        });
                                        break;
                                }
                            }
                        });
                    }else {
                        //运动记步
                        AppContext.mBleConnection.syncStep();
                        AppContext.mBleConnection.setStepChangeListener(this);
                    }
                }
            }

        } else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(), "msg"));
        }
    }
    @Override
    public void runStepsResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(), "ret");
        if (ret == 0) {
            Log.i("runSteps", "上传步数成功");
        } else {
            Log.i("runSteps", "上传步数失败");
        }
    }

    @Override
    public void versionInfoResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            JSONObject resp = JSONObject.parseObject(result.toString());
            VersionInfoBean mData = JSON.parseObject(resp.getString("data"), VersionInfoBean.class);
            new AppUpdateVersionUtils().UpdateVersion(getActivity(),mData);

        }else {
            Log.i("getVersion_err", "获取版本信息失败");
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

        ToastyHelper.toastyNormal(mActivity, err);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, AdvListBean model, int position) {
//        showLoadFailMsg("点击");

    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, AdvListBean model, int position) {
        GlideHelper.loadImageView(mActivity, model.getPic(), itemView);

    }

    /*@Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                *//*发送定位成功事件，保存获取定位到的信息*//*
                Log.e("aaaaa", aMapLocation.getAdCode());
                for (int i = 0; i < mCountryCityBean.size(); i++) {
                    if (aMapLocation.getProvince().equals(mCountryCityBean.get(i).getName())){
                        for(int j=0;j<mCountryCityBean.get(i).getCityList().size();j++){
                            if (aMapLocation.getCity().equals(mCountryCityBean.get(i).getCityList().get(j).getName())){
                                SharedPreferenceHelper.setCityId(mActivity,mCountryCityBean.get(i).getCityList().get(j).getCode());
                                AreaList.addAll(mCountryCityBean.get(i).getCityList().get(j).getAreaList());
                                mGridviewAreaAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
//                if (SharedPreferenceHelper.getSelect(mActivity)){
//                    tv_location.setText(SharedPreferenceHelper.getArea(mActivity));
//                    city_id = SharedPreferenceHelper.getCityId(mActivity);
//                    area_id = SharedPreferenceHelper.getAreaId(mActivity);
//                    mHomeViewMode.getHomeDatas(true,city_id,area_id,uid);
//                    return;
//                }
                tvPresentCity.setText("您正在看："+aMapLocation.getCity());
                SharedPreferenceHelper.setProvince(AppContext.getContext(),aMapLocation.getProvince());
                SharedPreferenceHelper.setCity(AppContext.getContext(),aMapLocation.getCity());
                SharedPreferenceHelper.setAreaId(AppContext.getContext(),aMapLocation.getAdCode());
                SharedPreferenceHelper.setArea(AppContext.getContext(),aMapLocation.getDistrict());
                city_id = SharedPreferenceHelper.getCityId(mActivity);

                area_id = SharedPreferenceHelper.getAreaId(mActivity);
                if (SharedPreferenceHelper.getSelect(mActivity)){
                    tv_location.setText(aMapLocation.getDistrict());
                    mHomeViewMode.getHomeDatas(true,city_id,area_id,uid);

                }else {
                    tv_location.setText(aMapLocation.getCity());
//                    tv_location.setText("全国");
                    mHomeViewMode.getHomeDatas(true,city_id,"0",uid);
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                SharedPreferenceHelper.setCityId(mActivity,AppConfig.CITY_ID_DEF);
                SharedPreferenceHelper.setCity(mActivity,AppConfig.CITY_DEF);
                SharedPreferenceHelper.setAreaId(AppContext.getContext(), AppConfig.CITY_ID_DEF);
                SharedPreferenceHelper.setArea(AppContext.getContext(),AppConfig.AREA_DEF);
                tv_location.setText(AppConfig.CITY_DEF);
                city_id = SharedPreferenceHelper.getCityId(mActivity);
                area_id = SharedPreferenceHelper.getAreaId(mActivity);
                mHomeViewMode.getHomeDatas(true,city_id,area_id,uid);
                for (int i = 0; i < mCountryCityBean.size(); i++) {
                    if (AppConfig.PROVINCE_DEF.equals(mCountryCityBean.get(i).getName())){
                        for(int j=0;j<mCountryCityBean.get(i).getCityList().size();j++){
                            if (AppConfig.CITY_DEF.equals(mCountryCityBean.get(i).getCityList().get(j).getName())){
                                AreaList.addAll(mCountryCityBean.get(i).getCityList().get(j).getAreaList());
                                mGridviewAreaAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        }
    }*/
}
