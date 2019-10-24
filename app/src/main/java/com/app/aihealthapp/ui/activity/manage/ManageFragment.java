package com.app.aihealthapp.ui.activity.manage;

import android.app.Activity;
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
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.uitrarefresh.UTRefreshLayout;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrFrameLayout;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrHandler;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.activity.forum.ForumFragment;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.util.UrlParseUtil;
import com.app.aihealthapp.view.FragmentProgressWebView;
import com.app.aihealthapp.view.ProgressWebView;
import com.app.aihealthapp.view.WebViewProgressBar;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：健康管理
 * @Author：Chen
 * @Date：2019/7/24 22:07
 * 修改人：Chen
 * 修改时间：2019/7/24 22:07
 */
public class ManageFragment extends BaseFragment implements  WebTitleView {


    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.webview)
    FragmentProgressWebView webview;
    public static ManageFragment getInstance(String title) {
        ManageFragment hf = new ManageFragment();
        hf.mTitle = title;
        return hf;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        webview.setWebTitleView(this);
        webview.setFocusable(true);//设置有焦点
        webview.setFocusableInTouchMode(true);//设置可触摸


    }
    @Override
    public void loadingData() {
        boolean isSlect = SharedPreferenceHelper.getSelect(mActivity);
        String city_code = SharedPreferenceHelper.getCityId(AppContext.getContext());
        String area_code = SharedPreferenceHelper.getAreaId(AppContext.getContext());
        if (isLogin()){
            if (isSlect) {
                webview.loadUrl(ApiUrl.WebApi.CONTROL_CENTER+"?uid="+ UserHelper.getUserInfo().getId()+"&city_code=" + city_code + "&area_code=" + area_code);//加载网址
            }else {
                webview.loadUrl(ApiUrl.WebApi.CONTROL_CENTER+"?uid="+ UserHelper.getUserInfo().getId()+"&city_code=" + city_code + "&area_code=0");//加载网址
            }
        }else {
            if (isSlect) {
                webview.loadUrl(ApiUrl.WebApi.CONTROL_CENTER + "?city_code=" + city_code + "&area_code=" + area_code);//加载网址
            }else {
                webview.loadUrl(ApiUrl.WebApi.CONTROL_CENTER + "?city_code=" + city_code + "&area_code=0");//加载网址
            }
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
//        int uid = SharedPreferenceHelper.getUserInfo(this).getId();
        if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {
            if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {
                if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {
                    String city_code = SharedPreferenceHelper.getCityId(AppContext.getContext());
                    String area_code = SharedPreferenceHelper.getAreaId(AppContext.getContext());
                    boolean isSlect = SharedPreferenceHelper.getSelect(mActivity);
                    if (UserHelper.getUserInfo() != null) {
                        String mUrl;
                        Uri uri = Uri.parse(webview.getUrl());
                        if (webview.getUrl().contains("uid")) {
                            String uid = UrlParseUtil.getUriParam(uri, "uid");
                            if (TextUtils.isEmpty(uid)) {
                                String replaceUrl = webview.getUrl().replaceAll("uid=", "uid=" + UserHelper.getUserInfo().getId());
                                if (isSlect) {
                                    mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=" + area_code;
                                } else {
                                    mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=0";
                                }
                            } else {
                                if (uid.equals(UserHelper.getUserInfo().getId())) {
                                    if (isSlect) {
                                        mUrl = webview.getUrl() + "&city_code=" + city_code + "&area_code=" + area_code;
                                    } else {
                                        mUrl = webview.getUrl() + "&city_code=" + city_code + "&area_code=0";
                                    }
                                } else {
                                    String replaceUrl = webview.getUrl().replaceAll("uid=" + uid, "uid=" + UserHelper.getUserInfo().getId());
                                    if (isSlect) {
                                        mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=" + area_code;
                                    } else {
                                        mUrl = replaceUrl + "&city_code=" + city_code + "&area_code=0";
                                    }
                                }
                            }
                            webview.loadUrl(mUrl);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void initData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTitleResult(String title) {
        tv_title_bar.setText(title);
    }
}
