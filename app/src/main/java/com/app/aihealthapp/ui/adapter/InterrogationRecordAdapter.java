package com.app.aihealthapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.ui.bean.InterrogationRecordBean;

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

        @BindView(R.id.tv_reply)
        TextView tv_reply;
        @BindView(R.id.tv_content)
        TextView tv_content;
        public InterrogationRecordHoler(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(InterrogationRecordBean data) {

            if (data.getIs_reply()==1){
                tv_reply.setText("已回复");
                tv_reply.setBackgroundColor(context.getResources().getColor(R.color.default_text_color));
            }else {
                tv_reply.setText("未回复");
                tv_reply.setBackgroundColor(context.getResources().getColor(R.color.default_hint_color));


            }
            tv_content.setText(data.getInfo());
        }
    }
}
