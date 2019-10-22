package com.app.aihealthapp.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.app.aihealthapp.R;
import com.app.aihealthapp.confing.AppConfig;
import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.activity.home.DoctorListActivity;
import com.app.aihealthapp.ui.activity.home.HealthAskActivity;
import com.app.aihealthapp.ui.activity.home.PayCentreActivity;
import com.app.aihealthapp.ui.activity.mine.LoginActivity;
import com.app.aihealthapp.ui.activity.mine.MineFragment;
import com.app.aihealthapp.ui.bean.PaymentBean;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.util.PayUtils;
import com.app.aihealthapp.wxapi.WXShareUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 19:17
 * 修改人：Chen
 * 修改时间：2019/8/18 19:17
 */
public class ProgressWebView extends WebView {

    private WebViewProgressBar progressBar;//进度条的矩形（进度线）
    private Handler handler;
    private Context context;
    private WebTitleView mWebTitleView;

    private BottomSheetDialog dialogs_share;
    private LinearLayout weChat_friend_layout;
    private LinearLayout weChat_moments_layout;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //实例化进度条
        progressBar = new WebViewProgressBar(context);
        //设置进度条的size
        progressBar.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //刚开始时候进度条不可见
        progressBar.setVisibility(GONE);
        //把进度条添加到webView里面
        addView(progressBar);
        //初始化handle
        handler = new Handler();
        initSettings();
    }

    public void setWebTitleView(WebTitleView mWebTitleView){
        this.mWebTitleView = mWebTitleView;
    }
    private void initSettings() {
        // 初始化设置
        WebSettings mSettings = this.getSettings();
        mSettings.setJavaScriptEnabled(true);//开启javascript
        mSettings.setDomStorageEnabled(true);//开启DOM
        mSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        //设置web页面
        mSettings.setAllowFileAccess(true);//设置支持文件流
        mSettings.setSupportZoom(true);// 支持缩放
        mSettings.setBuiltInZoomControls(true);// 支持缩放
        //不显示webview缩放按钮
        mSettings.setDisplayZoomControls(false);
        mSettings.setUseWideViewPort(true);// 调整到适合webview大小
        mSettings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        mSettings.setBlockNetworkImage(true);
        mSettings.setAppCacheEnabled(true);//开启缓存机制

        mSettings.setAllowFileAccessFromFileURLs(true);
        mSettings.setAllowUniversalAccessFromFileURLs(true);
        setWebViewClient(new MyWebClient());
        setWebChromeClient(new MyWebChromeClient());
        addJavascriptInterface(new WebAppInterface(), "jsAndroid");

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (canGoBack()) {
                        goBack();
                        return true;

                    } else {
                        return false;
                    }
                }
                return  false;
            }
        });
    }
    public class WebAppInterface {
        @JavascriptInterface
        public String jsCallToken() {
            return SharedPreferenceHelper.getUserToken(AppContext.getContext());
        }
        @JavascriptInterface
        public String andoridCity(){
            if (TextUtils.isEmpty(SharedPreferenceHelper.getAreaId(AppContext.getContext()))){
                return AppConfig.CITY_ID_DEF;
            }else {
                return SharedPreferenceHelper.getAreaId(AppContext.getContext());
            }
        }
        @JavascriptInterface
        public int jsCallUId() {
            return UserHelper.getUserInfo().getId();
        }
        @JavascriptInterface
        public void share(final String title, final String content, final String url) {
            View view = View.inflate(getContext(), R.layout.dialog_custom, null);
            weChat_friend_layout = view.findViewById(R.id.weChat_friend_layout);
            weChat_moments_layout = view.findViewById(R.id.weChat_moments_layout);
            weChat_friend_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    WXShareUtil.WXShare(getContext(),SendMessageToWX.Req.WXSceneSession,title,content,url);
                    dialogs_share.dismiss();

                }
            });
            weChat_moments_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    WXShareUtil.WXShare(getContext(),SendMessageToWX.Req.WXSceneTimeline,title,content,url);
                    dialogs_share.dismiss();

                }
            });
            dialogs_share = new BottomSheetDialog(getContext());
            dialogs_share.setContentView(view);
            dialogs_share.show();

        }

        @JavascriptInterface
        public void jsCallPay(String order_no, final String pay_type,String pwd){
            String url = ApiUrl.HomeApi.Pay;
            RequestParams params = new RequestParams();
            params.put("order_no",order_no);
            params.put("pwd",pwd);
            params.put("pay_type",String.valueOf(pay_type));
            new BaseMode().GetRequest(url, params, new ResultCallback() {
                @Override
                public void onSuccess(Object result) {
                    int ret = GsonHelper.GsonToInt(result.toString(),"ret");
                    if (ret==0){
                        if(pay_type.equals("1")){//支付宝支付
                            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
                            String alipay_sdk  = GsonHelper.GsonToString(data,"alipay_str");
                            new PayUtils((Activity) context).Alipay(alipay_sdk);
                        }else if (pay_type.equals("2")){//微信支付
                            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
                            PaymentBean paymentBean = GsonHelper.GsonToBean(data,PaymentBean.class);
                            new PayUtils((Activity) context).WXPay(paymentBean);//调用微信支付
                        }else {//密钥支付
                            ToastyHelper.toastyNormal((Activity) context,"密钥支付成功");
                        }
                    }else {
                        ToastyHelper.toastyNormal((Activity) context,GsonHelper.GsonToString(result.toString(),"msg"));
                    }
                }

                @Override
                public void onFailure(Object result) {
                    ToastyHelper.toastyNormal((Activity) context,result.toString());
                }
            });

        }

        @JavascriptInterface
        public void Exit(){
            AppManager.getAppManager().finishActivity((Activity) context);
        }
    }


    /**
     * 自定义WebChromeClient
     */
    private class MyWebChromeClient extends WebChromeClient {
        /**
         * 进度改变的回掉
         *
         * @param view        WebView
         * @param newProgress 新进度
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setProgress(100);
                handler.postDelayed(runnable, 200);//0.2秒后隐藏进度条

            } else if (progressBar.getVisibility() == GONE) {
                progressBar.setVisibility(VISIBLE);
            }
            //设置初始进度10，这样会显得效果真一点，总不能从1开始吧
            if (newProgress < 10) {
                newProgress = 10;
            }
            //不断更新进度
            progressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.d("ANDROID_LAB", "TITLE=" + title);
            if (!TextUtils.isEmpty(title)){
                mWebTitleView.onTitleResult(title);
            }
        }
    }
    private class MyWebClient extends WebViewClient {

        /**
         * 加载过程中 拦截加载的地址url
         *
         * @param view
         * @param url  被拦截的url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {

            if (url.startsWith("navigation://question")){//立即咨询
                context.startActivity(new Intent(context, HealthAskActivity.class));
                return true;
            }else if (url.startsWith("navigation://doctor?cate_id=16")){//中医问诊
                context.startActivity(new Intent(context, DoctorListActivity.class).putExtra("cate_id",16));
                return true;
            }else if (url.startsWith("navigation://doctor?cate_id=10")){//疑难杂症
                context.startActivity(new Intent(context, DoctorListActivity.class).putExtra("cate_id",10));
                return true;
            }else if (url.startsWith(ApiUrl.HOST+"order/cart")
                    ||url.startsWith(ApiUrl.HOST+"order/submit")
                    ||url.startsWith(ApiUrl.HOST+"user/user_address")
                    ||url.startsWith(ApiUrl.HOST+"user/login")
                    ||url.startsWith(ApiUrl.HOST+"index/shop/apply")){
                if (UserHelper.getUserInfo()==null){
                    context.startActivity(new Intent(context, LoginActivity.class));
//                    AppManager.getAppManager().finishActivity((Activity) context);
                    return true;
                }
            }
            return false;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 关闭图片加载阻塞
            view.getSettings().setBlockNetworkImage(false);
        }


        /**
         * 页面加载过程中，加载资源回调的方法
         *
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        /**
         * 页面开始加载调用的方法
         *
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            String city_code = SharedPreferenceHelper.getCityId(AppContext.getContext());
            String area_code = SharedPreferenceHelper.getAreaId(AppContext.getContext());
            boolean isSlect = SharedPreferenceHelper.getSelect(context);
            String mUrl;
            if (UserHelper.getUserInfo()!=null){
                if (url.contains("uid")){
                    if (url.indexOf("uid=0")!=-1){
                        String replaceUrl = url.replaceAll("uid=0","uid="+UserHelper.getUserInfo().getId());
                        if (isSlect){
                            mUrl = replaceUrl+"&city_code="+city_code+"&area_code="+area_code;
                        }else {
                            mUrl = replaceUrl+"&city_code="+city_code+"&area_code=0";
                        }
                    }else {
                        if (url.contains("city_code")){
                            mUrl = url ;
                        }else {
                            if (isSlect){
                                mUrl = url+"&city_code="+city_code+"&area_code="+area_code;
                            }else {
                                mUrl = url+"&city_code="+city_code+"&area_code=0";
                            }
                        }
                    }
//                    if (url.contains("city_code")){
//                        mUrl = url ;
//                    }else {
//                        if (isSlect){
//                            mUrl = url+"&city_code="+city_code+"&area_code="+area_code;
//                        }else {
//                            mUrl = url+"&city_code="+city_code+"&area_code=0";
//                        }
//                    }
                }else if (url.contains("?")){
                    if (url.contains("city_code")){
                        mUrl = url+"&uid="+UserHelper.getUserInfo().getId();
                    }else {
                        if (isSlect){
                            mUrl = url+"&uid="+UserHelper.getUserInfo().getId()+"&city_code="+city_code+"&area_code="+area_code;
                        }else {
                            mUrl = url+"&uid="+UserHelper.getUserInfo().getId()+"&city_code="+city_code+"&area_code=0";
                        }
                    }
                }else {
                    if (url.contains("city_code")){
                        mUrl = url+"?uid="+UserHelper.getUserInfo().getId();
                    }else {
                        if (isSlect){
                            mUrl = url+"?uid="+UserHelper.getUserInfo().getId()+"&city_code="+city_code+"&area_code="+area_code;
                        }else {
                            mUrl = url+"?uid="+UserHelper.getUserInfo().getId()+"&city_code="+city_code+"&area_code=0";
                        }
                    }
                }
            }else {
                if (url.contains("city_code")){
                    mUrl = url ;
                }else {
                    if (isSlect){
                        mUrl = url+"?city_code="+city_code+"&area_code="+area_code;
                    }else {
                        mUrl = url+"?city_code="+city_code+"&area_code=0";
                    }
                }
            }
            Log.e("Web_url",mUrl);
            super.onPageStarted(view, mUrl, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            ProgressWebView.this.requestFocus();
            ProgressWebView.this.requestFocusFromTouch();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            //此方法是为了处理在5.0以上Htts的问题，必须加上
            handler.proceed();
        }
    }
    /**
     *刷新界面（此处为加载完成后进度消失）
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
        }
    };
}

