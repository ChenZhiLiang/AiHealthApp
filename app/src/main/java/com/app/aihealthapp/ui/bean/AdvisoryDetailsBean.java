package com.app.aihealthapp.ui.bean;

import java.util.List;

public class AdvisoryDetailsBean {

    int id;
    String title;
    int kind_type;
    int doctor_id;
    String info;
    int user_id;
    String add_date;
    String checklist_pic;
    String medical_pic;
    String affected_part_pic;
    String other_pic;

    List<ReplyItemBean>reply_item;


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

    public List<ReplyItemBean> getReply_item() {
        return reply_item;
    }

    public void setReply_item(List<ReplyItemBean> reply_item) {
        this.reply_item = reply_item;
    }
}
