package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.DoctorDetalisView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 23:12
 * 修改人：Chen
 * 修改时间：2019/8/18 23:12
 */
public class DoctorDetalisViewMode {

    private DoctorDetalisView mDoctorDetalisView;
    private BaseMode mBaseMode;

    public DoctorDetalisViewMode(DoctorDetalisView mDoctorDetalisView) {
        this.mDoctorDetalisView = mDoctorDetalisView;
        mBaseMode = new BaseMode();
    }

    public void getDoctorDetalis(int id){

        mDoctorDetalisView.showProgress();
        String url = ApiUrl.DoctorApi.DoctorDetail;
        RequestParams params = new RequestParams();
        params.put("id",String.valueOf(id));
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mDoctorDetalisView.DoctorDetalisResult(result);
                mDoctorDetalisView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mDoctorDetalisView.hideProgress();
                mDoctorDetalisView.showLoadFailMsg(result.toString());
            }
        });
    }
}
