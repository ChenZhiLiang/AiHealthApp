package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealth
 * @Description：运动步数
 * @Author：Chen
 * @Date：2019/8/15 21:43
 * 修改人：Chen
 * 修改时间：2019/8/15 21:43
 */
public class RunStepsBean {

    /**
     * steps : null
     * distance : null
     * calories : null
     */

    private String steps;
    private String distance;
    private String calories;

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
