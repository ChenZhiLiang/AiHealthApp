package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;

/**
 * @Name：AiHealth
 * @Description：我的
 * @Author：Chen
 * @Date：2019/7/24 22:08
 * 修改人：Chen
 * 修改时间：2019/7/24 22:08
 */
public class MineFragment extends BaseFragment {

    public static MineFragment getInstance(String title) {
        MineFragment hf = new MineFragment();
        hf.mTitle = title;
        return hf;
    }
    @Override
    public void loadingData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
}
