package com.app.aihealthapp.ui.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.CircleDialogHelper;
import com.app.aihealthapp.core.helper.PermissionHelper;
import com.app.aihealthapp.core.permission.Permission;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

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
public class HealthAskActivity extends BaseActivity {

    @BindView(R.id.img_checklist)
    ImageView img_checklist;
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

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.img_checklist})
    public void onClick(View v){
        if (v==img_checklist){
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
}
