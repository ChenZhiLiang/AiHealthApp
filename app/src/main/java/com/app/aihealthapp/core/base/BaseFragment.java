package com.app.aihealthapp.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.interfaces.BaseFragmentInterface;
import com.app.aihealthapp.core.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/11.
 * Fragment基类
 */

public abstract class BaseFragment extends Fragment implements BaseFragmentInterface {

    protected Activity mActivity;
    public KProgressHUD hud;
    public String mTitle;

    protected boolean isViewInitiated;// 初始化view
    protected boolean isVisibleToUser;//fragment是否可见
    protected boolean isDataInitiated;//加载数据
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,view);
        if (isRegisterEventBus()) {
            EventBusHelper.register(this);
        }
        initView(view,savedInstanceState);
        initHud();
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }
    //查看这个fragment的可见状态
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }
    //在这个方法中写网络请求
    public abstract void loadingData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadingData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    /**
     * 初始化提示
     */
    private void initHud(){
//        View view = getLayoutInflater().inflate(R.layout.view_progress, null);
        hud = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setCustomView(popupView)
                .setLabel(getString(R.string.loading),this.getResources().getColor(R.color.white))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(true);
    }

    /**
     * 配置recycleview
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusHelper.unregister(this);
        }
    }


}
