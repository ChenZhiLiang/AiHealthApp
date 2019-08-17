package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/15 21:45
 * 修改人：Chen
 * 修改时间：2019/8/15 21:45
 */
public class GoodsCateListBean {

    /**
     * id : 1
     * parent_id : 0
     * title : 医院
     * wap_title : 医院
     * pic : 20190709/3dded8676a3a9f70603d802dd370ece3.jpg
     * slide_pic :
     * info :
     * keywords : 医院
     * description : 医院
     * commission_rate : 100.00
     * sequence : 1
     * is_top : 1
     * is_show : 1
     */

    private int id;
    private int parent_id;
    private String title;
    private String wap_title;
    private String pic;
    private String slide_pic;
    private String info;
    private String keywords;
    private String description;
    private String commission_rate;
    private int sequence;
    private int is_top;
    private int is_show;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWap_title() {
        return wap_title;
    }

    public void setWap_title(String wap_title) {
        this.wap_title = wap_title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSlide_pic() {
        return slide_pic;
    }

    public void setSlide_pic(String slide_pic) {
        this.slide_pic = slide_pic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(String commission_rate) {
        this.commission_rate = commission_rate;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }
}
