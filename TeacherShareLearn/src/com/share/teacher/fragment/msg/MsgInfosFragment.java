package com.share.teacher.fragment.msg;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.share.teacher.R;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.view.tab.ScrollingTabContainerView;
import com.share.teacher.view.tab.TabsActionBar;
import com.share.teacher.view.tab.TabsAdapter;

public class MsgInfosFragment extends BaseFragment {

    private String key;
    private ViewPager mViewPager;
    private ScrollingTabContainerView mTabContainerView;
    private TabsAdapter mTabsAdapter;


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
        View view = inflater.inflate(R.layout.fragment_msg_infos, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        setTitleText(R.string.schedule);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initView(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.order_viewpager);
        mViewPager.setOffscreenPageLimit(0);
        mTabContainerView = (ScrollingTabContainerView) view.findViewById(R.id.tab_container);
        onInitTabConfig();
    }

    private void onInitTabConfig() {
        TabsActionBar tabsActionBar = new TabsActionBar(getActivity(), mTabContainerView);
        mTabsAdapter = new TabsAdapter(getActivity(), mViewPager, tabsActionBar);
        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.msg, null))
                .setmTabbgDrawableId(R.drawable.login_tab), MsgFragment.class, null);
//        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.wait_buy, null))
//                .setmTabbgDrawableId(R.drawable.login_tab), WaitPayFragment.class, null);
//        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.wait_pay, null))
//                .setmTabbgDrawableId(R.drawable.login_tab), WaitBuyFragment.class, null);
        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.contact, null))
                .setmTabbgDrawableId(R.drawable.login_tab), ContactFragment.class, null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


}
