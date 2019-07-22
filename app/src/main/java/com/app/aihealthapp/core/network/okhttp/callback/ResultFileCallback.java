package com.app.aihealthapp.core.network.okhttp.callback;

/**
 * @author Administrator
 * @function 监听下载进度
 */
public interface ResultFileCallback extends ResultCallback {
	 void onProgress(int progrss);
}
