package com.app.aihealthapp.ui.mvvm.view;

import com.app.aihealthapp.core.base.BaseView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/15 21:23
 * 修改人：Chen
 * 修改时间：2019/8/15 21:23
 */
public interface HomeView extends BaseView {

    void HomeDatasResult(Object result);

    void MineDeviceInfo(Object result);

    void runStepsResult(Object result);

    void versionInfoResult(Object result);
}
