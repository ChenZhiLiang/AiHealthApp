package com.app.aihealthapp.ui.activity.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.adapter.PaymentModeAdapter;
import com.app.aihealthapp.ui.bean.PaymentBean;
import com.app.aihealthapp.ui.mvvm.view.PayCentreView;
import com.app.aihealthapp.ui.mvvm.viewmode.PayCentreViewMode;
import com.app.aihealthapp.ui.mvvm.viewmode.SelectViewMode;
import com.app.aihealthapp.util.PayUtils;

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
    @BindView(R.id.ll_password)
    LinearLayout ll_password;
    @BindView(R.id.edit_passowrd)
    EditText edit_passowrd;
    @BindView(R.id.btn_pay)
    Button btn_pay;
    private PaymentModeAdapter paymentModeAdapter;

    private PayCentreViewMode mPayCentreViewMode;
    private int doctor_id;

    private String doctor_name;
    private int advice_price;
    private String price_hek;
    private int pay_type;
    private PayUtils mPayUtil;//支付工具

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

        doctor_id = getIntent().getIntExtra("doctor_id",0);
        advice_price =getIntent().getIntExtra("advice_price",0);
        price_hek = getIntent().getStringExtra("price_hek");
        doctor_name = getIntent().getStringExtra("doctor_name");
        tv_doctor_name.setText("咨询医生："+doctor_name);
        tv_price.setText(price_hek);

        payment_mode_layout.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器 为线性布局
        paymentModeAdapter = new PaymentModeAdapter(advice_price>0?SelectViewMode.getCashPayDatas():SelectViewMode.getKeyPayDatas(), this);
        if (advice_price>0){
            ll_password.setVisibility(View.GONE);
        }else {
            ll_password.setVisibility(View.VISIBLE);
        }
        payment_mode_layout.setAdapter(paymentModeAdapter);

        mPayCentreViewMode = new PayCentreViewMode(this);
        mPayUtil = new PayUtils(this);//初始化支付工具

        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("action.pay.success1");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_pay})
    public void onClick(View v){
        if (v==btn_pay){
            if (advice_price>0){
                if (paymentModeAdapter.getSelectedPos() == 0){
                    pay_type = 2;
                }else if (paymentModeAdapter.getSelectedPos() == 1){
                    pay_type=1;
                }
                mPayCentreViewMode.buy(doctor_id,pay_type,"");

            }else {
                if (TextUtils.isEmpty(edit_passowrd.getText().toString())){
                    showLoadFailMsg("请输入登录密码");
                }else {
                    pay_type=3;
                    mPayCentreViewMode.buy(doctor_id,pay_type,edit_passowrd.getText().toString());
                }
            }
        }
    }

    @Override
    public void BuyResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String order_no = GsonHelper.GsonToString(data,"order_no");
            if (advice_price>0){
                mPayCentreViewMode.pay(order_no,pay_type,"");
            }else {
                mPayCentreViewMode.pay(order_no,pay_type,edit_passowrd.getText().toString());
            }
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void PayResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            if(pay_type==1){//支付宝支付
                String data = GsonHelper.GsonToData(result.toString(),"data").toString();
                String alipay_sdk  = GsonHelper.GsonToString(data,"alipay_str");
                mPayUtil.Alipay(alipay_sdk);
            }else if (pay_type==2){//微信支付
                String data = GsonHelper.GsonToData(result.toString(),"data").toString();
                PaymentBean paymentBean = GsonHelper.GsonToBean(data,PaymentBean.class);
                mPayUtil.WXPay(paymentBean);//调用微信支付
            }else {//密钥支付
                showLoadFailMsg("密钥支付成功");
                startActivity(new Intent(PayCentreActivity.this,HealthAskActivity.class).putExtra("doctor_id",doctor_id));
            }
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    private BroadcastReceiver mRefreshBroadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.pay.success1")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastyHelper.toastyNormal(PayCentreActivity.this, "支付成功");
                        startActivity(new Intent(PayCentreActivity.this,HealthAskActivity.class).putExtra("doctor_id",doctor_id));
                        finish();
                    }
                },100);

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
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
