package com.app.aihealthapp.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.xrecyclerview.ProgressStyle;
import com.app.aihealthapp.core.xrecyclerview.XRecyclerView;
import com.app.aihealthapp.core.xrecyclerview.XRecyclerView.LoadingListener;
import com.app.aihealthapp.ui.adapter.DoctorListAdapter;
import com.app.aihealthapp.ui.bean.DoctorListBean;
import com.app.aihealthapp.ui.mvvm.view.DoctorListView;
import com.app.aihealthapp.ui.mvvm.viewmode.DoctorListViewMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 10:42
 * 修改人：Chen
 * 修改时间：2019/8/18 10:42
 */
public class DoctorListActivity extends BaseActivity implements DoctorListView, LoadingListener {

    @BindView(R.id.no_record_layout)
    LinearLayout no_record_layout;
    @BindView(R.id.recy_doctor)
    XRecyclerView recy_doctor;

    @BindView(R.id.edit_input_content)
    EditText edit_input_content;
    @BindView(R.id.btn_search)
    Button btn_search;
    private DoctorListViewMode mDoctorListViewMode;
    private DoctorListAdapter mDoctorListAdapter;
    private int page = 1;
    private int totalPage ;//总页数

    private String keyword;
    private List<DoctorListBean> doctor_list = new ArrayList<>();

    private int cate_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_doctor_list;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("医生在线");
    }

    @Override
    public void initView() {
        mDoctorListViewMode = new DoctorListViewMode(this);
        mDoctorListAdapter = new DoctorListAdapter(this,doctor_list);
        recy_doctor.setLayoutManager(new LinearLayoutManager(this));
        recy_doctor.setRefreshProgressStyle(ProgressStyle.BallPulse);
        recy_doctor.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        recy_doctor.setArrowImageView(R.mipmap.icon_pull_down);
        recy_doctor.setLoadingListener(this);
        recy_doctor.setAdapter(mDoctorListAdapter);

        cate_id = getIntent().getIntExtra("cate_id",0);
    }

    @Override
    public void initData() {
        mDoctorListViewMode.getDoctorList(page,keyword,cate_id,true);
    }

    @Override
    public void DoctorListResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            List<DoctorListBean> datas = GsonHelper.GsonToList(data,DoctorListBean.class,"data");
            if (datas.size()>0){
                page  = GsonHelper.GsonToInt(data,"current_page");
                totalPage = GsonHelper.GsonToInt(data,"total");
                if (page == totalPage){
                    recy_doctor.setNoMore(true);
                }
                no_record_layout.setVisibility(View.GONE);
                recy_doctor.setVisibility(View.VISIBLE);
                if (page == 1) {
                    if (mDoctorListAdapter != null) {
                        mDoctorListAdapter.clear();
                    }
                }
                doctor_list = datas;
                mDoctorListAdapter.addItem(datas);
                mDoctorListAdapter.notifyDataSetChanged();
            }else {
                no_record_layout.setVisibility(View.VISIBLE);
                recy_doctor.setVisibility(View.GONE);
            }
            recy_doctor.refreshComplete();

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }


    @OnClick({R.id.btn_search})
    public void onClick(View v){
        if (v==btn_search){
            keyword = edit_input_content.getText().toString();
            mDoctorListViewMode.getDoctorList(page,keyword,cate_id,true);
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

        ToastyHelper.toastyNormal(this,err);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mDoctorListViewMode.getDoctorList(page,keyword,cate_id,false);

    }

    @Override
    public void onLoadMore() {
        page++;
        mDoctorListViewMode.getDoctorList(page,keyword,cate_id,false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case 100:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        if (type.equals("home")){
                            Intent intent =new Intent();
                            intent.setAction("action.intent_home");
                            sendBroadcast(intent);
                            finish();
                        }else if (type.equals("mine")){
                            Intent intent =new Intent();
                            intent.setAction("action.intent_mine");
                            sendBroadcast(intent);
                            finish();
                        }
                    }
            }
        }
    }
}
