package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.pictureviewer.ImagePagerActivity;
import com.app.aihealthapp.ui.adapter.AdvisoryReplyAdapter;
import com.app.aihealthapp.ui.bean.AdvisoryDetailsBean;
import com.app.aihealthapp.ui.bean.ReplyItemBean;
import com.app.aihealthapp.ui.mvvm.view.AdvisoryDetailsView;
import com.app.aihealthapp.ui.mvvm.viewmode.AdvisoryDetailsViewMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 咨询详情
* */
public class AdvisoryDetailsActivity extends BaseActivity implements AdvisoryDetailsView {

    @BindView(R.id.tv_reply_title)
    TextView tv_reply_title;
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

    @BindView(R.id.recy_reply)
    RecyclerView recy_reply;

    @BindView(R.id.rt_send_msg)
    RelativeLayout rt_send_msg;
    @BindView(R.id.edit_input_content)
    EditText edit_input_content;
    @BindView(R.id.btn_send)
    Button btn_send;

    private AdvisoryDetailsViewMode mAdvisoryDetailsViewMode;

    private AdvisoryReplyAdapter mAdvisoryReplyAdapter;
    private List<ReplyItemBean> ReplyItem = new ArrayList<>();
    private String id;
    private int kind_type;//0 当前身份是患者 1当前身份是医生
    private  AdvisoryDetailsBean mAdvisoryDetailsBean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_advisory_details;
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
        id = getIntent().getStringExtra("id");
        kind_type = getIntent().getIntExtra("kind_type",0);
        if (kind_type==0){
            tv_reply_title.setText("我的提问");
        }else {
            tv_reply_title.setText("患者提问");

        }
        recy_reply.setLayoutManager(new LinearLayoutManager(this));
        mAdvisoryReplyAdapter = new AdvisoryReplyAdapter(this,ReplyItem,kind_type);
        recy_reply.setAdapter(mAdvisoryReplyAdapter);
    }

    @Override
    public void initData() {
        mAdvisoryDetailsViewMode = new AdvisoryDetailsViewMode(this);
        mAdvisoryDetailsViewMode.getAdvisoryDetails(id,true);
    }

    @Override
    public void AdvisoryDetailsResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
             mAdvisoryDetailsBean = GsonHelper.GsonToBean(data,AdvisoryDetailsBean.class);
            tv_my_question.setText(mAdvisoryDetailsBean.getInfo());
            GlideHelper.loadImageView(this,mAdvisoryDetailsBean.getChecklist_pic(),image_checklist_pic);
            GlideHelper.loadImageView(this,mAdvisoryDetailsBean.getMedical_pic(),image_medical_pic);
            GlideHelper.loadImageView(this,mAdvisoryDetailsBean.getAffected_part_pic(),image_affected_part_pic);
            GlideHelper.loadImageView(this,mAdvisoryDetailsBean.getOther_pic(),image_other_pic);
            if (mAdvisoryDetailsBean.getReply_item().size()>0){
                recy_reply.setVisibility(View.VISIBLE);
                if (mAdvisoryReplyAdapter!=null){
                    mAdvisoryReplyAdapter.clear();
                }
                ReplyItem = mAdvisoryDetailsBean.getReply_item();
                mAdvisoryReplyAdapter.addItem(ReplyItem);
                mAdvisoryReplyAdapter.notifyDataSetChanged();
            }else {
                recy_reply.setVisibility(View.GONE);
            }

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void ReplyListResult(Object result) {


    }

    @Override
    public void AdviceCommentResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            showLoadFailMsg("回复成功");
            edit_input_content.setText("");
            mAdvisoryDetailsViewMode.getAdvisoryDetails(id,false);

        }else {
            showLoadFailMsg("回复失败");
        }

    }

    @OnClick({R.id.btn_send,R.id.image_checklist_pic,R.id.image_medical_pic,R.id.image_affected_part_pic,R.id.image_other_pic})
    public void onClick(View v){
        if (v==btn_send){
            if (TextUtils.isEmpty(edit_input_content.getText().toString())){
                ToastyHelper.toastyNormal(this,"请输入回复内容");
            }else {
                mAdvisoryDetailsViewMode.adviceComment(id,edit_input_content.getText().toString());
            }
        }else if (v==image_checklist_pic){
            Intent intent = new Intent(this, ImagePagerActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,  mAdvisoryDetailsBean.getChecklist_pic());
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
            startActivity(intent);
        }else if (v==image_medical_pic){
            Intent intent = new Intent(this, ImagePagerActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,  mAdvisoryDetailsBean.getMedical_pic());
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
            startActivity(intent);
        }else if (v==image_affected_part_pic){
            Intent intent = new Intent(this, ImagePagerActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,  mAdvisoryDetailsBean.getAffected_part_pic());
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
            startActivity(intent);
        }else if (v==image_other_pic){
            Intent intent = new Intent(this, ImagePagerActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,  mAdvisoryDetailsBean.getOther_pic());
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
            startActivity(intent);
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
