package com.app.aihealthapp.core.network.okhttp.request;


import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * @author Administrator
 * @function build the request
 * 创建Request请求
 */
public class OkHttpRequest {


	/**
	 * ressemble the params to the url
	 *  Get 请求
	 * @param params 参数
	 * @return
	 */
	public static Request createGetRequest(String url, RequestParams params) {
		StringBuilder urlBuilder = new StringBuilder(url).append("?");
		if (params != null) {
			for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
				urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
	}


	/**
	 * create the key-value Request
	 * Post 请求   数据格式application/x-www-form-urlencoded 数据是个普通表单
	 * @param url
	 * @param params
	 * @return
	 */
	public static Request createPostRequest(String url ,RequestParams params) {

		FormBody.Builder mFormBodyBuild = new FormBody.Builder();
		if (params != null) {
			for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
				mFormBodyBuild.add(entry.getKey(), entry.getValue());
			}
		}
		FormBody mFormBody = mFormBodyBuild.build();
		return new Request.Builder().url(url).post(mFormBody).build();
	}

}