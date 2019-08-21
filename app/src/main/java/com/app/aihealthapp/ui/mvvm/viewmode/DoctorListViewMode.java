package com.app.aihealthapp.ui.mvvm.viewmode;

import android.text.TextUtils;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.bean.DoctorListBean;
import com.app.aihealthapp.ui.mvvm.view.DoctorListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 11:23
 * 修改人：Chen
 * 修改时间：2019/8/18 11:23
 */
public class DoctorListViewMode {

    private DoctorListView mDoctorListView;
    private BaseMode mBaseMode;

    public DoctorListViewMode(DoctorListView mDoctorListView) {
        this.mDoctorListView = mDoctorListView;
        mBaseMode = new BaseMode();
    }


    public void getDoctorList(int page,String keyword,int cate_id,boolean isShow){

        if (isShow){
            mDoctorListView.showProgress();

        }
        String url = ApiUrl.DoctorApi.DoctorList;
        RequestParams params = new RequestParams();
        params.put("size",String.valueOf(10));
        if (!TextUtils.isEmpty(keyword)){
            params.put("keyword",keyword);
        }
        params.put("page",String.valueOf(page));
        params.put("cate_id",String.valueOf(cate_id));

        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mDoctorListView.DoctorListResult(result);
                mDoctorListView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mDoctorListView.hideProgress();
                mDoctorListView.showLoadFailMsg(result.toString());
            }
        });


    }
    public List<DoctorListBean> getdatas(){
        List<DoctorListBean> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DoctorListBean mDoctorListBean = new DoctorListBean();
            datas.add(mDoctorListBean);
        }
        return datas;
    }
}
