package com.share.learn.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.share.learn.R;
import com.share.learn.activity.center.*;
import com.share.learn.activity.home.MsgChooseActivity;
import com.share.learn.activity.login.LoginActivity;
import com.share.learn.bean.DataMapConstants;
import com.share.learn.bean.UserInfo;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.ImageLoaderUtil;
import com.share.learn.view.RoundImageView;

/**
 * 我
 *
 * @author ccs7727@163.com
 * @time 2015年9月28日上午11:44:26
 *
 */
public class PCenterInfoFragment extends BaseFragment implements OnClickListener {

    private TextView name;
    private TextView jonior;
    private RelativeLayout wallet_layout;
    private RelativeLayout order_layout;
    private RelativeLayout caution_layout;
    private RelativeLayout feedback_layout;
    private RelativeLayout custom_layout;
    private RelativeLayout setting_layout;

    private UserInfo mUserInfo;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = BaseApplication.getUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pcenter_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
    }

    private void initTitleView() {
        setTitleText(R.string.me_tab);
        setRightHeadIcon(R.drawable.pc_search_right,new OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 系统消息
                if(BaseApplication.isLogin()){
                    toClassActivity(PCenterInfoFragment.this,MsgChooseActivity.class.getName());
                }else {
                    toClassActivity(PCenterInfoFragment.this,LoginActivity.class.getName());

                }

            }
        });
    }

    private void initView(View v) {
        mHeadImg = (RoundImageView) v.findViewById(R.id.account_head_img);
        pcenter_avatar_layout = (RelativeLayout) v.findViewById(R.id.pcenter_avatar_layout);
        wallet_layout = (RelativeLayout) v.findViewById(R.id.wallet_layout);
        order_layout = (RelativeLayout) v.findViewById(R.id.order_layout);
        caution_layout = (RelativeLayout) v.findViewById(R.id.cation_layout);
        feedback_layout = (RelativeLayout) v.findViewById(R.id.feedBace_layout);
        custom_layout = (RelativeLayout) v.findViewById(R.id.custom_layout);
        setting_layout = (RelativeLayout) v.findViewById(R.id.set_layout);
        name = (TextView)v.findViewById(R.id.name);
        jonior = (TextView)v.findViewById(R.id.jonior);

        pcenter_avatar_layout.setOnClickListener(this);
        wallet_layout.setOnClickListener(this);
        order_layout.setOnClickListener(this);
        caution_layout.setOnClickListener(this);
        feedback_layout.setOnClickListener(this);
        custom_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);

        setData(mUserInfo);

    }

    private void setData(UserInfo userInfo) {
        if(userInfo == null){
            userInfo = mUserInfo = BaseApplication.getUserInfo();
        }
        if(userInfo != null){
            ImageLoader.getInstance().displayImage(userInfo.getHeadImg(), mHeadImg, ImageLoaderUtil.mHallIconLoaderOptions);
            name.setText(userInfo.getNickName());
            jonior.setText(String.format(getResources().getString(R.string.pohone),userInfo.getMobile()));
        }else {
            ImageLoader.getInstance().displayImage("", mHeadImg, ImageLoaderUtil.mHallIconLoaderOptions);
            name.setText("");
            jonior.setText("");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setData(mUserInfo);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.pcenter_avatar_layout:// 头像
                if(BaseApplication.isLogin()){
                    toClassActivity(PCenterInfoFragment.this, PCenterInfoUserActivity.class.getName());
                }else {
                    toClassActivity(PCenterInfoFragment.this,LoginActivity.class.getName());

                }
                break;
            case R.id.wallet_layout:// 钱包
                if(BaseApplication.isLogin()){
                    toClassActivity(PCenterInfoFragment.this, WalletActivity.class.getName());
                }else {
                    toClassActivity(PCenterInfoFragment.this,LoginActivity.class.getName());

                }
                break;
            case R.id.order_layout:// 订单
                if(BaseApplication.isLogin()){
                    toClassActivity(PCenterInfoFragment.this, OrderActivity.class.getName());
                }else {
                    toClassActivity(PCenterInfoFragment.this,LoginActivity.class.getName());

                }
                break;
            case R.id.feedBace_layout:// 反馈
                toClassActivity(PCenterInfoFragment.this, FeedBackActivity.class.getName());
                break;
            case R.id.set_layout:// 设置
                toClassActivity(PCenterInfoFragment.this, SettingActivity.class.getName());
                break;
        }

    }


    /******************************************** 修改头像start *****************************************************/
    private RoundImageView mHeadImg;
    private RelativeLayout pcenter_avatar_layout;
    // popupwidos


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setData(mUserInfo);
    }
}
