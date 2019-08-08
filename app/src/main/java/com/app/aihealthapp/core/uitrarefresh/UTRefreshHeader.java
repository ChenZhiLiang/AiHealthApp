package com.app.aihealthapp.core.uitrarefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrFrameLayout;
import com.app.aihealthapp.core.uitrarefresh.ptr.PtrUIHandler;
import com.app.aihealthapp.core.uitrarefresh.ptr.indicator.PtrIndicator;
import com.app.aihealthapp.core.xrecyclerview.ProgressStyle;
import com.app.aihealthapp.core.xrecyclerview.SimpleViewSwitcher;
import com.app.aihealthapp.core.xrecyclerview.progressindicator.AVLoadingIndicatorView;


/**
 * Created by Administrator on 2017/1/24.
 * 下拉刷新header
 */

public class UTRefreshHeader extends FrameLayout implements PtrUIHandler {

    private ImageView mArrowImageView;
    private SimpleViewSwitcher mProgressBar;
    private TextView mStatusTextView;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private static final int ROTATE_ANIM_DURATION = 180;

    /**
     * 状态识别
     */
    private int mState;

    /**
     * 重置
     * 准备刷新
     * 开始刷新
     * 结束刷新
     */
    public static final int STATE_RESET = -1;
    public static final int STATE_PREPARE = 0;
    public static final int STATE_BEGIN = 1;
    public static final int STATE_FINISH = 2;


    public UTRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public UTRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public UTRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化view
     */
    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.ut_refresh_header_view, this, false);
        mStatusTextView = (TextView) view.findViewById(R.id.refresh_status_textview);
        mArrowImageView = (ImageView) view.findViewById(R.id.listview_header_arrow);
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        mProgressBar = (SimpleViewSwitcher) view.findViewById(R.id.listview_header_progressbar);
        addView(view);
    }

    /**
     * 重置头View的动画状态，一般停止刷新动画
     * @param frame
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
       mState = STATE_RESET;
    }

    /**
     * 准备刷新的UI。
     * @param frame
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
       mState =STATE_PREPARE;
    }

    /**
     * 开始刷新的UI动画
     * @param frame
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mState = STATE_BEGIN;
    }

    /**
     * 刷新完成，停止刷新动画
     * @param frame
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mState = STATE_FINISH;
    }

    /**
     * 手指下拉的时候的状态，我们的下拉动画的控制就是通过这个方法：
     * @param frame 刷新的root layout
     * @param isUnderTouch 手指是否按下，因为还有自动刷新，手指肯定是松开状态
     * @param status 现在的加载状态，准备、加载中、完成：PREPARE、LOADING、COMPLETE
     * @param ptrIndicator 下拉偏移量的参数封装
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //处理提醒字体
        switch (mState) {
            case STATE_PREPARE:
                if (ptrIndicator.getCurrentPercent() < 1.2) {
                    if (mState == STATE_BEGIN) {
                        mArrowImageView.startAnimation(mRotateDownAnim);
                    }
                    if (mState == STATE_PREPARE) {
                        mArrowImageView.clearAnimation();
                    }
                    mStatusTextView.setText(R.string.listview_header_hint_normal);
                } else {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText(R.string.listview_header_hint_release);
                }
                break;
            case STATE_BEGIN:
                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(View.INVISIBLE);
                AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
                progressView.setIndicatorColor(0xffB5B5B5);
                progressView.setIndicatorId(ProgressStyle.SysProgress);
                mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setView(progressView);
                mStatusTextView.setText(R.string.refreshing);
                break;
            case STATE_FINISH:
                mArrowImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mArrowImageView.setVisibility(View.VISIBLE);
                    }
                 },200);
                mProgressBar.setVisibility(View.INVISIBLE);
                mStatusTextView.setText(R.string.refresh_done);
                break;
        }
    }
}
