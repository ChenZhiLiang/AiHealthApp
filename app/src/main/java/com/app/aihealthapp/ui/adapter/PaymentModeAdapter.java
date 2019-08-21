package com.app.aihealthapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.ui.bean.SelectedBean;

import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/21 23:36
 * 修改人：Chen
 * 修改时间：2019/8/21 23:36
 */
public class PaymentModeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<SelectedBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mSelectedPos = 0;//实现单选

    public PaymentModeAdapter(List<SelectedBean> datas, Context context) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PaymentHolder holder=new PaymentHolder(mInflater.inflate(R.layout.layout_payment_mode_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        final PaymentHolder contact= (PaymentHolder) holder;
        if(payloads.isEmpty()){//payloads即有效负载，当首次加载或调用notifyDatasetChanged() ,notifyItemChange(int position)进行刷新时，payloads为empty 即空
            SelectedBean user=mDatas.get(position);
            contact.tvPaymentName.setText(user.getName());
            contact.imgPaymode.setBackgroundResource(user.getBitmap());
            contact.checkBox.setChecked(mSelectedPos==position);
        }else{//当调用notifyItemChange(int position, Object payload)进行布局刷新时，payloads不会empty ，所以真正的布局刷新应该在这里实现 重点！
            contact.checkBox.setChecked(mSelectedPos==position);
        }
        contact.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSelectedPos!=holder.getAdapterPosition()){//当前选中的position和上次选中不是同一个position 执行
                    contact.checkBox.setChecked(true);
                    if(mSelectedPos!=-1){//判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos,0);
                    }
                    mSelectedPos=holder.getAdapterPosition();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedPos!=holder.getAdapterPosition()){//当前选中的position和上次选中不是同一个position 执行
                    contact.checkBox.setChecked(true);
                    if(mSelectedPos!=-1){//判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos,0);
                    }
                    mSelectedPos=holder.getAdapterPosition();
                }
            }
        });

    }
    //提供给外部Activity来获取当前checkBox选中的item，这样就不用去遍历了 重点！
    public int getSelectedPos(){
        return mSelectedPos;
    }

    public void setmSelectedPos(int mSelectedPos) {
        this.mSelectedPos = mSelectedPos;
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    public class PaymentHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;//单选框 重点！
        public ImageView imgPaymode;
        public TextView tvPaymentName;

        public PaymentHolder(View itemView) {
            super(itemView);
            checkBox= itemView.findViewById(R.id.checkbox_selecte);
            tvPaymentName= itemView.findViewById(R.id.tv_payment_name);
            imgPaymode= itemView.findViewById(R.id.img_payment_mode);
        }
    }
}

