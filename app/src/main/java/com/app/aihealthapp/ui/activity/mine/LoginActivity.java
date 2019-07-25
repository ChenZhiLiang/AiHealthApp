package com.app.aihealthapp.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.util.StatusBarUtil;
import com.app.aihealthapp.view.toasty.Toasty;

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
public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.tv_register)
    TextView tv_register;
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
    }

    @OnClick({R.id.tv_register})
    public void onClick(View v){
        if (v==tv_register){
            startActivity(new Intent(this,RegisterActivity.class));
        }
    }

}
