package com.app.aihealthapp.core.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.interfaces.BaseViewInterface;
import com.app.aihealthapp.core.kprogresshud.KProgressHUD;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.util.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {

    /**
     * 上下文
     **/
    protected Context mContext;
    protected RelativeLayout toolbar;
    public KProgressHUD hud;
    private ImageView img_back;
    private TextView tv_title_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = getApplicationContext();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        if (isRegisterEventBus()) {
            EventBusHelper.register(this);
        }
        // 通过注解绑定控件
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置状态栏字体颜色颜色为黑色
            StatusBarUtil.StatusBarLightMode(this);
        }
        init(savedInstanceState);
        initView();
        initHud();
        initData();
    }


    protected void init(Bundle savedInstanceState) {}

    /**
     * 初始化提示
     */
    private void initHud() {
//        View popupView = getLayoutInflater().inflate(R.layout.layout_progress, null);
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setCustomView(popupView)
                .setLabel(getString(R.string.loading),this.getResources().getColor(R.color.white))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(true);
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            img_back = findViewById(R.id.img_back);
            tv_title_bar = findViewById(R.id.tv_title_bar);
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setTitle(String title) {
        if (toolbar != null) {
            tv_title_bar.setText(title);
        }
    }

    /**
     * 是否登录
     * @return
     */
    public boolean isLogin(){
        if (UserHelper.getUserInfo()!=null){
            return  true;
        }else{
            return false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /**
     * 配置recycleview
     *
     * @param recyclerView
     * @param layoutManager
     */
    public void configRecycleView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    @Subscribe
    protected void receiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContext != null) {
            mContext = null;
        }
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        if (isRegisterEventBus()) {
            EventBusHelper.unregister(this);
        }
    }


    /**
     * 重写getResources 方法 设置固定字体大小 ，不随系统字体改变
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        //非默认值
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
