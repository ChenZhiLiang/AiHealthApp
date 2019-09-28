package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.bean.SearchRecordBean;
import com.app.aihealthapp.ui.mvvm.view.SearchRecordDetailsView;
import com.app.aihealthapp.ui.mvvm.viewmode.SearchRecordDetailsViewMode;

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
public class SearchRecordDetailsActivity extends BaseActivity implements SearchRecordDetailsView {


    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.image_checklist_pic)
    ImageView image_checklist_pic;
    @BindView(R.id.image_medical_pic)
    ImageView image_medical_pic;
    @BindView(R.id.image_affected_part_pic)
    ImageView image_affected_part_pic;
    @BindView(R.id.image_other_pic)
    ImageView image_other_pic;

    @BindView(R.id.ll_doctor_reply)
    LinearLayout ll_doctor_reply;
    @BindView(R.id.tv_reply_content)
    TextView tv_reply_content;

    @BindView(R.id.rt_send_msg)
    RelativeLayout rt_send_msg;
    @BindView(R.id.edit_reply_content)
    EditText edit_reply_content;
    @BindView(R.id.btn_send)
    Button btn_send;


    private SearchRecordBean mSearchRecordBean;
    private SearchRecordDetailsViewMode mSearchRecordDetailsViewMode;
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
        setTitle("问诊详情");

    }

    @Override
    public void initView() {
        mSearchRecordBean = (SearchRecordBean)getIntent().getSerializableExtra("datas");

        tv_content.setText(mSearchRecordBean.getInfo());
        GlideHelper.loadImageView(this,mSearchRecordBean.getChecklist_pic(),image_checklist_pic);
        GlideHelper.loadImageView(this,mSearchRecordBean.getMedical_pic(),image_medical_pic);
        GlideHelper.loadImageView(this,mSearchRecordBean.getAffected_part_pic(),image_affected_part_pic);
        GlideHelper.loadImageView(this,mSearchRecordBean.getOther_pic(),image_other_pic);
        if (mSearchRecordBean.getIs_reply()==1){
            rt_send_msg.setVisibility(View.GONE);
            ll_doctor_reply.setVisibility(View.VISIBLE);
            tv_reply_content.setText(mSearchRecordBean.getReply_info());

        }else {
            rt_send_msg.setVisibility(View.VISIBLE);
            ll_doctor_reply.setVisibility(View.GONE);

        }
        mSearchRecordDetailsViewMode = new SearchRecordDetailsViewMode(this);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_send})
    public void onClick(View v){
        if (v==btn_send){
            if (TextUtils.isEmpty(edit_reply_content.getText().toString())){
                showLoadFailMsg("请输入回复内容");
            }else {
                mSearchRecordDetailsViewMode.adviceComment(String.valueOf(mSearchRecordBean.getId()),edit_reply_content.getText().toString());
            }
        }
    }

    @Override
    public void AdviceCommentResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            showLoadFailMsg("回复成功");
            rt_send_msg.setVisibility(View.GONE);
            ll_doctor_reply.setVisibility(View.VISIBLE);
            tv_reply_content.setText(edit_reply_content.getText().toString());

        }else {
            showLoadFailMsg("回复失败");
        }
    }

    @Override
    public void showProgress() {

        hud.show();
    }

    @Override
    public void hideProgress() {

        hud.dismiss();
    }

    @Override
    public void showLoadFailMsg(String err) {

        ToastyHelper.toastyNormal(this,err);
    }
}
