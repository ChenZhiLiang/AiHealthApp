package com.app.aihealthapp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

import com.app.aihealthapp.R;

/**
 * @Author: chenzl
 * @ClassName: PrivacyAgreementDialog
 * @Description: java类作用描述
 * @CreateDate: 2020/1/20 12:53
 */
public class PrivacyAgreementDialog extends Dialog {
    public PrivacyAgreementDialog(Context context) {
        super(context,R.style.alert_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy_agreement);
        setCancelable(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
