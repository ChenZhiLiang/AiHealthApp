package com.app.aihealthapp.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.core.network.api.ApiUrl;
import com.app.aihealthapp.ui.AppContext;
import com.app.aihealthapp.util.utils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import butterknife.internal.Utils;

/**
 * author：chenzl
 * Create time: 2018/5/7 0007 09:51
 * describe:
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class WXShareUtil {

    /**
     * @param context
     * @param scene = SendMessageToWX.Req.WXSceneTimeline //设置发送到朋友圈; SendMessageToWX.Req.WXSceneSession;   //设置发送给朋友
     */
    public static void WXShare(Context context, int scene){
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = ApiUrl.WebApi.ShareFriend + UserHelper.getUserInfo().getId();
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        if (scene==SendMessageToWX.Req.WXSceneSession){//微信好友分享
            msg.title = context.getString(R.string.share_title);
            msg.description = context.getString(R.string.share_description);
        }else {
            msg.title = context.getString(R.string.share_title);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
        msg.thumbData = utils.bmpToByteArray(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage";
        req.message = msg;
        req.scene = scene;
        AppContext.wxapi.sendReq(req);
    }

    public static void WXShare(Context context, int scene,String title,String content,String url){
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        if (scene==SendMessageToWX.Req.WXSceneSession){//微信好友分享
            msg.title = title;
            msg.description = content;
        }else {
            msg.title = title;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
        msg.thumbData = utils.bmpToByteArray(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage";
        req.message = msg;
        req.scene = scene;
        AppContext.wxapi.sendReq(req);
    }
}
