package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.HomeView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/15 21:23
 * 修改人：Chen
 * 修改时间：2019/8/15 21:23
 */
public class HomeViewMode {

    private HomeView mHomeView;
    private BaseMode mBaseMode;

    public HomeViewMode(HomeView mHomeView) {
        this.mHomeView = mHomeView;
        mBaseMode = new BaseMode();
    }

    public void getHomeDatas(boolean isShow){

        if (isShow){
            mHomeView.showProgress();
        }
        String url = ApiUrl.HomeApi.Home;
        RequestParams params = new RequestParams();
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mHomeView.HomeDatasResult(result);
                mHomeView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mHomeView.hideProgress();
                mHomeView.showLoadFailMsg(result.toString());
            }
        });
    }
}
