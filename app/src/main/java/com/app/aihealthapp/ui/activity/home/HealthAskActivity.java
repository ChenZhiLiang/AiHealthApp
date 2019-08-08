package com.app.aihealthapp.ui.activity.home;

import android.os.Bundle;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;

/**
 * @Name：AiHealth
 * @Description：健康提问
 * @Author：Chen
 * @Date：2019/7/26 21:13
 * 修改人：Chen
 * 修改时间：2019/7/26 21:13
 */
public class HealthAskActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_health_ask;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("健康提问");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
