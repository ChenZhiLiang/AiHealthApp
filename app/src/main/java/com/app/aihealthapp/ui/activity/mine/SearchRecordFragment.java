package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.xrecyclerview.ProgressStyle;
import com.app.aihealthapp.core.xrecyclerview.XRecyclerView;
import com.app.aihealthapp.ui.adapter.SearchRecordAdapter;
import com.app.aihealthapp.ui.bean.DoctorListBean;
import com.app.aihealthapp.ui.bean.SearchRecordBean;
import com.app.aihealthapp.ui.mvvm.view.SearchRecordView;
import com.app.aihealthapp.ui.mvvm.viewmode.SearchRecordViewMode;
import com.app.aihealthapp.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：咨询记录
 * @Author：Chen
 * @Date：2019/8/19 21:35
 * 修改人：Chen
 * 修改时间：2019/8/19 21:35
 */
public class SearchRecordFragment extends BaseFragment implements SearchRecordView , XRecyclerView.LoadingListener {

    @BindView(R.id.no_record_layout)
    LinearLayout no_record_layout;
    @BindView(R.id.recy_search_record)
    XRecyclerView recy_search_record;

    private SearchRecordAdapter mSearchRecordAdapter;
    private SearchRecordViewMode mSearchRecordViewMode;
    List<SearchRecordBean> SearchRecords = new ArrayList<>();

    private int page = 1;
    private int totalPage ;//总页数
    public static SearchRecordFragment getInstance() {
        SearchRecordFragment hf = new SearchRecordFragment();
        return hf;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_record;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

        recy_search_record.setLayoutManager(new LinearLayoutManager(mActivity));
        mSearchRecordViewMode = new SearchRecordViewMode(this);
        mSearchRecordAdapter = new SearchRecordAdapter(SearchRecords);
        recy_search_record.addItemDecoration(new SpaceItemDecoration(mActivity,1));
        recy_search_record.setRefreshProgressStyle(ProgressStyle.BallPulse);
        recy_search_record.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        recy_search_record.setArrowImageView(R.mipmap.icon_pull_down);
        recy_search_record.setLoadingListener(this);
        recy_search_record.setAdapter(mSearchRecordAdapter);
        mSearchRecordAdapter.setOnItemClickListener(new BaseXRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {


                startActivity(new Intent(mActivity,SearchRecordDetailsActivity.class));
            }
        });

    }

    @Override
    public void loadingData() {
        mSearchRecordViewMode.SearchRecord(page,true);
    }
    @Override
    public void initData() {

    }

    @Override
    public void SearchRecordDatasResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){

            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            List<SearchRecordBean> datas = GsonHelper.GsonToList(data,SearchRecordBean.class,"data");
            if (datas.size()>0){
                page  = GsonHelper.GsonToInt(data,"current_page");
                totalPage = GsonHelper.GsonToInt(data,"total");

                no_record_layout.setVisibility(View.GONE);
                recy_search_record.setVisibility(View.VISIBLE);
                if (page == 1) {
                    if (mSearchRecordAdapter != null) {
                        mSearchRecordAdapter.clear();
                    }
                } else {
                    if (page == totalPage){
                        recy_search_record.setNoMore(true);
                    }
                }
                SearchRecords = datas;
                mSearchRecordAdapter.addItem(SearchRecords);
                mSearchRecordAdapter.notifyDataSetChanged();
            }else {
                no_record_layout.setVisibility(View.VISIBLE);
                recy_search_record.setVisibility(View.GONE);
            }
            recy_search_record.refreshComplete();

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
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

        ToastyHelper.toastyNormal(mActivity,err);

    }

    @Override
    public void onRefresh() {
        page = 1;
        mSearchRecordViewMode.SearchRecord(page,false);

    }

    @Override
    public void onLoadMore() {
        page++;
        mSearchRecordViewMode.SearchRecord(page,false);

    }
}
