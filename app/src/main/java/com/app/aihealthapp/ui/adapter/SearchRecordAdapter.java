package com.app.aihealthapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.ui.activity.mine.AdvisoryDetailsActivity;
import com.app.aihealthapp.ui.activity.mine.SearchRecordDetailsActivity;
import com.app.aihealthapp.ui.bean.SearchRecordBean;
import com.app.aihealthapp.view.CircleImageView;

import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 21:59
 * 修改人：Chen
 * 修改时间：2019/8/19 21:59
 */
public class SearchRecordAdapter extends BaseXRecyclerViewAdapter<SearchRecordBean> {


    private Activity mActivity;
    public SearchRecordAdapter(Activity mActivity,List<SearchRecordBean> data) {
        super(data);
        this.mActivity = mActivity;
    }

    @Override
    public BaseHolder<SearchRecordBean> getHolder(View v) {
        return new SearchRecordHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_search_record_item;
    }

    class SearchRecordHolder extends BaseHolder<SearchRecordBean>{
        @BindView(R.id.rt_doctor_record)
        RelativeLayout rt_doctor_record;
        @BindView(R.id.img_doctor_head)
        CircleImageView img_doctor_head;
        @BindView(R.id.tv_doctor_name)
        TextView tv_doctor_name;
        @BindView(R.id.tv_doctor_content)
        TextView tv_doctor_content;

        @BindView(R.id.ll_interrogation_record)
        LinearLayout ll_interrogation_record;
        @BindView(R.id.tv_reply)
        TextView tv_reply;
        @BindView(R.id.tv_content)
        TextView tv_content;
        public SearchRecordHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(final SearchRecordBean data) {

            if (data.getKind_type()==1){
                rt_doctor_record.setVisibility(View.VISIBLE);
                ll_interrogation_record.setVisibility(View.GONE);
                GlideHelper.loadHeadImageView(mActivity,data.getDoctor_avatar(),img_doctor_head);
                tv_doctor_name.setText(data.getDoctor_name());
                tv_doctor_content.setText(data.getInfo());
                rt_doctor_record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.startActivity(new Intent(mActivity, AdvisoryDetailsActivity.class).putExtra("id",String.valueOf(data.getId())).putExtra("kind_type",data.getKind_type()));
                    }
                });

            }else {
                rt_doctor_record.setVisibility(View.GONE);
                ll_interrogation_record.setVisibility(View.VISIBLE);
                if (data.getIs_reply()==1){
                    tv_reply.setText("已回复");
                    tv_reply.setBackgroundColor(mActivity.getResources().getColor(R.color.default_text_color));
                }else {
                    tv_reply.setText("未回复");
                    tv_reply.setBackgroundColor(mActivity.getResources().getColor(R.color.default_hint_color));
                }
                tv_content.setText(data.getInfo());
                ll_interrogation_record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.startActivity(new Intent(mActivity, SearchRecordDetailsActivity.class).putExtra("datas", data));
                    }
                });
            }

        }

    }
}
