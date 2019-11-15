package com.app.aihealthapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
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
import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.activity.mine.OrderWebActyivity;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.util.UrlParseUtil;
import com.app.aihealthapp.view.ProgressWebView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private String mTitle;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        url = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
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
                finish();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onTitleResult(String title) {

        if (TextUtils.isEmpty(mTitle)){
            setTitle(title);
        }else {
            setTitle(mTitle);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        if (media.isCompressed()) {
//                            loading_img = media.getCompressPath();
                            File file = new File(media.getCompressPath());
                            uploadHead(file);
                        }
                    }

                    break;
                case PictureConfig.REQUEST_CAMERA:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectList) {
                        if (media.isCompressed()) {
//                            loading_img = media.getCompressPath();
                            File file = new File(media.getCompressPath());
                            uploadHead(file);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * @author
     * @time
     * @describe 上传图片
     */
    public void uploadHead(File avatar) {
        hud.show();
        String url = ApiUrl.HomeApi.Upload;
        RequestParams params = new RequestParams();
        params.fileParams.put("pic", avatar);
        new BaseMode().MultiPostRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                hud.dismiss();
                int ret = GsonHelper.GsonToInt(result.toString(), "ret");
                if (ret == 0) {
                    String data = GsonHelper.GsonToData(result.toString(), "data").toString();
                    final String url = GsonHelper.GsonToString(data, "url");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int UploadType = SharedPreferenceHelper.getUploadType(WebActyivity.this);
                            // 调用javascript的callJS()方法
                            webView.loadUrl("javascript:androidUpload('"+UploadType+"','"+url+"')");
                        }
                    });

                } else {
                    ToastyHelper.toastyNormal(WebActyivity.this, GsonHelper.GsonToString(result.toString(), "msg"));

                }
            }

            @Override
            public void onFailure(Object result) {
                hud.dismiss();
                ToastyHelper.toastyNormal(WebActyivity.this, result.toString());
            }
        });
    }
}
