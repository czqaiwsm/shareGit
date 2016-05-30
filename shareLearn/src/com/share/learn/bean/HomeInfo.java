package com.share.learn.bean;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @desc 主页
 * @creator caozhiqing
 * @data 2016/3/30
 */
public class HomeInfo {

    private String servicePhone;
    private ArrayList<HomePagerBanner> topAdList;
    private String alipay;
    private String realName;
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
