package com.app.aihealthapp.ui.activity.forum;

import android.os.Bundle;
import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;

/**
 * @Name：AiHealth
 * @Description：健康询问
 * @Author：Chen
 * @Date：2019/7/24 22:07
 * 修改人：Chen
 * 修改时间：2019/7/24 22:07
 */
public class ForumFragment extends BaseFragment {

    public static ForumFragment getInstance(String title) {
        ForumFragment hf = new ForumFragment();
        hf.mTitle = title;
        return hf;
    }

    @Override
    public void loadingData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forum;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
}
