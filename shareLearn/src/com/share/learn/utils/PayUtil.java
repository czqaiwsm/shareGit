package com.share.learn.utils;

import android.app.Activity;
import android.os.Handler;
import com.alipay.sdk.pay.demo.AlipayUtil;
import com.alipay.sdk.pay.demo.PayCallBack;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.PayInfo;
import com.share.learn.bean.TeacherDetailBean;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.parse.OrderDetailBeanParse;
import com.share.learn.parse.OrderListBeanParse;
import com.toast.ToasetUtil;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.lang.reflect.Type;
import java.util.Map;

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

    public static void walletPay(final Activity mActivity, PayInfo payInfo, final PayCallBack payCallBack){
        final WaitLayer waitLayer = new WaitLayer(mActivity, WaitLayer.DialogType.MODALESS);
        waitLayer.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              waitLayer.dismiss();
            }
        },20000);
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = null;
        RequestParam param = new RequestParam();
                postParams = RequestHelp.getBaseParaMap("BalancePay");
                postParams.put("orderCode",payInfo.getOrderNum());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(mActivity, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object o) {
                waitLayer.dismiss();
                JsonParserBase base = ParserUtil.fromJsonBase(o.toString(), new TypeToken<JsonParserBase>() {
                }.getType());
                if(base.getRespCode().equalsIgnoreCase(URLConstants.SUCCESS_CODE)){
                    SmartToast.showText(mActivity,"支付成功");
                    if(payCallBack != null)
                    payCallBack.paySucc();
                }else {
                    SmartToast.showText(mActivity,base.getRespDesc());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmartToast.showText(mActivity,"支付失败");
                waitLayer.dismiss();
                if(payCallBack != null)
                    payCallBack.payFail();

            }
        }, param);



    }

}
