package com.app.aihealthapp.view.circledialog.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.app.aihealthapp.view.circledialog.scale.ScaleUtils;


/**
 * Created by hupei on 2017/3/29.
 */

class ScaleTextView extends AppCompatTextView {
    public ScaleTextView(Context context) {
        super(context);
        config();
    }

    private void config() {
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setHeight(int pixels) {
        int dimenHeight = ScaleUtils.scaleValue(pixels);
        super.setHeight(dimenHeight);
    }

    @Override
    public void setTextSize(float size) {
        int dimenTextSize = ScaleUtils.scaleValue((int) size);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize);
    }

    public void setAutoPadding(int left, int top, int right, int bottom) {
        int dimenLeft = ScaleUtils.scaleValue(left);
        int dimenTop = ScaleUtils.scaleValue(top);
        int dimenRight = ScaleUtils.scaleValue(right);
        int dimenBottom = ScaleUtils.scaleValue(bottom);
        super.setPadding(dimenLeft, dimenTop, dimenRight, dimenBottom);
    }
}
