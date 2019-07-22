package com.app.aihealthapp.core.network.okhttp.callback;

/**********************************************************
 * @文件名称：ResultCallback.java
 * @文件作者：@Administrator
 * @创建时间：2017年1月16日 上午11:01:13
 * @文件描述：业务逻辑层真正处理的地方，包括java层异常和业务层异常
 * @修改历史：2017年1月16日创建初始版本
 **********************************************************/
public interface ResultCallback {


	/**
	 * 请求成功回调事件处理
	 * @param result 返回结果
     */
	 void onSuccess(Object result);


	/**
	 * 请求失败回调事件处理
	 * @param result 返回结果
     */
	 void onFailure(Object result);

}
