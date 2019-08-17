package com.app.aihealthapp.ui.adapter;

import android.app.Activity;
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
import com.app.aihealthapp.ui.bean.ArticleCateListBean;
import com.app.aihealthapp.ui.bean.HomeShopBean;

import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/17 10:22
 * 修改人：Chen
 * 修改时间：2019/8/17 10:22
 */
public class HealthManageAdapter extends BaseAdapter {

    private List<ArticleCateListBean> data;//数据

    private Activity activity;//上下文

    public HealthManageAdapter(Activity activity, List<ArticleCateListBean> data) {
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
            view = LayoutInflater.from(activity).inflate(R.layout.layout_grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.imgage);
            viewHolder.textView = view.findViewById(R.id.tv_title);

//            viewHolder.textView = (TextView) view.findViewById(R.id.text1);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        GlideHelper.loadImageView(activity,data.get(position).getPic(),viewHolder.imageView);
//        viewHolder.imageView.setImageResource(data.get(position).getBitmapId());
        viewHolder.textView.setText(data.get(position).getTitle());
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}