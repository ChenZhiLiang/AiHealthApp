package com.app.aihealthapp.ui.bean;

import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/15 21:37
 * 修改人：Chen
 * 修改时间：2019/8/15 21:37
 */
public class HomeBean {

    int is_bind_bracelet;
    List<AdvListBean> adv_list;
    RunStepsBean run_steps;
    HealthDataBean health_data;
    int health_report;
    List<ArticleCateListBean>article_cate_list;
    List<GoodsCateListBean>goods_cate_list;
    List<ShopListBean>shop_list;

    List<GoodsListBean>goods_list;

    public int getIs_bind_bracelet() {
        return is_bind_bracelet;
    }

    public void setIs_bind_bracelet(int is_bind_bracelet) {
        this.is_bind_bracelet = is_bind_bracelet;
    }

    public List<AdvListBean> getAdv_list() {
        return adv_list;
    }

    public void setAdv_list(List<AdvListBean> adv_list) {
        this.adv_list = adv_list;
    }

    public RunStepsBean getRun_steps() {
        return run_steps;
    }

    public void setRun_steps(RunStepsBean run_steps) {
        this.run_steps = run_steps;
    }

    public HealthDataBean getHealth_data() {
        return health_data;
    }

    public void setHealth_data(HealthDataBean health_data) {
        this.health_data = health_data;
    }

    public int getHealth_report() {
        return health_report;
    }

    public void setHealth_report(int health_report) {
        this.health_report = health_report;
    }

    public List<ArticleCateListBean> getArticle_cate_list() {
        return article_cate_list;
    }

    public void setArticle_cate_list(List<ArticleCateListBean> article_cate_list) {
        this.article_cate_list = article_cate_list;
    }

    public List<GoodsCateListBean> getGoods_cate_list() {
        return goods_cate_list;
    }

    public void setGoods_cate_list(List<GoodsCateListBean> goods_cate_list) {
        this.goods_cate_list = goods_cate_list;
    }

    public List<ShopListBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopListBean> shop_list) {
        this.shop_list = shop_list;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }
}
