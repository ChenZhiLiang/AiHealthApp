package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.HealthAskView;

import java.io.File;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/20 20:37
 * 修改人：Chen
 * 修改时间：2019/8/20 20:37
 */
public class HealthAskViewMode {

    private HealthAskView mHealthAskView;
    private BaseMode mBaseMode;

    public HealthAskViewMode(HealthAskView mHealthAskView) {
        this.mHealthAskView = mHealthAskView;
        mBaseMode = new BaseMode();
    }



    public void question(int doctor_id,String info,int kind_type,String checklist_pic,String medical_pic,String affected_part_pic,String other_pic){

        mHealthAskView.showProgress();
        String url = ApiUrl.DeviceApi.Question;
        RequestParams params = new RequestParams();
        params.put("doctor_id",String.valueOf(doctor_id));
        params.put("info",info);
        params.put("kind_type",String.valueOf(kind_type));
        params.put("checklist_pic",checklist_pic);
        params.put("medical_pic",medical_pic);
        params.put("affected_part_pic",affected_part_pic);
        params.put("other_pic",other_pic);


        mBaseMode.PostRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mHealthAskView.questionResult(result);
                mHealthAskView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {

                mHealthAskView.hideProgress();
                mHealthAskView.showLoadFailMsg(result.toString());
            }
        });
    }
    /**
     *  @author
     *  @time
     *  @describe 上传图片
     */
    public void uploadHead(File avatar){
        mHealthAskView.showProgress();
        String url = ApiUrl.HomeApi.Upload;
        RequestParams params = new RequestParams();
        params.fileParams.put("pic",avatar);
        mBaseMode.MultiPostRequest(url,params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mHealthAskView.uploadResult(result);
                mHealthAskView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mHealthAskView.hideProgress();
                mHealthAskView.showLoadFailMsg(result.toString());
            }
        });
    }
}
