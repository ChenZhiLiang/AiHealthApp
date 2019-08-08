package com.app.aihealthapp.ui.activity.home;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：测量页面
 * @Author：Chen
 * @Date：2019/8/8 21:12
 * 修改人：Chen
 * 修改时间：2019/8/8 21:12
 */
public class MeasureActivity extends Activity {

    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_measure);

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
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
        tv_title_bar.setText(getIntent().getStringExtra("title"));
    }

    @OnClick({R.id.img_back})
    public void onClick(View v){
        if (v==img_back){
            finish();
        }
    }
}
