package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.bean.InterrogationRecordBean;
import com.app.aihealthapp.ui.mvvm.view.InterrogationDetailsView;
import com.app.aihealthapp.ui.mvvm.viewmode.InterrogationDetailsViewMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealthApp
 * @Description：详情
 * @Author：Chen
 * @Date：2019/8/22 21:56
 * 修改人：Chen
 * 修改时间：2019/8/22 21:56
 */
public class InterrogationDetailsActivity extends BaseActivity implements InterrogationDetailsView {

    @BindView(R.id.tv_my_question)
    TextView tv_my_question;
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
    @BindView(R.id.edit_input_content)
    EditText edit_input_content;
    @BindView(R.id.btn_send)
    Button btn_send;
    private InterrogationRecordBean datas;

    private InterrogationDetailsViewMode mInterrogationDetailsViewMode;
    @Override
    public int getLayoutId() {
        return R.layout.activity_interrogation_details;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
    }

    @Override
    public void initView() {
        datas = (InterrogationRecordBean)getIntent().getSerializableExtra("datas");
        if (datas.getKind_type()==1){
            setTitle("咨询详情");
            rt_send_msg.setVisibility(View.GONE);

        }else {
            setTitle("问诊详情");
            //用户问诊详情 没有回复功能
            rt_send_msg.setVisibility(View.GONE);
            if (datas.getIs_reply()==1){
                ll_doctor_reply.setVisibility(View.VISIBLE);
                tv_reply_content.setText(datas.getReply_info());
            }else {
                ll_doctor_reply.setVisibility(View.GONE);
            }
        }
        tv_my_question.setText(datas.getInfo());
        GlideHelper.loadImageView(this,datas.getChecklist_pic(),image_checklist_pic);
        GlideHelper.loadImageView(this,datas.getMedical_pic(),image_medical_pic);
        GlideHelper.loadImageView(this,datas.getAffected_part_pic(),image_affected_part_pic);
        GlideHelper.loadImageView(this,datas.getOther_pic(),image_other_pic);


        mInterrogationDetailsViewMode = new InterrogationDetailsViewMode(this);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_send})
    public void onClick(View v){
        if (TextUtils.isEmpty(edit_input_content.getText().toString())){
            ToastyHelper.toastyNormal(this,"请输入回复内容");
        }else {
            mInterrogationDetailsViewMode.adviceComment(datas.getId(),edit_input_content.getText().toString());
        }
    }

    @Override
    public void AdviceCommentResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){

        }else{
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
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
