package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.SleepView;

/**
 * @Author: chenzl
 * @ClassName: SleepViewMode
 * @Description: java类作用描述
 * @CreateDate: 2019/10/19 16:58
 */
public class SleepViewMode {

    private SleepView mSleepView;
    private BaseMode mBaseMode;

    public SleepViewMode(SleepView mSleepView) {
        this.mSleepView = mSleepView;
        mBaseMode = new BaseMode();
    }

    /**
     * @author: czl
     * @description 上传睡眠监测  数据
     * @date: 2019/10/19 17:06
     */
    public void UploadMeasureSleep(int total_time, int restfull_time, int light_time, int sober_time){

        String url = ApiUrl.DeviceApi.MeasureSleep;
        RequestParams params = new RequestParams();
        params.put("total_time",String.valueOf(total_time));
        params.put("restfull_time",String.valueOf(restfull_time));
        params.put("light_time",String.valueOf(light_time));
        params.put("sober_time",String.valueOf(sober_time));
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mSleepView.UploadMeasureSleepResult(result);
            }

            @Override
            public void onFailure(Object result) {
                mSleepView.showLoadFailMsg(result.toString() );
            }
        });
    }

    /**
     * @author: czl
     * @description 查询监测 某天监测数据  date=2019-10-19
     * @date: 2019/10/19 17:08
     */
    public void QueryMeasureSleep(String date){
        mSleepView.showProgress();
        String url = ApiUrl.DeviceApi.MeasureCheckSleep;
        RequestParams params = new RequestParams();
        params.put("date",date);
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mSleepView.CheckSleepResult(result);
                mSleepView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mSleepView.showLoadFailMsg(result.toString());
                mSleepView.hideProgress();
            }
        });

    }
}
