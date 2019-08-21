package com.app.aihealthapp.ui.mvvm.viewmode;

import android.content.Context;

import com.app.aihealthapp.R;
import com.app.aihealthapp.ui.bean.SelectedBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/21 23:41
 * 修改人：Chen
 * 修改时间：2019/8/21 23:41
 */
public class SelectViewMode {

    public static String[] parking_pay = {"微信扫码支付","支付宝扫码支付","健康密钥支付"};




    public  SelectViewMode(Context context){

    }


    public static List<SelectedBean> getDatas(){
        List<SelectedBean> datas = new ArrayList<>();
        for (int i=0;i<parking_pay.length;i++){
            SelectedBean bean = new SelectedBean();
            switch (i){
                case 0:
                    bean.setName(parking_pay[i]);
                    bean.setBitmap(R.mipmap.weixin_icon);
                    break;
                case 1:
                    bean.setName(parking_pay[i]);
                    bean.setBitmap(R.mipmap.zhifubao_icon);
                    break;
                case 2:
                    bean.setName(parking_pay[i]);
                    bean.setBitmap(R.mipmap.miyao_icon);
                    break;

            }

            bean.setSelected(i==0?true:false);
            datas.add(bean);
        }
        return datas;
    }
}
