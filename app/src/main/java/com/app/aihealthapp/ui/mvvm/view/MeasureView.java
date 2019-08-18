package com.app.aihealthapp.ui.mvvm.view;

import com.app.aihealthapp.core.base.BaseView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 15:13
 * 修改人：Chen
 * 修改时间：2019/8/18 15:13
 */
public interface MeasureView extends BaseView {

    void HomeDatasResult(Object result);
    void MeasureBloodPressureResult(Object result);
    void MeasureBloodOxygenResult(Object result);
    void MeasureHeartRateResult(Object result);

}
