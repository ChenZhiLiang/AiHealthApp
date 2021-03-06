package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.AuthenticationUserView;

import java.io.File;

/**
 * @Name：AiHealth
 * @Description：实名认证
 * @Author：Chen
 * @Date：2019/8/18 0:11
 * 修改人：Chen
 * 修改时间：2019/8/18 0:11
 */
public class AuthenticationUserViewMode {

    private AuthenticationUserView mAuthenticationUserView;
    private BaseMode mBaseMode;

    public AuthenticationUserViewMode(AuthenticationUserView mAuthenticationUserView) {
        this.mAuthenticationUserView = mAuthenticationUserView;
        mBaseMode = new BaseMode();
    }

    public void getUserInfo(){

        String url = ApiUrl.UserApi.UserInfo;
//        mMineView.showProgress();
        mBaseMode.GetRequest(url, null, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mAuthenticationUserView.UesrInfoResult(result);
//                mMineView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
//                mMineView.hideProgress();
                mAuthenticationUserView.showLoadFailMsg(result.toString());
            }
        });
    }

    public void AuthenticationUser(String realname,String age,String nickname,int sex,String height,String weight,String card_no,String bank_name,String bank_no,
                                   String alipay_no,String alipay_pay_pic){

        mAuthenticationUserView.showProgress();
        String url = ApiUrl.UserApi.Authentication;
        RequestParams params = new RequestParams();
        params.put("realname",realname);
        params.put("age",age);
        params.put("nickname",nickname);
        params.put("sex",String.valueOf(sex));
        params.put("height",height);
        params.put("weight",weight);
        params.put("card_no",card_no);
        params.put("bank_name",bank_name);
        params.put("bank_no",bank_no);
        params.put("alipay_no",alipay_no);
        params.put("alipay_pay_pic",alipay_pay_pic);

        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mAuthenticationUserView.AuthenticationUserResult(result);
                mAuthenticationUserView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mAuthenticationUserView.hideProgress();
                mAuthenticationUserView.showLoadFailMsg(result.toString());
            }
        });
    }
    public void getAds(int stype){

        mAuthenticationUserView.showProgress();
        String url = ApiUrl.UserApi.OneAds;
        RequestParams params = new RequestParams();
        params.put("stype",String.valueOf(stype));
        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mAuthenticationUserView.AdsResult(result);
                mAuthenticationUserView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mAuthenticationUserView.hideProgress();
                mAuthenticationUserView.showLoadFailMsg(result.toString());
            }
        });

    }
    /**
     *  @author
     *  @time
     *  @describe 上传图片
     */
    public void uploadHead(File avatar){
        mAuthenticationUserView.showProgress();
        String url = ApiUrl.HomeApi.Upload;
        RequestParams params = new RequestParams();
        params.fileParams.put("pic",avatar);
        mBaseMode.MultiPostRequest(url,params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mAuthenticationUserView.uploadResult(result);
                mAuthenticationUserView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mAuthenticationUserView.hideProgress();
                mAuthenticationUserView.showLoadFailMsg(result.toString());
            }
        });
    }
}
