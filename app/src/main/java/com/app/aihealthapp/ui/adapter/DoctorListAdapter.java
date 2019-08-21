package com.app.aihealthapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.activity.home.DoctorDetalisActivity;
import com.app.aihealthapp.ui.activity.home.DoctorListActivity;
import com.app.aihealthapp.ui.activity.home.PayCentreActivity;
import com.app.aihealthapp.ui.bean.DoctorListBean;

import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 10:58
 * 修改人：Chen
 * 修改时间：2019/8/18 10:58
 */
public class DoctorListAdapter extends BaseXRecyclerViewAdapter<DoctorListBean> {

    private Activity mActivity;
    public DoctorListAdapter(Activity mActivity,List<DoctorListBean> data) {
        super(data);
        this.mActivity = mActivity;
    }

    @Override
    public BaseHolder<DoctorListBean> getHolder(View v) {
        return new DoctorListHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_doctor_list;
    }

    public class DoctorListHolder extends BaseHolder<DoctorListBean>{

        @BindView(R.id.rt_main)
        RelativeLayout rt_main;
        @BindView(R.id.img_doctor)
        ImageView img_doctor;
        @BindView(R.id.tv_doctor_name)
        TextView tv_doctor_name;
        @BindView(R.id.tv_doctor_level)
        TextView tv_doctor_level;

        @BindView(R.id.tv_doctor_experience)
        TextView tv_doctor_experience;
        @BindView(R.id.tv_hospital_name)
        TextView tv_hospital_name;
        @BindView(R.id.tv_adept_content)
        TextView tv_adept_content;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_buy_number)
        TextView tv_buy_number;

        @BindView(R.id.btn_ask)
        Button btn_ask;

        public DoctorListHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(final DoctorListBean data) {

            GlideHelper.loadImageView(mActivity,data.getAvatar(),img_doctor);
            tv_doctor_name.setText(data.getNickname());
            tv_doctor_level.setText(data.getDepartment_name());
            tv_doctor_experience.setText(data.getPosition());
            tv_hospital_name.setText(data.getHospital());
            tv_adept_content.setText(data.getDoctor_skill());
            tv_price.setText("¥"+data.getAdvice_price());
            tv_buy_number.setText("（"+data.getBuy_count()+"人购买）");
            btn_ask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, PayCentreActivity.class).putExtra("doctor_id",data.getId())
                            .putExtra("advice_price",data.getAdvice_price())
                            .putExtra("doctor_name",data.getNickname()));
                }
            });

            rt_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, DoctorDetalisActivity.class).putExtra("id",data.getId()));
                }
            });


        }
    }
}
