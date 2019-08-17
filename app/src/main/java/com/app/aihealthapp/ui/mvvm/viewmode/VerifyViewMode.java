package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.VerifyView;

/**
 * @Name：AiHealth
 * @Description：验证码
 * @Author：Chen
 * @Date：2019/8/17 11:26
 * 修改人：Chen
 * 修改时间：2019/8/17 11:26
 */
public class VerifyViewMode {

    private VerifyView mVerifyView;
    private BaseMode mBaseMode;

    public VerifyViewMode(VerifyView mVerifyView) {
        this.mVerifyView = mVerifyView;
        mBaseMode = new BaseMode();
    }

    public void getVerifyCode(String mobile){

        String url = ApiUrl.UserApi.Verify;
        mVerifyView.showProgress();
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mVerifyView.onVerifyCodeResult(result);
                mVerifyView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mVerifyView.hideProgress();
                mVerifyView.showLoadFailMsg(result.toString());
            }
        });

    }
}
