package com.app.aihealthapp.ui.mvvm.view;

import com.app.aihealthapp.core.base.BaseView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 0:47
 * 修改人：Chen
 * 修改时间：2019/8/18 0:47
 */
public interface MineDeviceView extends BaseView {
    void MineDeviceInfo(Object result);
    void UpdateDeviceResult(Object result);
    void UnBindResult(Object result);
}
