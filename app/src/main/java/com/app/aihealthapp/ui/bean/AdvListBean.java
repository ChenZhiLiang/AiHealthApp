package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：广告轮播列表
 * @Author：Chen
 * @Date：2019/8/15 21:42
 * 修改人：Chen
 * 修改时间：2019/8/15 21:42
 */
public class AdvListBean {

    /**
     * id : 2
     * title : 幻灯片一
     * url : #
     * pic : 20190701/c16c43f38eb602ab1f83c0db1369ee6e.jpg
     * info : 123
     * type : 2
     * sequence : 1
     * is_show : 0
     */

    private int id;
    private String title;
    private String url;
    private String pic;
    private String info;
    private int type;
    private int sequence;
    private int is_show;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }
}
