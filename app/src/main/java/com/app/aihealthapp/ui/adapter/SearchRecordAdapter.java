package com.app.aihealthapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.helper.GlideHelper;
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

        @BindView(R.id.rt_search_record)
        RelativeLayout rt_search_record;
        @BindView(R.id.img_doctor_head)
        CircleImageView img_doctor_head;
        @BindView(R.id.tv_doctor_name)
        TextView tv_doctor_name;
        @BindView(R.id.tv_content)
        TextView tv_content;
        public SearchRecordHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(final SearchRecordBean data) {

            GlideHelper.loadHeadImageView(mActivity,data.getDoctor_avatar(),img_doctor_head);
            tv_doctor_name.setText(data.getDoctor_name());
            tv_content.setText(data.getInfo());
            rt_search_record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, SearchRecordDetailsActivity.class).putExtra("SearchRecordBean",data));
                }
            });
        }
    }
}
