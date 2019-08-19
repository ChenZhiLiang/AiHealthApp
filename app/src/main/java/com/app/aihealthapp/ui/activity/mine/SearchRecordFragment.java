package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.adapter.SearchRecordAdapter;
import com.app.aihealthapp.ui.mvvm.view.SearchRecordView;
import com.app.aihealthapp.ui.mvvm.viewmode.SearchRecordViewMode;
import com.app.aihealthapp.util.SpaceItemDecoration;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：咨询记录
 * @Author：Chen
 * @Date：2019/8/19 21:35
 * 修改人：Chen
 * 修改时间：2019/8/19 21:35
 */
public class SearchRecordFragment extends BaseFragment implements SearchRecordView {

    @BindView(R.id.recy_search_record)
    RecyclerView recy_search_record;

    private SearchRecordAdapter mSearchRecordAdapter;
    private SearchRecordViewMode mSearchRecordViewMode;
    public static SearchRecordFragment getInstance() {
        SearchRecordFragment hf = new SearchRecordFragment();
        return hf;
    }
    @Override
    public void loadingData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_record;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

        recy_search_record.setLayoutManager(new LinearLayoutManager(mActivity));
        mSearchRecordViewMode = new SearchRecordViewMode(this);
        mSearchRecordAdapter = new SearchRecordAdapter(mSearchRecordViewMode.getDatas());
        recy_search_record.addItemDecoration(new SpaceItemDecoration(mActivity,1));
        recy_search_record.setAdapter(mSearchRecordAdapter);
        mSearchRecordAdapter.setOnItemClickListener(new BaseXRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                startActivity(new Intent(mActivity,SearchRecordDetailsActivity.class));
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void SearchRecordDatasResult(Object result) {

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

        ToastyHelper.toastyNormal(mActivity,err);

    }
}
