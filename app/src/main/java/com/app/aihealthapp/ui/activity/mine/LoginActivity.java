package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.app.aihealthapp.R;
import com.app.aihealthapp.confing.AppConfig;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.kprogresshud.KProgressHUD;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.bean.UserInfoBean;
import com.app.aihealthapp.ui.mvvm.view.LoginView;
import com.app.aihealthapp.ui.mvvm.viewmode.LoginViewMode;
import com.app.aihealthapp.util.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：登录
 * @Author：Chen
 * @Date：2019/7/26 0:06
 * 修改人：Chen
 * 修改时间：2019/7/26 0:06
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.edit_input_phone)
    EditText edit_input_phone;
    @BindView(R.id.edit_input_pass)
    EditText edit_input_pass;

    @BindView(R.id.tv_forget_password)
    TextView tv_forget_password;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.checkbox_agreed)
    CheckBox checkbox_agreed;
    @BindView(R.id.tv_user_protocol)
    TextView tv_user_protocol;
    @BindView(R.id.tv_privacy_protocol)
    TextView tv_privacy_protocol;
    private LoginViewMode mLoginViewMode;
    public KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 通过注解绑定控件
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置状态栏字体颜色颜色为黑色
//            StatusBarUtil.StatusBarLightMode(this);
        }
        initView();
    }


    private void initView(){

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setCustomView(popupView)
                .setLabel(getString(R.string.loading),this.getResources().getColor(R.color.white))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(true);

        mLoginViewMode = new LoginViewMode(this);
    }
    @OnClick({R.id.tv_register,R.id.btn_login,R.id.tv_forget_password,R.id.tv_user_protocol,R.id.tv_privacy_protocol})
    public void onClick(View v){
        if (v==tv_register){
            startActivity(new Intent(this,RegisterActivity.class));
        }else if (v==btn_login){
            if (TextUtils.isEmpty(edit_input_phone.getText().toString())){
                ToastyHelper.toastyNormal(this,"请输入手机号");
            }else if (TextUtils.isEmpty(edit_input_pass.getText().toString())){
                ToastyHelper.toastyNormal(this,"请输入密码");
            }else if (!checkbox_agreed.isChecked()){
                showLoadFailMsg("请同意用户协议");

            }else {
                mLoginViewMode.Login(edit_input_phone.getText().toString(),edit_input_pass.getText().toString());
            }
        }else if (v==tv_forget_password){
            startActivity(new Intent(this, WebActyivity.class).putExtra("url", ApiUrl.WebApi.FORGET_PASSWORD));
        }else if (v==tv_user_protocol){
            startActivity(new Intent(this, WebActyivity.class).putExtra("url", ApiUrl.WebApi.UserProtocol));
        }else if (v==tv_privacy_protocol){
            startActivity(new Intent(this, WebActyivity.class).putExtra("url", ApiUrl.WebApi.PrivacyProtocol));

        }
    }

    @Override
    public void onLoginResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String token = GsonHelper.GsonToString(data,"token");
            SharedPreferenceHelper.setUserToken(AppContext.getContext(),token);
            String user = GsonHelper.GsonToData(data,"user").toString();

            UserInfoBean mUserInfo = GsonHelper.GsonToBean(user,UserInfoBean.class);
            SharedPreferenceHelper.setUserInfo(AppContext.getContext(),mUserInfo);
            //登录成功重新设置地区当前默认选中
            SharedPreferenceHelper.setProvince(AppContext.getContext(), AppConfig.PROVINCE_DEF);
            SharedPreferenceHelper.setCityId(AppContext.getContext(),AppConfig.CITY_ID_DEF);
            SharedPreferenceHelper.setCity(AppContext.getContext(),AppConfig.CITY_DEF);
            SharedPreferenceHelper.setAreaId(AppContext.getContext(),AppConfig.AREA_ID_DEF);
            SharedPreferenceHelper.setArea(AppContext.getContext(),AppConfig.AREA_DEF);
            EventBusHelper.sendEvent(new Event(EventCode.Code.LOGIN_SUCCESS));
            showLoadFailMsg("登录成功");
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
