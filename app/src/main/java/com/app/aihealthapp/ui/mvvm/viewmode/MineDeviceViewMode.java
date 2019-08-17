package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.ui.mvvm.view.MineDeviceView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 0:46
 * 修改人：Chen
 * 修改时间：2019/8/18 0:46
 */
public class MineDeviceViewMode {

    private MineDeviceView mMineDeviceView;
    private BaseMode mBaseMode;

    public MineDeviceViewMode(MineDeviceView mMineDeviceView) {
        this.mMineDeviceView = mMineDeviceView;
        mBaseMode = new BaseMode();
    }

    public void getMineDeviceInfo(){

        mMineDeviceView.showProgress();
        String url = ApiUrl.DeviceApi.DeviceInfo;
        mBaseMode.GetRequest(url, null, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMineDeviceView.MineDeviceInfo(result);
                mMineDeviceView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mMineDeviceView.hideProgress();
                mMineDeviceView.showLoadFailMsg(result.toString());
            }
        });
    }
}
