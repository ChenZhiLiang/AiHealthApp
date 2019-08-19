package com.app.aihealthapp.ui.adapter;

import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.ui.bean.InterrogationRecordBean;

import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 22:17
 * 修改人：Chen
 * 修改时间：2019/8/19 22:17
 */
public class InterrogationRecordAdapter extends BaseXRecyclerViewAdapter<InterrogationRecordBean> {


    public InterrogationRecordAdapter(List<InterrogationRecordBean> data) {
        super(data);
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

        public InterrogationRecordHoler(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(InterrogationRecordBean data) {

        }
    }
}
