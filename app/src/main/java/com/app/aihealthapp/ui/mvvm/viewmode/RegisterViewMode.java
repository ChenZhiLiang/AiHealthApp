package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.RegisterView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/17 11:45
 * 修改人：Chen
 * 修改时间：2019/8/17 11:45
 */
public class RegisterViewMode {

    private RegisterView mRegisterView;
    private BaseMode mBaseMode;

    public RegisterViewMode(RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mBaseMode = new BaseMode();
    }

    public void register(String mobile,String pwd,String code,String invite_code){
        mRegisterView.showProgress();
        String url = ApiUrl.UserApi.Register;
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        params.put("pwd",pwd);
        params.put("code",code);
        params.put("invite_code",invite_code);
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mRegisterView.onRegisterResult(result);
                mRegisterView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mRegisterView.hideProgress();
                mRegisterView.showLoadFailMsg(result.toString());
            }
        });
    }

    public void getAds(int stype){

        mRegisterView.showProgress();
        String url = ApiUrl.UserApi.OneAds;
        RequestParams params = new RequestParams();
        params.put("stype",String.valueOf(stype));
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mRegisterView.AdsResult(result);
                mRegisterView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mRegisterView.hideProgress();
                mRegisterView.showLoadFailMsg(result.toString());
            }
        });

    }
}
