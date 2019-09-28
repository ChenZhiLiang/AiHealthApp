package com.app.aihealthapp.ui.activity.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.activity.mine.MineFragment;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.view.ProgressWebView;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：商城
 * @Author：Chen
 * @Date：2019/7/24 22:22
 * 修改人：Chen
 * 修改时间：2019/7/24 22:22
 */
public class ShopFragment extends BaseFragment implements WebTitleView {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.webview)
    ProgressWebView webview;
    public static ShopFragment getInstance(String title) {
        ShopFragment hf = new ShopFragment();
        hf.mTitle = title;
        return hf;
    }

    private BroadcastReceiver mRefreshBroadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.pay.success")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastyHelper.toastyNormal(mActivity, "支付成功");
//                        webView.loadUrl(ApiUrl.WebApi.MyOrder);
                        startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyOrder+UserHelper.getUserInfo().getId()));


                    }
                },100);

            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mRefreshBroadcastReceiver);
    }

    @Override
    public void loadingData() {

        if (isLogin()){
            String url = ApiUrl.WebApi.Self_Support+"?uid="+ UserHelper.getUserInfo().getId();
            webview.loadUrl(url);//加载网址
        }else {
            webview.loadUrl(ApiUrl.WebApi.Self_Support);//加载网址
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        webview.setWebTitleView(this);
        webview.setFocusable(true);//设置有焦点
        webview.setFocusableInTouchMode(true);//设置可触摸
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("action.pay.success");
        mActivity.registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onTitleResult(String title) {
        tv_title_bar.setText(title);

    }
}
