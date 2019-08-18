package com.app.aihealthapp.ui.activity.mine;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

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
import com.app.aihealthapp.ui.activity.home.BindDeviceActivity;
import com.app.aihealthapp.ui.bean.DeviceInfoBean;
import com.app.aihealthapp.ui.mvvm.view.MineDeviceView;
import com.app.aihealthapp.ui.mvvm.viewmode.MineDeviceViewMode;

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
public class MineDeviceActivity extends BaseActivity implements MineDeviceView {

    @BindView(R.id.rt_bind_device)
    RelativeLayout rt_bind_device;
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

    @BindView(R.id.btn_logout)
    Button btn_logout;
    private MineDeviceViewMode mMineDeviceViewMode;

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
    }

    @Override
    public void initData() {
        mMineDeviceViewMode.getMineDeviceInfo();
    }

    @OnClick({R.id.btn_bind,R.id.btn_logout})
    public void onClick(View v){
        if (v==btn_bind){
            if (!AppContext.getBleClient(AppContext.getContext()).isBluetoothEnable()){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBtIntent);
                return;
            }else {
                startActivity(new Intent(this, BindDeviceActivity.class));
            }
        }else if (v==btn_logout){
            CircleDialogHelper.ShowDialogHint(this, "确定注销吗?", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserHelper.clearUser();
                    EventBusHelper.sendEvent(new Event(EventCode.Code.LOGOUT));
                    finish();
                }
            }, null);
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
                DeviceInfoBean mDeviceInfoBean = GsonHelper.GsonToBean(data,DeviceInfoBean.class);
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
    public void UpdateDeviceResult(Object result) {

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
