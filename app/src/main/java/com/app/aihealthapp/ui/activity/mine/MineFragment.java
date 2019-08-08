package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;

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
public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.btn_authentication)
    Button btn_authentication;
    @BindView(R.id.rt_mine_device)
    RelativeLayout rt_mine_device;
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

    }

    @Override
    public void initData() {

    }
    @Override
    public void loadingData() {

    }

    @OnClick({R.id.btn_authentication,R.id.rt_mine_device})
    public void onClick(View v){
        if (v==btn_authentication){
            startActivity(new Intent(getContext(),AuthenticationUserActivity.class));
        }else if (v==rt_mine_device){

            startActivity(new Intent(getContext(),MineDeviceActivity.class));
        }
    }
}
