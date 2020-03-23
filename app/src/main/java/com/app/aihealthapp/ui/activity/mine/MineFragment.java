package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseFragment;
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
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.core.permission.Permission;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.ui.WebActyivity;
import com.app.aihealthapp.ui.bean.UserInfoBean;
import com.app.aihealthapp.ui.bean.VersionInfoBean;
import com.app.aihealthapp.ui.mvvm.view.MineView;
import com.app.aihealthapp.ui.mvvm.viewmode.MineViewMode;
import com.app.aihealthapp.util.AppUpdateVersionUtils;
import com.app.aihealthapp.util.utils;
import com.app.aihealthapp.view.PrivacyAgreementDialog;
import com.app.aihealthapp.wxapi.WXShareUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * @Name：AiHealth
 * @Description：我的
 * @Author：Chen
 * @Date：2019/7/24 22:08
 * 修改人：Chen
 * 修改时间：2019/7/24 22:08
 */
public class MineFragment extends BaseFragment implements MineView {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.image_head)
    ImageView image_head;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_invite_code)
    TextView tv_invite_code;
    @BindView(R.id.btn_authentication)
    Button btn_authentication;
    @BindView(R.id.rt_my_key)
    RelativeLayout rt_my_key;//我的健康秘钥
    @BindView(R.id.rt_cash_details)
    RelativeLayout rt_cash_details;
    @BindView(R.id.rt_mine_device)
    RelativeLayout rt_mine_device;//我的健康设备
    @BindView(R.id.rt_mine_ask)
    RelativeLayout rt_mine_ask;//我的咨询
    @BindView(R.id.rt_medical_report)
    RelativeLayout rt_medical_report;//我的体检报告
    @BindView(R.id.rt_healthy_report)
    RelativeLayout rt_healthy_report;
    @BindView(R.id.rt_myorder)//我的订单
    RelativeLayout rt_myorder;
    @BindView(R.id.rt_address)
    RelativeLayout rt_address;//收货地址
    @BindView(R.id.rt_health_plan)
    RelativeLayout rt_health_plan;//我的健康方案

    @BindView(R.id.rt_myfriend_list)
    RelativeLayout rt_myfriend_list;//我的健康朋友圈
    @BindView(R.id.rt_my_members)
    RelativeLayout rt_my_members;
    @BindView(R.id.rt_share_friend)
    RelativeLayout rt_share_friend;//推荐给好友
    @BindView(R.id.rt_about)
    RelativeLayout rt_about;//关于健康秘钥

    @BindView(R.id.rt_agreement)
    RelativeLayout rt_agreement;
    @BindView(R.id.rt_privacy_policy)
    RelativeLayout rt_privacy_policy;

    @BindView(R.id.rt_feedback)
    RelativeLayout rt_feedback;//帮助与反馈

    @BindView(R.id.rt_problem)
    RelativeLayout rt_problem;//常见问题
    @BindView(R.id.rt_version_update)
    RelativeLayout rt_version_update;//版本更新
    @BindView(R.id.rt_editpaw)
    RelativeLayout rt_editpaw;
    @BindView(R.id.btn_logout)
    Button btn_logout;

    private MineViewMode mMineViewMode;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String loading_img;

    private BottomSheetDialog dialogs_share;
    private LinearLayout weChat_friend_layout;
    private LinearLayout weChat_moments_layout;

    public static MineFragment getInstance(String title) {
        MineFragment hf = new MineFragment();
        hf.mTitle = title;
        return hf;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        tv_title_bar.setText("个人中心");

        mMineViewMode = new MineViewMode(this);
    }

    @Override
    public void initData() {
        if (isLogin()){
            btn_authentication.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
            GlideHelper.loadHeadImageView(mActivity,UserHelper.getUserInfo().getAvatar(),image_head);
            tv_invite_code.setVisibility(View.VISIBLE);
            tv_invite_code.setText("邀请码："+UserHelper.getUserInfo().getInvite_code());
            if (UserHelper.getUserInfo().getIs_auth()==0){
                tv_user_name.setText(UserHelper.getUserInfo().getMobile());
                btn_authentication.setText("去实名认证");
            }else {
                tv_user_name.setText(UserHelper.getUserInfo().getOauth_nickname());
                btn_authentication.setText("已认证");
            }
            if (11<=UserHelper.getUserInfo().getLevel_id()&&UserHelper.getUserInfo().getLevel_id()<=16){
                rt_my_members.setVisibility(View.VISIBLE);
            }else {
                rt_my_members.setVisibility(View.GONE);
            }
        }else {
            tv_user_name.setText("点击登录");
            btn_authentication.setVisibility(View.GONE);
            btn_logout.setVisibility(View.GONE);
            tv_invite_code.setVisibility(View.GONE);
        }
    }



    @Override
    public void loadingData() {
        if (isLogin()){
            mMineViewMode.getUserInfo();
        }
    }

    @OnClick({R.id.image_head,R.id.tv_user_name,R.id.btn_authentication,R.id.rt_my_key,R.id.rt_mine_device,R.id.rt_mine_ask,R.id.rt_medical_report,R.id.rt_healthy_report,
            R.id.rt_myorder,R.id.rt_address,R.id.rt_health_plan,R.id.rt_myfriend_list, R.id.rt_about,R.id.rt_agreement,R.id.rt_privacy_policy,R.id.rt_share_friend,R.id.rt_my_members,
            R.id.rt_feedback,R.id.rt_editpaw,R.id.rt_cash_details,R.id.btn_logout,R.id.rt_problem,R.id.rt_version_update})
    public void onClick(View v){

        if (v==rt_about){
            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.About));
        }else if (v==rt_agreement){
            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.UserProtocol));

        }else if (v==rt_privacy_policy){
            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.PrivacyProtocol));

        }else if (v==rt_feedback){
            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Feedback));
        }else if (v==rt_problem){
            startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.common_problem));
        }else if (v==rt_version_update){
//            showLoadFailMsg("版本更新");
            mMineViewMode.GetVersionInfo();

        }else {
            if (isLogin()){
                if (v==image_head){
                    CircleDialogHelper.ShowBottomDialog((AppCompatActivity) mActivity, getResources().getStringArray(R.array.head_check), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) {
                                // 获得摄像权限才能进入拍照界面
                                if (new PermissionHelper().RequestPermisson(mActivity, Permission.CAMERA)){
                                    PictureSelector.create(MineFragment.this)
                                            .openCamera(PictureMimeType.ofImage())
                                            .compress(true)
                                            .forResult(PictureConfig.REQUEST_CAMERA);
                                }
                            } else {
                                //获得访问外部存储权限才能访问相册
                                if (new PermissionHelper().RequestPermisson(mActivity, Permission.WRITE_EXTERNAL_STORAGE)){
                                    PictureSelector.create(MineFragment.this)
                                            .openGallery(PictureMimeType.ofImage())
                                            .compress(true)
                                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                                            .forResult(PictureConfig.CHOOSE_REQUEST);
                                }
                            }
                        }
                    });
                }else if (v==btn_authentication){
                    startActivity(new Intent(getContext(),AuthenticationUserActivity.class));
                }else if (v==rt_cash_details){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyWallet+UserHelper.getUserInfo().getId()));

                }else if (v==rt_my_key){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyKeyList+UserHelper.getUserInfo().getId()));
                }else if (v==rt_mine_device){
                    startActivity(new Intent(getContext(),MineDeviceActivity.class));
                }else if (v==rt_mine_ask){
                    startActivity(new Intent(getContext(),MineAskActivity.class));
                }else if (v==rt_medical_report){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MedicalReport+UserHelper.getUserInfo().getId()));
                }else if (v==rt_healthy_report){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.Healthy_Report+UserHelper.getUserInfo().getId()));

                }else if (v==rt_myorder){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyOrder+UserHelper.getUserInfo().getId()));

                }else if (v==rt_address){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.User_Address+UserHelper.getUserInfo().getId()));

                }else if (v==rt_health_plan){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.HealthPlan+UserHelper.getUserInfo().getId()));
                }else if (v==rt_myfriend_list){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyfriendList + UserHelper.getUserInfo().getId()));
                }else if (v==rt_editpaw){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.EditPwd + UserHelper.getUserInfo().getId()));

                }else if (v==rt_share_friend){
                    View view = View.inflate(getContext(), R.layout.dialog_custom, null);
                    weChat_friend_layout = view.findViewById(R.id.weChat_friend_layout);
                    weChat_moments_layout = view.findViewById(R.id.weChat_moments_layout);
                    onShareClick shareClick = new onShareClick();
                    weChat_friend_layout.setOnClickListener(shareClick);
                    weChat_moments_layout.setOnClickListener(shareClick);
                    dialogs_share = new BottomSheetDialog(getContext());
                    dialogs_share.setContentView(view);
                    dialogs_share.show();
//                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.ShareFriend));
                }else if (v==rt_my_members){
                    startActivity(new Intent(mActivity, WebActyivity.class).putExtra("url", ApiUrl.WebApi.MyMembers));
                }else if (v==btn_logout){
                    CircleDialogHelper.ShowDialogHint((AppCompatActivity)mActivity, "确定注销吗?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UserHelper.clearUser();
                            EventBusHelper.sendEvent(new Event(EventCode.Code.LOGOUT));
                        }
                    }, null);
                }
            }else {
                startActivity(new Intent(mActivity,LoginActivity.class));
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
            btn_authentication.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
            tv_invite_code.setVisibility(View.VISIBLE);
            tv_invite_code.setText("邀请码："+mUserInfo.getInvite_code());
            GlideHelper.loadHeadImageView(mActivity,UserHelper.getUserInfo().getAvatar(),image_head);
            if (TextUtils.isEmpty(UserHelper.getUserInfo().getNickname())){
                tv_user_name.setText(UserHelper.getUserInfo().getMobile());
                btn_authentication.setText("去实名认证");
            }else {
                tv_user_name.setText(UserHelper.getUserInfo().getOauth_nickname());
                btn_authentication.setText("已认证");
            }
            if (11<=UserHelper.getUserInfo().getLevel_id()&&UserHelper.getUserInfo().getLevel_id()<=16){
                rt_my_members.setVisibility(View.VISIBLE);
            }else {
                rt_my_members.setVisibility(View.GONE);
            }
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
            mMineViewMode.UpdateProfile(url);
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),""));
        }
    }

    @Override
    public void UpdateProfileResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
