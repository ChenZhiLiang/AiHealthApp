package com.app.aihealthapp.ui.activity.manage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：健康管理
 * @Author：Chen
 * @Date：2019/7/24 22:07
 * 修改人：Chen
 * 修改时间：2019/7/24 22:07
 */
public class ManageFragment extends BaseFragment {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    public static ManageFragment getInstance(String title) {
        ManageFragment hf = new ManageFragment();
        hf.mTitle = title;
        return hf;
    }
    @Override
    public void loadingData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        tv_title_bar.setText("健康管理");

    }

    @Override
    public void initData() {

    }
}
