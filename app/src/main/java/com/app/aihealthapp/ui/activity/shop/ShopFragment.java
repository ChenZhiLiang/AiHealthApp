package com.app.aihealthapp.ui.activity.shop;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.ui.activity.mine.MineFragment;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：商城
 * @Author：Chen
 * @Date：2019/7/24 22:22
 * 修改人：Chen
 * 修改时间：2019/7/24 22:22
 */
public class ShopFragment extends BaseFragment {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    public static ShopFragment getInstance(String title) {
        ShopFragment hf = new ShopFragment();
        hf.mTitle = title;
        return hf;
    }
    @Override
    public void loadingData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        tv_title_bar.setText("健康商城");

    }

    @Override
    public void initData() {

    }
}
