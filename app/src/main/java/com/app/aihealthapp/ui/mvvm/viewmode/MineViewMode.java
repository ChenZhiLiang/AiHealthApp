package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.MineView;

import java.io.File;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/17 12:14
 * 修改人：Chen
 * 修改时间：2019/8/17 12:14
 */
public class MineViewMode {

    private MineView mMineView;
    private BaseMode mBaseMode;

    public MineViewMode(MineView mMineView) {
        this.mMineView = mMineView;
        mBaseMode = new BaseMode();
    }


    public void getUserInfo(){

        String url = ApiUrl.UserApi.UserInfo;
//        mMineView.showProgress();
        mBaseMode.GetRequest(url, null, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMineView.UesrInfoResult(result);
//                mMineView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
//                mMineView.hideProgress();
                mMineView.showLoadFailMsg(result.toString());
            }
        });
    }
    /**
     *  @author
     *  @time
     *  @describe 上传图片
     */
    public void uploadHead(File avatar){
        mMineView.showProgress();
        String url = ApiUrl.HomeApi.Upload;
        RequestParams params = new RequestParams();
        params.fileParams.put("pic",avatar);
        mBaseMode.MultiPostRequest(url,params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMineView.uploadResult(result);
                mMineView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mMineView.hideProgress();
                mMineView.showLoadFailMsg(result.toString());
            }
        });
    }

    public void UpdateProfile(String avatar){
        mMineView.showProgress();
        String url = ApiUrl.UserApi.UpdateProfile;
        RequestParams params = new RequestParams();
        params.put("avatar",avatar);
        mBaseMode.GetRequest(url,params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMineView.UpdateProfileResult(result);
                mMineView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mMineView.hideProgress();
                mMineView.showLoadFailMsg(result.toString());
            }
        });

    }

    public void GetVersionInfo(){
        mMineView.showProgress();

        String url = ApiUrl.UserApi.UpdateVersion;
        RequestParams params = new RequestParams();
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mMineView.versionInfoResult(result);
                mMineView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mMineView.showLoadFailMsg(result.toString());
                mMineView.hideProgress();
            }
        });
    }
}
