package com.app.aihealthapp.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.AppManager;
import com.app.aihealthapp.ui.MainActivity;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.bean.DoctorDetalisBean;
import com.app.aihealthapp.ui.mvvm.view.DoctorDetalisView;
import com.app.aihealthapp.ui.mvvm.view.WebTitleView;
import com.app.aihealthapp.ui.mvvm.viewmode.DoctorDetalisViewMode;
import com.app.aihealthapp.view.CircleImageView;
import com.app.aihealthapp.view.ProgressWebView;
import com.app.aihealthapp.view.WebViewProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 22:43
 * 修改人：Chen
 * 修改时间：2019/8/18 22:43
 */
public class DoctorDetalisActivity extends BaseActivity implements DoctorDetalisView , WebTitleView {

    @BindView(R.id.img_doctor)
    CircleImageView img_doctor;

    @BindView(R.id.tv_doctor_name)
    TextView tv_doctor_name;
    @BindView(R.id.tv_doctor_type)
    TextView tv_doctor_type;
    @BindView(R.id.tv_doctor_level)
    TextView tv_doctor_level;
    @BindView(R.id.tv_doctor_experience)
    TextView tv_doctor_experience;
    @BindView(R.id.tv_hospital_name)
    TextView tv_hospital_name;
    @BindView(R.id.tv_ask_num)
    TextView tv_ask_num;
    @BindView(R.id.tv_good_evaluation)
    TextView tv_good_evaluation;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_ask)
    TextView tv_ask;

    @BindView(R.id.webview)
    ProgressWebView webview;
    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.tv_mine)
    TextView tv_mine;

    @BindView(R.id.btn_ask)
    Button btn_ask;


    private DoctorDetalisViewMode mDoctorDetalisViewMode;
    private int id;
    private int Is_valid;
    private int advice_price;
    private String price_hek;
    private String doctor_name;
    @Override
    public int getLayoutId() {
        return R.layout.activity_doctor_detalis;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("医生在线");
    }

    @Override
    public void initView() {
        id  =getIntent().getIntExtra("id",0);
        Is_valid = getIntent().getIntExtra("Is_valid",0);
        advice_price = getIntent().getIntExtra("advice_price",0);
        price_hek = getIntent().getStringExtra("price_hek");
        doctor_name = getIntent().getStringExtra("doctor_name");
        mDoctorDetalisViewMode = new DoctorDetalisViewMode(this);
        webview.setWebTitleView(this);
        webview.setFocusable(true);//设置有焦点
        webview.setFocusableInTouchMode(true);//设置可触摸

    }

    @Override
    public void initData() {
        mDoctorDetalisViewMode.getDoctorDetalis(id);
    }

    @OnClick({R.id.img_back,R.id.tv_ask,R.id.btn_ask,R.id.tv_home,R.id.tv_mine})
    public void onClick(View v){
        if (v.getId()==R.id.img_back){
            if (webview.canGoBack()) {
                webview.goBack();//返回上一页面
            } else {
              finish();
            }
        }else if (v==tv_ask||v==btn_ask){
            if (Is_valid==0){
                startActivity(new Intent(this, PayCentreActivity.class).putExtra("doctor_id",id)
                        .putExtra("advice_price",advice_price)
                        .putExtra("price_hek",price_hek)
                        .putExtra("doctor_name",doctor_name));
            }else {
//                startActivity(new Intent(this, HealthAskActivity.class).putExtra("doctor_id",id));
                startActivity(new Intent(this, HealthAskActivity.class).putExtra("doctor_id",id).putExtra("kind_type",1));

            }
        }else if (v==tv_home){
            Intent intent = new Intent(this, DoctorListActivity.class);
            intent.putExtra("type","home");
            setResult(RESULT_OK, intent);
            finish();
        }else if (v==tv_mine){
            Intent intent = new Intent(this, DoctorListActivity.class);
            intent.putExtra("type","mine");
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
                webview.goBack();//返回上一页面
                return true;
            } else {
               finish();
                return true;
            }
        }
        return false;
    }

    @Override
    public void DoctorDetalisResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            DoctorDetalisBean mDoctorDetalisBean = GsonHelper.GsonToBean(data,DoctorDetalisBean.class);
            GlideHelper.loadImageView(this,mDoctorDetalisBean.getAvatar(),img_doctor);
            tv_doctor_name.setText(mDoctorDetalisBean.getNickname());
            tv_doctor_type.setText(mDoctorDetalisBean.getDepartment_name());
            tv_doctor_level.setText(mDoctorDetalisBean.getPosition());
            tv_doctor_experience.setText("从业"+mDoctorDetalisBean.getObtain()+"年");
            tv_hospital_name.setText(mDoctorDetalisBean.getHospital());
            tv_ask_num.setText(mDoctorDetalisBean.getPerson_time());
            tv_good_evaluation.setText(mDoctorDetalisBean.getGood_rate());
            tv_price.setText(mDoctorDetalisBean.getPrice_hek());
            webview.loadUrl(ApiUrl.WebApi.DoctorDetail+id);//加载网址

        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void showProgress() {

        hud.show();
    }

    @Override
    public void hideProgress() {

        hud.dismiss();
    }

    @Override
    public void showLoadFailMsg(String err) {
        ToastyHelper.toastyNormal(this,err);
    }

    @Override
    public void onTitleResult(String title) {

    }
}
