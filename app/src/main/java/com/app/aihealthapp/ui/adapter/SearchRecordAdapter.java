package com.app.aihealthapp.ui.adapter;

import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.ui.bean.SearchRecordBean;

import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 21:59
 * 修改人：Chen
 * 修改时间：2019/8/19 21:59
 */
public class SearchRecordAdapter extends BaseXRecyclerViewAdapter<SearchRecordBean> {


    public SearchRecordAdapter(List<SearchRecordBean> data) {
        super(data);
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

        public SearchRecordHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(SearchRecordBean data) {

        }
    }
}
