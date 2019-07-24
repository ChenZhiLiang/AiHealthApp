package com.app.aihealthapp.ui.activity.shop;

import android.os.Bundle;
import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.ui.activity.mine.MineFragment;

/**
 * @Name：AiHealth
 * @Description：商城
 * @Author：Chen
 * @Date：2019/7/24 22:22
 * 修改人：Chen
 * 修改时间：2019/7/24 22:22
 */
public class ShopFragment extends BaseFragment {

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

    }

    @Override
    public void initData() {

    }
}
