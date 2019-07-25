package com.app.aihealthapp.ui.adapter;

import android.view.View;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.ui.bean.HomeShopAreaBean;

import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/25 22:09
 * 修改人：Chen
 * 修改时间：2019/7/25 22:09
 */
public class HomeShopAreaAdapter extends BaseXRecyclerViewAdapter<HomeShopAreaBean> {



    public HomeShopAreaAdapter(List<HomeShopAreaBean> data) {
        super(data);
    }

    @Override
    public BaseHolder<HomeShopAreaBean> getHolder(View v) {
        return new HomeShopAreaHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_home_shop_area_item;
    }

    public class HomeShopAreaHolder extends BaseHolder<HomeShopAreaBean>{

        public HomeShopAreaHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(HomeShopAreaBean data) {

        }
    }
}
