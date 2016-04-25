package com.share.learn.utils;

import android.app.Activity;
import com.alipay.sdk.pay.demo.AlipayUtil;
import com.alipay.sdk.pay.demo.PayCallBack;
import com.share.learn.bean.PayInfo;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/4/22L
 */
public class PayUtil {


    public static void alipay(Activity mActivity,PayInfo payInfo,PayCallBack payCallBack){

        AlipayUtil alipayUtil = new AlipayUtil(mActivity,payInfo.getOrderNum(),payInfo.getContent(),payInfo.getProductDesc(),payInfo.getPrice(),payCallBack);
        alipayUtil.alipay();
    }

    public static void walletPay(){

    }


}
