package com.app.aihealthapp.ui.bean;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/11/23 23:08
 * 修改人：Chen
 * 修改时间：2019/11/23 23:08
 */
public class VersionInfoBean {

    /**
     * appName : 健康密钥
     * versionCode : 1
     * versionName : 1.0
     * apkUrl : https://api.jiankangkey.com/public/download/jiankangkey.apk
     * changeLog : 发现新版本
     * updateTips : 更新提示
     * forceUpgrade : false
     */

    private String appName;
    private String versionCode;
    private String versionName;
    private String apkUrl;
    private String changeLog;
    private String updateTips;
    private boolean forceUpgrade;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }

    public boolean isForceUpgrade() {
        return forceUpgrade;
    }

    public void setForceUpgrade(boolean forceUpgrade) {
        this.forceUpgrade = forceUpgrade;
    }
}
