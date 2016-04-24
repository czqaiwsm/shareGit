
package com.share.learn.bean;

import java.util.ArrayList;

public class ChatMstList {

    private String teacherName	;//老师姓名
    private ArrayList<ChatMsgEntity> msgList;//	消息列表对象

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public ArrayList<ChatMsgEntity> getMsgList() {
        return msgList;
    }

    public void setMsgList(ArrayList<ChatMsgEntity> msgList) {
        this.msgList = msgList;
    }
}
