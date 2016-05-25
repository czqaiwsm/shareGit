package com.share.learn.fragment.center;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.share.learn.R;
import com.share.learn.adapter.OrderPageFragmentAdapter;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.fragment.msg.ContactFragment;
import com.share.learn.view.tab.ScrollingTabContainerView;
import com.share.learn.view.tab.TabsActionBar;
import com.share.learn.view.tab.TabsAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment implements View.OnClickListener{

    private ViewPager mViewPager;
    private final int  viewCount = 4;
    private ImageView[] imageViews = new ImageView[viewCount];
    private RelativeLayout[] rLayouts = new RelativeLayout[viewCount];
    private TextView[] textViews = new TextView[viewCount];

    public  static final int PAY_SUCC = 0X12;
    public  static final int CANCEL_ORDER = 0X13;
    public  static final int CONFIRM_ORDER = 0X22;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PAY_SUCC:
                    mViewPager.setCurrentItem(1,false);
                    break;
                case CANCEL_ORDER:
                    mViewPager.setCurrentItem(2,false);
                    break;
                case CONFIRM_ORDER:
                    mViewPager.setCurrentItem(3,false);
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // startReqTask(this);
        // mLoadHandler.sendEmptyMessageDelayed(Constant.NET_SUCCESS, 100);// 停止加载框
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initTitle();
    }

    private void initTitle(){
        setTitleText(R.string.order);
        setLeftHeadIcon(0);
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.order_viewpager);
        rLayouts[0] = (RelativeLayout)view.findViewById(R.id.waitPayRl);
        rLayouts[1] = (RelativeLayout)view.findViewById(R.id.hasPayRl);
        rLayouts[2] = (RelativeLayout)view.findViewById(R.id.hasCancelRl);
        rLayouts[3] = (RelativeLayout)view.findViewById(R.id.donePayRl);

        imageViews[0] = (ImageView)view.findViewById(R.id.waitPayImg);
        imageViews[1] = (ImageView)view.findViewById(R.id.hasPayImg);
        imageViews[2] = (ImageView)view.findViewById(R.id.hasCancelImg);
        imageViews[3] = (ImageView)view.findViewById(R.id.donePayImg);
        textViews[0] = (TextView)view.findViewById(R.id.waitPayTxt);
        textViews[1] = (TextView)view.findViewById(R.id.hasPayTxt);
        textViews[2] = (TextView)view.findViewById(R.id.hasCancelTxt);
        textViews[3] = (TextView)view.findViewById(R.id.donePayTxt);
        mViewPager.setOffscreenPageLimit(0);
        onInitTabConfig();
    }

    private void onInitTabConfig() {
        List<Fragment> orderPayFragments = new ArrayList<Fragment>();
        for(int i=0;i<viewCount;i++){
            rLayouts[i].setOnClickListener(this);
            orderPayFragments.add(new OrderPayFragment(i+1,handler));
        }
        mViewPager.setAdapter(new OrderPageFragmentAdapter(getFragmentManager(), orderPayFragments));
        imageViews[0].setSelected(true);
        textViews[0].setSelected(true);
        mViewPager.setOffscreenPageLimit(viewCount);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for(int i=0;i<viewCount;i++){
                    imageViews[i].setSelected(false);
                    textViews[i].setSelected(false);
                }
                imageViews[position].setSelected(true);
                textViews[position].setSelected(true);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int index = -1;
        switch (v.getId()){
            case R.id.waitPayRl:
                 index = 0;
                break;
            case R.id.hasPayRl:
                index = 1;
                break;
            case R.id.hasCancelRl:
                index = 2;
                break;
            case R.id.donePayRl:
                index = 3;
                break;
            default:break;
        }
        changeStatus(index);
    }

    private void changeStatus(int position){
            if(position == mViewPager.getCurrentItem()){
                return;
            }
        mViewPager.setCurrentItem(position,true);
    }

}
