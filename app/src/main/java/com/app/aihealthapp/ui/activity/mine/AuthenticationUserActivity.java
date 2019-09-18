package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.eventbus.Event;
import com.app.aihealthapp.core.eventbus.EventCode;
import com.app.aihealthapp.core.helper.CircleDialogHelper;
import com.app.aihealthapp.core.helper.EventBusHelper;
import com.app.aihealthapp.core.helper.GlideHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.PermissionHelper;
import com.app.aihealthapp.core.helper.SharedPreferenceHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.permission.Permission;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.bean.AdvListBean;
import com.app.aihealthapp.ui.bean.UserInfoBean;
import com.app.aihealthapp.ui.mvvm.view.AuthenticationUserView;
import com.app.aihealthapp.ui.mvvm.viewmode.AuthenticationUserViewMode;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：认证会员
 * @Author：Chen
 * @Date：2019/7/26 20:31
 * 修改人：Chen
 * 修改时间：2019/7/26 20:31
 */
public class AuthenticationUserActivity extends BaseActivity implements AuthenticationUserView {


    @BindView(R.id.image_logo)
    ImageView image_logo;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.edit_input_name)
    EditText edit_input_name;
    @BindView(R.id.edit_input_nickname)
    EditText edit_input_nickname;
    @BindView(R.id.edit_input_height)
    EditText edit_input_height;
    @BindView(R.id.edit_input_weight)
    EditText edit_input_weight;
    @BindView(R.id.edit_input_card_no)
    EditText edit_input_card_no;
    @BindView(R.id.edit_input_bank_no)
    EditText edit_input_bank_no;
    @BindView(R.id.edit_input_bank_name)
    EditText edit_input_bank_name;
    @BindView(R.id.edit_input_alipay_no)
    EditText edit_input_alipay_no;
    @BindView(R.id.tv_uploading)
    TextView tv_uploading;
    @BindView(R.id.rt_sex)
    RelativeLayout rt_sex;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    private int sex = 1;//0保密 1男 2女
    private String alipay_pay_pic;

    private AuthenticationUserViewMode mAuthenticationUserViewMode;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String loading_img;
    @Override
    public int getLayoutId() {
        return R.layout.activity_authentication_user;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("实名认证");
    }

    @Override
    public void initView() {
        mAuthenticationUserViewMode = new AuthenticationUserViewMode(this);
    }

    @Override
    public void initData() {
        mAuthenticationUserViewMode.getAds(6);
        if (isLogin()){
            mAuthenticationUserViewMode.getUserInfo();
        }
    }

    @OnClick({R.id.rt_sex,R.id.tv_uploading,R.id.btn_submit})
    public void onClick(View v){
        if (v==rt_sex){
            CircleDialogHelper.ShowBottomDialog(this, getResources().getStringArray(R.array.sex), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position==0){
                       tv_sex.setText("男");
                        sex = 1;
                    }else if (position==1){
                        sex = 2;
                        tv_sex.setText("女");
                    }else {
                        sex = 0;
                        tv_sex.setText("保密");
                    }
                }
            });
        }else if (v==btn_submit){
            if (TextUtils.isEmpty(edit_input_name.getText().toString())){
                showLoadFailMsg("请输入真实姓名");
            }else if (TextUtils.isEmpty(edit_input_nickname.getText().toString())){
                showLoadFailMsg("请输入昵称");
            }else if (TextUtils.isEmpty(edit_input_height.getText().toString())){
                showLoadFailMsg("请输入身高");
            }else if (TextUtils.isEmpty(edit_input_weight.getText().toString())){
                showLoadFailMsg("请输入体重");
            }else if (TextUtils.isEmpty(edit_input_card_no.getText().toString())){
                showLoadFailMsg("请输入身份证号");
            }else if (TextUtils.isEmpty(edit_input_bank_no.getText().toString())){
                showLoadFailMsg("请输入银行卡号");
            }else if (TextUtils.isEmpty(edit_input_bank_no.getText().toString())){
                showLoadFailMsg("请输入银行卡号");
            }else if (TextUtils.isEmpty(edit_input_alipay_no.getText().toString())){
                showLoadFailMsg("请输入支付宝账号");
            }else {
                mAuthenticationUserViewMode.AuthenticationUser(edit_input_name.getText().toString(),edit_input_nickname.getText().toString(),sex,
                        edit_input_height.getText().toString(),edit_input_weight.getText().toString(),edit_input_card_no.getText().toString(),edit_input_bank_name.getText().toString(),
                        edit_input_bank_no.getText().toString(),edit_input_alipay_no.getText().toString(),alipay_pay_pic);
            }
        }else if (v==tv_uploading){
            CircleDialogHelper.ShowBottomDialog(AuthenticationUserActivity.this, getResources().getStringArray(R.array.head_check), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        // 获得摄像权限才能进入拍照界面
                        if (new PermissionHelper().RequestPermisson(AuthenticationUserActivity.this, Permission.CAMERA)){
                            PictureSelector.create(AuthenticationUserActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .compress(true)
                                    .forResult(PictureConfig.REQUEST_CAMERA);
                        }
                    } else {
                        //获得访问外部存储权限才能访问相册
                        if (new PermissionHelper().RequestPermisson(AuthenticationUserActivity.this, Permission.WRITE_EXTERNAL_STORAGE)){
                            PictureSelector.create(AuthenticationUserActivity.this)
                                    .openGallery(PictureMimeType.ofImage())
                                    .compress(true)
                                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        if (media.isCompressed()){
                            loading_img = media.getCompressPath();
                            File file = new File(media.getCompressPath());
                            mAuthenticationUserViewMode.uploadHead(file);
                        }
                    }

                    break;
                case PictureConfig.REQUEST_CAMERA:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectList) {
                        if (media.isCompressed()){
                            loading_img = media.getCompressPath();
                            File file = new File(media.getCompressPath());
                            mAuthenticationUserViewMode.uploadHead(file);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void UesrInfoResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            UserInfoBean mUserInfo = GsonHelper.GsonToBean(data,UserInfoBean.class);
            SharedPreferenceHelper.setUserInfo(AppContext.getContext(),mUserInfo);
            if (!TextUtils.isEmpty(UserHelper.getUserInfo().getNickname())){
                edit_input_name.setText(UserHelper.getUserInfo().getOauth_nickname());
                edit_input_name.setSelection(UserHelper.getUserInfo().getOauth_nickname().length());
                edit_input_nickname.setText(UserHelper.getUserInfo().getNickname());
                if (UserHelper.getUserInfo().getSex()==1){
                    tv_sex.setText("男");
                }else {
                    tv_sex.setText("女");
                }
                edit_input_height.setText(UserHelper.getUserInfo().getHeight()+"");
                edit_input_weight.setText(UserHelper.getUserInfo().getWeight()+"");
                edit_input_card_no.setText(UserHelper.getUserInfo().getCard_no());
                edit_input_bank_no.setText(UserHelper.getUserInfo().getBank_no());
                edit_input_bank_name.setText(UserHelper.getUserInfo().getBank_name());
                edit_input_alipay_no.setText(UserHelper.getUserInfo().getAlipay_no());
            }
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void AuthenticationUserResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            showLoadFailMsg("实名认证成功");
            EventBusHelper.sendEvent(new Event(EventCode.Code.AUTHENTICATION_SUCCESS));
            finish();
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),"msg"));
        }
    }

    @Override
    public void uploadResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String url = GsonHelper.GsonToString(data,"url");
            alipay_pay_pic = url;
            tv_uploading.setText("已上传");
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),""));
        }
    }

    @Override
    public void AdsResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String path = GsonHelper.GsonToString(data,"pic");
            String title = GsonHelper.GsonToString(data,"title");
            String info = GsonHelper.GsonToString(data,"info");
            GlideHelper.loadImageView(this,path,image_logo);
            tv_title.setText(title);
            tv_content.setText(info);
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
}
