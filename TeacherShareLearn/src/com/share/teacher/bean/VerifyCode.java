package com.share.teacher.bean;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class VerifyCode {


    private String  smsCode;//短信验证码
    private String  sendId;//唯一串
    private String  startPosition;
    private String  smsLength;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getSmsLength() {
        return smsLength;
    }

    public void setSmsLength(String smsLength) {
        this.smsLength = smsLength;
    }
}
