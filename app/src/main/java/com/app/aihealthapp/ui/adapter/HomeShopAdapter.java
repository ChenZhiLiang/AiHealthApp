package com.app.aihealthapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.helper.ToastyHelper;
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

    private List<HomeShopBean> data;//数据

    private Activity activity;//上下文

    public HomeShopAdapter(Activity activity, List<HomeShopBean> data) {
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
//            viewHolder.textView = (TextView) view.findViewById(R.id.text1);
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
//        viewHolder.imageView.setImageResource(data.get(position).getBitmapId());
//        viewHolder.textView.setText(data.get(position).getName());
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
        Button btn_buy;
    }
}