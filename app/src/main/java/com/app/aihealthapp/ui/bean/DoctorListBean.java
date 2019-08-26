package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 10:58
 * 修改人：Chen
 * 修改时间：2019/8/18 10:58
 */
public class DoctorListBean {

    /**
     * id : 41
     * nickname : 张三
     * avatar : http://aijiankang.cacpo.com/wap/images/default.png
     * department_name : 产科
     * position : 副主任医师
     * obtain : 从业20年
     * hospital : 南宁中医学院仁爱医院北院
     * hospital_hot : 不孕不育，脐带绕颈，产前检查，产后恢复
     * introduce : 不孕不育，脐带绕颈，产前检查，产后恢复
     * doctor_skill : 不孕不育，脐带绕颈，产前检查，产后恢复
     * advice_price : 10.00
     * buy_count : 0
     */

    private int id;
    private String nickname;
    private String avatar;
    private String department_name;
    private String position;
    private String obtain;
    private String hospital;
    private String hospital_hot;
    private String introduce;
    private String doctor_skill;
    private String advice_price;
    private int buy_count;
    private int is_valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getObtain() {
        return obtain;
    }

    public void setObtain(String obtain) {
        this.obtain = obtain;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospital_hot() {
        return hospital_hot;
    }

    public void setHospital_hot(String hospital_hot) {
        this.hospital_hot = hospital_hot;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getDoctor_skill() {
        return doctor_skill;
    }

    public void setDoctor_skill(String doctor_skill) {
        this.doctor_skill = doctor_skill;
    }

    public String getAdvice_price() {
        return advice_price;
    }

    public void setAdvice_price(String advice_price) {
        this.advice_price = advice_price;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }

    public int getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(int is_valid) {
        this.is_valid = is_valid;
    }
}
