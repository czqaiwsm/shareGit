package com.share.learn.parse;

import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.MsgDetail;
import com.share.learn.bean.PayCourseInfo;
import com.share.learn.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @desc 验证码
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class PayCourseInfoParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<PayCourseInfo>>() {
            }.getType());
        }
        return result;
    }
}
