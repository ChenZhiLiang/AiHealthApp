package com.app.aihealthapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.view.ProgressWebView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：chenzl
 * Create time: 2017/11/24 0024 11:42
 * describe: Web
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class WebActyivity extends BaseActivity implements  WebTitleView {


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

        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("action.pay.success");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

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

    private BroadcastReceiver mRefreshBroadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.pay.success")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastyHelper.toastyNormal(WebActyivity.this, "支付成功");
                        webView.loadUrl(ApiUrl.WebApi.MyOrder);
//                        startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyOrder));


                    }
                },100);

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);

    }
}
