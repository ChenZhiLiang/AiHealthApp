package com.app.aihealthapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.bean.GoodsListBean;
import com.app.aihealthapp.ui.bean.HomeShopBean;

import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/25 22:50
 * 修改人：Chen
 * 修改时间：2019/7/25 22:50
 */
public class HomeShopAdapter extends BaseAdapter {

    private List<GoodsListBean> data;//数据

    private Activity activity;//上下文

    public HomeShopAdapter(Activity activity, List<GoodsListBean> data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            //加载子布局
            view = LayoutInflater.from(activity).inflate(R.layout.layout_home_shop_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.btn_buy = view.findViewById(R.id.btn_buy);
            viewHolder.image_goods = view.findViewById(R.id.image_goods);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_content = view.findViewById(R.id.tv_content);
            viewHolder.tv_shop_price = view.findViewById(R.id.tv_shop_price);
            viewHolder.tv_shop_original_price = view.findViewById(R.id.tv_shop_original_price);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastyHelper.toastyNormal(activity,"确认购买");
            }
        });

        GlideHelper.loadImageView(activity,data.get(position).getPic(),viewHolder.image_goods);
        viewHolder.tv_title.setText(data.get(position).getTitle());
        viewHolder.tv_content.setText(data.get(position).getSubtitle());
        viewHolder.tv_shop_price.setText("¥ "+data.get(position).getPrice_hek());
        viewHolder.tv_shop_original_price.setText("¥ "+data.get(position).getMarkey_price());
        viewHolder.tv_shop_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        return view;
    }

    private class ViewHolder {
        ImageView image_goods;
        TextView tv_title;
        TextView tv_content;
        TextView tv_shop_price;
        TextView tv_shop_original_price;

        Button btn_buy;
    }
}