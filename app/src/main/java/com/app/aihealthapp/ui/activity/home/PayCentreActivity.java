package com.app.aihealthapp.ui.activity.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.adapter.PaymentModeAdapter;
import com.app.aihealthapp.ui.mvvm.view.PayCentreView;
import com.app.aihealthapp.ui.mvvm.viewmode.PayCentreViewMode;
import com.app.aihealthapp.ui.mvvm.viewmode.SelectViewMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：支付中心
 * @Author：Chen
 * @Date：2019/8/21 23:23
 * 修改人：Chen
 * 修改时间：2019/8/21 23:23
 */
public class PayCentreActivity extends BaseActivity implements PayCentreView {

    @BindView(R.id.tv_doctor_name)
    TextView tv_doctor_name;
    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.payment_mode_layout)
    RecyclerView payment_mode_layout;
    @BindView(R.id.btn_pay)
    Button btn_pay;
    private PaymentModeAdapter paymentModeAdapter;

    private PayCentreViewMode mPayCentreViewMode;
    private int doctor_id;

    private String doctor_name;
    private String advice_price;
    private int pay_type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_centre;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("支付中心");
    }

    @Override
    public void initView() {
        payment_mode_layout.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器 为线性布局
        paymentModeAdapter = new PaymentModeAdapter(SelectViewMode.getDatas(), this);
        payment_mode_layout.setAdapter(paymentModeAdapter);

        doctor_id = getIntent().getIntExtra("doctor_id",0);
        advice_price =getIntent().getStringExtra("advice_price");
        doctor_name = getIntent().getStringExtra("doctor_name");
        tv_doctor_name.setText("咨询医生："+doctor_name);
        tv_price.setText("¥"+advice_price);

        mPayCentreViewMode = new PayCentreViewMode(this);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_pay})
    public void onClick(View v){
        if (v==btn_pay){
            if (paymentModeAdapter.getSelectedPos() == 0){
                pay_type = 2;
            }else if (paymentModeAdapter.getSelectedPos() == 1){
                pay_type=1;
            }else if (paymentModeAdapter.getSelectedPos() == 2){
                pay_type=3;
            }
            mPayCentreViewMode.buy(doctor_id,advice_price,pay_type);
        }
    }

    @Override
    public void BuyResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String order_no = GsonHelper.GsonToString(data,"order_no");
            mPayCentreViewMode.pay(order_no,pay_type);
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void PayResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
//            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
//            String order_no = GsonHelper.GsonToString(data,"order_no");
//            mPayCentreViewMode.pay(order_no,pay_type);
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void showProgress() {

        hud.show();
    }

    @Override
    public void hideProgress() {

        hud.dismiss();
    }

    @Override
    public void showLoadFailMsg(String err) {

        ToastyHelper.toastyNormal(this,err);
    }
}
