package com.app.aihealthapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.app.aihealthapp.R;
import com.app.aihealthapp.confing.AppConfig;
import com.app.aihealthapp.util.StatusBarUtil;

/**
 * 欢迎页Activity
 */
public class SplashActivity extends AppCompatActivity {

    private boolean isFirstIn = false;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟2秒
    private static final long SPLASH_DELAY_MILLIS = 1500;

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goMain();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//           //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置状态栏字体颜色颜色为黑色
            StatusBarUtil.StatusBarLightMode(this);
        }
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        setContentView(R.layout.acty_splash);


        initAPP();
    }

    private void initAPP() {
        mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);

    }
    /**
     * 进入主界面
     */
    private void goMain(){
        //将参数取出，传递到MainActivity中
        Intent intent = new Intent(this,MainActivity.class);
        if(getIntent().getBundleExtra("EXTRA_BUNDLE") != null){
            intent.putExtra("EXTRA_BUNDLE", getIntent().getBundleExtra("EXTRA_BUNDLE"));
        }
        startActivity(intent);
        finish();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
