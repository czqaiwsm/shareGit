package com.share.learn.fragment.center;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.*;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.share.learn.R;
import com.share.learn.activity.ChooseCityActivity;
import com.share.learn.activity.center.PCenterModifyInfoActivity;
import com.share.learn.activity.center.ResetPassActivity;
import com.share.learn.activity.center.ServiceProtocolActivity;
import com.share.learn.activity.center.SettingActivity;
import com.share.learn.activity.login.LoginActivity;
import com.share.learn.activity.teacher.ChooseJoinorActivity;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.utils.*;
import com.share.learn.view.RoundImageView;
import com.share.learn.view.UpdateAvatarPopupWindow;
import com.volley.req.UserInfo;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.RequestParamSub;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 设置界面
 *
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class SettingFragmentUser extends BaseFragment implements OnClickListener {



    private RelativeLayout changePass_layout;
    private RelativeLayout service_pro_layout;
    private RelativeLayout clear_layout;
    private RelativeLayout about_layout;

    private TextView exitLogin;
    private TextView clearSize;


    // 需要清除缓存的文件
    private File file1;// 内部缓存
    private File file2;// 外部缓存

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        file1 = getActivity().getCacheDir();// 内部缓存
        file2 = getActivity().getExternalCacheDir();// 外部缓存
        initTitleView();
        initView(view);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.settting);
    }

    private void initView(View v) {
        changePass_layout = (RelativeLayout) v.findViewById(R.id.changePass_layout);
        service_pro_layout = (RelativeLayout) v.findViewById(R.id.service_pro_layout);
        clear_layout = (RelativeLayout) v.findViewById(R.id.clear_layout);
        about_layout = (RelativeLayout) v.findViewById(R.id.about_layout);
        exitLogin =(TextView)v.findViewById(R.id.exitLogin);
        clearSize =(TextView)v.findViewById(R.id.clearSize);
        exitLogin.setOnClickListener(this);
        changePass_layout.setOnClickListener(this);
        service_pro_layout.setOnClickListener(this);
        clear_layout.setOnClickListener(this);
        about_layout.setOnClickListener(this);

        setCache();
    }

    String cache_size = "";

    /**
     * 获取缓存
     */
    private void setCache() {
        try {
            long a = DataCleanManager.getFolderSize(file1);
            long b = DataCleanManager.getFolderSize(file2);
//			long d = DataCleanManager.getFolderSize(file3);
            cache_size = DataCleanManager.getFormatSize(a + b );
            clearSize.setText(cache_size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除缓存
     */
    private void clearCache(){
        file1 = getActivity().getCacheDir();// 内部缓存
        file2 = getActivity().getExternalCacheDir();// 外部缓存
//			file3 = new File("/data/data/" + getActivity().getPackageName() + "/databases");// 数据库缓存
        List<String> fileList = new ArrayList<String>();
        if (file1 != null) {
            fileList.add(getActivity().getCacheDir().getAbsolutePath());
        }
        if (file2 != null) {
            fileList.add(getActivity().getExternalCacheDir().getAbsolutePath());
        }
//			if (file3 != null) {
//				fileList.add(new File("/data/data/" + getActivity().getPackageName() + "/databases").getAbsolutePath());
//			}
        DataCleanManager.cleanApplicationData(fileList);
//			Constant.CLEAR_SEARCH = true;
        SmartToast.showText(mActivity, "成功清理缓存" + cache_size);
        setCache();
    }

    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.changePass_layout:// 修改密码
                toClassActivity(SettingFragmentUser.this, ResetPassActivity.class.getName());
                break;
            case R.id.clear_layout:// 缓存
                clearCache();
            break;
            case R.id.about_layout://

            break;
            case R.id.service_pro_layout:
                toClassActivity(SettingFragmentUser.this, ServiceProtocolActivity.class.getName());
                break;
            case R.id.exitLogin:// 退出登录
//                AppManager.getAppManager().AppExit(BaseApplication.getInstance());
                AppManager.getAppManager().finishAllActivity();
                SettingActivity.exit = true;
            break;
        }

    }



    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        // TODO Auto-generated method stub
        RequestParam param = new RequestParamSub(getActivity());
        HttpURL url = new HttpURL();
//        url.setmBaseUrl(Constant.GET_PcenterInfo);
//        param.setmHttpURL(url);
//        param.setmParserClassName(PcenterInfoParser.class.getName());
        RequestManager.getRequestData(getActivity(), createMyReqSuccessListener(), createMyReqErrorListener(), param);
    }

    private Listener<Object> createMyReqSuccessListener() {
        return new Listener<Object>() {
            @Override
            public void onResponse(Object object) {
//                ResultInfo info = (ResultInfo) object;
//                if (getActivity() != null && !isDetached()) {
//                    if (info.getmCode() == ResultCode.CODE_0) {
//                        mUserInfo = (UserInfo) info.getObject();
//                        setData(mUserInfo);
//                    } else {
//                        SmartToast.showText(info.getmData());
//                    }
//                }
            }
        };
    }

    protected ErrorListener createMyReqErrorListener() {
        return new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.Loge(" data failed to load" + error.getMessage());
                if (!isDetached()) {
//                    showUIDialogState(false);
                }
            }
        };
    }

}
