package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.AdvisoryDetailsView;

public class AdvisoryDetailsViewMode  {

    private AdvisoryDetailsView mAdvisoryDetailsView;
    private BaseMode mBaseMode;

    public AdvisoryDetailsViewMode(AdvisoryDetailsView mAdvisoryDetailsView) {
        this.mAdvisoryDetailsView = mAdvisoryDetailsView;
        mBaseMode = new BaseMode();
    }


    public void getAdvisoryDetails(String id,boolean isShow){
        if (isShow){
            mAdvisoryDetailsView.showProgress();
        }
        String url = ApiUrl.UserApi.AdviceDetail;
        RequestParams params = new RequestParams();
        params.put("id",id);
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mAdvisoryDetailsView.AdvisoryDetailsResult(result);
                mAdvisoryDetailsView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mAdvisoryDetailsView.hideProgress();
                mAdvisoryDetailsView.showLoadFailMsg(result.toString());
            }
        });
    }
    public void adviceComment(String id,String info){
        mAdvisoryDetailsView.showProgress();
        String url = ApiUrl.UserApi.AdviceReply;
        RequestParams params = new RequestParams();
        params.put("id",String.valueOf(id));
        int kind_type = UserHelper.getUserInfo().getKind_type()==0?1:0;
        params.put("kind_type",String.valueOf(kind_type) );
        params.put("info",info);
        mBaseMode.PostRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mAdvisoryDetailsView.AdviceCommentResult(result);
                mAdvisoryDetailsView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mAdvisoryDetailsView.hideProgress();
                mAdvisoryDetailsView.showLoadFailMsg(result.toString());
            }
        });

    }
    public void getReplyList(String doctor_id,int size,String kind_type){
        mAdvisoryDetailsView.showProgress();
        String url = ApiUrl.UserApi.ReplyList;
        RequestParams params = new RequestParams();
        params.put("doctor_id",doctor_id);
        params.put("size",String.valueOf(size));
        params.put("kind_type",kind_type);

        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mAdvisoryDetailsView.ReplyListResult(result);
                mAdvisoryDetailsView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mAdvisoryDetailsView.hideProgress();
                mAdvisoryDetailsView.showLoadFailMsg(result.toString());
            }
        });
    }
}
