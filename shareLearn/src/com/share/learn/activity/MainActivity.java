package com.share.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.download.base.utils.ScreenUtils;
import com.download.update.UpdateMgr;
import com.google.gson.reflect.TypeToken;
import com.share.learn.R;
import com.share.learn.bean.LoginInfo;
import com.share.learn.bean.VersionBean;
import com.share.learn.fragment.*;
import com.share.learn.fragment.center.PCenterInfoFragment;
import com.share.learn.fragment.center.PCenterInfoFragmentUser;
import com.share.learn.fragment.center.UnLoginPCenterFragment;
import com.share.learn.fragment.msg.MsgInfosFragment;
import com.share.learn.fragment.schedule.ScheduleFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.LoginInfoParse;
import com.share.learn.service.LocationUitl;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.SmartToast;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.util.Map;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */

    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    private TextView unreadAddressLable;

    private Button[] mTabs;
    private Fragment[] fragments;

    private HomePageFragment homePageFragment;
    private MsgInfosFragment msgInfosFragment;
    private ScheduleFragment scheduleFragment;
    private PCenterInfoFragment pCenterFragment;
    private UnLoginPCenterFragment unLoginPCenterFragment;

    private final int VIEW_COUNT = 5;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;
    Fragment currentFragment = null;
    int i=0;
    private WaitLayer loadingDilog;//默认是与界面绑定对话框

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtils.getScreenSize(this);
        UpdateMgr.getInstance(this).checkUpdateInfo(null, false);
        setContentView(R.layout.main_activity);
        requestData(0);
        fragments = new Fragment[VIEW_COUNT];
        fragments[0] = homePageFragment =new HomePageFragment();
        fragments[1] = msgInfosFragment = new MsgInfosFragment();
        fragments[2] = scheduleFragment = new ScheduleFragment();
        fragments[3] = pCenterFragment = new PCenterInfoFragment();
        fragments[4] = unLoginPCenterFragment = new UnLoginPCenterFragment();


        initView();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i=0;i<fragments.length;i++){
            transaction.add(R.id.fragment_container,fragments[i]).hide(fragments[i]);

        }
        currentFragment = fragments[0];
        transaction.show(currentFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        mTabs = new Button[VIEW_COUNT-1];
        mTabs[0] = (Button) findViewById(R.id.btn_conversation);
        mTabs[1] = (Button) findViewById(R.id.btn_address_list);
        mTabs[2] = (Button) findViewById(R.id.btn_setting);
        mTabs[3] = (Button) findViewById(R.id.btn_center);
        // 把第一个tab设为选中状态
        currentTabIndex = 0;
        mTabs[currentTabIndex].setSelected(true);

        for(int i=0;i<mTabs.length;i++){
            mTabs[i].setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View v) {
        int index = 0;
        switch (v.getId()){
            case R.id.btn_conversation:
                currentFragment = fragments[0];
                index = 0;
                break;
            case R.id.btn_address_list:
                index = 1;
                currentFragment = showFragment(index);
                break;
            case R.id.btn_setting:
                index = 2;
                currentFragment = showFragment(index);
                break;
            case R.id.btn_center:
                index = 3;
                currentFragment = showSetting();
                break;
        }

        if(currentTabIndex != index ){
            for(Button button:mTabs){
                button.setSelected(false);
            }
            mTabs[index].setSelected(true);
            hidShow(currentFragment);
            currentTabIndex = index;
        }
    }

    private void hidShow(Fragment newf){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i=0;i<fragments.length;i++){
            transaction.hide(fragments[i]);
        }
        transaction.show(newf);
        transaction.commitAllowingStateLoss();
    }



    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("Vervion") ;
        RequestParam param = new RequestParam();
//        param.setmParserClassName(LoginInfoParse.class.getName());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
//        RequestManager.getRequestData(this, createReqSuccessListener(requestType),null, param);

    }

    protected Response.Listener<Object> createReqSuccessListener(final int requestType) {
        return new Response.Listener<Object>() {
            @Override
            public void onResponse(Object object) {
                if (object != null){

                    JsonParserBase result = ParserUtil.fromJsonBase(object.toString(), new TypeToken<JsonParserBase>() {
                    }.getType());

                    if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
                        result =   ParserUtil.fromJsonBase(object.toString(), new TypeToken<JsonParserBase<VersionBean>>() {
                        }.getType());
                        VersionBean versionBean = (VersionBean)result.getData();

                    }
                }
            }
        };
    }



    private Fragment showFragment(int index){

        if(BaseApplication.isLogin()){
            return fragments[index];
        }else {
            return fragments[fragments.length-1];
        }
    }

    /**
     * @return
     */
    private Fragment showSetting(){
            return pCenterFragment;
    }
    private long firstTime = 0;
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于2秒，则不退出
                SmartToast.makeText(this,
                        getString(R.string.exit), Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                System.gc();
                System.exit(0);// 否则退出程序
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            Button button = mTabs[currentTabIndex];
            currentTabIndex = -1;
            button.performClick();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().locationUitl.stopLocation();
    }
}
