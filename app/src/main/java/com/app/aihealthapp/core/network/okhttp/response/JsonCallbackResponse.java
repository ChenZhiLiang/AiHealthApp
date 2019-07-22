package com.app.aihealthapp.core.network.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCookieCallback;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author vision
 * @function 专门处理JSON的回调
 */
public class JsonCallbackResponse implements Callback {

	private static final String TAG = "JsonCallbackResponse";
	private final String RESULT_CODE = "retcode";
	private final String RESULT = "code";
	private final String RESULT_STATUS = "status";
	private final String EMPTY_MSG_TEXT = "网络连接失败，请检查网络设置。";
	private final String COOKIE_STORE = "Set-Cookie";

	/**
	 * 将其它线程的数据转发到UI线程
	 */
	private Handler mDeliveryHandler;
	private ResultCallback mResultCallback;

	public JsonCallbackResponse(ResultCallback resultCallback) {
		this.mResultCallback = resultCallback;
		this.mDeliveryHandler = new Handler(Looper.getMainLooper());
	}

	@Override
	public void onFailure(final Call call, final IOException ioexception) {
		/**
		 * 此时还在非UI线程，因此要转发
		 */
		mDeliveryHandler.post(new Runnable() {
			@Override
			public void run() {
				mResultCallback.onFailure(EMPTY_MSG_TEXT);
			}
		});
	}

	@Override
	public void onResponse(final Call call, final Response response) throws IOException {
			final String result = response.body().string();
			final ArrayList<String> cookieLists = handleCookie(response.headers());
			mDeliveryHandler.post(new Runnable() {
				@Override
				public void run() {
					handleResponse(result);
					/**
					 * handle the cookie
					 */
					if (mResultCallback instanceof ResultCookieCallback) {
						((ResultCookieCallback) mResultCallback).onCookie(cookieLists);
					}
				}
			});
	}

	private ArrayList<String> handleCookie(Headers headers) {
		ArrayList<String> tempList = new ArrayList<>();
		for (int i = 0; i < headers.size(); i++) {
			if (headers.name(i).equalsIgnoreCase(COOKIE_STORE)) {
				tempList.add(headers.value(i));
			}
		}
		return tempList;
	}

	private void handleResponse(Object responseObj) {
		if (responseObj == null) {
			mResultCallback.onFailure(EMPTY_MSG_TEXT);
			return;
		}
		try {
			JSONObject result = new JSONObject(responseObj.toString());
			if (result.has(RESULT_CODE)||result.has(RESULT_STATUS)||result.has(RESULT)) {
				mResultCallback.onSuccess(result);
			} else {
				mResultCallback.onFailure(EMPTY_MSG_TEXT);
			}
			//打印请求返回json数据
			Log.i(TAG,result.toString());
		} catch (Exception e) {
			mResultCallback.onFailure(e.toString());
			e.printStackTrace();
		}
	}
}