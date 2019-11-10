package com.app.aihealthapp.ui.activity.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.activity.mine.MineFragment;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.util.UrlParseUtil;
import com.app.aihealthapp.view.FragmentProgressWebView;
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
    FragmentProgressWebView webview;
    public static ShopFragment getInstance(String title) {
        ShopFragment hf = new ShopFragment();
        hf.mTitle = title;
        return hf;
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {
            WebLoadUrl();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadingData() {


        WebLoadUrl();
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
    }
    private void WebLoadUrl() {

        boolean isSlect = SharedPreferenceHelper.getSelect(mActivity);
        String city_code = SharedPreferenceHelper.getCityId(AppContext.getContext());
        String area_code = SharedPreferenceHelper.getAreaId(AppContext.getContext());
        //post访问需要提交的参数
        String postDate;
        if (isLogin()) {
            if (isSlect) {
                postDate = "?uid=" + UserHelper.getUserInfo().getId() + "&city_code=" + city_code + "&area_code=" + area_code;
            } else {
                postDate = "?uid=" + UserHelper.getUserInfo().getId() + "&city_code=" + city_code + "&area_code=" + 0;
            }
        } else {
            if (isSlect) {
                postDate = "?city_code=" + city_code + "&area_code=" + area_code;
            } else {
                postDate = "?city_code=" + city_code + "&area_code=" + area_code;
            }
        }
        webview.loadUrl(ApiUrl.WebApi.Self_Support+postDate);
//        webview.postUrl(ApiUrl.WebApi.CONTROL_CENTER, EncodingUtils.getBytes(postDate, "BASE64"));

    }

    @Override
    public void initData() {

    }


    @Override
    public void onTitleResult(String title) {
        tv_title_bar.setText(title);

    }
}
