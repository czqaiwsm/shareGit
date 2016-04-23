package com.share.learn.parse;

import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.OrderInfo;
import com.share.learn.bean.OrderListBean;
import com.share.learn.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

/**
 * @desc 验证码
 * @creator caozhiqing
 * @data 2016/3/28
 */
public  class  OrderListBeanParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }


    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<OrderListBean>>() {
            }.getType());
        }
        return result;
    }


    public static void main(String args[]){
        String json = "{\n" +
                "    \"respCode\": 200,\n" +
                "    \"respDesc\": \"\",\n" +
                "    \"respScore\": 0,\n" +
                "    \"respCoin\": 0,\n" +
                "    \"serviceTime\": \"2016-04-08 13:44:36\",\n" +
                "    \"data\": {\n" +
                "        \"pageNo\": 1,\n" +
                "        \"pageSize\": 10,\n" +
                "        \"totalCount\": 16,\n" +
                "        \"elements\": [\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-01-27\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 3,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 101,\n" +
                "                \"payTime\": null,\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 6,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 303,\n" +
                "                \"payTime\": null,\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 10,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-01-29\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 14,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-01-29\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 20,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-02-01\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 26,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-02-01\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 28,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-02-01\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 30,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-02-01\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 31,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"payPrice\": 1,\n" +
                "                \"payTime\": \"2016-02-01\",\n" +
                "                \"teacherName\": \"曹智青\",\n" +
                "                \"orderId\": 32,\n" +
                "                \"courseName\": \"语文\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"totalPages\": 2\n" +
                "    }\n" +
                "}";


    }


}
