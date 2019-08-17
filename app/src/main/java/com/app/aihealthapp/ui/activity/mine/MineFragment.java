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
import com.app.aihealthapp.ui.AppContext;
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
    @BindView(R.id.rt_mine_device)
    RelativeLayout rt_mine_device;

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

    @OnClick({R.id.image_head,R.id.btn_authentication,R.id.rt_mine_device})
    public void onClick(View v){
        if (isLogin()){
            if (v==btn_authentication){
                startActivity(new Intent(getContext(),AuthenticationUserActivity.class));
            }else if (v==rt_mine_device){
                startActivity(new Intent(getContext(),MineDeviceActivity.class));
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
