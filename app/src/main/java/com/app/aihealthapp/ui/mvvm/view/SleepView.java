package com.app.aihealthapp.ui.mvvm.view;

import com.app.aihealthapp.core.base.BaseView;

/**
 * @Author: chenzl
 * @ClassName: SleepView
 * @Description: java类作用描述
 * @CreateDate: 2019/10/19 16:58
 */
public interface SleepView extends BaseView {

    void UploadMeasureSleepResult(Object result);
    void CheckSleepResult(Object result);
}
