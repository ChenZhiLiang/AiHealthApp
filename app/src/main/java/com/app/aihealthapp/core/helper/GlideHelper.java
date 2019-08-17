package com.app.aihealthapp.core.helper;

import android.content.Context;
import android.widget.ImageView;

import com.app.aihealthapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * author：chenzl
 * Create time: 2017/11/9 0009 17:09
 * describe: Glide二次封装
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class GlideHelper {


    static RequestOptions options_context = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.aio_image_fail_round)
            .error(R.mipmap.aio_image_fail_round)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE);
    static RequestOptions options_head = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.my_head)
            .error(R.mipmap.my_head)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE);


    public static void loadHeadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).apply(options_head).into(mImageView);
    }
    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).apply(options_context).into(mImageView);
    }

    //默认加载
    public static void loadImageView(Context mContext, Integer resourceId, ImageView mImageView) {
        Glide.with(mContext).load(resourceId).apply(options_context).into(mImageView);
    }

    /**
     * 会先加载缩略图
     */
    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }
}
