package com.share.learn.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
    private String newsId;
    private String newsInfo;
    private String newsImg;
    private String newsTime;
    private String pay_sn;//������

    public News() {

    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsInfo() {
        return this.newsInfo;
    }

    public void setNewsInfo(String newsInfo) {
        this.newsInfo = newsInfo;
    }

    public String getNewsImg() {
        return this.newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public String getNewsTime() {
        return this.newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getPay_sn() {
        return pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn;
    }

    @Override
    public String toString() {
        return "News [newsId=" + this.newsId + ", newsInfo=" + this.newsInfo
                + ", newsImg=" + this.newsImg + ", newsTime=" + this.newsTime
                + "]";
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newsId);
        dest.writeString(this.newsImg);
        dest.writeString(this.newsInfo);
        dest.writeString(this.newsTime);

    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News[] newArray(int size) {
            return new News[size];
        }

        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }
    };

    public News(Parcel in) {
        newsId = in.readString();
        newsImg = in.readString();
        newsInfo = in.readString();
        newsTime = in.readString();
        pay_sn = in.readString();
    }
}
