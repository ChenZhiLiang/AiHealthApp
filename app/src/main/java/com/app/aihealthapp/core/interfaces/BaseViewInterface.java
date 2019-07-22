package com.app.aihealthapp.core.interfaces;

/**
 * Created by Administrator on 2017/1/11.
 * 基类Activity实现接口
 */

public interface BaseViewInterface {

    /**
     * 初始化布局
     */
     int getLayoutId();

    /**
     * 导航栏标题
     * @return
     */
     String returnToolBarTitle();

    /**
     * 初始化View
     */
     void initView();
    /**
     * 初始化加载数据
     */
     void initData();
}
