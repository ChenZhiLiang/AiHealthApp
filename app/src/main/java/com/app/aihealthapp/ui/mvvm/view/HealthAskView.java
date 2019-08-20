package com.app.aihealthapp.ui.mvvm.view;

import com.app.aihealthapp.core.base.BaseView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/20 20:38
 * 修改人：Chen
 * 修改时间：2019/8/20 20:38
 */
public interface HealthAskView extends BaseView {

    void uploadResult(Object result);

    void questionResult(Object result);
}
