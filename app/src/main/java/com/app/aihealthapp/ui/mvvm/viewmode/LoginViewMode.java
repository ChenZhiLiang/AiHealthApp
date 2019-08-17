package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.LoginView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/17 12:03
 * 修改人：Chen
 * 修改时间：2019/8/17 12:03
 */
public class LoginViewMode {

    private LoginView mLoginView;
    private BaseMode mBaseMode;

    public LoginViewMode(LoginView mLoginView) {
        this.mLoginView = mLoginView;
        this.mBaseMode = new BaseMode();
    }

    public void Login(String mobile,String pwd){

        mLoginView.showProgress();
        String url = ApiUrl.UserApi.Login;
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        params.put("pwd",pwd);
        mBaseMode.PostRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mLoginView.onLoginResult(result);
                mLoginView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mLoginView.hideProgress();
                mLoginView.showLoadFailMsg(result.toString());
            }
        });

    }
}
