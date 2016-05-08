package com.volley.req.parser;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @desc GSON 解析的封装
 * @creator caozhiqing
 * @data 2015/8/20
 */
public class ParserUtil {

    private static Gson gson = new Gson();

    /**
     * 最基础的json解析
     * @param json 要解析的json字符串
     * @param type 对应的类型
     * @param <T>
     * @return
     */
    private static <T extends Object> T fromJson(String json,Type type){
        if(json == null || "".equalsIgnoreCase(json) || type == null){
            return null;
        }
        if(gson == null){
            gson = new Gson();
        }
        T returnResult = null;
        try {
            returnResult = gson.fromJson(json, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  returnResult;
    }

    /**
     * 专为JsonParserBase设计解析
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends Object> JsonParserBase<T> fromJsonBase(String json,Type type){
        JsonParserBase<T> returnResult = fromJson(json, type);
        return returnResult;
    }

    public static <T extends Object> String toJson(T entity){

        if(entity == null){
            return "";
        }
        String result = "";
        if(entity instanceof String){
            result = (String)entity;
        }else {
            result = gson.toJson(entity);
        }
        return result;
    }


    public static void main(String[] arg){
       /* Area area = new Area();
        area.setmName("II");
        JsonParserBase<Area> jsonParserBase = new JsonParserBase<Area>();
        jsonParserBase.setData(null);
        jsonParserBase.setMessage("hhh");

        System.out.println(gson.toJson(jsonParserBase));
        JsonParserBase<Area> result = fromJsonBase(gson.toJson(jsonParserBase), new TypeToken<JsonParserBase<Area>>() {
        }.getType());
        System.out.println(result.getMessage());
//        System.out.println(result.getData().getmName());
//        System.out.println(result.getData().getmId());
        String json = "{\"code\":10,\"message\":\"dafs\",\"data\":NULL}";
        JsonParserBase<Area> jsonParserBase1 = gson.fromJson(json, new TypeToken<JsonParserBase<Area>>() {
        }.getType());
        System.out.println(jsonParserBase1.getMessage());
        System.out.println(jsonParserBase1.getCode());*/
    }

}
