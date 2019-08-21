package com.app.aihealthapp.ui.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/22 0:57
 * 修改人：Chen
 * 修改时间：2019/8/22 0:57
 */
public class PaymentBean {
    /**
     * sign : 3BA285B59731E02545202ABCC27CB430
     * timestamp : 1517553061
     * noncestr : 5a7405a57504b
     * partnerid : 1495387632
     * park_id : 10012
     * prepayid : wx20180202143101836380a39e0145760552
     * pay_log_id : 143724
     * package : Sign=WXPay
     * appid : wx43a677968f4bad47
     */

    private String sign;
    private String timestamp;
    private String noncestr;
    private String partnerid;
    private String park_id;
    private String prepayid;
    private String pay_log_id;
    @SerializedName("package")
    private String packageX;
    private String appid;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPark_id() {
        return park_id;
    }

    public void setPark_id(String park_id) {
        this.park_id = park_id;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPay_log_id() {
        return pay_log_id;
    }

    public void setPay_log_id(String pay_log_id) {
        this.pay_log_id = pay_log_id;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
