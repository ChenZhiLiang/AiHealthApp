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
     * appid : wxee6db4bf522e8daa
     * noncestr : zrNRie0B7Vm3Hpx4VPlhNAlpSFFva8QN
     * package : Sign=WXPay
     * partnerid : 1549058731
     * prepayid : wx23223847941278a969c49bb61450645600
     * timestamp : 1566571127
     * sign : 201D373A8A94E2C1AE2466A58BD6F017
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
