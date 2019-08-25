package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
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

    public void getMineDeviceInfo(boolean isShow){

        if (isShow){
            mMineDeviceView.showProgress();

        }
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

    public void UpdateDevice(int id,int is_bind,int is_open_phone,int is_open_sms,int is_open_wechat,int is_open_qq,int is_open_photo){

        mMineDeviceView.showProgress();
        String url = ApiUrl.DeviceApi.UpdateDevice;
        RequestParams params = new RequestParams();
        params.put("id",String.valueOf(id));
        params.put("is_bind",String.valueOf(is_bind));
        params.put("is_open_phone",String.valueOf(is_open_phone));
        params.put("is_open_sms",String.valueOf(is_open_sms));
        params.put("is_open_wechat",String.valueOf(is_open_wechat));
        params.put("is_open_qq",String.valueOf(is_open_qq));
        params.put("is_open_photo",String.valueOf(is_open_photo));
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMineDeviceView.UpdateDeviceResult(result);
                mMineDeviceView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mMineDeviceView.hideProgress();
                mMineDeviceView.showLoadFailMsg(result.toString());
            }
        });

    }

    public void UnBind(int id){
        mMineDeviceView.showProgress();
        String url = ApiUrl.DeviceApi.UnBind;
        RequestParams params = new RequestParams();
        params.put("id",String.valueOf(id));
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMineDeviceView.UnBindResult(result);
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
