package com.app.aihealthapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.ui.bean.ReplyItemBean;

import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/9/28 22:49
 * 修改人：Chen
 * 修改时间：2019/9/28 22:49
 */
public class AdvisoryReplyAdapter extends BaseXRecyclerViewAdapter<ReplyItemBean> {

    private Context context;
    private int kind_type;

    public AdvisoryReplyAdapter(Context context,List<ReplyItemBean> data,int kind_type) {
        super(data);
        this.context = context;
        this.kind_type = kind_type;
    }

    @Override
    public BaseHolder<ReplyItemBean> getHolder(View v) {
        return new AdvisoryReplyHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_advisory_reply;
    }

    public class AdvisoryReplyHolder extends BaseHolder<ReplyItemBean>{

        @BindView(R.id.image_head)
        ImageView image_head;
        @BindView(R.id.tv_reply_name)
        TextView tv_reply_name;
        @BindView(R.id.tv_reply_content)
        TextView tv_reply_content;

        public AdvisoryReplyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ReplyItemBean data) {

            GlideHelper.loadHeadImageView(context,data.getUser_avatar(),image_head);
            if (kind_type==0){
                if (data.getType()==1){
                    tv_reply_name.setText("我 回复 "+data.getDoctor_name()+":");
                }else {
                    tv_reply_name.setText(data.getDoctor_name()+" 回复");
                }
            }else {
                if (data.getType()==1){
                    tv_reply_name.setText(data.getUser_name()+" 回复");

                }else {
                    tv_reply_name.setText("我 回复 "+data.getUser_name()+":");
                }
            }

            tv_reply_content.setText(data.getInfo());
        }
    }
}
