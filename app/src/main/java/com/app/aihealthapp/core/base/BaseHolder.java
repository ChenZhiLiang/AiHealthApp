package com.app.aihealthapp.core.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/11.
 * Adapter 中 Holder 的基类
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnViewClickListener mOnViewClickListener = null;

    public BaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);//点击事件
        ButterKnife.bind(this, itemView);//注解绑定
    }

    /**
     * 设置数据
     * 刷新界面
     * @param
     */
    public abstract void setData(T data);

    @Override
    public void onClick(View view) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(view, this.getPosition());
        }
    }

    /**
     * 接口
     */
    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }

    /**
     * 外部点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }
}
