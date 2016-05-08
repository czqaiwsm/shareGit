package com.share.teacher.parse;

import com.google.gson.reflect.TypeToken;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/3/29
 */
public class BaseParse  implements IParser {

    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {

        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        return result;
    }

    public static void main(String[] arg){
        String json = "{\"respCode\":100,\"respDesc\":\"请求失败，请稍后再试\",\"respScore\":0,\"respCoin\":0,\"serviceTime\":\"2016-05-07 12:30:19\",\"data\":{\"value\":\"\"}}";
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());

        System.out.println(result.getData());
    }


}
