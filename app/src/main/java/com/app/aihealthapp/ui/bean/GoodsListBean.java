package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/15 21:46
 * 修改人：Chen
 * 修改时间：2019/8/15 21:46
 */
public class GoodsListBean {

    /**
     * id : 119
     * title :  白帆鱼 500g
     * subtitle :  白帆鱼
     * pic : shop/1/20190709/a6a27c52416d890782b5103896b3a029.jpg
     * markey_price : 0.00
     * price : 0.00
     */

    private int id;
    private String title;
    private String subtitle;
    private String pic;
    private String markey_price;
    private String price;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMarkey_price() {
        return markey_price;
    }

    public void setMarkey_price(String markey_price) {
        this.markey_price = markey_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
