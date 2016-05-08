package com.volley.req;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.RequestParamSub;
import com.volley.req.parser.JsonParserBase;

public class MyActivity extends Activity {

    public static final String BASEURL = "http://www.bgsz.tv/mobile/index.php";
    public static final String LOGIN = BASEURL + "?act=login&op=index";// 用户登录
    public static final int SUCCESS = 200;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestData();
    }


    private void requestData(){

        RequestParam param = new RequestParamSub(this);
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
//        url.setmGetParam("user", "14");
//        url.setmGetParam("password", "7888");

        /****post 参数为Map****/
//        Map<String, String> postParams = new HashMap<String, String>();
//        postParams.put("username", "1234");
//        postParams.put("password", "7888");
//          param.setmPostarams(new JSONObject(postParams));

        /****post 参数为String****/
//        String json = "{\"password\":\"7888\",\"username\":\"1234\"}";
//        param.setmPostarams(json);

        /****post 参数为实体类****/
//        User user = new User();
//        user.setUserName("haha");
//        user.setPass("333");
//        param.setmPostarams(user);

//        param.setPostRequestMethod();
        param.setmHttpURL(url);
//        param.setmParserClassName(LoginParser.class.getName());
        param.setmParserClassName(new  LoginParser());
        RequestManager.getRequestData(this, createMyReqSuccessListener(), createMyReqErrorListener(), param);
    }


    /**
     * @return
     */
    private Response.Listener<Object> createMyReqSuccessListener() {
        return new Response.Listener<Object>() {
            @Override
            public void onResponse(Object object) {
                JsonParserBase<UserInfo> jsonParserBase = (JsonParserBase<UserInfo>) object;
//                System.out.println(jsonParserBase.getCode());
//                    if (jsonParserBase.getCode() == 200) {
//                        UserInfo userInfo = (UserInfo) jsonParserBase.getData();
//                    }
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(""," data failed to load" + error.getMessage());
            }
        };
    }

}
