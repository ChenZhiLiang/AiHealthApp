package com.app.aihealthapp.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.adapter.InterrogationRecordAdapter;
import com.app.aihealthapp.ui.adapter.SearchRecordAdapter;
import com.app.aihealthapp.ui.mvvm.view.InterrogationRecordView;
import com.app.aihealthapp.ui.mvvm.viewmode.InterrogationRecordViewMode;
import com.app.aihealthapp.ui.mvvm.viewmode.SearchRecordViewMode;
import com.app.aihealthapp.util.SpaceItemDecoration;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：问诊记录
 * @Author：Chen
 * @Date：2019/8/19 21:35
 * 修改人：Chen
 * 修改时间：2019/8/19 21:35
 */
public class InterrogationRecordFragment extends BaseFragment implements InterrogationRecordView {

    @BindView(R.id.recy_interrogation_record)
    RecyclerView recy_interrogation_record;

    private InterrogationRecordAdapter mInterrogationRecordAdapter;
    private InterrogationRecordViewMode mInterrogationRecordViewMode;
    public static InterrogationRecordFragment getInstance() {
        InterrogationRecordFragment hf = new InterrogationRecordFragment();
        return hf;
    }
    @Override
    public void loadingData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_interrogation_record;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        recy_interrogation_record.setLayoutManager(new LinearLayoutManager(mActivity));
        mInterrogationRecordViewMode = new InterrogationRecordViewMode(this);
        mInterrogationRecordAdapter = new InterrogationRecordAdapter(mInterrogationRecordViewMode.getDatas());
        recy_interrogation_record.addItemDecoration(new SpaceItemDecoration(mActivity,1));
        recy_interrogation_record.setAdapter(mInterrogationRecordAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void InterrogationRecordResult(Object result) {

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
