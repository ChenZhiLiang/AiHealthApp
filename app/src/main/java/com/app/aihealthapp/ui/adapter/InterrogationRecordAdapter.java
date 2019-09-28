package com.app.aihealthapp.ui.adapter;

import android.content.Context;
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
import com.app.aihealthapp.ui.activity.mine.InterrogationDetailsActivity;
import com.app.aihealthapp.ui.activity.mine.SearchRecordDetailsActivity;
import com.app.aihealthapp.ui.bean.InterrogationRecordBean;
import com.app.aihealthapp.view.CircleImageView;

import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 22:17
 * 修改人：Chen
 * 修改时间：2019/8/19 22:17
 */
public class InterrogationRecordAdapter extends BaseXRecyclerViewAdapter<InterrogationRecordBean> {


    private Context context;
    public InterrogationRecordAdapter(Context context,List<InterrogationRecordBean> data) {
        super(data);
        this.context = context;
    }

    @Override
    public BaseHolder<InterrogationRecordBean> getHolder(View v) {
        return new InterrogationRecordHoler(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_interrogation_record_item;
    }

    class InterrogationRecordHoler extends BaseHolder<InterrogationRecordBean>{

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
        public InterrogationRecordHoler(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(final InterrogationRecordBean data) {

            if (data.getKind_type()==1){
                rt_doctor_record.setVisibility(View.VISIBLE);
                ll_interrogation_record.setVisibility(View.GONE);
                GlideHelper.loadHeadImageView(context,data.getDoctor_avatar(),img_doctor_head);
                tv_doctor_name.setText(data.getDoctor_name());
                tv_doctor_content.setText(data.getInfo());
                rt_doctor_record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, AdvisoryDetailsActivity.class).putExtra("id",String.valueOf(data.getId())));
                    }
                });

            }else {
                rt_doctor_record.setVisibility(View.GONE);
                ll_interrogation_record.setVisibility(View.VISIBLE);
                if (data.getIs_reply()==1){
                    tv_reply.setText("已回复");
                    tv_reply.setBackgroundColor(context.getResources().getColor(R.color.default_text_color));
                }else {
                    tv_reply.setText("未回复");
                    tv_reply.setBackgroundColor(context.getResources().getColor(R.color.default_hint_color));
                }
                tv_content.setText(data.getInfo());
                ll_interrogation_record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, InterrogationDetailsActivity.class).putExtra("datas", data));
                    }
                });
            }

        }
    }
}
