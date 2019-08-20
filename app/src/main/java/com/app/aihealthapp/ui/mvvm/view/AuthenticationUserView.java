package com.app.aihealthapp.ui.mvvm.view;

import com.app.aihealthapp.core.base.BaseView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 0:11
 * 修改人：Chen
 * 修改时间：2019/8/18 0:11
 */
public interface AuthenticationUserView extends BaseView {

    void AuthenticationUserResult(Object result);
    void uploadResult(Object result);
    void AdsResult(Object result);
}
