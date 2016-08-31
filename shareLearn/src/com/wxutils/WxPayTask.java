package com.wxutils;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import com.share.learn.bean.News;
import com.share.learn.bean.PayInfo;
import com.share.learn.utils.AppLog;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.PayUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/1/14
 */
public class WxPayTask extends AsyncTask<PayInfo,Void,Map<String,String>> {

    private final String TAG = WxPayTask.class.getName();

    private PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(BaseApplication.getInstance(), WxConstants.APP_ID.trim());
    private Map<String,String> resultunifiedorder;

    public WxPayTask(Handler handler){
        req = new PayReq();
        msgApi.registerApp(WxConstants.APP_ID.trim());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        handler.removeMessages(Constant.NET_LOAD);
//        handler.sendEmptyMessage(Constant.NET_LOAD);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                cancelWaitDiag();
//            }
//        },5000);
    }

    private void cancelWaitDiag(){
//        handler.removeMessages(Constant.NET_SUCCESS);
//        handler.sendEmptyMessage(Constant.NET_SUCCESS);
    }

    private static long orderId = 10009;
    @Override
    protected Map<String, String> doInBackground(PayInfo... params) {

        String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
        PayMessage mPayMessage = new PayMessage();
        mPayMessage.setAppid(WxConstants.APP_ID.trim());
        mPayMessage.setMch_id(WxConstants.MCH_ID.trim());
        mPayMessage.setNonce_str(genNonceStr());
        PayInfo payInfo = params[0];

        /******body,Out_trade_no,Total_fee都是请求后台的接口得到的，即在“统一下单”前，要去请求后台接口*******/
        String string = null;
        try {
//            string = new String(params[0].getNewsInfo().getBytes(),"UTF-8");
            string = new String(payInfo.getProductDesc().getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mPayMessage.setBody(string);//订单描述
        AppLog.Logi("body==" + string);
//        mPayMessage.setOut_trade_no(params[0].getNewsId());//订单号，两次下单的订单号不能相同
//        mPayMessage.setTotal_fee((int) (Float.parseFloat(params[0].getNewsTime()) * 100));//价格
        mPayMessage.setOut_trade_no(payInfo.getOrderNum());//订单号，两次下单的订单号不能相同
        mPayMessage.setTotal_fee((int) (Float.parseFloat(payInfo.getPrice()) * 100));//价格
        mPayMessage.setNotify_url(WxConstants.WX_NOTIFY_URL);//支付完成后的回调地址
        /******body,Out_trade_no,Total_fee都是请求后台的接口得到的，即在“统一下单”前，要去请求后台接口*******/

        mPayMessage.setSpbill_create_ip("127.0.0.1");
        mPayMessage.setTrade_type("APP");
        String sign = SignUtil.sign(SignUtil.sortedMapToSortedParams(SignUtil.orderParams(mPayMessage)), WxConstants.API_KEY.trim());
        mPayMessage.setSign(sign);

        String entity = genProductArgs(mPayMessage);
        try {
            entity =  new String(entity.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] buf = Util.httpPost(url, entity);
        String content = new String(buf);
        System.out.println("下单返回==="+content);
        return decodeXml(content);
    }


    @Override
    protected void onPostExecute(Map<String, String> resultunifiedorder) {
        this.resultunifiedorder = resultunifiedorder;
        genPayReq();
        sendPayReq();
        cancelWaitDiag();
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");
            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public Map<String,String> decodeXml(String content) {
        try {

            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

    /**
     * 封装“统一下单”的请求参数
     * @param payMessage
     * @return
     */
    private String genProductArgs(PayMessage payMessage) {
        try {
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid",payMessage.getAppid()));
            packageParams.add(new BasicNameValuePair("body", payMessage.getBody()));
            packageParams.add(new BasicNameValuePair("mch_id",payMessage.getMch_id()));
            packageParams.add(new BasicNameValuePair("nonce_str",payMessage.getNonce_str()));
            packageParams.add(new BasicNameValuePair("notify_url", payMessage.getNotify_url()));
            packageParams.add(new BasicNameValuePair("out_trade_no",payMessage.getOut_trade_no()));
            packageParams.add(new BasicNameValuePair("spbill_create_ip",payMessage.getSpbill_create_ip()));
            packageParams.add(new BasicNameValuePair("total_fee", payMessage.getTotal_fee()+""));
            packageParams.add(new BasicNameValuePair("trade_type", payMessage.getTrade_type()));
            packageParams.add(new BasicNameValuePair("sign", payMessage.getSign()));
            String xmlstring =toXml(packageParams);
            System.out.println("统一下单para:"+xmlstring);
            return xmlstring;
        } catch (Exception e) {
            Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            cancelWaitDiag();
            return null;
        }
    }

    /**
     * 封装“调起支付”的请求参数
     */
    private void genPayReq() {
        req.appId = WxConstants.APP_ID.trim();
        req.partnerId = WxConstants.MCH_ID.trim();
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        SortedMap<String, String> params = new TreeMap<String, String>();
        params.put("appid", req.appId);
        params.put("noncestr", req.nonceStr);
        params.put("package", req.packageValue);
        params.put("partnerid", req.partnerId);
        params.put("prepayid", req.prepayId);
        params.put("timestamp", req.timeStamp);
        String sign = SignUtil.sign(SignUtil.sortedMapToSortedParams(params), WxConstants.API_KEY.trim());
        req.sign = sign;
    }

    private void sendPayReq() {
        msgApi.registerApp(WxConstants.APP_ID.trim());
        msgApi.sendReq(req);
    }


    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

}
