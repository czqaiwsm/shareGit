package com.share.learn.parse;

import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.SystemMsg;
import com.share.learn.bean.SystemMsgListBean;
import com.share.learn.bean.TeacherInfo;
import com.share.learn.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.List;

/**
 * @desc 验证码
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class SystemMsgParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<SystemMsgListBean>>() {
            }.getType());
        }
        return result;
    }
}
