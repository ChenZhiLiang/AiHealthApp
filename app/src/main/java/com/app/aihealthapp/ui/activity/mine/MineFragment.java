package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.bean.UserInfoBean;
import com.app.aihealthapp.ui.mvvm.view.MineView;
import com.app.aihealthapp.ui.mvvm.viewmode.MineViewMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：我的
 * @Author：Chen
 * @Date：2019/7/24 22:08
 * 修改人：Chen
 * 修改时间：2019/7/24 22:08
 */
public class MineFragment extends BaseFragment implements MineView {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.image_head)
    ImageView image_head;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.btn_authentication)
    Button btn_authentication;
    @BindView(R.id.rt_my_key)
    RelativeLayout rt_my_key;//我的健康秘钥
    @BindView(R.id.rt_mine_device)
    RelativeLayout rt_mine_device;//我的健康设备
    @BindView(R.id.rt_mine_ask)
    RelativeLayout rt_mine_ask;//我的咨询
    @BindView(R.id.rt_medical_report)
    RelativeLayout rt_medical_report;//我的体检报告
    @BindView(R.id.rt_healthy_report)
    RelativeLayout rt_healthy_report;
    @BindView(R.id.rt_health_plan)
    RelativeLayout rt_health_plan;//我的健康方案

    @BindView(R.id.rt_myfriend_list)
    RelativeLayout rt_myfriend_list;//我的健康朋友圈
    @BindView(R.id.rt_about)
    RelativeLayout rt_about;//关于健康秘钥

    @BindView(R.id.rt_feedback)
    RelativeLayout rt_feedback;//帮助与反馈
    private MineViewMode mMineViewMode;
    public static MineFragment getInstance(String title) {
        MineFragment hf = new MineFragment();
        hf.mTitle = title;
        return hf;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        tv_title_bar.setText("个人中心");

        mMineViewMode = new MineViewMode(this);
    }

    @Override
    public void initData() {
        if (isLogin()){
            btn_authentication.setVisibility(View.VISIBLE);
            GlideHelper.loadHeadImageView(mActivity,UserHelper.getUserInfo().getAvatar(),image_head);
            if (UserHelper.getUserInfo().getIs_auth()==0){
                tv_user_name.setText(UserHelper.getUserInfo().getMobile());
                btn_authentication.setText("实名认证");
                btn_authentication.setEnabled(true);
            }else {
                tv_user_name.setText(UserHelper.getUserInfo().getNickname());
                btn_authentication.setText("已认证");
                btn_authentication.setEnabled(false);
            }
        }else {
            tv_user_name.setText("点击登录");
            btn_authentication.setVisibility(View.GONE);
        }
    }



    @Override
    public void loadingData() {
        if (isLogin()){
            mMineViewMode.getUserInfo();
        }
    }

    @OnClick({R.id.image_head,R.id.tv_user_name,R.id.btn_authentication,R.id.rt_my_key,R.id.rt_mine_device,R.id.rt_mine_ask,R.id.rt_medical_report,R.id.rt_healthy_report,
            R.id.rt_health_plan,R.id.rt_myfriend_list, R.id.rt_about,R.id.rt_feedback})
    public void onClick(View v){
        if (isLogin()){
            if (v==btn_authentication){
                startActivity(new Intent(getContext(),AuthenticationUserActivity.class));
            }else if (v==rt_my_key){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyKeyList));
            }else if (v==rt_mine_device){
                startActivity(new Intent(getContext(),MineDeviceActivity.class));
            }else if (v==rt_mine_ask){
                startActivity(new Intent(getContext(),MineAskActivity.class));
            }else if (v==rt_medical_report){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MedicalReport));
            }else if (v==rt_healthy_report){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Healthy_Report));

            }else if (v==rt_health_plan){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.HealthPlan));
            }else if (v==rt_myfriend_list){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyfriendList));
            }else if (v==rt_about){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.About));
            }else if (v==rt_feedback){
                startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Feedback));

            }
        }else {
            startActivity(new Intent(mActivity,LoginActivity.class));
        }
    }

    @Override
    public void UesrInfoResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            UserInfoBean mUserInfo = GsonHelper.GsonToBean(data,UserInfoBean.class);
            SharedPreferenceHelper.setUserInfo(AppContext.getContext(),mUserInfo);

            btn_authentication.setVisibility(View.VISIBLE);
            GlideHelper.loadHeadImageView(mActivity,UserHelper.getUserInfo().getAvatar(),image_head);
            if (TextUtils.isEmpty(UserHelper.getUserInfo().getNickname())){
                tv_user_name.setText(UserHelper.getUserInfo().getMobile());
                btn_authentication.setText("实名认证");
                btn_authentication.setEnabled(true);
            }else {
                tv_user_name.setText(UserHelper.getUserInfo().getNickname());
                btn_authentication.setText("已认证");
                btn_authentication.setEnabled(false);
            }
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }

    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {//登录成功
            btn_authentication.setVisibility(View.VISIBLE);
            GlideHelper.loadHeadImageView(mActivity,UserHelper.getUserInfo().getAvatar(),image_head);
            if (TextUtils.isEmpty(UserHelper.getUserInfo().getNickname())){
                tv_user_name.setText(UserHelper.getUserInfo().getMobile());
                btn_authentication.setText("实名认证");
                btn_authentication.setEnabled(true);
            }else {
                tv_user_name.setText(UserHelper.getUserInfo().getNickname());
                btn_authentication.setText("已认证");
                btn_authentication.setEnabled(false);
            }
        }else if (event.getCode()== EventCode.Code.AUTHENTICATION_SUCCESS){
            mMineViewMode.getUserInfo();

        } else if (event.getCode() == EventCode.Code.LOGOUT) {//注销
            GlideHelper.loadImageView(mActivity, R.mipmap.my_head, image_head);
            tv_user_name.setText("点击登录");
            btn_authentication.setVisibility(View.GONE);
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

        ToastyHelper.toastyNormal(mActivity,err);
    }
}
