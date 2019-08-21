package com.app.aihealthapp.ui.activity.manage;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.uitrarefresh.UTRefreshLayout;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrFrameLayout;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrHandler;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.activity.forum.ForumFragment;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
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
    ProgressWebView webview;
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
        webview.addJavascriptInterface(new WebAppInterface(), "jsAndroid");

    }
    @Override
    public void loadingData() {
        webview.loadUrl(ApiUrl.WebApi.CONTROL_CENTER);//加载网址

    }
    @Override
    public void initData() {

    }
    public class WebAppInterface {
        @JavascriptInterface
        public String jsCallToken() {
            return SharedPreferenceHelper.getUserToken(AppContext.getContext());
        }
    }

    @Override
    public void onTitleResult(String title) {
        tv_title_bar.setText(title);
    }
}
