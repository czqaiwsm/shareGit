package com.share.teacher.bean;

import java.io.Serializable;

/**
 * @author 用户信息
 * @desc 请用一句话描述它
 * @date 16/3/29
 */
public class UserInfo implements Serializable {

    private String id;
    private String mobile;
    private String headImg	    ;//用户头像
    private String nickName	    ;//昵称
    private String signature	;//签名
    private String experience	;//荣誉经历
    private String introduction	;//简介
    private String gender	    ;//性别



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
