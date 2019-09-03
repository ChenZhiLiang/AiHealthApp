package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.ui.bean.InterrogationRecordBean;

import butterknife.BindView;

/**
 * @Name：AiHealthApp
 * @Description：问诊详情
 * @Author：Chen
 * @Date：2019/8/22 21:56
 * 修改人：Chen
 * 修改时间：2019/8/22 21:56
 */
public class InterrogationDetailsActivity extends BaseActivity {

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
    private InterrogationRecordBean datas;
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
        }else {
            setTitle("问诊详情");
        }
        tv_my_question.setText(datas.getInfo());
        GlideHelper.loadImageView(this,datas.getChecklist_pic(),image_checklist_pic);
        GlideHelper.loadImageView(this,datas.getMedical_pic(),image_medical_pic);
        GlideHelper.loadImageView(this,datas.getAffected_part_pic(),image_affected_part_pic);
        GlideHelper.loadImageView(this,datas.getOther_pic(),image_other_pic);

        if (datas.getIs_reply()==1){
            ll_doctor_reply.setVisibility(View.VISIBLE);
            tv_reply_content.setText(datas.getReply_info());
        }else {
            ll_doctor_reply.setVisibility(View.GONE);

        }

    }

    @Override
    public void initData() {

    }
}
