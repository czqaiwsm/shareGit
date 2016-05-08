package com.share.teacher.parse;

import com.google.gson.reflect.TypeToken;
import com.share.teacher.bean.ChatMsgEntity;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @desc 教师详情
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class ChatMsgParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            JsonParserBase<ArrayList<ChatMsgEntity>> base = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ArrayList<ChatMsgEntity>>>() {
            }.getType());
            return base;
        }
        return result;
    }

}
