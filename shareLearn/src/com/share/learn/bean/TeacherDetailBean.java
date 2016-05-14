package com.share.learn.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 老师详情
 * @date 16/4/7
 */
public class TeacherDetailBean {

    private ArrayList<CourseInfo> courseList;

    private TeacherDetailInfo teacherInfo ;

    private String isCheck;//1-取消，2-关注


    public ArrayList<CourseInfo> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<CourseInfo> courseList) {
        this.courseList = courseList;
    }

    public TeacherDetailInfo getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherDetailInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }
}
