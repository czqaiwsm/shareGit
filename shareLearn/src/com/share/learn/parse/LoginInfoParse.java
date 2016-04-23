package com.share.learn.parse;

import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.LoginInfo;
import com.share.learn.bean.UserInfo;
import com.share.learn.bean.VerifyCode;
import com.share.learn.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

/**
 * @desc 登录
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class LoginInfoParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        Log.e(">>>>>","json:"+json);

        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());

        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<LoginInfo>>() {
            }.getType());
        }
        return result;
    }

    public static void main(String[] args){

    }

}
