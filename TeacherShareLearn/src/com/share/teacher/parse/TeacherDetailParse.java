package com.share.teacher.parse;

import com.google.gson.reflect.TypeToken;
import com.share.teacher.bean.TeacherDetailBean;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

/**
 * @desc 教师详情
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class TeacherDetailParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<TeacherDetailBean>>() {
            }.getType());
        }
        return result;
    }
}
