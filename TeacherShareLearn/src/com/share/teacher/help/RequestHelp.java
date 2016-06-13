package com.share.teacher.help;

import com.share.teacher.utils.BaseApplication;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 一些接口使用的公共方法
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class RequestHelp {

    public static Map getBaseParaMap(String cmd){
        BaseApplication application = BaseApplication.getInstance();
        Map postParams = new HashMap<String, String>();
        postParams.put("cmd", cmd);
//        postParams.put("userId", application.userId);
//        postParams.put("mobile", application.mobile);
        postParams.put("appVersion", application.appVersion);
        postParams.put("clientType", 3);
        postParams.put("accessToken", application.getMt_token());
        postParams.put("deviceId", BaseApplication.diviceId);
        return postParams;
    }

    //1.注册 2.重置密码 3.修改密码 4.手机号绑定
    public static RequestParam getVcodePara(String cmd,String mobile,int sendType){
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);

        Map postParams =getBaseParaMap(cmd) ;
        postParams.put("sendMobile",mobile);
        postParams.put("smsType", sendType);
        RequestParam param = new RequestParam();

        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        return param;
    }



}
