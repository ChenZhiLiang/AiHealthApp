package com.app.aihealthapp.ui.activity.service;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/9/20 21:39
 * 修改人：Chen
 * 修改时间：2019/9/20 21:39
 */
public interface MyMessage {
    /**
     * 电话来了
     */
     void comePhone();

    /**
     * 短信来了
     */
    void  comeShortMessage();

    /**
     * 微信消息
     */
    void  comeWxMessage();

    /**
     * qq消息
     */
    void comeQQmessage();
}
