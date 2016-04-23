package com.share.learn.parse;

import com.google.gson.reflect.TypeToken;
import com.share.learn.bean.CourseInfo;
import com.share.learn.bean.TeacherDetailBean;
import com.share.learn.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 教师详情
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class ScheduleParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            JsonParserBase<ArrayList<ArrayList<CourseInfo>>> base = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ArrayList<ArrayList<CourseInfo>>>>() {
            }.getType());
            return base;
        }
        return result;
    }

    public static void  main(String args[]){
        String json = "{\"respCode\":200,\"respDesc\":\"\",\"respScore\":0,\"respCoin\":0,\"serviceTime\":\"2016-04-23 16:22:59\",\"data\":[[{\"teacherName\":\"13637055938\",\"schooltime\":\"AM(10:00--12:00)\",\"courseName\":\"语文\"}],[{\"teacherName\":\"13637055938\",\"schooltime\":\"PM(12:00--04:39)\",\"courseName\":\"语文\"}],[{\"teacherName\":\"13637055938\",\"schooltime\":\"PM(01:00--04:39)\",\"courseName\":\"数学\"},{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"语文\"}],[{\"teacherName\":\"13637055938\",\"schooltime\":\"PM(01:00--04:40)\",\"courseName\":\"英语\"},{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"语文\"},{\"teacherName\":\"杨飞\",\"schooltime\":\"PM(02:02--02:02)\",\"courseName\":\"语文\"},{\"teacherName\":\"杨飞\",\"schooltime\":\"PM(02:03--02:03)\",\"courseName\":\"数学\"},{\"teacherName\":\"杨飞\",\"schooltime\":\"PM(02:04--02:04)\",\"courseName\":\"英语\"}],[{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"语文\"}],[{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"语文\"},{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"数学\"},{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"英语\"},{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"英语\"}],[{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"语文\"},{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"数学\"},{\"teacherName\":\"刘乐廷\",\"schooltime\":\"AM(09:00--11:00)\",\"courseName\":\"英语\"}]]}";

        JsonParserBase<ArrayList<ArrayList<CourseInfo>>> base =ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ArrayList<ArrayList<CourseInfo>>>>() {
        }.getType());

        ArrayList<ArrayList<CourseInfo>> arrayLists = base.getData();

        System.out.println(arrayLists.get(0).get(0).getCourseName());
    }

}
