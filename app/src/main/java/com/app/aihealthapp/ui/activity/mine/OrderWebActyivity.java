package com.app.aihealthapp.ui.activity.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.util.UrlParseUtil;
import com.app.aihealthapp.view.ProgressWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/10/24 22:17
 * 修改人：Chen
 * 修改时间：2019/10/24 22:17
 */
public class OrderWebActyivity extends BaseActivity  implements WebTitleView {


    @BindView(R.id.webview)
    ProgressWebView webView;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        url = getIntent().getStringExtra("url");
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    public void initView() {

        webView.setWebTitleView(this);
        webView.setFocusable(true);//设置有焦点
        webView.setFocusableInTouchMode(true);//设置可触摸

        webView.loadUrl(url);//加载网址

    }


    @Override
    public void initData() {

    }

    @OnClick(R.id.img_back)
    public void onClick(View v) {
        if (webView.canGoBack()) {
            webView.goBack();//返回上一页面
        } else {
            AppManager.getAppManager().finishActivity(this);
        }
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                AppManager.getAppManager().finishActivity(this);
                return true;
            }
        }
        return false;
    }


    @Override
    public void onTitleResult(String title) {
        setTitle(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
