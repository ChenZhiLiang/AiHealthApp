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
     * id : 62
     * cat_id : 16
     * nickname : 张医生
     * avatar : http://aijiankang.cacpo.com/upload/pic/20190827/28edde523ec77719cc51f96939996790.jpg
     * department_name : 外科
     * position : 主任医生
     * obtain : 外科
     * hospital : 外科医院
     * hospital_hot :
     * introduce : 外科
     * doctor_skill : 外科
     * advice_price : 1
     * advice_hek : 0
     * price_hek : ¥1+0健康密钥
     * buy_count : 8
     * is_valid : 0
     */

    private int id;
    private int cat_id;
    private String nickname;
    private String avatar;
    private String department_name;
    private String position;
    private String obtain;
    private String hospital;
    private String hospital_hot;
    private String introduce;
    private String doctor_skill;
    private int advice_price;
    private int advice_hek;
    private String price_hek;
    private int buy_count;
    private int is_valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
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

    public int getAdvice_price() {
        return advice_price;
    }

    public void setAdvice_price(int advice_price) {
        this.advice_price = advice_price;
    }

    public int getAdvice_hek() {
        return advice_hek;
    }

    public void setAdvice_hek(int advice_hek) {
        this.advice_hek = advice_hek;
    }

    public String getPrice_hek() {
        return price_hek;
    }

    public void setPrice_hek(String price_hek) {
        this.price_hek = price_hek;
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
