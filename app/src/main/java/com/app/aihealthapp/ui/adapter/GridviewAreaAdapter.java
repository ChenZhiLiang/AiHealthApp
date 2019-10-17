package com.app.aihealthapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.ui.bean.CountryCityBean;

import java.util.List;

/**
 * @Author: chenzl
 * @ClassName: GridviewAreaAdapter
 * @Description: java类作用描述
 * @CreateDate: 2019/10/17 9:47
 */
public class GridviewAreaAdapter extends BaseAdapter {

    private List<CountryCityBean.CityListBean.AreaListBean> datas;
    private Context context;
    LayoutInflater inflater;
    public ViewHolder holder;

    public GridviewAreaAdapter(Context context, List<CountryCityBean.CityListBean.AreaListBean> datas) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.datas = datas;
    }
    /**
     * 添加，刷新数据
     * @param datas
     */
    public void addItem(List<CountryCityBean.CityListBean.AreaListBean> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_area_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mText.setText(datas.get(position).getName());
        return convertView;
    }

    public static class ViewHolder {
        TextView mText;

        public ViewHolder(View itemView) {
            mText = itemView.findViewById(R.id.tv_area_name);
        }
    }
}
