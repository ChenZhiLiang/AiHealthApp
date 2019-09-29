package com.app.aihealthapp.core.network.okhttp.request;


import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.ui.AppContext;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

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
		String mToken = SharedPreferenceHelper.getUserToken(AppContext.getContext());

		if (mToken == null) {
			return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
		}else {
			return new Request.Builder().addHeader("Authorization", "Bearer " + mToken).url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
		}
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
		String mToken = SharedPreferenceHelper.getUserToken(AppContext.getContext());
		if (mToken == null) {
			return new Request.Builder().url(url).post(mFormBody).build();
		}else {
			return new Request.Builder().addHeader("Authorization", "Bearer " + mToken).url(url).post(mFormBody).build();
		}
	}

	/**
	 * 文件上传请求
	 *  File 请求
	 * @param
	 * @return
	 */
	public static Request createMultiPostRequest(String url ,RequestParams params) {

		MultipartBody.Builder requestBody = new MultipartBody.Builder();

		requestBody.setType(MultipartBody.FORM);

		if (params != null) {
			if (params.urlParams != null) {
				for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
					requestBody.addFormDataPart(entry.getKey(),entry.getValue());
				}
			}
			if (params.fileParams != null) {
				for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
					requestBody.addFormDataPart(entry.getKey(), ((File) entry.getValue()).getName(),
							RequestBody.create(MediaType.parse("application/octet-stream"), (File) entry.getValue()));
				}
			}
		}

		String mToken = SharedPreferenceHelper.getUserToken(AppContext.getContext());
		if (mToken == null) {
			return new Request.Builder().url(url).post(requestBody.build()).build();
		}else {
			return new Request.Builder().addHeader("Authorization", "Bearer " + mToken).url(url).post(requestBody.build()).build();
		}
	}
}