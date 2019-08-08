package com.app.aihealthapp.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseHolder;
import com.app.aihealthapp.core.base.BaseXRecyclerViewAdapter;
import com.crrepa.ble.conn.CRPBleDevice;

import java.util.List;

import butterknife.BindView;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/7/27 17:50
 * 修改人：Chen
 * 修改时间：2019/7/27 17:50
 */
public class DeviceListAdapter extends BaseXRecyclerViewAdapter<CRPBleDevice> {


    public DeviceListAdapter(List<CRPBleDevice> data) {
        super(data);
    }

    @Override
    public BaseHolder<CRPBleDevice> getHolder(View v) {
        return new DeviceListHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_device_item;
    }

    public class DeviceListHolder extends BaseHolder<CRPBleDevice>{

        @BindView(R.id.tv_device_name)
        TextView tv_device_name;
        @BindView(R.id.tv_mac_address)
        TextView tv_mac_address;
        public DeviceListHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(CRPBleDevice data) {
            tv_device_name.setText(data.getName());
            tv_mac_address.setText(data.getMacAddress());

        }
    }
}
