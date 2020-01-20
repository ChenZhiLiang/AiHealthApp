package com.app.aihealthapp.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.WebActyivity;

/**
 * @Author: chenzl
 * @ClassName: PrivacyAgreementDialog
 * @Description: java类作用描述
 * @CreateDate: 2020/1/20 12:53
 */
public class PrivacyAgreementDialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private TextView tvPrivacyProtocol;
    private Button btnCancel;
    private Button btnOk;
    private OnAgreeInterface mOnAgreeInterface;
    public PrivacyAgreementDialog(Activity mActivity,OnAgreeInterface mOnAgreeInterface) {
        super(mActivity,R.style.alert_dialog);
        this.mActivity = mActivity;
        this.mOnAgreeInterface = mOnAgreeInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy_agreement);
        setCancelable(false);
        // 将对话框的大小按屏幕大小的百分比设置
        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int)(display.getWidth() * 0.8); //设置宽度
        getWindow().setAttributes(lp);
        initView();
    }

    private void initView(){
        tvPrivacyProtocol = findViewById(R.id.tv_privacy_protocol);
        SpannableString span = new SpannableString(getContext().getString(R.string.privacy_agreement_hint));
        span.setSpan(new MyCheckTextView(mActivity,MyCheckTextView.PRIVACY),118,124 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new MyCheckTextView(mActivity,MyCheckTextView.AGRREEMENT),125,131 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrivacyProtocol.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        tvPrivacyProtocol.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
        tvPrivacyProtocol.setText(span);

        btnCancel = findViewById(R.id.btn_cancel);
        btnOk = findViewById(R.id.btn_ok);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (v==btnCancel){
            mOnAgreeInterface.isAgree(false);
        }else if (v==btnOk){
            mOnAgreeInterface.isAgree(true);
        }
        dismiss();
    }

    public class MyCheckTextView extends ClickableSpan{
        public static final int PRIVACY = 1;
        public static final int AGRREEMENT = 2;
        private Context context;
        private int status;
        public MyCheckTextView(Context context,int status) {
            this.context = context;
            this.status =status;
        }
        @Override
        public void onClick(View widget) {
            if (status==PRIVACY){
                context.startActivity(new Intent(context, WebActyivity.class).putExtra("url", ApiUrl.WebApi.UserProtocol));
            }else {
                context.startActivity(new Intent(context, WebActyivity.class).putExtra("url", ApiUrl.WebApi.PrivacyProtocol));
            }

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getContext().getResources().getColor(R.color.default_text_color));
            ds.setUnderlineText(false);
        }
    }

    public interface OnAgreeInterface{
        void isAgree(boolean b);
    }
}
