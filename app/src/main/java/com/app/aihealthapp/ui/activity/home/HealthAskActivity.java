package com.app.aihealthapp.ui.activity.home;

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

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.CircleDialogHelper;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.helper.PermissionHelper;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.permission.Permission;
import com.app.aihealthapp.ui.mvvm.view.HealthAskView;
import com.app.aihealthapp.ui.mvvm.viewmode.HealthAskViewMode;
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
 * @Description：健康提问
 * @Author：Chen
 * @Date：2019/7/26 21:13
 * 修改人：Chen
 * 修改时间：2019/7/26 21:13
 */
public class HealthAskActivity extends BaseActivity implements HealthAskView {

    @BindView(R.id.edit_input_content)
    EditText edit_input_content;
    @BindView(R.id.img_checklist)
    ImageView img_checklist;
    @BindView(R.id.img_case)
    ImageView img_case;
    @BindView(R.id.img_affected_part)
    ImageView img_affected_part;
    @BindView(R.id.img_other)
    ImageView img_other;

    @BindView(R.id.btn_submit)
    Button btn_submit;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String loading_img;

    private HealthAskViewMode mHealthAskViewMode;
    private String checklist_pic;
    private String medical_pic;
    private String affected_part_pic;
    private String other_pic;

    private int ClickType = 0;
    private  int doctor_id = 0;
    private int kind_type;
    @Override
    public int getLayoutId() {
        return R.layout.activity_health_ask;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("健康提问");
    }

    @Override
    public void initView() {
        doctor_id = getIntent().getIntExtra("doctor_id",0);
        kind_type = getIntent().getIntExtra("kind_type",0);
        mHealthAskViewMode = new HealthAskViewMode(this);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.img_checklist,R.id.img_case,R.id.img_affected_part,R.id.img_other,R.id.btn_submit})
    public void onClick(View v){

        if (v==btn_submit){

            if (TextUtils.isEmpty(edit_input_content.getText().toString())){
                showLoadFailMsg("请输入内容");
            }
//            else if (TextUtils.isEmpty(checklist_pic)){
//                showLoadFailMsg("请上传检查单照片");
//            }else if(TextUtils.isEmpty(medical_pic)){
//                showLoadFailMsg("请上传病例照片");
//            }else if (TextUtils.isEmpty(affected_part_pic)){
//                showLoadFailMsg("请上传患处照片");
//            }else if (TextUtils.isEmpty(other_pic)){
//                showLoadFailMsg("请上传其他照片");
//            }
            else {
                mHealthAskViewMode.question(doctor_id,edit_input_content.getText().toString(),kind_type,checklist_pic,medical_pic,affected_part_pic,other_pic);
            }
        }else {
            if (v==img_checklist){
                ClickType = 0;
            }else if (v==img_case){
                ClickType=1;
            }else if (v==img_affected_part){
                ClickType=2;
            }else if (v==img_other){
                ClickType=3;
            }
            CircleDialogHelper.ShowBottomDialog(HealthAskActivity.this, getResources().getStringArray(R.array.head_check), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        // 获得摄像权限才能进入拍照界面
                        if (new PermissionHelper().RequestPermisson(HealthAskActivity.this, Permission.CAMERA)){
                            PictureSelector.create(HealthAskActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .compress(true)
                                    .forResult(PictureConfig.REQUEST_CAMERA);
                        }
                    } else {
                        //获得访问外部存储权限才能访问相册
                        if (new PermissionHelper().RequestPermisson(HealthAskActivity.this, Permission.WRITE_EXTERNAL_STORAGE)){
                            PictureSelector.create(HealthAskActivity.this)
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
                            mHealthAskViewMode.uploadHead(file);
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
                            mHealthAskViewMode.uploadHead(file);
                        }
                    }
                    break;
            }
        }
    }


    @Override
    public void questionResult(Object result) {
        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            showLoadFailMsg("提交成功");
            finish();
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),""));
        }
    }

    @Override
    public void uploadResult(Object result) {

        int ret = GsonHelper.GsonToInt(result.toString(),"ret");
        if (ret==0){
            String data = GsonHelper.GsonToData(result.toString(),"data").toString();
            String url = GsonHelper.GsonToString(data,"url");
            Bitmap bm = BitmapFactory.decodeFile(loading_img);

            if (ClickType==0){
                checklist_pic = url;
                img_checklist.setImageBitmap(bm);
            }else if (ClickType==1){
                medical_pic=url;
                img_case.setImageBitmap(bm);
            }else if (ClickType==2){
                affected_part_pic=url;
                img_affected_part.setImageBitmap(bm);
            }else if (ClickType==3){
                other_pic=url;
                img_other.setImageBitmap(bm);
            }
        }else {
            showLoadFailMsg(GsonHelper.GsonToString(result.toString(),""));
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
