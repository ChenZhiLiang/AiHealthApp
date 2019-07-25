package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;

/**
 * @Name：AiHealth
 * @Description：注册
 * @Author：Chen
 * @Date：2019/7/26 0:27
 * 修改人：Chen
 * 修改时间：2019/7/26 0:27
 */
public class RegisterActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("用户注册");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
