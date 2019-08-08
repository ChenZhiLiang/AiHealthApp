package com.app.aihealthapp.core.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @Author: chen
 * @ProjectName: MoDou
 * @ClassName: BaseXRecyclerViewAdapter
 * @Description: 描述
 * @CreateDate: 2019/3/15 15:27
 * @Version: 1.0
 */
public abstract class BaseXRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>>{

    public List<T> mData;
    protected OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private BaseHolder<T> mHolder;

    public BaseXRecyclerViewAdapter(List<T> data) {
        super();
        this.mData = data;
    }

    /**
     * 创建Holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseHolder<T> onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),parent,false);
        mHolder = getHolder(view);
        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {
            @Override
            public void onViewClick(View view, int position) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view,mData.get(position),position);
                }
            }
        });
        return mHolder;
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        holder.setData(mData.get(position));
    }

    /**
     * 数据总数
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 添加，刷新数据
     * @param datas
     */
    public void addItem(List<T> datas){
        this.mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.mData.remove(position);
        notifyDataSetChanged();
    }
    /**
     * 清空数据
     */
    public void clear(){
        this.mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 获得item的数据
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    /**
     * 子类实现提供holder
     * @param v
     * @return
     */
    public abstract BaseHolder<T> getHolder(View v);

    /**
     * 提供Item的布局
     * @return
     */
    public abstract int getLayoutId();


    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
