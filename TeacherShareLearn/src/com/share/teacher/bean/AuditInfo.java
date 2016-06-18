package com.share.teacher.bean;

import java.io.Serializable;

/**
 * @author czq
 * @desc 学历认证信息
 * @date 16/5/21
 */
public class AuditInfo  implements Serializable{

    private String education;//	学历
    private String college	  ;//学校
    private String profession;//	专业
    private String imgUrl	  ;// 证书图片
    private String faculty	  ;// 院系

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
