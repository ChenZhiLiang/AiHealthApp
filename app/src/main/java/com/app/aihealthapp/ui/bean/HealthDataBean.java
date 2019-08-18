package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/15 21:43
 * 修改人：Chen
 * 修改时间：2019/8/15 21:43
 */
public class HealthDataBean {

    /**
     * blood_pressure : null
     * blood_oxygen : null
     * heart_rate : null
     */

    private String blood_pressure;
    private String blood_oxygen;
    private String heart_rate;

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(String blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public String getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(String blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }
}
