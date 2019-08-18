package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.MeasureView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 15:13
 * 修改人：Chen
 * 修改时间：2019/8/18 15:13
 */
public class MeasureViewMode {

    private MeasureView mMeasureView;
    private BaseMode mBaseMode;

    public MeasureViewMode(MeasureView mMeasureView) {
        this.mMeasureView = mMeasureView;
        mBaseMode = new BaseMode();
    }

    public void getHomeDatas(){

        mMeasureView.showProgress();

        String url = ApiUrl.HomeApi.Home;
        RequestParams params = new RequestParams();
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMeasureView.HomeDatasResult(result);
                mMeasureView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mMeasureView.hideProgress();
                mMeasureView.showLoadFailMsg(result.toString());
            }
        });
    }

    public void MeasureBloodPressure(String shrink_value,String relax_value){
        String url = ApiUrl.DeviceApi.Bloodpressure;
        RequestParams params = new RequestParams();
        params.put("shrink_value",shrink_value);
        params.put("relax_value",relax_value);

        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMeasureView.MeasureBloodPressureResult(result);
            }

            @Override
            public void onFailure(Object result) {
                mMeasureView.showLoadFailMsg(result.toString());
                mMeasureView.hideProgress();
            }
        });

    }
    public void MeasureBloodOxygen(String value){
        String url = ApiUrl.DeviceApi.Bloodoxygen;
        RequestParams params = new RequestParams();
        params.put("value",value);
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMeasureView.MeasureBloodOxygenResult(result);
            }

            @Override
            public void onFailure(Object result) {
                mMeasureView.showLoadFailMsg(result.toString());
                mMeasureView.hideProgress();

            }
        });
    }
    public void MeasureHeartRate(String value){
        String url = ApiUrl.DeviceApi.Heartrate;
        RequestParams params = new RequestParams();
        params.put("value",value);
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMeasureView.MeasureHeartRateResult(result);
            }

            @Override
            public void onFailure(Object result) {
                mMeasureView.showLoadFailMsg(result.toString());
                mMeasureView.hideProgress();
            }
        });
    }
}
