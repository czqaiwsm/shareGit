package com.share.learn.bean;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/5/8
 */
public class VersionBean {

    private String versionCode;//	版本号
    private String versionName;//	版本名称
    private String versionText;//	版本描述
    private String downPath	   ;//下载地址

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
}
