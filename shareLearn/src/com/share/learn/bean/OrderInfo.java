package com.share.learn.bean;

/**
 * @desc 订单列表实体
 * @creator caozhiqing
 * @data 2016/4/1
 */
public class OrderInfo {

    private String orderId;//	订单ID	是	Int
    private String teacherName;//	老师姓名	是	String

    private String courseName	;//课程ID	是	Int	详见课程定义

    private String payTime	;//支付时间	是	Date	yyyy年-MM月-dd日
    private String payPrice	;//实际支付金额	是	Int




    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }
}
