package com.app.aihealthapp.core.uitrarefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.app.aihealthapp.core.uitrarefresh.ptr.PtrFrameLayout;


/**
 * Created by Administrator on 2017/1/24.
 * 下拉刷新
 */

public class UTRefreshLayout extends PtrFrameLayout {

    //拉刷新header
    UTRefreshHeader mUtRefreshHeader;
    private boolean disallowInterceptTouchEvent = false;

    public UTRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public UTRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public UTRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context){
        //初始化上面的头View
        mUtRefreshHeader = new UTRefreshHeader(context);
        //这里设置头View为上面自定义的头View
        setHeaderView(mUtRefreshHeader);
        // 下拉和刷新状态监听：
        // 因为ParallaxHeader已经实现过PtrUIHandler接口，所以直接设置为ParallaxHeader：
        addPtrUIHandler(mUtRefreshHeader);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        disallowInterceptTouchEvent = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:
                //解除刷新的屏蔽
                this.requestDisallowInterceptTouchEvent(false);
                break;
        }
        if (disallowInterceptTouchEvent) {
            //事件向下分发给子控件，子控件会屏蔽掉父控件的刷新
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }
}
