package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.bean.InterrogationRecordBean;
import com.app.aihealthapp.ui.mvvm.view.InterrogationRecordView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：AiHealth
 * @Description：会员描述信息
 * @Author：Chen
 * @Date：2019/8/19 22:24
 * 修改人：Chen
 * 修改时间：2019/8/19 22:24
 */
public class InterrogationRecordViewMode {

    private InterrogationRecordView mInterrogationRecordView;
    private BaseMode mBaseMode;

    public InterrogationRecordViewMode(InterrogationRecordView mInterrogationRecordView) {
        this.mInterrogationRecordView = mInterrogationRecordView;
        mBaseMode = new BaseMode();
    }

    public void InterrogationRecord(int page,int kind_type,boolean isShow){
        if (isShow){
            mInterrogationRecordView.showProgress();
        }
        String url = ApiUrl.UserApi.InterrogationRecord;
        RequestParams params = new RequestParams();
        params.put("page",String.valueOf(page));
        params.put("size",String.valueOf(10));
        params.put("kind_type",String.valueOf(kind_type));//问诊记录

        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mInterrogationRecordView.InterrogationRecordResult(result);
                mInterrogationRecordView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mInterrogationRecordView.hideProgress();
                mInterrogationRecordView.showLoadFailMsg(result.toString());
            }
        });

    }

    public List<InterrogationRecordBean> getDatas(){
        List<InterrogationRecordBean> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            InterrogationRecordBean bean = new InterrogationRecordBean();
            datas.add(bean);
        }
        return datas;
    }
}
