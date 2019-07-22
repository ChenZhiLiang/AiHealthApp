package com.app.aihealthapp.core.interfaces;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/1/11.
 * 基类Fragment实现接口
 */

public interface BaseFragmentInterface {

    /**
     * 初始化布局
     */
     int getLayoutId();

    /**
     * 初始化View
     */
     void initView(View view, Bundle savedInstanceState);

    /**
     * 初始化加载数据
     */
     void initData();

}
