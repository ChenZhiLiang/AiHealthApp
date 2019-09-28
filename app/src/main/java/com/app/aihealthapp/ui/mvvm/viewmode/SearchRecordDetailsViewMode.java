package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.SearchRecordDetailsView;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/9/29 0:32
 * 修改人：Chen
 * 修改时间：2019/9/29 0:32
 */
public class SearchRecordDetailsViewMode {

    private SearchRecordDetailsView mSearchRecordDetailsView;
    private BaseMode mBaseMode;

    public SearchRecordDetailsViewMode(SearchRecordDetailsView mSearchRecordDetailsView) {
        this.mSearchRecordDetailsView = mSearchRecordDetailsView;
        this.mBaseMode = new BaseMode();
    }

    public void adviceComment(String id,String info){
        mSearchRecordDetailsView.showProgress();
        String url = ApiUrl.UserApi.AdviceReply;
        RequestParams params = new RequestParams();
        params.put("id",String.valueOf(id));
        int kind_type = UserHelper.getUserInfo().getKind_type()==0?1:0;
        params.put("kind_type",String.valueOf(kind_type) );
        params.put("info",info);
        mBaseMode.PostRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mSearchRecordDetailsView.AdviceCommentResult(result);
                mSearchRecordDetailsView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mSearchRecordDetailsView.hideProgress();
                mSearchRecordDetailsView.showLoadFailMsg(result.toString());
            }
        });

    }
}
