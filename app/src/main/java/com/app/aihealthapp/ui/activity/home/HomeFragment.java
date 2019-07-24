package com.app.aihealthapp.ui.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;

import butterknife.BindView;

/**
 * @Name：aihealthapp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/22 22:57
 * 修改人：Chen
 * 修改时间：2019/7/22 22:57
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
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
    }

    @Override
    public void loadingData() {

    }

    @Override
    public void initData() {

    }
}
