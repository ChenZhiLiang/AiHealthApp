package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.InterrogationDetailsView;

public class InterrogationDetailsViewMode {

    private InterrogationDetailsView mInterrogationDetailsView;
    private BaseMode mBaseMode;

    public InterrogationDetailsViewMode(InterrogationDetailsView mInterrogationDetailsView) {
        this.mInterrogationDetailsView = mInterrogationDetailsView;
        mBaseMode = new BaseMode();
    }


    public void adviceComment(int id,String info){
        mInterrogationDetailsView.showProgress();
        String url = ApiUrl.UserApi.AdviceComment;
        RequestParams params = new RequestParams();
        params.put("id",String.valueOf(id));
        params.put("info",info);
        mBaseMode.PostRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mInterrogationDetailsView.AdviceCommentResult(result);
                mInterrogationDetailsView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mInterrogationDetailsView.hideProgress();
                mInterrogationDetailsView.showLoadFailMsg(result.toString());
            }
        });

    }
    public void getAdvisoryDetails(String id,boolean isShow){
        mInterrogationDetailsView.showProgress();
        String url = ApiUrl.UserApi.AdviceDetail;
        RequestParams params = new RequestParams();
        params.put("id",id);
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mInterrogationDetailsView.AdvisoryDetailsResult(result);
                mInterrogationDetailsView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mInterrogationDetailsView.hideProgress();
                mInterrogationDetailsView.showLoadFailMsg(result.toString());
            }
        });
    }
}
