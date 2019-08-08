package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;

/**
 * @Name：AiHealth
 * @Description：我的健康设备
 * @Author：Chen
 * @Date：2019/7/26 21:53
 * 修改人：Chen
 * 修改时间：2019/7/26 21:53
 */
public class MineDeviceActivity extends BaseActivity {
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

    }

    @Override
    public void initData() {

    }
}
