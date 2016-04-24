package com.share.learn.bean;

/**
 * @author czq
 * @desc 消息列表
 * @date 16/4/12
 */
public class MsgDetail {
    private String id	        ;//消息ID
    private String senderId   	;//当前登录者id
    private String receiverId	;//消息接受者id
    private String teacherName	;//老师姓名
    private String content	    ;//消息内容
    private String createTime	;//创建时间
    private String headImg	    ;//用户头像
    private String studentImg   ;
    private String teacherImg   ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getStudentImg() {
        return studentImg;
    }

    public void setStudentImg(String studentImg) {
        this.studentImg = studentImg;
    }

    public String getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(String teacherImg) {
        this.teacherImg = teacherImg;
    }
}
