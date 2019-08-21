package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.bean.SearchRecordBean;
import com.app.aihealthapp.ui.mvvm.view.SearchRecordView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 22:07
 * 修改人：Chen
 * 修改时间：2019/8/19 22:07
 */
public class SearchRecordViewMode {

    private SearchRecordView mSearchRecordView;
    private BaseMode mBaseMode;

    public SearchRecordViewMode(SearchRecordView mSearchRecordView) {
        this.mSearchRecordView = mSearchRecordView;
        mBaseMode = new BaseMode();
    }


    public void SearchRecord(int page,boolean isShow){

        if (isShow){
            mSearchRecordView.showProgress();

        }
        String url = ApiUrl.UserApi.SearchRecord;
        RequestParams params = new RequestParams();
        params.put("page",String.valueOf(page));
        params.put("size",String.valueOf(10));
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mSearchRecordView.SearchRecordDatasResult(result);
                mSearchRecordView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mSearchRecordView.hideProgress();
                mSearchRecordView.showLoadFailMsg(result.toString());
            }
        });

    }
    public List<SearchRecordBean> getDatas(){

        List<SearchRecordBean> datas = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            SearchRecordBean mSearchRecordBean = new SearchRecordBean();
            datas.add(mSearchRecordBean);
        }
        return datas;
    }
}
