package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 23:17
 * 修改人：Chen
 * 修改时间：2019/8/18 23:17
 */
public class DoctorDetalisBean {

    /**
     * id : 41
     * nickname : 张三
     * avatar : http://aijiankang.cacpo.com/upload/pic/
     * department_name : 产科
     * position : 副主任医师
     * obtain : 从业20年
     * hospital : 南宁中医学院仁爱医院北院
     * hospital_hot : 不孕不育，脐带绕颈，产前检查，产后恢复
     * introduce : 不孕不育，脐带绕颈，产前检查，产后恢复
     * doctor_skill : 不孕不育，脐带绕颈，产前检查，产后恢复
     * advice_price : 10.00
     * good_rate : 90%
     * person_time : 12.9次
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
    private String good_rate;
    private String person_time;

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

    public String getGood_rate() {
        return good_rate;
    }

    public void setGood_rate(String good_rate) {
        this.good_rate = good_rate;
    }

    public String getPerson_time() {
        return person_time;
    }

    public void setPerson_time(String person_time) {
        this.person_time = person_time;
    }
}
