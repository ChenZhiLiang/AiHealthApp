package com.app.aihealthapp.util;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import com.app.aihealthapp.R;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/17 23:07
 * 修改人：Chen
 * 修改时间：2019/8/17 23:07
 */
public class CountDownTimerUtils extends CountDownTimer {

    private TextView mTextView;
    private Context context;
    public CountDownTimerUtils(Context context, long millisInFuture, long countDownInterval, TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mTextView =mTextView;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("还剩"+"("+millisUntilFinished / 1000 + "秒)"); //设置倒计时时间
        mTextView.setTextColor(context.getResources().getColor(R.color.default_hint_color));
//        mButton.setBackgroundResource(R.drawable.bg_gray_border); //设置按钮为灰色，这时是不能点击的
        SpannableString spannableString = new SpannableString(mTextView.getText().toString()); //获取按钮上的文字
        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(span1, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(context.getResources().getColor(R.color.blue));
//        mButton.setBackgroundResource(R.drawable.bg_blue_border); //还原背景色
    }
}