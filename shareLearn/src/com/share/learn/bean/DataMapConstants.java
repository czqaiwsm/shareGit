package com.share.learn.bean;

import android.content.Context;
import android.text.TextUtils;
import com.share.learn.R;
import com.share.learn.utils.BaseApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc
 * @creator caozhiqing
 * @data 2016/4/1
 */
public class DataMapConstants {

    private static Map<String,String> coursesMap ;
    private static Map<String,String> joniorMap;
    private static Map<String,String> gender;
    private static Map<String,String> degree;//学历

    /**
     * 获取课程
     * @returnc
     */
    public static Map<String,String> getCourse(){
        if(coursesMap == null || coursesMap.size()==0){
            coursesMap = new HashMap<String, String>();

            Context context = BaseApplication.getInstance().getApplicationContext();
            String[] ids = context.getResources().getStringArray(R.array.course_id);
            String[] values = context.getResources().getStringArray(R.array.course);
            for(int i=0;i<ids.length;i++){
                coursesMap.put(ids[i],values[i]);
            }
        }
        return coursesMap;
    }

    /**
     * 年级
     * @return
     */
    public static Map<String,String> getJoniorMap(){
        if(joniorMap == null){
            joniorMap = new HashMap<String, String>();
            Context context = BaseApplication.getInstance().getApplicationContext();
            String[] joniorIds = context.getResources().getStringArray(R.array.jonior_id);
            String[] joniorS = context.getResources().getStringArray(R.array.jonior);
            for(int i=0;i<joniorIds.length;i++){
                joniorMap.put(joniorIds[i],joniorS[i]);
            }
        }
        return joniorMap;

    }

    public static Map<String,String> getGender(){
        if(gender == null){
            gender = new HashMap<String, String>();
            gender.put("1","男");
            gender.put("2","nv");
        }
        return gender;
    }

    public static Map<String, String> getDegree(){
        if(degree == null){
            degree = new HashMap<String, String>();
            Context context = BaseApplication.getInstance().getApplicationContext();
            String[] ids = context.getResources().getStringArray(R.array.degree_id);
            String[] values = context.getResources().getStringArray(R.array.degree);
            for(int i=0;i<ids.length;i++){
                degree.put(ids[i],values[i]);
            }
        }
        return degree;

    }

}
