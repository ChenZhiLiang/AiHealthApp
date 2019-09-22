package com.app.aihealthapp.ui.activity.mine;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.aihealthapp.R;
import com.app.aihealthapp.camera.CameraSurfaceView;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.ui.AppContext;
import com.crrepa.ble.conn.listener.CRPCameraOperationListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/9/22 22:31
 * 修改人：Chen
 * 修改时间：2019/9/22 22:31
 */
public class TakePhotoActivity extends BaseActivity {

    @BindView(R.id.img_switch_camera)
    ImageView img_switch_camera;
    @BindView(R.id.img_take_photo)
    ImageView img_take_photo;
    @BindView(R.id.sv_camera)
    CameraSurfaceView mCameraSurfaceView;

    private boolean isClick = true;
    private static final String PATH_IMAGES = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "easy_check";
    @Override
    public int getLayoutId() {
        return R.layout.activity_take_photo;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("相机");
    }

    @Override
    public void initView() {
        img_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        img_switch_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mCameraSurfaceView.changeCamera();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initData() {

        if (AppContext.mBleDevice!=null && AppContext.mBleDevice.isConnected()){
            AppContext.mBleConnection.switchCameraView();
            //手环点击拍照回调 控制手机拍照
            AppContext.mBleConnection.setCameraOperationListener(new CRPCameraOperationListener() {
                @Override
                public void onTakePhoto() {
                    takePhoto();
                }
            });
        }else {
            ToastyHelper.toastyNormal(this,"设备未连接，请连接设备再重试");
        }
    }

    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };
    private Camera.PictureCallback rawPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };
    private Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mCameraSurfaceView.startPreview();
            saveFile(data);
//            Toast.makeText(TakePhotoActivity.this, "拍照成功", Toast.LENGTH_SHORT).show();
            isClick = true;

        }
    };

    public void takePhoto() {
        if (isClick) {
            isClick = false;
            mCameraSurfaceView.takePicture(mShutterCallback, rawPictureCallback, jpegPictureCallback);
        }
    }
    public void saveFile(byte[] data) {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        FileOutputStream outputStream = null;
        try {
            File file = new File(PATH_IMAGES);
            if (!file.exists()) {
                file.mkdirs();
            }
            outputStream = new FileOutputStream(PATH_IMAGES + File.separator + fileName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
