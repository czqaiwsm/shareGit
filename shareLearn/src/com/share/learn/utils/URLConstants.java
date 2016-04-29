package com.share.learn.utils;

import com.alipay.sdk.pay.demo.AlipayConstant;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/3/15
 */
public class URLConstants {
//
//    https://svn.duapp.com/appid7ofdiffbr0/learn
//    No1RightAnswer
//    ls/2016

    public static int SCREENH = 0;
    public static int SCREENW = 0;
    public static final String SUCCESS_CODE = "200";
    /********requestCode*************/
    public static final int CHOOSE_CITY_REQUEST_CODE = 11;//城市选择
    public static final int CHOOSE_JOINOR_REQUEST_CODE = 22;//年级选择
    /********requestCode*************/

    public static String CHOOSE = "CHOOSE";

    /********Intent value*************/
    public static final  String COURSEID = "courseId";
    public static final int ORDERLEFT = 0X20;
    public static final int ORDERRIGHT = 0X21;
    /********Intent value*************/

    public static final String BASE_URL = "http://120.25.171.4/learn-interface/interface/api.action";//基础URL
//    public static final String BASE_URL = "http://120.25.171.4:80/learn-teach/teach/api.action";//基础 teacher URL

    public static final String TEACHER_UPLOAD = "http://120.25.171.4:80/learn-teach/teach/upload.action";

    static {
        AlipayConstant.NOTIFY_URL = "http://120.25.171.4/learn-interface/alipayOrderServlet";
        AlipayConstant.SELLER = "1138088601@qq.com";
        AlipayConstant.RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALLE1FNSf0l4IF1c\n" +
                "cBAsu4cKvp4mIzLTsuHtJSHLHwV3fpH1ReYIoNCCqMKx7CWMnFuQEeq2xlop9LHf\n" +
                "4VSrgDN2jYMiqSHTxBHtvh7EyNLK6MRZA4CED5bHpdpVKbXbVSJboAfeNFoL/ao4\n" +
                "5I8Uf5taaw93SyDvXzmmixh7tEkTAgMBAAECgYBVmumuCMhJT0v4SSakqDcYwK1t\n" +
                "IAgk87kqdRwd5Z2ySXmDvXc+Y41Wq+rSaM8TYBUTx5a81c+AJ600bGgD2qhlrs3T\n" +
                "cSVDz/lngoANxEKnCZDp1LVOOIw6KIsBy+6p/nQ9RheVpRZpHo7OiNXn+gpVZyCw\n" +
                "fsjiwYe98FKHh+fUCQJBAOlAZQl/HUMjWFY5XxG+9rfnySXW8KsI2jMQOiCCXsYV\n" +
                "eKJdExRyDqUkiWM5WGxj7L6QcruEnXP17ZAsJAABwfUCQQDENCmVf/SEgzMnCH6z\n" +
                "F5otjEvWdRB9QupR9mzt82QgIwI4JjQ0nFpzwwFE1SjnZ/chpiGgyb1OP6BA8rec\n" +
                "KRHnAkEA0v1O5+I2BA8qzwQifRjyb7SY/UOKfAwL5HjfO5zyQdgWQThUFACAQt3j\n" +
                "8P9kftd8xXxAbGMvUj+5XtquzdrJKQJBAJ88iodX+tZVOP2z6khlnm7bD221QrW2\n" +
                "yj/NFOkmARwH7bQuZW5ReyO6n1wC+BifCzZXA7HgKXDJOMGde6EUv/8CQDrM0ZAK\n" +
                "wFA5DiiQh9LNu7ztSZFE+4dulNXf62aQ/qybxmYsfqM+onwL3pFt34QAuKWmXh5T\n" +
                "0XR2JECVUVlEU9k=";
        AlipayConstant.PARTNER = "2088221254922632";
    }

}
