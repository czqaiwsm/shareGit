package com.share.teacher.bean;

/**
 * @author czq
 * @desc 版本
 * @date 16/5/10
 */
public class Version {

   private String versionCode;//	版本号
   private String versionName;//	版本名称
   private String versionText;//	版本描述
   private String downPath	   ;//下载地址
    private String isForce = "-1";

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

    public String getVersionText() {
        return versionText;
    }

    public void setVersionText(String versionText) {
        this.versionText = versionText;
    }

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }
}
