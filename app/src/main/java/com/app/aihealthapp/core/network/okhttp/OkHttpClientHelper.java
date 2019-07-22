package com.app.aihealthapp.core.network.okhttp;



import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.cookie.SimpleCookieJar;
import com.app.aihealthapp.core.network.okhttp.response.JsonCallbackResponse;
import com.app.aihealthapp.core.network.okhttp.ssl.HttpsUtils;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * v
 *
 * @author Administrator
 * @function 用来发送get, post请求的工具类，包括设置一些请求的共用参数
 */
public class OkHttpClientHelper {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;
    /**
     * 静态代码块初始化Client相关的信息
     */
    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.retryOnConnectionFailure(false);//设置不进行连接失败重试


//        okHttpClientBuilder.addNetworkInterceptor(new CacheInterceptor());//缓存拦截器
//        okHttpClientBuilder.addInterceptor(new OfflineCacheInterceptor());
//        okHttpClientBuilder.cache(new Cache(AppContext.getInstance().getCacheDir(), 10 * 1024 * 1024));//使用应用缓存文件路径，缓存大小为10MB
        okHttpClientBuilder.followRedirects(true);
        //添加https支持
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        mOkHttpClient = okHttpClientBuilder.build();

    }

    /**
     * 通过构造好的Request,Callback去发送请求
     * Get、Post、Put 请求
     * @param request 创建 Request
     * @param callback 回调结果
     * @return
     */
    public static Call call(Request request, ResultCallback callback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new JsonCallbackResponse(callback));
        return call;
    }

}