package com.share.teacher.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.share.teacher.R;
import com.share.teacher.activity.center.*;
import com.share.teacher.activity.home.MsgChooseActivity;
import com.share.teacher.activity.teacher.MyAssetActivity;
import com.share.teacher.bean.UserInfo;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.fragment.TeacherHomePageFragment;
import com.share.teacher.utils.BaseApplication;
import com.share.teacher.utils.ImageLoaderUtil;
import com.share.teacher.view.RoundImageView;

/**
 * 我
 *
 * @author ccs7727@163.com
 * @time 2015年9月28日上午11:44:26
 *
 */
public class PCenterInfoFragment extends BaseFragment implements OnClickListener {

    private TextView name;
    private TextView phone;
    private RelativeLayout wallet_layout;
    private RelativeLayout order_layout;
    private RelativeLayout caution_layout;
    private RelativeLayout feedback_layout;
    private RelativeLayout custom_layout;
    private RelativeLayout setting_layout;

    private UserInfo mUserInfo;
    private TextView account_customname;

    private boolean isPrepare = false;
    private boolean isVisible = false;


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
        isPrepare = true;
        onLazyLoad();
    }

    private void initTitleView() {
        setTitleText(R.string.me_tab);
        setRightHeadIcon(R.drawable.pc_search_right,new OnClickListener() {
            @Override
            public void onClick(View v) {
                toClassActivity(PCenterInfoFragment.this,MsgChooseActivity.class.getName());
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
        }else {
            isVisible = false;
        }

        onLazyLoad();
    }

    private void onLazyLoad(){

        if(!isPrepare || !isVisible){
            return;
        }
        if(TeacherHomePageFragment.homeInfo != null){
            account_customname.setText(TeacherHomePageFragment.homeInfo.getServicePhone());
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(TeacherHomePageFragment.homeInfo != null){
                account_customname.setText(TeacherHomePageFragment.homeInfo.getServicePhone());
            }
        }
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
        phone = (TextView)v.findViewById(R.id.jonior);
        account_customname = (TextView)v.findViewById(R.id.account_customname);

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
        if(userInfo != null){
            ImageLoader.getInstance().displayImage(userInfo.getHeadImg(), mHeadImg, ImageLoaderUtil.mHallIconLoaderOptions);
            name.setText(userInfo.getNickName());
            phone.setText(userInfo.getMobile());
//            phone.setText(DataMapConstants.getJoniorMap().get(userInfo.getGrade()));
        }


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.pcenter_avatar_layout:// 头像
                toClassActivity(PCenterInfoFragment.this, PCenterInfoUserActivity.class.getName());
                break;
            case R.id.wallet_layout:// 钱包
                toClassActivity(PCenterInfoFragment.this, WalletActivity.class.getName());
                break;
            case R.id.cation_layout:// 我的评价
                toClassActivity(PCenterInfoFragment.this, MyAssetActivity.class.getName());
                break;
            case R.id.order_layout:// 订单
                toClassActivity(PCenterInfoFragment.this, OrderActivity.class.getName());
                break;
            case R.id.feedBace_layout:// 反馈
                toClassActivity(PCenterInfoFragment.this, FeedBackActivity.class.getName());
                break;
            case R.id.custom_layout:// 设置
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+account_customname.getText().toString()));
                startActivity(intent);
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
