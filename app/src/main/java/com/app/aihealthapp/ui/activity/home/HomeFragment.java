package com.app.aihealthapp.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
import com.app.aihealthapp.ui.activity.mine.LoginActivity;
import com.app.aihealthapp.ui.adapter.HomeShopAdapter;
import com.app.aihealthapp.ui.adapter.HomeShopAreaAdapter;
import com.app.aihealthapp.ui.bean.HomeShopAreaBean;
import com.app.aihealthapp.ui.bean.HomeShopBean;
import com.app.aihealthapp.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：aihealthapp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/22 22:57
 * 修改人：Chen
 * 修改时间：2019/7/22 22:57
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.btn_add_wristband)
    Button btn_add_wristband;
    @BindView(R.id.recycler_shop_area)
    RecyclerView recycler_shop_area;
    @BindView(R.id.gridview_shop)
    MyGridView gridview_shop;
    List<HomeShopAreaBean> datas  = new ArrayList<>();
    List<HomeShopBean> items = new ArrayList<>();
    private HomeShopAreaAdapter mHomeShopAreaAdapter;
    private HomeShopAdapter mHomeShopAdapter;
    public static HomeFragment getInstance(String title) {
        HomeFragment hf = new HomeFragment();
        hf.mTitle = title;
        return hf;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragement_home;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        tv_title_bar.setText("首页");
        configRecycleView(recycler_shop_area,new LinearLayoutManager(getContext()));

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
//        configRecycleView(recycler_shop,gridLayoutManager);
        mHomeShopAreaAdapter = new HomeShopAreaAdapter(datas);
        recycler_shop_area.setAdapter(mHomeShopAreaAdapter);


        for (int i = 0;i<4;i++){
            HomeShopBean mHomeShopBean = new HomeShopBean();
            items.add(mHomeShopBean);
        }
        mHomeShopAdapter = new HomeShopAdapter(getContext(),items);
        gridview_shop.setAdapter(mHomeShopAdapter);
//        recycler_shop.setAdapter(mHomeShopAdapter);
    }

    @Override
    public void loadingData() {

        for (int i = 0;i<2;i++){
            HomeShopAreaBean mHomeShopAreaBean = new HomeShopAreaBean();
            datas.add(mHomeShopAreaBean);
        }
        mHomeShopAreaAdapter.notifyDataSetChanged();

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_add_wristband})
    public void onClick(View v){
        if (v==btn_add_wristband){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }
}
