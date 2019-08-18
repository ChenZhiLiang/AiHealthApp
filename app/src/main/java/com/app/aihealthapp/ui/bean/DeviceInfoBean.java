package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 11:52
 * 修改人：Chen
 * 修改时间：2019/8/18 11:52
 */
public class DeviceInfoBean {

    /**
     * id : 1
     * user_id : 44
     * remark : 123绑定了手环设备
     * is_bind : 1
     * device_no : F4:A8:11:25:26:91
     * add_date : 1566099112
     * is_open_phone : 0
     * is_open_sms : 0
     * is_open_wechat : 0
     * is_open_qq : 0
     * is_open_photo : 0
     */

    private int id;
    private int user_id;
    private String remark;
    private int is_bind;
    private String device_no;
    private int add_date;
    private int is_open_phone;
    private int is_open_sms;
    private int is_open_wechat;
    private int is_open_qq;
    private int is_open_photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(int is_bind) {
        this.is_bind = is_bind;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public int getAdd_date() {
        return add_date;
    }

    public void setAdd_date(int add_date) {
        this.add_date = add_date;
    }

    public int getIs_open_phone() {
        return is_open_phone;
    }

    public void setIs_open_phone(int is_open_phone) {
        this.is_open_phone = is_open_phone;
    }

    public int getIs_open_sms() {
        return is_open_sms;
    }

    public void setIs_open_sms(int is_open_sms) {
        this.is_open_sms = is_open_sms;
    }

    public int getIs_open_wechat() {
        return is_open_wechat;
    }

    public void setIs_open_wechat(int is_open_wechat) {
        this.is_open_wechat = is_open_wechat;
    }

    public int getIs_open_qq() {
        return is_open_qq;
    }

    public void setIs_open_qq(int is_open_qq) {
        this.is_open_qq = is_open_qq;
    }

    public int getIs_open_photo() {
        return is_open_photo;
    }

    public void setIs_open_photo(int is_open_photo) {
        this.is_open_photo = is_open_photo;
    }
}
