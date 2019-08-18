package com.app.aihealthapp.ui.activity.home;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.kprogresshud.KProgressHUD;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.adapter.HealthManageAdapter;
import com.app.aihealthapp.ui.adapter.HealthShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAreaAdapter;
import com.app.aihealthapp.ui.bean.HomeBean;
import com.app.aihealthapp.ui.mvvm.view.MeasureView;
import com.app.aihealthapp.ui.mvvm.viewmode.MeasureViewMode;
import com.app.aihealthapp.util.StatusBarUtil;
import com.app.aihealthapp.util.utils;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.bean.CRPHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPMovementHeartRateInfo;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;
import com.crrepa.ble.conn.listener.CRPBloodOxygenChangeListener;
import com.crrepa.ble.conn.listener.CRPBloodPressureChangeListener;
import com.crrepa.ble.conn.listener.CRPHeartRateChangeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * @Name：AiHealth
 * @Description：测量页面
 * @Author：Chen
 * @Date：2019/8/8 21:12
 * 修改人：Chen
 * 修改时间：2019/8/8 21:12
 */
public class MeasureActivity extends Activity implements CRPBloodPressureChangeListener, CRPBloodOxygenChangeListener , MeasureView {

    public static final int TYPE = 0;//0:血压测量 1：心率测量 2:血氧测量

    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;

    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_measure_value)
    TextView tv_measure_value;
    @BindView(R.id.tv_measure_statue)
    TextView tv_measure_statue;
    @BindView(R.id.tv_measure_result_status)
    TextView tv_measure_result_status;

    @BindView(R.id.tv_standard_value)
    TextView tv_standard_value;
    @BindView(R.id.btn_start_measure)
    Button btn_start_measure;

    @BindView(R.id.tv_blood_pressure)
    TextView tv_blood_pressure;
    @BindView(R.id.tv_blood_oxygen)
    TextView tv_blood_oxygen;
    @BindView(R.id.tv_heart_rate)
    TextView tv_heart_rate;


    private int type;
    private String Device_no;

    private CRPBleClient mCRPBleClient;
    private CRPBleDevice mBleDevice;
    private CRPBleConnection mBleConnection;

    private MeasureViewMode mMeasureViewMode;
    public KProgressHUD hud;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_measure);

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        // 通过注解绑定控件
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initView();
    }


    private void initView(){
        Device_no = getIntent().getStringExtra("Device_no");
        type = getIntent().getIntExtra("type",0);
        tv_date.setText(utils.getPresentTime());
        if (type==0){
            tv_title_bar.setText("血压测量");
            tv_measure_statue.setText("血压状态");
            tv_standard_value.setText("收缩压：90-140mmHg\n舒张压：60-90mmHg ");
        }else if (type==1){
            tv_title_bar.setText("心率测量");
            tv_measure_statue.setText("心率状态");
            tv_standard_value.setText("60-100BMP/分");


        }else {
            tv_title_bar.setText("血氧测量");
            tv_measure_statue.setText("血氧状态");
            tv_standard_value.setText("94%-97%");

        }

        mCRPBleClient = AppContext.getBleClient(AppContext.getContext());
        mBleDevice = mCRPBleClient.getBleDevice(Device_no);
        mBleConnection = mBleDevice.connect();
        mBleConnection.setConnectionStateListener(new CRPBleConnectionStateListener() {
            @Override
            public void onConnectionStateChange(int i) {
                if (i==CRPBleConnectionStateListener.STATE_CONNECTED){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type==0){
                                mBleConnection.setBloodPressureChangeListener(MeasureActivity.this);
                            }else if (type==1){
                                mBleConnection.setHeartRateChangeListener(mHeartRateChangListener);
                            }else {
                                mBleConnection.setBloodOxygenChangeListener(MeasureActivity.this);
                            }
                        }
                    });
                }
            }
        });

        mMeasureViewMode = new MeasureViewMode(this);
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading),this.getResources().getColor(R.color.white))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(true);
        mMeasureViewMode.getHomeDatas();
    }

    @OnClick({R.id.img_back,R.id.btn_start_measure})
    public void onClick(View v){
        if (v==img_back){
            finish();
        }else if (v==btn_start_measure){
            if (type==0){
                mBleConnection.startMeasureBloodPressure();
            }else if (type==1){
                mBleConnection.startMeasureOnceHeartRate();
            }else {
                mBleConnection.startMeasureBloodOxygen();
            }
            showProgress();
        }
    }

    @Override
    public void onBloodOxygenChange(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //血氧
                tv_blood_oxygen.setText(String.valueOf(i)+"%");
                tv_measure_value.setText(String.valueOf(i)+"%");
                mMeasureViewMode.MeasureBloodOxygen(String.valueOf(i));
            }
        });


    }

    @Override
    public void onBloodPressureChange(final int i, final int i1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //血压
                tv_blood_pressure.setText(i+"/"+i1);
                tv_measure_value.setText(i+"/"+i1);
                mMeasureViewMode.MeasureBloodPressure(String.valueOf(i),String.valueOf(i1));
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //心率
                    tv_heart_rate.setText(String.valueOf(rate));
                    tv_measure_value.setText(String.valueOf(rate));
                    mMeasureViewMode.MeasureHeartRate(String.valueOf(rate));
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
    public void HomeDatasResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){

            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            HomeBean homeBean = GsonHelper.GsonToBean(data,HomeBean.class);
            if (type==0){
                if (TextUtils.isEmpty(homeBean.getHealth_data().getBlood_pressure())){
                    tv_blood_pressure.setText("0/0");
                    tv_measure_value.setText("0/0");
                }else {
                    tv_blood_pressure.setText(homeBean.getHealth_data().getBlood_pressure());
                    tv_measure_value.setText(homeBean.getHealth_data().getBlood_pressure());

                }
            }else if (type==1){
                if (TextUtils.isEmpty(homeBean.getHealth_data().getBlood_oxygen())){
                    tv_heart_rate.setText("0");
                    tv_measure_value.setText("0");

                }else {
                    tv_heart_rate.setText(homeBean.getHealth_data().getHeart_rate());
                    tv_measure_value.setText(homeBean.getHealth_data().getHeart_rate());

                }
            }else {
                if (TextUtils.isEmpty(homeBean.getHealth_data().getBlood_oxygen())){
                    tv_blood_oxygen.setText("0");
                    tv_measure_value.setText("0");
                }else {
                    tv_blood_oxygen.setText(homeBean.getHealth_data().getBlood_oxygen()+"%");
                    tv_measure_value.setText(homeBean.getHealth_data().getBlood_oxygen()+"%");
                }
            }

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void MeasureBloodPressureResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            Log.i("Measure","上传血压成功");
            EventBusHelper.sendEvent(new Event(EventCode.Code.MEASURE_SUCCESS));

        }else {
            Log.i("Measure","上传血压失败");
        }
        hideProgress();

    }

    @Override
    public void MeasureBloodOxygenResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            Log.i("Measure","上传血氧数成功");
            EventBusHelper.sendEvent(new Event(EventCode.Code.MEASURE_SUCCESS));

        }else {
            Log.i("Measure","上传血氧失败");
        }
        hideProgress();
    }

    @Override
    public void MeasureHeartRateResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            EventBusHelper.sendEvent(new Event(EventCode.Code.MEASURE_SUCCESS));
            Log.i("Measure","上传心率成功");
        }else {
            Log.i("Measure","上传心率失败");
        }
        hideProgress();
    }

    @Override
    public void showProgress() {
        hud.show("正在测量...");
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
