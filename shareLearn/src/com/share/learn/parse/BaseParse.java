package com.share.learn.parse;

import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.CourseInfo;
import com.share.learn.bean.VerifyCode;
import com.share.learn.utils.URLConstants;
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
        String json = "{\"respCode\":200,\"respDesc\":\"\",\"respScore\":0,\"respCoin\":0,\"serviceTime\":\"2016-04-06 03:17:04\",\"data\":{\"id\":0,\"imgPath\":\"http://120.25.171.4:80/learn-resource/rest/7w\"}}";
        JsonParserBase<HashMap<String,String>> result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<HashMap<String,String>>>() {
        }.getType());

        System.out.println(result.getData());
    }


}
