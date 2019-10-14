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

    public static String[] cash_pay = {"微信支付","支付宝支付"};

    public static String[]  key_pay = {"健康密钥支付"};



    public  SelectViewMode(Context context){

    }


    public static List<SelectedBean> getCashPayDatas(){
        List<SelectedBean> datas = new ArrayList<>();
        for (int i=0;i<cash_pay.length;i++){
            SelectedBean bean = new SelectedBean();
            switch (i){
                case 0:
                    bean.setName(cash_pay[i]);
                    bean.setBitmap(R.mipmap.weixin_icon);
                    break;
                case 1:
                    bean.setName(cash_pay[i]);
                    bean.setBitmap(R.mipmap.zhifubao_icon);
                    break;
             /*   case 2:
                    bean.setName(parking_pay[i]);
                    bean.setBitmap(R.mipmap.miyao_icon);
                    break;*/

            }

            bean.setSelected(i==0?true:false);
            datas.add(bean);
        }
        return datas;
    }
    public static List<SelectedBean> getKeyPayDatas(){
        List<SelectedBean> datas = new ArrayList<>();
        for (int i=0;i<key_pay.length;i++){
            SelectedBean bean = new SelectedBean();
            switch (i){
                case 0:
                    bean.setName(key_pay[i]);
                    bean.setBitmap(R.mipmap.miyao_icon);
                    break;
            }

            bean.setSelected(i==0?true:false);
            datas.add(bean);
        }
        return datas;
    }
}
