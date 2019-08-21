package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.network.okhttp.callback.ResultCallback;
import com.app.aihealthapp.core.network.okhttp.request.RequestParams;
import com.app.aihealthapp.ui.mvvm.view.PayCentreView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/21 23:52
 * 修改人：Chen
 * 修改时间：2019/8/21 23:52
 */
public class PayCentreViewMode {

    private PayCentreView mPayCentreView;
    private BaseMode mBaseMode;

    public PayCentreViewMode(PayCentreView mPayCentreView) {
        this.mPayCentreView = mPayCentreView;
        mBaseMode = new BaseMode();
    }

    public void buy(int doctor_id,String advice_price,int pay_type){
        mPayCentreView.showProgress();
        String url = ApiUrl.HomeApi.Buy;
        RequestParams params = new RequestParams();
        params.put("doctor_id",String.valueOf(doctor_id));
        params.put("advice_price",advice_price);
        params.put("pay_type",String.valueOf(pay_type));

        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mPayCentreView.BuyResult(result);
                mPayCentreView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mPayCentreView.hideProgress();
                mPayCentreView.showLoadFailMsg(result.toString());
            }
        });
    }

    public void pay(String order_no,int pay_type){
        mPayCentreView.showProgress();
        String url = ApiUrl.HomeApi.Pay;
        RequestParams params = new RequestParams();
        params.put("order_no",order_no);
        params.put("pay_type",String.valueOf(pay_type));

        mBaseMode.GetRequest(url, params, new ResultCallback() {
            @Override
            public void onSuccess(Object result) {
                mPayCentreView.PayResult(result);
                mPayCentreView.hideProgress();
            }

            @Override
            public void onFailure(Object result) {
                mPayCentreView.hideProgress();
                mPayCentreView.showLoadFailMsg(result.toString());
            }
        });
    }
}
