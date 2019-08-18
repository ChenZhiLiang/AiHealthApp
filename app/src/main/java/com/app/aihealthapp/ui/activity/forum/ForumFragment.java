package com.app.aihealthapp.ui.activity.forum;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.view.ProgressWebView;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：健康询问
 * @Author：Chen
 * @Date：2019/7/24 22:07
 * 修改人：Chen
 * 修改时间：2019/7/24 22:07
 */
public class ForumFragment extends BaseFragment implements WebTitleView {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.webview)
    ProgressWebView webview;
    public static ForumFragment getInstance(String title) {
        ForumFragment hf = new ForumFragment();
        hf.mTitle = title;
        return hf;
    }





    @Override
    public int getLayoutId() {
        return R.layout.fragment_forum;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        webview.setWebTitleView(this);
        webview.setFocusable(true);//设置有焦点
        webview.setFocusableInTouchMode(true);//设置可触摸
    }

    @Override
    public void initData() {

    }
    @Override
    public void loadingData() {
        webview.loadUrl(ApiUrl.WebApi.COMMUNITY);//加载网址

    }
    @Override
    public void onTitleResult(String title) {
        tv_title_bar.setText(title);

    }
}
