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
     * id : 217
     * title : 锦汇健康手环
     * subtitle : 健康中国行动推广使用手环，健康密钥专用手环
     * pic : http://aijiankang.cacpo.com/upload/pic/shop/1/20190916/d83c8d51cbb44af6ce9d5c2358e9547f.png
     * markey_price : 0.00
     * price : 0.00
     * hek : 1
     * url : http://aijiankang.cacpo.com/goods/item/217
     * price_hek : 0.00+1健康密钥
     */

    private int id;
    private String title;
    private String subtitle;
    private String pic;
    private String markey_price;
    private String price;
    private int hek;
    private String url;
    private String price_hek;

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

    public int getHek() {
        return hek;
    }

    public void setHek(int hek) {
        this.hek = hek;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice_hek() {
        return price_hek;
    }

    public void setPrice_hek(String price_hek) {
        this.price_hek = price_hek;
    }
}
