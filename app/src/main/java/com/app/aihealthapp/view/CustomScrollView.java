package com.app.aihealthapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * author：chenzl
 * Create time: 2018/3/30 0030 16:17
 * describe: 重写ScrollView  scrollview嵌套HorizontalScrollView卡顿现象解决
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class CustomScrollView extends ScrollView {
    private float mDownPosX = 0;
    private float mDownPosY = 0;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                if (deltaX > deltaY) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
