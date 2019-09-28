package com.app.aihealthapp.ui.bean;

import java.io.Serializable;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 22:00
 * 修改人：Chen
 * 修改时间：2019/8/19 22:00
 */
public class SearchRecordBean implements Serializable {

    /**
     * id : 17
     * title :
     * kind_type : 1
     * doctor_id : 62
     * info : 毫末来咯噢噢噢哦哦下雨啦阿卡丽咯哦哦老K就拉个咯啦咯啦
     * user_id : 68
     * add_date : 2019-09-03
     * checklist_pic : 20190903/cef30d3ac233ba88c07c2f66d1eca4f1.png
     * medical_pic : 20190903/f0de957af502dafef5fbc7fb3f479aa8.png
     * affected_part_pic : 20190903/3bb7a7365e1deb2e4b7f5e94e5fb2508.png
     * other_pic : 20190903/2219a8e150c251d1647c7c45e83d1103.jpg
     * reply_info : null
     * is_reply : 0
     * doctor_name : 张医生
     * doctor_avatar : /upload/pic/avatar/20190827/28edde523ec77719cc51f96939996790.jpg
     */

    private int id;
    private String title;
    private int kind_type;
    private int doctor_id;
    private String info;
    private int user_id;
    private String add_date;
    private String checklist_pic;
    private String medical_pic;
    private String affected_part_pic;
    private String other_pic;
    private String reply_info;
    private int is_reply;
    private String doctor_name;
    private String doctor_avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getKind_type() {
        return kind_type;
    }

    public void setKind_type(int kind_type) {
        this.kind_type = kind_type;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public String getChecklist_pic() {
        return checklist_pic;
    }

    public void setChecklist_pic(String checklist_pic) {
        this.checklist_pic = checklist_pic;
    }

    public String getMedical_pic() {
        return medical_pic;
    }

    public void setMedical_pic(String medical_pic) {
        this.medical_pic = medical_pic;
    }

    public String getAffected_part_pic() {
        return affected_part_pic;
    }

    public void setAffected_part_pic(String affected_part_pic) {
        this.affected_part_pic = affected_part_pic;
    }

    public String getOther_pic() {
        return other_pic;
    }

    public void setOther_pic(String other_pic) {
        this.other_pic = other_pic;
    }

    public String getReply_info() {
        return reply_info;
    }

    public void setReply_info(String reply_info) {
        this.reply_info = reply_info;
    }

    public int getIs_reply() {
        return is_reply;
    }

    public void setIs_reply(int is_reply) {
        this.is_reply = is_reply;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_avatar() {
        return doctor_avatar;
    }

    public void setDoctor_avatar(String doctor_avatar) {
        this.doctor_avatar = doctor_avatar;
    }
}
