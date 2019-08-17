package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/15 21:44
 * 修改人：Chen
 * 修改时间：2019/8/15 21:44
 */
public class ArticleCateListBean {

    /**
     * id : 10
     * parent_id : 0
     * title : 疑难杂症
     * wap_title : 疑难杂症
     * info : 疑难杂症
     * keywords :
     * description :
     * sequence : 1
     * is_top : 1
     * is_show : 1
     * pic : 20190814/29b6a2416d19dcd888a3c3d6306235ef.jpg
     */

    private int id;
    private int parent_id;
    private String title;
    private String wap_title;
    private String info;
    private String keywords;
    private String description;
    private int sequence;
    private int is_top;
    private int is_show;
    private String pic;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
