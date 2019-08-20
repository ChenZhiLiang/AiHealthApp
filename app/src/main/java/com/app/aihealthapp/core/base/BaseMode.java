package com.app.aihealthapp.core.base;


import com.app.aihealthapp.core.network.okhttp.OkHttpClientHelper;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.OkHttpRequest;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;

/**
 * author：chenzl
 * Create time: 2018/12/26 0026 15:57
 * describe: Mode通用
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class BaseMode {
    /**
     * get请求
     *
     * @param params   参数集
     * @param callback
     */
    public void GetRequest(String url, RequestParams params, ResultCallback callback) {
        OkHttpClientHelper.call(OkHttpRequest.createGetRequest(url,params),callback);
    }

    /**
     * post请求
     * @param url
     * @param params
     * @param callback
     */
    public void PostRequest(String url,RequestParams params,ResultCallback callback){
        OkHttpClientHelper.call(OkHttpRequest.createPostRequest(url,params),callback);
    }

    /**
     * 上传文件
     * @param params
     * @param callback
     */
    public void MultiPostRequest( String url,RequestParams params, ResultCallback callback) {
        OkHttpClientHelper.call(OkHttpRequest.createMultiPostRequest(url,params),callback);
    }

}
