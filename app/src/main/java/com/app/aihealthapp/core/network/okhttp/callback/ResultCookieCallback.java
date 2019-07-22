package com.app.aihealthapp.core.network.okhttp.callback;

import java.util.ArrayList;

/**********************************************************
 * @文件名称：ResultCookieCallback.java
 * @文件作者：Administrator
 * @创建时间：2017年1月16日  下午10:17:53
 * @文件描述：当需要专门处理Cookie时创建此回调接口
 * @修改历史：2017年1月16日创建初始版本
 **********************************************************/
public interface ResultCookieCallback extends ResultCallback
{
	 void onCookie(ArrayList<String> cookieStrLists);
}
