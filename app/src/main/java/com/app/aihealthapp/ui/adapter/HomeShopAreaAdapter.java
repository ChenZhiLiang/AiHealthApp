package com.app.aihealthapp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.ui.bean.HomeShopAreaBean;
import com.app.aihealthapp.ui.bean.ShopListBean;

import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/25 22:09
 * 修改人：Chen
 * 修改时间：2019/7/25 22:09
 */
public class HomeShopAreaAdapter extends BaseXRecyclerViewAdapter<ShopListBean> {



    private Activity mActivity;
    public HomeShopAreaAdapter(Activity mActivity,List<ShopListBean> data) {
        super(data);
        this.mActivity = mActivity;
    }

    @Override
    public BaseHolder<ShopListBean> getHolder(View v) {
        return new HomeShopAreaHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_home_shop_area_item;
    }

    public class HomeShopAreaHolder extends BaseHolder<ShopListBean>{

        @BindView(R.id.image_shop_area)
        ImageView image_shop_area;
        @BindView(R.id.tv_shop_area_name)
        TextView tv_shop_area_name;
        @BindView(R.id.tv_shop_area_details)
        TextView tv_shop_area_details;
        @BindView(R.id.tv_shop_area_discounts)
        TextView tv_shop_area_discounts;
        public HomeShopAreaHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ShopListBean data) {

            GlideHelper.loadImageView(mActivity,data.getPic(),image_shop_area);
            tv_shop_area_name.setText(data.getTitle());
            tv_shop_area_details.setText(data.getInfo());
            tv_shop_area_discounts.setText(data.getSeo_des());
        }
    }
}
