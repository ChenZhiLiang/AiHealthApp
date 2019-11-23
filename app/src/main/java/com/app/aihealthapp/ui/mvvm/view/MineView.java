package com.app.aihealthapp.ui.mvvm.view;

import com.app.aihealthapp.core.base.BaseView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/17 12:14
 * 修改人：Chen
 * 修改时间：2019/8/17 12:14
 */
public interface MineView extends BaseView {

    void UesrInfoResult(Object result);
    void uploadResult(Object result);
    void UpdateProfileResult(Object result);
    void versionInfoResult(Object result);
}
