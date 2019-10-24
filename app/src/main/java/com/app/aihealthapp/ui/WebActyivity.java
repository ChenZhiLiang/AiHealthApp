package com.app.aihealthapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebStorage;
import android.webkit.WebView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.activity.mine.OrderWebActyivity;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.util.UrlParseUtil;
import com.app.aihealthapp.view.ProgressWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：chenzl
 * Create time: 2017/11/24 0024 11:42
 * describe: Web
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class WebActyivity extends BaseActivity implements WebTitleView {


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

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.pay.success");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        webView.setWebTitleView(this);
        webView.setFocusable(true);//设置有焦点
        webView.setFocusableInTouchMode(true);//设置可触摸

        webView.loadUrl(url);//加载网址

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {
            String city_code = SharedPreferenceHelper.getCityId(AppContext.getContext());
            String area_code = SharedPreferenceHelper.getAreaId(AppContext.getContext());
            boolean isSlect = SharedPreferenceHelper.getSelect(this);
            if (UserHelper.getUserInfo() != null) {
                String mUrl;
                Uri uri = Uri.parse(url);
                if (url.contains("uid")) {
                    String uid = UrlParseUtil.getUriParam(uri, "uid");
                    if (TextUtils.isEmpty(uid)) {
                        String replaceUrl = url.replaceAll("uid=", "uid=" + UserHelper.getUserInfo().getId());
                        if (isSlect) {
                            mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=" + area_code;
                        } else {
                            mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=0";
                        }
                    } else {
                        if (uid.equals(UserHelper.getUserInfo().getId())) {
                            if (isSlect) {
                                mUrl = url + "&city_code=" + city_code + "&area_code=" + area_code;
                            } else {
                                mUrl = url + "&city_code=" + city_code + "&area_code=0";
                            }
                        } else {
                            String replaceUrl = url.replaceAll("uid=" + uid, "uid=" + UserHelper.getUserInfo().getId());
                            if (isSlect) {
                                mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=" + area_code;
                            } else {
                                mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=0";
                            }
                        }
                    }
                    webView.loadUrl(mUrl);
                }
            }
        }
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

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.pay.success")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastyHelper.toastyNormal(WebActyivity.this, "支付成功");
                        startActivity(new Intent(WebActyivity.this, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyOrder + UserHelper.getUserInfo().getId()));
                        AppManager.getAppManager().finishActivity(WebActyivity.this);
                    }
                }, 100);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);

    }
}
