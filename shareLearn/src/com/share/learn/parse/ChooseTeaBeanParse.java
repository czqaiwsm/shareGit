package com.share.learn.parse;

import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.ChooseTeachBean;
import com.share.learn.bean.TeacherInfo;
import com.share.learn.bean.VerifyCode;
import com.share.learn.utils.AppLog;
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
public class ChooseTeaBeanParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {

        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ArrayList<TeacherInfo>>>() {
            }.getType());
        }
        return result;
    }

    public static void main(String args[]){

        String json = "{\"respCode\":200,\"respDesc\":\"\",\"respScore\":0,\"respCoin\":0,\"serviceTime\":\"2016-06-08 15:17:32\",\"data\":[{\"id\":87,\"mobile\":null,\"password\":null,\"inviteCode\":null,\"userLevel\":null,\"nickName\":\"乐老师\",\"gender\":null,\"headImg\":\"http://120.76.47.196:8080/learn-resource/rest/dR\",\"signature\":null,\"college\":null,\"osType\":null,\"xcode\":0,\"hcode\":0,\"acode\":0,\"orderNum\":null,\"commentNum\":null,\"isIdCardVip\":1,\"isSchoolVip\":1,\"introduction\":null,\"experience\":null,\"price\":100,\"alipay\":null,\"status\":null,\"createTime\":null,\"hname\":null,\"aname\":null},{\"id\":81,\"mobile\":null,\"password\":null,\"inviteCode\":null,\"userLevel\":null,\"nickName\":\"杨老师\",\"gender\":null,\"headImg\":\"http://120.76.47.196:8080/learn-resource/rest/ch\",\"signature\":null,\"college\":null,\"osType\":null,\"xcode\":0,\"hcode\":0,\"acode\":0,\"orderNum\":null,\"commentNum\":null,\"isIdCardVip\":1,\"isSchoolVip\":1,\"introduction\":null,\"experience\":null,\"price\":100,\"alipay\":null,\"status\":null,\"createTime\":null,\"hname\":null,\"aname\":null},{\"id\":43,\"mobile\":null,\"password\":null,\"inviteCode\":null,\"userLevel\":null,\"nickName\":\"曹智清\",\"gender\":null,\"headImg\":\"http://120.76.47.196:8080/learn-resource/rest/dd\",\"signature\":null,\"college\":null,\"osType\":null,\"xcode\":0,\"hcode\":0,\"acode\":0,\"orderNum\":null,\"commentNum\":null,\"isIdCardVip\":1,\"isSchoolVip\":1,\"introduction\":null,\"experience\":null,\"price\":100,\"alipay\":null,\"status\":null,\"createTime\":null,\"hname\":null,\"aname\":null},{\"id\":89,\"mobile\":null,\"password\":null,\"inviteCode\":null,\"userLevel\":null,\"nickName\":\"姜老师\",\"gender\":null,\"headImg\":\"http://120.76.47.196:8080/learn-resource/rest/cQ\",\"signature\":null,\"college\":null,\"osType\":null,\"xcode\":0,\"hcode\":0,\"acode\":0,\"orderNum\":null,\"commentNum\":null,\"isIdCardVip\":1,\"isSchoolVip\":1,\"introduction\":null,\"experience\":null,\"price\":100,\"alipay\":null,\"status\":null,\"createTime\":null,\"hname\":null,\"aname\":null},{\"id\":45,\"mobile\":null,\"password\":null,\"inviteCode\":null,\"userLevel\":null,\"nickName\":\"何乐乐\",\"gender\":null,\"headImg\":\"http://120.76.47.196:8080/learn-resource/rest/cP\",\"signature\":null,\"college\":null,\"osType\":null,\"xcode\":0,\"hcode\":0,\"acode\":0,\"orderNum\":null,\"commentNum\":null,\"isIdCardVip\":1,\"isSchoolVip\":1,\"introduction\":null,\"experience\":null,\"price\":100,\"alipay\":null,\"status\":null,\"createTime\":null,\"hname\":null,\"aname\":null}]}";

        JsonParserBase<ChooseTeachBean> jsonParserBase = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ChooseTeachBean>>() {
        }.getType());
        ChooseTeachBean bean = jsonParserBase.getData();


    }

}
