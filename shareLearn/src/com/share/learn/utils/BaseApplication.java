package com.share.learn.utils;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.share.learn.bean.UserInfo;
import com.share.learn.service.LocationService;
import com.share.learn.service.LocationUitl;
import com.share.learn.service.WriteLog;

import java.util.Set;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/3/15
 */
public class BaseApplication extends Application {


    private static BaseApplication  instance;

    public String location[] = new String[2];// 城市、城市编码
    public LocationService locationService;
    public BDLocation mapLocation;
    public Vibrator mVibrator;

    private static  UserInfo userInfo;

    private static String userId = "0" ;//用户Id,默认为0；
    private static String accessToken = "";//校验
    public String appVersion = "";//版本
    public String address = "";//
    public LocationUitl locationUitl = new LocationUitl();
    public static String diviceId = "";



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        if(getUserInfo() != null){
            JPushInterface.setAlias(BaseApplication.getInstance(), "s_" + getUserInfo().getId(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    AppLog.Logi("jpush alias:"+s+"   "+(set!=null?set.toString():""));
                }
            });
            ;
        }
        JPushInterface.init(this);     		// 初始化 JPush


        initImageLoader();
        appVersion = AppManager.getVersion(this);
        address = AppManager.getLocalMacAddressFromWifiInfo(this);
        URLConstants.SCREENW = ScreenUtils.getScreenWidth(this);
        URLConstants.SCREENH = ScreenUtils.getScreenHeight(this);
        locationUitl.startLocation();
        location[0] = "合肥市";
        diviceId = ((TelephonyManager) this.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
    }


    public static BaseApplication getInstance(){
        return instance;
    }

    public static UserInfo getUserInfo(){
        if(userInfo == null){
            userInfo = ContextUtils.getObjFromSp(BaseApplication.getInstance(),"userInfo");
        }
        return  userInfo;
    }

    public static void saveUserInfo(UserInfo userInfo){
        BaseApplication.userInfo = userInfo;
        ContextUtils.saveObj2SP(BaseApplication.getInstance(),userInfo,"userInfo");
    }


    public static String getMt_id() {
        if(TextUtils.isEmpty(userId)){
            userId =  BaseApplication.getInstance().getApplicationContext()
                    .getSharedPreferences("userId", Context.MODE_PRIVATE).getString("userId","");
        }
        return userId;
    }
    public static void setMt_id(String userId) {
        BaseApplication.getInstance().getApplicationContext()
                .getSharedPreferences("userId", Context.MODE_PRIVATE).edit().putString("userId",userId).commit();
        BaseApplication.userId = userId;
    }

    public static String getMt_token() {
        if(TextUtils.isEmpty(accessToken)){
            accessToken =  BaseApplication.getInstance().getApplicationContext()
                    .getSharedPreferences("accessToken", Context.MODE_PRIVATE).getString("accessToken","");
        }
        if(TextUtils.isEmpty(accessToken)){
            accessToken = "00000000";
        }
        return accessToken;
    }

    public static void setMt_token(String accessToken) {
        BaseApplication.getInstance().getApplicationContext()
                .getSharedPreferences("accessToken", Context.MODE_PRIVATE).edit().putString("accessToken",accessToken).commit();
        BaseApplication.accessToken = accessToken;
    }
    
    public static boolean isLogin(){
        if(getUserInfo() != null && !TextUtils.isEmpty(userInfo.getId())){
            return true;
        }
        return false;
    }



    private void initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024)).memoryCacheSize(5 * 1024 * 1024).diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .tasksProcessingOrder(QueueProcessingType.LIFO) // Not
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

    }



}