//            Bitmap bm = BitmapFactory.decodeFile(loading_img);
            mMineViewMode.getUserInfo();
            showLoadFailMsg("上传成功");
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),""));

        }
    }

    @Override
    public void versionInfoResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            JSONObject resp = JSONObject.parseObject(result.toString());
            VersionInfoBean mData = JSON.parseObject(resp.getString("data"), VersionInfoBean.class);

            if (Integer.parseInt(mData.getVersionCode()) > utils.getVersionCode(mActivity)) {
                new AppUpdateVersionUtils().UpdateVersion(getActivity(),mData);
            }else {
                showLoadFailMsg("已是最新版本");
            }
        }else {
            Log.i("getVersion_err", "获取版本信息失败");
        }
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (event.getCode() == EventCode.Code.LOGIN_SUCCESS) {//登录成功
            mMineViewMode.getUserInfo();
//            btn_authentication.setVisibility(View.VISIBLE);
//            tv_invite_code.setVisibility(View.VISIBLE);
//            btn_logout.setVisibility(View.VISIBLE);
//            GlideHelper.loadHeadImageView(mActivity,UserHelper.getUserInfo().getAvatar(),image_head);
//            tv_invite_code.setText("邀请码："+UserHelper.getUserInfo().getInvite_code());
//            if (TextUtils.isEmpty(UserHelper.getUserInfo().getNickname())){
//                tv_user_name.setText(UserHelper.getUserInfo().getMobile());
//                btn_authentication.setText("实名认证");
//                btn_authentication.setEnabled(true);
//            }else {
//                tv_user_name.setText(UserHelper.getUserInfo().getNickname());
//                btn_authentication.setText("已认证");
//                btn_authentication.setEnabled(false);
//            }
        }else if (event.getCode()== EventCode.Code.AUTHENTICATION_SUCCESS){
            mMineViewMode.getUserInfo();

        } else if (event.getCode() == EventCode.Code.LOGOUT) {//注销
            GlideHelper.loadImageView(mActivity, R.mipmap.my_head, image_head);
            tv_user_name.setText("点击登录");
            btn_authentication.setVisibility(View.GONE);
            tv_invite_code.setVisibility(View.GONE);
            btn_logout.setVisibility(View.GONE);

        }
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            mMineViewMode.uploadHead(file);
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
                            mMineViewMode.uploadHead(file);
                        }
                    }
                    break;
            }
        }
    }
    /**
     * 分享dialog 点击事件
     */
    private class onShareClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == weChat_friend_layout) {//微信好友分享
                WXShareUtil.WXShare(getContext(), SendMessageToWX.Req.WXSceneSession);
            } else {//微信朋友圈分享
                WXShareUtil.WXShare(getContext(),SendMessageToWX.Req.WXSceneTimeline);
            }
            dialogs_share.dismiss();
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

        ToastyHelper.toastyNormal(mActivity,err);
    }
}
