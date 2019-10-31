package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.bean.AdvListBean;
import com.app.aihealthapp.ui.mvvm.view.RegisterView;
import com.app.aihealthapp.ui.mvvm.view.VerifyView;
import com.app.aihealthapp.ui.mvvm.viewmode.RegisterViewMode;
import com.app.aihealthapp.ui.mvvm.viewmode.VerifyViewMode;
import com.app.aihealthapp.util.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：注册
 * @Author：Chen
 * @Date：2019/7/26 0:27
 * 修改人：Chen
 * 修改时间：2019/7/26 0:27
 */
public class RegisterActivity extends BaseActivity implements VerifyView , RegisterView {

    @BindView(R.id.image_logo)
    ImageView image_logo;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.edit_input_phone)
    EditText edit_input_phone;
    @BindView(R.id.edit_input_verify)
    EditText edit_input_verify;
    @BindView(R.id.tv_get_verify)
    TextView tv_get_verify;
    @BindView(R.id.edit_input_pass)
    EditText edit_input_pass;
    @BindView(R.id.edit_input_again_pass)
    EditText edit_input_again_pass;

    @BindView(R.id.edit_input_invite_code)
    EditText edit_input_invite_code;
    @BindView(R.id.checkbox_agreed)
    CheckBox checkbox_agreed;
    @BindView(R.id.tv_user_protocol)
    TextView tv_user_protocol;
    @BindView(R.id.tv_privacy_protocol)
    TextView tv_privacy_protocol;
    @BindView(R.id.btn_register)
    Button btn_register;


    private VerifyViewMode mVerifyViewMode;
    private RegisterViewMode mRegisterViewMode;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("用户注册");
    }

    @Override
    public void initView() {

        mVerifyViewMode = new VerifyViewMode(this);
        mRegisterViewMode = new RegisterViewMode(this);
    }

    @Override
    public void initData() {
        mRegisterViewMode.getAds(5);
    }

    @OnClick({R.id.tv_get_verify,R.id.btn_register,R.id.tv_user_protocol,R.id.tv_privacy_protocol})
    public void onClick(View v){
        if (v==tv_get_verify){
            if (TextUtils.isEmpty(edit_input_phone.getText().toString())){
                ToastyHelper.toastyNormal(this,"请输入手机号");
            }else {
                mVerifyViewMode.getVerifyCode(edit_input_phone.getText().toString());
            }
        }else if (v==btn_register){
            if (TextUtils.isEmpty(edit_input_phone.getText().toString())){
                ToastyHelper.toastyNormal(this,"请输入手机号");
            }else if (TextUtils.isEmpty(edit_input_verify.getText().toString())){
               showLoadFailMsg("请输入验证码");
            }else if (TextUtils.isEmpty(edit_input_pass.getText().toString())){
                showLoadFailMsg("请输入密码");
            }else if (TextUtils.isEmpty(edit_input_again_pass.getText().toString())){
                showLoadFailMsg("请确认密码");
            }else if (!edit_input_pass.getText().toString().equals(edit_input_again_pass.getText().toString())){
                showLoadFailMsg("两次密码不一致，请重新输入");
            }else if (!checkbox_agreed.isChecked()){
                showLoadFailMsg("请同意用户协议");

            }else{
                mRegisterViewMode.register(edit_input_phone.getText().toString(),edit_input_pass.getText().toString()
                        ,edit_input_verify.getText().toString(),edit_input_invite_code.getText().toString());
            }
        }else if (v==tv_user_protocol){
            startActivity(new Intent(this, WebActyivity.class).putExtra("url", ApiUrl.WebApi.UserProtocol));
        }else if (v==tv_privacy_protocol){
            startActivity(new Intent(this, WebActyivity.class).putExtra("url", ApiUrl.WebApi.PrivacyProtocol));

        }
    }

    @Override
    public void onVerifyCodeResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String code = GsonHelper.GsonToString(data,"code");
            showLoadFailMsg("验证码已发送，请注意查收");
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(this, 60000, 1000, tv_get_verify);
            mCountDownTimerUtils.start();
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }


    @Override
    public void onRegisterResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            showLoadFailMsg("注册成功，请登录");
            finish();
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));

        }
    }

    @Override
    public void AdsResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String path = GsonHelper.GsonToString(data,"pic");
            String title = GsonHelper.GsonToString(data,"title");
            String info = GsonHelper.GsonToString(data,"info");
            GlideHelper.loadImageView(this,path,image_logo);
            tv_title.setText(title);
            tv_content.setText(info);

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
