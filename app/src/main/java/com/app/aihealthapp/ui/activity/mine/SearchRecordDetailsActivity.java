package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：咨询详情
 * @Author：Chen
 * @Date：2019/8/19 22:32
 * 修改人：Chen
 * 修改时间：2019/8/19 22:32
 */
public class SearchRecordDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_reply)
    TextView tv_reply;
    @BindView(R.id.rt_send_msg)
    RelativeLayout rt_send_msg;
    @Override
    public int getLayoutId() {
        return R.layout.activity_search_record_details;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("咨询详情");

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_reply})
    public void onClick(View v){
        if (v==tv_reply){
            if (rt_send_msg.getVisibility()==View.GONE){
                rt_send_msg.setVisibility(View.VISIBLE);
            }
        }
    }
}
