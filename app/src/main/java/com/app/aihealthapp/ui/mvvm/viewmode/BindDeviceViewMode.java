package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.BindDeviceView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 0:58
 * 修改人：Chen
 * 修改时间：2019/8/18 0:58
 */
public class BindDeviceViewMode {

    private BindDeviceView mBindDeviceView;
    private BaseMode mBaseMode;

    public BindDeviceViewMode(BindDeviceView mBindDeviceView) {
        this.mBindDeviceView = mBindDeviceView;
        mBaseMode = new BaseMode();
    }

    public void BindDevice(String device_no){

        String url = ApiUrl.DeviceApi.BindDevice;
        RequestParams params = new RequestParams();
        params.put("device_no",device_no);
        params.put("device_id","");
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mBindDeviceView.BindDeviceReuslt(result);
                mBindDeviceView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mBindDeviceView.hideProgress();
                mBindDeviceView.showLoadFailMsg(result.toString());
            }
        });
    }
}
