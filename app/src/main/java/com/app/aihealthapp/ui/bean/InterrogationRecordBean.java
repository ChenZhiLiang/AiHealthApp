package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 22:18
 * 修改人：Chen
 * 修改时间：2019/8/19 22:18
 */
public class InterrogationRecordBean {

    /**
     * id : 3
     * title :
     * kind_type : 0
     * doctor_id : 0
     * info : 王伟娥方飞飞哥哥光头然然服服帖帖哥哥
     * user_id : 51
     * add_date : 2019-08-21
     * checklist_pic : 20190821/f51f1988dfbe2cb46e6838595ac82aef.png
     * medical_pic : 20190821/a47380f23084e3a72dc4318b70e868ad.png
     * affected_part_pic : 20190821/66051199a6be7493d2e3ae426da38b41.png
     * other_pic : 20190821/8236c8181be5ae8d65b3b7736e6829b0.png
     * reply_info : null
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
    int is_reply;//1 已回复

    private String reply_info;

    public int getIs_reply() {
        return is_reply;
    }

    public void setIs_reply(int is_reply) {
        this.is_reply = is_reply;
    }

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
}
