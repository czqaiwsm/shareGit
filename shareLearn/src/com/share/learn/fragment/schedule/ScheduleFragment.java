package com.share.learn.fragment.schedule;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.share.learn.R;
import com.share.learn.bean.BannerImgInfo;
import com.share.learn.bean.CourseInfo;
import com.share.learn.bean.HomeInfo;
import com.share.learn.bean.HomePagerBanner;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.HomePageBannerParse;
import com.share.learn.parse.ScheduleParse;
import com.share.learn.service.LocationUitl;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.share.learn.view.tab.ScrollingTabContainerView;
import com.share.learn.view.tab.TabsActionBar;
import com.share.learn.view.tab.TabsAdapter;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 课表 界面
 */
public class ScheduleFragment extends BaseFragment implements LocationUitl.LocationListener,RequsetListener{

    private String key;
    private ViewPager mViewPager;
    private ScrollingTabContainerView mTabContainerView;
    private TabsAdapter mTabsAdapter;

    private boolean isPrepare = false;
    private boolean isHiden = true;

    private ArrayList<ArrayList<CourseInfo>> weekCourseList ;

    private String weeks[] = new String[]{"周一","周二","周三","周四","周五","周六","周日"};

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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        LocationUitl.registListener(this);
        initView(view);

        String city = BaseApplication.getInstance().location[0];
        setTitleText( city!=null?city:"");
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.schedule_viewpager);
        mViewPager.setOffscreenPageLimit(0);
        mTabContainerView = (ScrollingTabContainerView) view.findViewById(R.id.schedule_tab_container);
//        onInitTabConfig();
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        isPrepare = true;
        onLoadData();
    }

    private void onInitTabConfig() {
        mTabContainerView.removeAllTabs();
        TabsActionBar tabsActionBar = new TabsActionBar(getActivity(), mTabContainerView);
        mTabsAdapter = new TabsAdapter(getActivity(), mViewPager, tabsActionBar);

        View inflateViwe = null;
        for(int i=0;i<weeks.length;i++){

            ArrayList<CourseInfo> courseInfos = null;
            if(weekCourseList != null && i<weekCourseList.size()){
                courseInfos = weekCourseList.get(i);
            }

            inflateViwe = LayoutInflater.from(getActivity()).inflate(R.layout.schedule, null);
            ((TextView)inflateViwe.findViewById(R.id.scheduleTxt)).setText(String.format(getString(R.string.schedule),weeks[i]));

            Bundle bundle = new Bundle();
            bundle.putInt("position",i);
            if(courseInfos != null){
                bundle.putSerializable("courList",courseInfos);
            }
            mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(inflateViwe)
                        .setmTabbgDrawableId(R.drawable.login_tab), WeeksFragment.class,bundle);

        }

    }


    @Override
    public void locatinNotify(BDLocation location) {

        if (BaseApplication.getInstance().location[0] == null && location.getCity() != null){
            //// TODO: 16/3/22  请求借口
            setTitleText(location.getCity());

        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHiden = hidden;
        if(isPrepare && hidden){
            dismissLoadingDilog();
        }
        onLoadData();

    }

    private void onLoadData(){

        if(isPrepare && !isHiden){
            requestTask();
        }

    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);

        Map postParams = RequestHelp.getBaseParaMap("ScheduleList");
        RequestParam param = new RequestParam();
//        param.setmParserClassName(HomePageBannerParse.class.getName());
        param.setmParserClassName(new ScheduleParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<ArrayList<ArrayList<CourseInfo>>>  jsonParserBase = (JsonParserBase<ArrayList<ArrayList<CourseInfo>>>)obj;
        if(jsonParserBase != null){
            weekCourseList = jsonParserBase.getData();
        }
        onInitTabConfig();
    }

    @Override
    protected void failRespone() {
        super.failRespone();
        onInitTabConfig();
    }

    @Override
    public void onStop() {
        super.onStop();
        LocationUitl.removeListener(this);
    }
}
