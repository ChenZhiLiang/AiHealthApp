package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.CircleDialogHelper;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.mvvm.view.AuthenticationUserView;
import com.app.aihealthapp.ui.mvvm.viewmode.AuthenticationUserViewMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：认证会员
 * @Author：Chen
 * @Date：2019/7/26 20:31
 * 修改人：Chen
 * 修改时间：2019/7/26 20:31
 */
public class AuthenticationUserActivity extends BaseActivity implements AuthenticationUserView {

    @BindView(R.id.edit_input_name)
    EditText edit_input_name;
    @BindView(R.id.edit_input_nickname)
    EditText edit_input_nickname;
    @BindView(R.id.edit_input_height)
    EditText edit_input_height;
    @BindView(R.id.edit_input_weight)
    EditText edit_input_weight;
    @BindView(R.id.edit_input_card_no)
    EditText edit_input_card_no;
    @BindView(R.id.edit_input_bank_no)
    EditText edit_input_bank_no;
    @BindView(R.id.edit_input_bank_name)
    EditText edit_input_bank_name;
    @BindView(R.id.edit_input_alipay_no)
    EditText edit_input_alipay_no;
    @BindView(R.id.tv_uploading)
    TextView tv_uploading;
    @BindView(R.id.rt_sex)
    RelativeLayout rt_sex;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private AuthenticationUserViewMode mAuthenticationUserViewMode;
    @Override
    public int getLayoutId() {
        return R.layout.activity_authentication_user;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("实名认证");
    }

    @Override
    public void initView() {
        mAuthenticationUserViewMode = new AuthenticationUserViewMode(this);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.rt_sex,R.id.btn_submit})
    public void onClick(View v){
        if (v==rt_sex){
            CircleDialogHelper.ShowBottomDialog(this, getResources().getStringArray(R.array.sex), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position==0){
                       tv_sex.setText("男");
                    }else {
                        tv_sex.setText("女");
                    }
                }
            });
        }else if (v==btn_submit){
            if (TextUtils.isEmpty(edit_input_name.getText().toString())){
                showLoadFailMsg("请输入真实姓名");
            }else if (TextUtils.isEmpty(edit_input_nickname.getText().toString())){
                showLoadFailMsg("请输入昵称");
            }else if (TextUtils.isEmpty(edit_input_height.getText().toString())){
                showLoadFailMsg("请输入身高");
            }else if (TextUtils.isEmpty(edit_input_weight.getText().toString())){
                showLoadFailMsg("请输入体重");
            }else if (TextUtils.isEmpty(edit_input_card_no.getText().toString())){
                showLoadFailMsg("请输入身份证号");
            }else if (TextUtils.isEmpty(edit_input_bank_no.getText().toString())){
                showLoadFailMsg("请输入银行卡号");
            }else if (TextUtils.isEmpty(edit_input_bank_no.getText().toString())){
                showLoadFailMsg("请输入银行卡号");
            }else if (TextUtils.isEmpty(edit_input_alipay_no.getText().toString())){
                showLoadFailMsg("请输入支付宝账号");
            }else {
                mAuthenticationUserViewMode.AuthenticationUser(edit_input_name.getText().toString(),edit_input_nickname.getText().toString(),tv_sex.getText().toString(),
                        edit_input_height.getText().toString(),edit_input_weight.getText().toString(),edit_input_card_no.getText().toString(),edit_input_bank_name.getText().toString(),
                        edit_input_bank_no.getText().toString(),edit_input_alipay_no.getText().toString(),"123456");
            }
        }
    }

    @Override
    public void AuthenticationUserResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            showLoadFailMsg("实名认证成功");
            EventBusHelper.sendEvent(new Event(EventCode.Code.AUTHENTICATION_SUCCESS));
            finish();
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
