package com.app.aihealthapp.ui.activity.forum;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：健康询问
 * @Author：Chen
 * @Date：2019/7/24 22:07
 * 修改人：Chen
 * 修改时间：2019/7/24 22:07
 */
public class ForumFragment extends BaseFragment {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
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
        tv_title_bar.setText("健康论坛");

    }

    @Override
    public void initData() {

    }
}
