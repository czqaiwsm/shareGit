package com.share.teacher.bean;

import java.util.ArrayList;

/**
 * @desc 主页
 * @creator caozhiqing
 * @data 2016/3/30
 */
public class HomeInfo {
    private String servicePhone;
    private String alipay;
    private String realName;
    private ArrayList<HomePagerBanner> topAdList;

    public ArrayList<HomePagerBanner> getTopAdList() {
        return topAdList;
    }

    public void setTopAdList(ArrayList<HomePagerBanner> topAdList) {
        this.topAdList = topAdList;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
