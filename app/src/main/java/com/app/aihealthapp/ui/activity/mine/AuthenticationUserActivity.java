package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.CircleDialogHelper;

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
public class AuthenticationUserActivity extends BaseActivity {

    @BindView(R.id.rt_sex)
    RelativeLayout rt_sex;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
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

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.rt_sex})
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
        }
    }
}
