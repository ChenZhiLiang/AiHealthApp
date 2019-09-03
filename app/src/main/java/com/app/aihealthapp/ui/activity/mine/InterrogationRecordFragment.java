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
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.xrecyclerview.ProgressStyle;
import com.app.aihealthapp.core.xrecyclerview.XRecyclerView;
import com.app.aihealthapp.ui.adapter.InterrogationRecordAdapter;
import com.app.aihealthapp.ui.adapter.SearchRecordAdapter;
import com.app.aihealthapp.ui.bean.InterrogationRecordBean;
import com.app.aihealthapp.ui.bean.SearchRecordBean;
import com.app.aihealthapp.ui.mvvm.view.InterrogationRecordView;
import com.app.aihealthapp.ui.mvvm.viewmode.InterrogationRecordViewMode;
import com.app.aihealthapp.ui.mvvm.viewmode.SearchRecordViewMode;
import com.app.aihealthapp.util.SpaceItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：会员 询问、问诊记录
 * @Author：Chen
 * @Date：2019/8/19 21:35
 * 修改人：Chen
 * 修改时间：2019/8/19 21:35
 */
public class InterrogationRecordFragment extends BaseFragment implements InterrogationRecordView , XRecyclerView.LoadingListener {
    @BindView(R.id.no_record_layout)
    LinearLayout no_record_layout;
    @BindView(R.id.recy_interrogation_record)
    XRecyclerView recy_interrogation_record;

    private InterrogationRecordAdapter mInterrogationRecordAdapter;
    private InterrogationRecordViewMode mInterrogationRecordViewMode;
    List<InterrogationRecordBean> InterrogationRecords = new ArrayList<>();

    private int page = 1;
    private int totalPage ;//总页数
    private int kind_type;
    public static InterrogationRecordFragment getInstance(int kind_type) {
        InterrogationRecordFragment hf = new InterrogationRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("kind_type",kind_type);
        hf.setArguments(bundle);
        return hf;
    }
    @Override
    public void loadingData() {
        mInterrogationRecordViewMode.InterrogationRecord(page,kind_type,true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_interrogation_record;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

        kind_type = getArguments().getInt("kind_type");
        recy_interrogation_record.setLayoutManager(new LinearLayoutManager(mActivity));
        mInterrogationRecordViewMode = new InterrogationRecordViewMode(this);
        mInterrogationRecordAdapter = new InterrogationRecordAdapter(getContext(),InterrogationRecords);
//        recy_interrogation_record.addItemDecoration(new SpaceItemDecoration(mActivity,1));
        recy_interrogation_record.setRefreshProgressStyle(ProgressStyle.BallPulse);
        recy_interrogation_record.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        recy_interrogation_record.setArrowImageView(R.mipmap.icon_pull_down);
        recy_interrogation_record.setLoadingListener(this);
        recy_interrogation_record.setAdapter(mInterrogationRecordAdapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public void InterrogationRecordResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){

            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            List<InterrogationRecordBean> datas = GsonHelper.GsonToList(data,InterrogationRecordBean.class,"data");
            if (datas.size()>0){
                page  = GsonHelper.GsonToInt(data,"current_page");
                totalPage = GsonHelper.GsonToInt(data,"total");

                no_record_layout.setVisibility(View.GONE);
                recy_interrogation_record.setVisibility(View.VISIBLE);
                if (page == 1) {
                    if (mInterrogationRecordAdapter != null) {
                        mInterrogationRecordAdapter.clear();
                    }
                }else {
                    if (page == totalPage){
                        recy_interrogation_record.setNoMore(true);
                    }
                }
                InterrogationRecords = datas;
                mInterrogationRecordAdapter.addItem(InterrogationRecords);
                mInterrogationRecordAdapter.notifyDataSetChanged();
            }else {
                no_record_layout.setVisibility(View.VISIBLE);
                recy_interrogation_record.setVisibility(View.GONE);
            }
            recy_interrogation_record.refreshComplete();
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
        mInterrogationRecordViewMode.InterrogationRecord(page,kind_type,false);

    }

    @Override
    public void onLoadMore() {
        page++;
        mInterrogationRecordViewMode.InterrogationRecord(page,kind_type,false);

    }
}
